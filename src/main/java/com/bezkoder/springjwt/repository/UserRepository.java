package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);

  

  User findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query(value = "SELECT * FROM users WHERE CONCAT('email', ' ', 'fullname', ' ', 'phone') LIKE %?1%", nativeQuery = true)
  public Page<User> findAll(String keyword, Pageable pageable);
}
