package com.example.series_ranking.user.service;

import com.example.series_ranking.user.User;
import com.example.series_ranking.user.dto.LoginUser;
import com.example.series_ranking.user.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    UserDTO save(UserDTO user);
//
//    Optional<User> update(Long id, User user);
//
//    boolean delete(Long id);
}