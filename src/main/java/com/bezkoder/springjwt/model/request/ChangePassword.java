package com.bezkoder.springjwt.model.request;

import lombok.Data;

@Data
public class ChangePassword {

    private String email;
    private String oldPassword;
    private String newPassword;

}
