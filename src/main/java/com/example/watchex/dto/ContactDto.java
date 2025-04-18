package com.example.watchex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Integer id;

    @NotEmpty(message = "Thiếu email")
    @Email(message = "Email không hợp lệ")
    private String email;

    private String subject;
    private String message;

}
