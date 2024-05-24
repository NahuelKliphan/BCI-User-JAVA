package com.example.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.userapi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
