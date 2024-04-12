package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User findByUserName(String userName);
    User findByEmail(String email);
    void deleteByUserName(String userName);
    User findByUserId(Long userId);

}
