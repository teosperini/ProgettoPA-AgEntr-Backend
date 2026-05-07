package com.github.teosperini.progetto_pa.controllers;

import com.github.teosperini.progetto_pa.dtos.PasswordChangeRequestDTO;
import com.github.teosperini.progetto_pa.dtos.UserResponseDTO;
import com.github.teosperini.progetto_pa.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Grazie al flag @RestController questa classe è pronta per essere
    utilizzata da Spring MVC per gestire le richieste web
 */

@CrossOrigin(origins = {"http://editor.swagger.io", "http://localhost:4200", "http://localhost:5173"}) // Specifica chi può chiamarti
@RestController
@RequestMapping("/api/v1/users") // Aggiunto versionamento
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Ritorna tutti gli utenti nel sistema
    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String ruolo
    ){
        return ResponseEntity.ok(userService.searchUsers(username, nome, cognome, ruolo));
    }

    @PutMapping("/{cf}")
    public ResponseEntity<Void> changeCodiceFiscale(@PathVariable String username, @RequestBody String newUsername){
        userService.changeCodiceFiscale(username, newUsername);
        return ResponseEntity.noContent().build();
    }

    // Elimina un utente
    @DeleteMapping("/{cf}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username){
        userService.deleteUser(username);
        // Se c'è un errore in deleteUser, il return successivo viene bypassato.
        // Spring, grazie all'annotazione sopra la classe di errore (UserNotFoundException)
        // riesce a capire automaticamente cosa ritornare in caso venga istanziata quella classe
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cf}/password")
    public ResponseEntity<Void> changePassword(@Valid @PathVariable String username, @RequestBody PasswordChangeRequestDTO pwdChangeRequestDTO) {
        userService.changePassword(username, pwdChangeRequestDTO);
        return ResponseEntity.noContent().build();
    }

}
