package com.example.watchex.dto;

import com.example.watchex.entity.Devices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmBorrowRequestDto {

    private int id;
    private int status;
    private String action;
    private int deviceId;
    private Date borrow_date;
    private Date expected_return_date;
    private String reason;


}
