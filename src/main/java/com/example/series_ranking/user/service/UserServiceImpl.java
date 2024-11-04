package com.example.series_ranking.user.service;

import com.example.series_ranking.user.User;
import com.example.series_ranking.user.UserRepository;
import com.example.series_ranking.user.dto.UserDTO;
import com.example.series_ranking.user.exception.UserAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().iterator().forEachRemaining(users::add);
            return users.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List of users not found.");
        }
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(this::convertToDto);
    }


    @Override
    public UserDTO save(UserDTO user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user.getUserFromDto());
            return convertToDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
        }
    }

    private UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}