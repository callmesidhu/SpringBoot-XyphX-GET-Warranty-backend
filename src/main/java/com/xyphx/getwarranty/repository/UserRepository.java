package com.xyphx.getwarranty.repository;

import com.xyphx.getwarranty.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
}
