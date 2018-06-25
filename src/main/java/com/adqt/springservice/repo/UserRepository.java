package com.adqt.springservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adqt.springservice.entity.User;
/**
 * Repository Interface for CRUD operations on User Table
 */

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmailIgnoreCase(String email); 
}
