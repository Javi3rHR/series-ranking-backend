package com.example.series_ranking.user;

import com.example.series_ranking.user.dto.UserDTO;
import com.example.series_ranking.user.exception.UserNotFoundException;
import com.example.series_ranking.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        if (userService.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        return userService.update(id, userDTO.getUserFromDto())
//                .map(this::convertToDTO)
//                .map(ResponseEntity::ok)
//                .orElseThrow(() -> new UserNotFoundException(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        if (userService.delete(id)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            throw new UserNotFoundException(id);
//        }
//    }
}
