package com.recap.domain.repository;

import com.recap.domain.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
}
