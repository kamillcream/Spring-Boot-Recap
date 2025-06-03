package com.recap.domain.university.controller;

import com.recap.domain.university.dto.response.UniversityInfoResponse;
import com.recap.domain.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/universities")
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<List<UniversityInfoResponse>> getUniversityDtoList(){
        return ResponseEntity.ok(universityService.fetchAllUniversity());
    }

}
// dummy change
