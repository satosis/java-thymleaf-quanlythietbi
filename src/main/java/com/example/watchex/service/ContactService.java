package com.example.watchex.service;

import com.example.watchex.config.CommonConfigurations;
import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.Contact;
import com.example.watchex.repository.ContactRepository;
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
public class ContactService {
    @Autowired
    private ContactRepository repository;

    public Page<Contact> get(Map<String, String> params) {
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
        return repository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    public List<Contact> getAll() {
        return repository.findAll();
    }

    public void save(Contact contact) {
        repository.save(contact);
    }

    public Contact show(Integer id) throws ClassNotFoundException {
        Optional<Contact> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ClassNotFoundException("Contact not found");
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
