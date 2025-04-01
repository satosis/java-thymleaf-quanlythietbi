package com.example.watchex.service;

import com.example.watchex.entity.Devices;
import com.example.watchex.entity.User;
import com.example.watchex.repository.UserFavouriteRepository;
import com.example.watchex.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFavouriteService {
    @Autowired
    private UserFavouriteRepository userFavouriteRepository;

    public UserFavourite getByProductId(Devices devices) {
        User user = CommonUtils.getCurrentUser();
        UserFavourite result = userFavouriteRepository.getByProductId(user, devices.getId());
        return result;
    }

}
