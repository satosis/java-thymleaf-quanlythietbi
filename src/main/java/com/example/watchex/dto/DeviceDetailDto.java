package com.example.watchex.dto;

import com.example.watchex.entity.Category;

import java.util.Date;

public interface DeviceDetailDto {
    Integer getId();

    String getName();

    String getAvatar();

    String getSlug();

    Category getCategory();

    Integer getUser();

    Integer getPay();

    String getPro_description();

    String getPro_content();

    Integer getPro_review_total();

    String getKeywordseo();

    Integer getPro_review_star();

    String get_wysihtml5_mode();

    Date getCreatedAt();

    Date getUpdatedAt();

    default Integer getStar() {
        if (getPro_review_total() > 0) {
            return (getPro_review_star() - 5) / getPro_review_total();
        }
        return getPro_review_star();
    }


}
