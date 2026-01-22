package com.backend.springboot.tickets.service;

import com.backend.springboot.tickets.dto.input.DeveloperDtoRequest;
import com.backend.springboot.tickets.dto.output.DeveloperDtoResponse;

import java.util.List;

public interface IDeveloperService {

    List<DeveloperDtoResponse> listDevelopers();
    DeveloperDtoResponse createDeveloper(DeveloperDtoRequest developer);
    DeveloperDtoResponse getDeveloperById(Long id);
    DeveloperDtoResponse updateDeveloper(DeveloperDtoRequest developer, Long id);
    void deleteDeveloper(Long id);


}
