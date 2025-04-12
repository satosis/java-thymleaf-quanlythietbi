package com.example.watchex.service.impl;

import com.example.watchex.dto.SearchDto;
import com.example.watchex.entity.User;
import com.example.watchex.repository.UserRepository;
import com.example.watchex.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Page<User> get(Map<String, String> params) {
        SearchDto dto = new SearchDto();
        if (params.get("page") != null) {
            dto.setPageIndex(Integer.parseInt(params.get("page")) - 1);
        }
        if (params.get("pageSize") != null) {
            dto.setPageSize(Integer.parseInt(params.get("pageSize")));
        }
       if (params.get("status") != null) {
            dto.setStatus(params.get("status"));
        }
        return repository.search(dto, PageRequest.of(dto.getPageIndex(), dto.getPageSize()));
    }

    @Override
    public User show(Integer id) throws UserPrincipalNotFoundException {
        Optional<User> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email) > 0;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> result = Optional.ofNullable(repository.findByEmail(email));
        return result.orElse(null);
    }
    
    public List<User> getActive() {
        return repository.getActive();
    }
}
