package com.github.teosperini.prova_spring.controller;

import com.github.teosperini.prova_spring.dto.LoginRequestDTO;
import com.github.teosperini.prova_spring.dto.UserResponseDTO;
import com.github.teosperini.prova_spring.entities.User;
import com.github.teosperini.prova_spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://editor.swagger.io", "http://localhost:4200"}) // Specifica chi può chiamarti
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody User nuovoUtente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(nuovoUtente));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> doLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO user = userService.doLoginByUsername(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(user);
    }
}
