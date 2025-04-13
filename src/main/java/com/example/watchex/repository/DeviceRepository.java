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
            "WHERE (p.availability_status = :#{#dto.getStatus()} or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '') and" +
            "(p.operational_status = :#{#dto.getOperationalStatus()} or :#{#dto.getOperationalStatus()} is null or :#{#dto.getOperationalStatus()} = '' ) and" +
            "(p.name LIKE %:#{#dto.getName()}% or :#{#dto.getName()} is null or :#{#dto.getName()} = '' ) and" +
            "(p.id = :#{#dto.getId()} or :#{#dto.getId()} is null ) and" +
            "(p.category.id = :#{#dto.getCategory()} or :#{#dto.getCategory()} is null )")
    Page<Devices> search(SearchDto dto, Pageable pageable);


    @Query("SELECT p FROM Devices p " +
            "WHERE (p.availability_status LIKE %:#{#dto.getStatus()}% or :#{#dto.getStatus()} is null or :#{#dto.getStatus()} = '' )")
    List<Devices> listSearch(SearchDto dto);

    @Query("select p from Devices p where p.slug = :slug")
    DeviceDetailDto findBySlug(String slug);

    @Query("select p from Devices p where p.category.slug = :slug")
    Page<Devices> findBySlugCategory(String slug, Pageable pageable);

    @Query("select p from Devices p where p.operational_status = 'WORKING'")
    List<Devices> getActive();
}
