package com.recap.domain.university.mapper;

import com.recap.domain.university.dto.response.UniversityInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UniversityMapper {
    List<UniversityInfoResponse> findUniversityAndConvert();
}
// dummy change
// dummy change
