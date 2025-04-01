package com.example.watchex.repository;

import com.example.watchex.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Integer> {

    @Query("SELECT userFavourite FROM UserFavourite userFavourite " +
            "WHERE userFavourite.product.id = :productId and userFavourite.user = :user")
    UserFavourite getByProductId(User user, int productId);


}
