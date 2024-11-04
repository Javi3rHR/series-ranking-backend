package com.example.series_ranking.user.service;

import com.example.series_ranking.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    UserDTO save(UserDTO user);
}