package com.backend.springboot.tickets.service.imp;

import com.backend.springboot.tickets.entity.Developer;
import com.backend.springboot.tickets.repository.DeveloperRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaDeveloperDetailService implements UserDetailsService {

    private final DeveloperRepository developerRepository;

    public JpaDeveloperDetailService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Developer> developerOptional = developerRepository.findByUsername(username);

        if (developerOptional.isEmpty())
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);

        Developer developer = developerOptional.get();
        List<GrantedAuthority> authorities = developer.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getName()))
                .collect(Collectors.toList());

        return new User(
                developer.getUsername(),
                developer.getPassword(),
                developer.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
