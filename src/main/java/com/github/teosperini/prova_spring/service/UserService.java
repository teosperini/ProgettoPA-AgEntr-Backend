package com.github.teosperini.prova_spring.service;

import com.github.teosperini.prova_spring.dto.PasswordChangeRequestDTO;
import com.github.teosperini.prova_spring.dto.UserResponseDTO;
import com.github.teosperini.prova_spring.entities.User;
import com.github.teosperini.prova_spring.errors.exceptions.UserNotAuthorizedException;
import com.github.teosperini.prova_spring.errors.exceptions.UserNotFoundException;
import com.github.teosperini.prova_spring.errors.exceptions.UsernameAlreadyInUseException;
import com.github.teosperini.prova_spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO saveUser(User nuovoUtente){
        String username = nuovoUtente.getUsername().trim();
        String password = nuovoUtente.getPassword().trim();

        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and/or password are required");
        }

        ensureUsernameIsAvailable(username);
        nuovoUtente.setUsername(username);
        nuovoUtente.setPassword(password);

        String nome = nuovoUtente.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            nuovoUtente.setNome(username);
        } else {
            nuovoUtente.setNome(nome.trim());
        }

        String ruolo = nuovoUtente.getRuolo();
        if (ruolo == null || ruolo.trim().isEmpty()) {
            if (nuovoUtente.getAnniEsperienza() == 0) {
                nuovoUtente.setRuolo("Junior employee");
            } else if (nuovoUtente.getAnniEsperienza() >= 10) {
                nuovoUtente.setRuolo("Senior employee");
            } else {
                nuovoUtente.setRuolo("employee");
            }
        } else {
            nuovoUtente.setRuolo(ruolo);
        }
        User user = userRepository.saveAndFlush(nuovoUtente);
        return new UserResponseDTO(user.getUsername(), user.getNome(), user.getRuolo(), user.getAnniEsperienza());
    }

    public List<UserResponseDTO> searchUsers(String username, String nome, String ruolo){
        User filter = new User();
        if (username != null && !username.trim().isEmpty()) {
            filter.setUsername(username);
        }
        if (nome != null && !nome.trim().isEmpty()) {
            filter.setNome(nome);
        }
        if (ruolo != null && !ruolo.trim().isEmpty()) {
            filter.setRuolo(ruolo);
        }

        // Creazione di un matcher per la ricerca tramite query
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<User> example = Example.of(filter, matcher);
        List<User> utentiTrovati = userRepository.findAll(example);

        // List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        // utentiTrovati.forEach(user -> {
        //     userResponseDTOS.add(new UserResponseDTO(user.getUsername(), user.getNome(), user.getRuolo(), user.getAnniEsperienza()));
        // });
        // return userResponseDTOS;

        // Per trasformare la lista di user in una di dto uso uno stream
        // invece di un forEach

        return utentiTrovati.stream()
                .map(user -> new UserResponseDTO(
                        user.getUsername(),
                        user.getNome(),
                        user.getRuolo(),
                        user.getAnniEsperienza()))
                .toList();
    }

    @Transactional
    public void deleteUser(String username){
        // TODO manca verifica del
        User user = getExistingUser(username);
        userRepository.delete(user);
    }

    private User getExistingUser(String username){
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private void ensureUsernameIsAvailable(String username){
        if (userRepository.existsByUsernameIgnoreCase(username)){
            throw new UsernameAlreadyInUseException(username);
        }
    }

    @Transactional
    public void changeUsername(String username, String newUsername) {
        User user = getExistingUser(username);
        ensureUsernameIsAvailable(newUsername);
        user.setUsername(newUsername);
        // Salvando l'utente dopo che ne abbiamo cambiato l'username
        // (quindi l'utente esiste già) andiamo a modificare l'utente esistente
        userRepository.save(user);
    }

   @Transactional
    public void changePassword(String username, PasswordChangeRequestDTO pwdChangeRequest) {
        // 1. Verifica coerenza dei dati (Fail Fast)
        if (!username.equalsIgnoreCase(pwdChangeRequest.getUsername())) {
            throw new UserNotAuthorizedException("Tentativo di modifica su utente non corrispondente");
        }
        if (pwdChangeRequest.getPassword() == null || pwdChangeRequest.getPassword().trim().isEmpty()) {
           throw new IllegalArgumentException("La nuova password non può essere vuota");
        }

        // 2. Verifica identità
        doLoginByUsername(username, pwdChangeRequest.getOldPassword());

       // 3. Aggiornamento password
        User user = getExistingUser(username);
        user.setPassword(pwdChangeRequest.getPassword());
        userRepository.save(user);
   }

    public UserResponseDTO doLoginByUsername(String username, String password) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotAuthorizedException(username));
        if (!user.getPassword().equals(password)) {
            throw new UserNotAuthorizedException(username);
        }
        return new UserResponseDTO(user.getUsername(), user.getNome(), user.getRuolo(), user.getAnniEsperienza());
    }
}
