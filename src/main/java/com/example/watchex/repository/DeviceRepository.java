package com.example.watchex.repository;

import com.example.watchex.dto.DeviceDetailDto;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Devices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Devices, Integer> {

    @Query("SELECT p FROM Devices p " +
            "WHERE (p.availability_status LIKE %:#{#dto.getStatus()}% or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '' )")
    Page<Devices> search(SearchDto dto, Pageable pageable);


    @Query("SELECT p FROM Devices p " +
            "WHERE (p.availability_status LIKE %:#{#dto.getStatus()}% or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '' )")
    List<Devices> listSearch(SearchDto dto);

    @Query("select p from Devices p where p.slug = :slug")
    DeviceDetailDto findBySlug(String slug);

    @Query("select p from Devices p where p.category.slug = :slug")
    Page<Devices> findBySlugCategory(String slug, Pageable pageable);

    @Query("select p from Devices p where p.availability_status = 'WORKING'")
    List<Devices> getActive();
}
