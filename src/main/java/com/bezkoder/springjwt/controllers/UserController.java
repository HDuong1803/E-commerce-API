package com.bezkoder.springjwt.controllers;

import java.util.List;

import com.bezkoder.springjwt.model.dto.UserDto;
import com.bezkoder.springjwt.model.request.ChangePassword;
import com.bezkoder.springjwt.service.UserService;
import com.bezkoder.springjwt.utils.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUser(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        List<UserDto> dtos = userService.getAllUser(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<UserDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> getAllUserWithFilter(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(name = "keyword") String keyword) {

        List<UserDto> dtos = userService.getAllUserWithFilter(keyword, pageNo, pageSize, sortBy);

        return new ResponseEntity<List<UserDto>>(dtos, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword req) {

        userService.changePassword(req);
        return new ResponseEntity<MessageResponse>(new MessageResponse("Sucesss"), HttpStatus.OK);

    }

    

}
