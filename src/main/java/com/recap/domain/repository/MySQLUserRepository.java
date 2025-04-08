package com.recap.domain.repository;

import com.recap.domain.entity.MySQLUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySQLUserRepository extends JpaRepository<MySQLUser, Integer> {
}
