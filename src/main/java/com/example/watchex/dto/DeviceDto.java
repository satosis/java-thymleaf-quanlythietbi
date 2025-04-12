package com.example.watchex.dto;

import com.example.watchex.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private int id;

    @NotEmpty(message = "Thiếu tên thiết bị")
    private String name;

    private Category category;

    private String serial_number;

    private String description;
    private MultipartFile avatar = null;

    private String location;

}
