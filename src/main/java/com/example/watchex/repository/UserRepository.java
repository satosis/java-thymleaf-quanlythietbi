package com.example.watchex.repository;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT p FROM User p " +
            "WHERE (p.status LIKE %:#{#dto.getStatus()}% or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '' )" +
            "and p.status != 'DELETED'")
    Page<User> search(SearchDto dto, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
    @Query("SELECT count(u) FROM User u WHERE u.email = :email")
    int existsByEmail(String email);
}
