package com.example.watchex.dto;

import com.example.watchex.entity.Devices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreBorrowRequestDto {

    @NotEmpty(message = "Thiếu sản phẩm")
    private Devices deviceId;

    @NotEmpty(message = "Thiếu ngày gửi yêu cầu")
    private Date requestDate;

    @NotEmpty(message = "Thiếu lý do mượn thiết bị")
    private String reason;

    @NotEmpty(message = "Thiếu ngày dự kiến trả thiết bị.")
    private Date dueDate;

}
