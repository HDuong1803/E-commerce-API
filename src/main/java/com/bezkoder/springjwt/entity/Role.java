package com.bezkoder.springjwt.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // @Enumerated(EnumType.STRING)
  // @Column(length = 20)
  // private ERole name;

  private String name;

  @OneToMany(mappedBy = "role")
  private List<User> users;
}