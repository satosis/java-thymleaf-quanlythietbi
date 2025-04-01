package com.example.watchex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;

    private String pro_name;

    private String pro_avatar;

    private String pro_slug;

    private Integer pro_amount;

    private Integer pro_view;

    private Integer pro_price;

    private Integer pro_sale;

    private Category category;

    private Integer pro_favourite;

    private Integer pro_hot;

    private Integer pro_active;

    private Integer pro_admin_id;

    private Integer pro_pay;

    private String pro_description;

    private String pro_content;

    private Integer pro_review_total;

    private String Keywordseo;

    private Integer pro_review_star;

    private String _wysihtml5_mode;


    private Date CreatedAt;

    private Date UpdatedAt;



}
