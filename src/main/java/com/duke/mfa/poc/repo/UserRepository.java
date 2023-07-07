package com.duke.mfa.poc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duke.mfa.poc.entity.User;

/**
 * @author Kazi
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmailId(String emailId);
}
