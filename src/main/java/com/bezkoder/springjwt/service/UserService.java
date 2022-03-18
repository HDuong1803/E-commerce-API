package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.model.dto.UserDto;
import com.bezkoder.springjwt.model.request.ChangePassword;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public boolean checkExistByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return true;
        }

        return false;

    }

    // public UserDto getUserById(Integer userId) {
    // User user = userRepository.findById(userId).get();
    // if (user != null) {
    // return UserDto.builder()
    // .id(user.getId())
    // .email(user.getEmail())

    // .address(user.getAddress())
    // .phone(user.getPhone())
    // .build();
    // }

    // return null;
    // }

    public List<UserDto> getAllUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pageResult = userRepository.findAll(pageable);

        if (pageResult.hasContent()) {
            List<User> listUsers = pageResult.getContent();
            List<UserDto> dtos = convertToDto(listUsers);
            return dtos;
        } else {
            return new ArrayList<UserDto>();
        }
    }

    public List<UserDto> getAllUserWithFilter(String keyword, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pageResult = userRepository.findAll(keyword, pageable);

        if (pageResult.hasContent()) {
            List<User> listUsers = pageResult.getContent();
            List<UserDto> dtos = convertToDto(listUsers);
            return dtos;
        } else {
            return new ArrayList<UserDto>();
        }
    }

    public void changePassword(ChangePassword req) {
        User user = userRepository.findByEmail(req.getEmail());

        boolean isPasswordCorrect = false;

        if (passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            isPasswordCorrect = true;
            String encodedPassword = passwordEncoder.encode(req.getNewPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    private List<UserDto> convertToDto(List<User> listUsers) {

        List<UserDto> dtos = listUsers.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build())
                .collect(Collectors.toList());
        return dtos;
    }

}
