package com.github.teosperini.progetto_pa.controllers;

import com.github.teosperini.progetto_pa.dtos.LoginRequestDTO;
import com.github.teosperini.progetto_pa.dtos.UserResponseDTO;
import com.github.teosperini.progetto_pa.entities.User;
import com.github.teosperini.progetto_pa.errors.exceptions.NotValidException;
import com.github.teosperini.progetto_pa.services.AuthService;
import com.github.teosperini.progetto_pa.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://editor.swagger.io", "http://localhost:4200", "http://localhost:5173"}) // Specifica chi può chiamarti
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody User nuovoUtente) throws NotValidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.saveUser(nuovoUtente));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> doLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO user = authService.doLoginByCodiceFiscale(loginRequestDTO.getCodiceFiscale(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(user);
    }
}
