package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.entity.ERole;
import com.bezkoder.springjwt.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
