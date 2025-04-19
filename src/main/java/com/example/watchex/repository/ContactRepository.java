package com.example.watchex.repository;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT p FROM Contact p " +
            "WHERE (p.user.id = :#{#dto.getId()} or :#{#dto.getId()} is null) order by p.id desc")
    Page<Contact> search(SearchDto dto, Pageable pageable);

}
