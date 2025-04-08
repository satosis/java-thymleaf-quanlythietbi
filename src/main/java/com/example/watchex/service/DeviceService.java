package com.example.watchex.service;

import com.example.watchex.config.CommonConfigurations;
import com.example.watchex.dto.DeviceDetailDto;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Devices;
import com.example.watchex.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public Page<Devices> get(Map<String, String> params) {
        SearchDto dto = new SearchDto();
        if (params.get("page") != null) {
            dto.setPageIndex(Integer.parseInt(params.get("page")) - 1);
        }
        if (params.get("pageSize") != null) {
            dto.setPageSize(Integer.parseInt(params.get("pageSize")));
        }
        if (Objects.equals(CommonConfigurations.getCurrentUser().getRole(), "USER")) {
            dto.setStatus("AVAILABLE");
        }
        return deviceRepository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    public List<Devices> getAll() {
        return deviceRepository.findAll();
    }

    public void save(Devices devices) {
        deviceRepository.save(devices);
    }

    public Devices show(Integer id) throws ClassNotFoundException {
        Optional<Devices> result = deviceRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ClassNotFoundException("Devices not found");
    }

    public DeviceDetailDto findBySlug(String slug) {
        return deviceRepository.findBySlug(slug);
    }

    public Page<Devices> findBySlugCategory(String slug, Map<String, String> params) {
        SearchDto dto = new SearchDto();
        if (params.get("page") != null) {
            dto.setPageIndex(Integer.parseInt(params.get("page")) - 1);
        }
        dto.setPageSize(10);
        return deviceRepository.findBySlugCategory(slug, PageRequest.of(dto.getPageIndex(), dto.getPageSize(), Sort.by("id").descending()));
    }

    public void delete(Integer id) {
        deviceRepository.deleteById(id);
    }

    public List<Devices> getActive() {
        return deviceRepository.getActive();
    }
}
