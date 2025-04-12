package com.example.watchex.dto;

import com.example.watchex.entity.Category;
import com.example.watchex.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto implements Serializable {
    private Integer id;
    private Integer price;
    private Integer sort;
    private Integer pageIndex=0;
    private Integer pageSize=5;
    private String status;
    private String operationalStatus;
    private String asc;
    private String name;
    private Integer category;
    private String desc="createdDate";
    public SearchDto(Integer pageSize,Integer pageIndex){
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }
}
