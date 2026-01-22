package com.backend.springboot.tickets.service.imp;

import com.backend.springboot.tickets.dto.input.DeveloperDtoRequest;
import com.backend.springboot.tickets.dto.output.DeveloperDtoResponse;
import com.backend.springboot.tickets.entity.Developer;
import com.backend.springboot.tickets.entity.Rol;
import com.backend.springboot.tickets.exception.DuplicateEmailDeveloperException;
import com.backend.springboot.tickets.exception.ResourceNotFoundException;
import com.backend.springboot.tickets.repository.DeveloperRepository;
import com.backend.springboot.tickets.repository.RoleRepository;
import com.backend.springboot.tickets.service.IDeveloperService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService implements IDeveloperService {

    private final Logger LOGGER = LoggerFactory.getLogger(DeveloperService.class);
    private final DeveloperRepository developerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public DeveloperService(DeveloperRepository developerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.developerRepository = developerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DeveloperDtoResponse> listDevelopers() {
        return developerRepository.findAll()
                .stream()
                .map(developer -> modelMapper.map(developer, DeveloperDtoResponse.class))
                .toList();
    }


    @Override
    @Transactional
    public DeveloperDtoResponse createDeveloper(DeveloperDtoRequest developerDtoRequest) {

        developerRepository.findByEmail(developerDtoRequest.getEmail())
                .ifPresent(dev -> {
                    LOGGER.error("Duplicate email found: {}", developerDtoRequest.getEmail());
                    throw new DuplicateEmailDeveloperException("A developer with email "
                            + developerDtoRequest.getEmail() + " already exists.");
                });

        Developer developerEntity = modelMapper.map(developerDtoRequest, Developer.class);
        LOGGER.info("Developer entity --> {}", developerEntity);

        developerEntity.setRoles(getRoles(developerDtoRequest));
        developerEntity.setPassword(passwordEncoder.encode(developerDtoRequest.getPassword()));
        developerEntity.setEnabled(developerDtoRequest.getEnabled() == null
                || developerDtoRequest.getEnabled());
        Developer newDeveloper = developerRepository.save(developerEntity);
        DeveloperDtoResponse developerDtoResponse = modelMapper.map(newDeveloper, DeveloperDtoResponse.class);
        LOGGER.info("Developer DTO out --> {}", developerDtoResponse);

        return developerDtoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperDtoResponse getDeveloperById(Long id) {
        return developerRepository.findById(id).map(developer ->
                        modelMapper.map(developer, DeveloperDtoResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found with id " + id));
    }

    @Override
    @Transactional
    public DeveloperDtoResponse updateDeveloper(DeveloperDtoRequest developer, Long id) {

        return developerRepository.findById(id)
                .map(existingDeveloper -> {
                    existingDeveloper.setId(id);
                    existingDeveloper.setName(developer.getName());
                    existingDeveloper.setUsername(developer.getUsername());
                    existingDeveloper.setPassword(developer.getPassword());
                    existingDeveloper.setEmail(developer.getEmail());
                    existingDeveloper.setRoles(getRoles(developer));
                    existingDeveloper.setEnabled(developer.getEnabled());

                    return modelMapper.map(developerRepository.save(existingDeveloper)
                            , DeveloperDtoResponse.class);
                }).orElseThrow(() -> new ResourceNotFoundException("Developer not found with id " + id));

    }

    private List<Rol> getRoles(DeveloperDtoRequest developer) {

        List<Rol> roles = new ArrayList<>();
        roleRepository.findByName("ROLE_USER").ifPresent(roles::add);
        if (developer.getAdmin()) roleRepository.findByName("ROLE_ADMIN").ifPresent(roles::add);
        return roles;
    }

    @Override
    @Transactional
    public void deleteDeveloper(Long id) {

        if (developerRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Developer not found with id " + id);
        }

        developerRepository.deleteById(id);

    }
}
