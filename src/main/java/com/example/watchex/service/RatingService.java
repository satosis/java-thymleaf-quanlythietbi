package com.example.watchex.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RatingService extends GenericService<Rating, Integer> {
    Page<Rating> get(int page);

    Rating show(Integer id);

}
