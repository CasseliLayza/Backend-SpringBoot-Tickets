package com.backend.springboot.tickets.controller;

import com.backend.springboot.tickets.dto.input.DeveloperDtoRequest;
import com.backend.springboot.tickets.dto.output.DeveloperDtoResponse;
import com.backend.springboot.tickets.service.IDeveloperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
@CrossOrigin(originPatterns = "*")
public class DeveloperController {

    private final IDeveloperService developerService;

    public DeveloperController(IDeveloperService developerService) {
        this.developerService = developerService;
    }


    @GetMapping
    public ResponseEntity<List<DeveloperDtoResponse>> listDevelopers() {
        return new ResponseEntity<>(developerService.listDevelopers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeveloperDtoResponse> createDeveloper(@RequestBody @Valid DeveloperDtoRequest developer) {
        return new ResponseEntity<>(developerService.createDeveloper(developer), HttpStatus.CREATED);

    }

    @PostMapping("/register")
    public ResponseEntity<DeveloperDtoResponse> createRegister(@RequestBody @Valid DeveloperDtoRequest developer) {
        developer.setAdmin(false);
        return createDeveloper(developer);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDtoResponse> getDeveloperById(@PathVariable Long id) {
        return new ResponseEntity<>(developerService.getDeveloperById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDtoResponse> updateDeveloper(@RequestBody DeveloperDtoRequest developer, @PathVariable Long id) {
        return new ResponseEntity<>(developerService.updateDeveloper(developer, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
    }


}
