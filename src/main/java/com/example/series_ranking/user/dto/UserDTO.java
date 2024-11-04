package com.example.series_ranking.user.dto;

import com.example.series_ranking.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 5, message = "Invalid password")
    private String password;

    @NotNull(message = "Email cannot be null")
    private String email;

    @JsonIgnore
    public User getUserFromDto() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
