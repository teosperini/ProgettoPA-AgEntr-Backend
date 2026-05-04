package com.github.teosperini.prova_spring.controller;

import com.github.teosperini.prova_spring.dto.PasswordChangeRequestDTO;
import com.github.teosperini.prova_spring.dto.UserResponseDTO;
import com.github.teosperini.prova_spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Grazie al flag @RestController questa classe è pronta per essere
    utilizzata da Spring MVC per gestire le richieste web
 */

@CrossOrigin(origins = {"http://editor.swagger.io", "http://localhost:4200"}) // Specifica chi può chiamarti
@RestController
@RequestMapping("/api/v1") // Aggiunto versionamento
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Ritorna tutti gli utenti nel sistema
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String ruolo
    ){
        return ResponseEntity.ok(userService.searchUsers(username, nome, ruolo));
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<Void> changeUsername(@PathVariable String username, @RequestBody String newUsername){
        userService.changeUsername(username, newUsername);
        return ResponseEntity.noContent().build();
    }

    // Elimina un utente
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username){
        userService.deleteUser(username);
        // Se c'è un errore in deleteUser, il return successivo viene bypassato.
        // Spring, grazie all'annotazione sopra la classe di errore (UserNotFoundException)
        // riesce a capire automaticamente cosa ritornare in caso venga istanziata quella classe
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{username}/password")
    public ResponseEntity<Void> changePassword(@Valid @PathVariable String username, @RequestBody PasswordChangeRequestDTO pwdChangeRequestDTO) {
        userService.changePassword(username, pwdChangeRequestDTO);
        return ResponseEntity.noContent().build();
    }

}
