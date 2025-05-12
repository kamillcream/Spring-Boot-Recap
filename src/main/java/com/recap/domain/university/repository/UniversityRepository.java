package com.recap.domain.university.repository;

import com.recap.domain.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findUniversityByCode(String code);
}
