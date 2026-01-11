package com.github.krishantx.Cloud_Security.repo;

import org.springframework.stereotype.Repository;
import com.github.krishantx.Cloud_Security.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String username);
}
