package com.example.series_ranking.user.service;

import com.example.series_ranking.user.User;
import com.example.series_ranking.user.UserRepository;
import com.example.series_ranking.user.dto.UserDTO;
import com.example.series_ranking.user.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        users.add(user1);

        when(userRepository.findAll()).thenReturn(users);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

        // Act
        List<UserDTO> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Act
        Optional<UserDTO> result = userService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Act
        UserDTO result = userService.save(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSave_UserAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("password");

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.save(userDTO));
    }
}
