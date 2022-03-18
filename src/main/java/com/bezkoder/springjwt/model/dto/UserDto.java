package com.bezkoder.springjwt.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;
    private String email;
    private String fullname;
    private String address;
    private String phone;
    
}
