package com.bezkoder.springjwt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private String phone;

  private String address;


  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;


  public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
      @NotBlank @Size(max = 120) String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }


public User(Integer id) {
    this.id = id;
}

  

  

}
