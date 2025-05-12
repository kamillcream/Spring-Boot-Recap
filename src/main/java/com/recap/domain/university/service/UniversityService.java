package com.recap.domain.university.service;

import com.recap.domain.university.dto.response.UniversityInfoResponse;
import com.recap.domain.university.entity.University;
import com.recap.domain.university.mapper.UniversityMapper;
import com.recap.domain.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;

    public List<UniversityInfoResponse> fetchAllUniversity(){
        List<UniversityInfoResponse> infoResponses = universityMapper.findUniversityAndConvert();
        infoResponses.stream().map(UniversityInfoResponse::code).toList();

        return universityMapper.findUniversityAndConvert();
    }

    public University findUniversityByCode(String univCode){
        return universityRepository.findUniversityByCode(univCode);
    }
}
