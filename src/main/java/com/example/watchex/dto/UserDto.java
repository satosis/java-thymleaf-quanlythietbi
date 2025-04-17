package com.example.watchex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;

    @NotEmpty(message = "Thiếu tên khách hàng")
    private String name;

    private String email;
    private String password;
    private String avatar;
    private MultipartFile avatarFile = null;

    private String oldpassword;
    private String re_password;

    @NotEmpty(message = "Thiếu số điện thoại")
    private String phone;

    private String studentId;
    private String role;
    private String status;
}
