package com.github.teosperini.progetto_pa.services;

import com.github.teosperini.progetto_pa.components.UserValidator;
import com.github.teosperini.progetto_pa.dtos.PasswordChangeRequestDTO;
import com.github.teosperini.progetto_pa.dtos.UserResponseDTO;
import com.github.teosperini.progetto_pa.entities.User;
import com.github.teosperini.progetto_pa.errors.exceptions.*;
import com.github.teosperini.progetto_pa.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserService(UserMapper userMapper, UserValidator userValidator) {
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    public List<UserResponseDTO> searchUsers(String codiceFiscale, String nome, String cognome, String ruolo){
        User filter = new User();
        if (codiceFiscale != null && !codiceFiscale.trim().isEmpty()) {
            filter.setCodiceFiscale(codiceFiscale.toUpperCase());
        }
        if (cognome != null && !cognome.trim().isEmpty()) {
            filter.setCognome(cognome.toLowerCase());
        }
        if (nome != null && !nome.trim().isEmpty()) {
            filter.setNome(nome.toLowerCase());
        }
        if (ruolo != null && !ruolo.trim().isEmpty()) {
            filter.setRuolo(ruolo.toLowerCase());
        }

        List<User> utentiTrovati = userMapper.search(filter);

        return utentiTrovati.stream()
                .map(user -> new UserResponseDTO(
                        user.getCodiceFiscale(),
                        user.getNome(),
                        user.getCognome(),
                        user.getRuolo(),
                        user.getAnniEsperienza()))
                .toList();
    }

    @Transactional
    public void deleteUser(String codiceFiscale){
        // TODO manca verifica dell'effettiva proprietà dell'account
        User user = userValidator.getExistingUser(codiceFiscale);
        userMapper.deleteByCF(user.getCodiceFiscale().toUpperCase());
    }

    @Transactional
    public void changeCodiceFiscale(String codiceFiscale, String newcodiceFiscale) {
        User user = userValidator.getExistingUser(codiceFiscale);
        userValidator.ensureCFIsAvailable(newcodiceFiscale);
        user.setCodiceFiscale(newcodiceFiscale);
        // Salvando l'utente dopo che ne abbiamo cambiato il codice fiscale
        // (quindi l'utente esiste già) andiamo a modificare l'utente esistente
        userMapper.update(user);
    }

   @Transactional
    public void changePassword(String codiceFiscale, PasswordChangeRequestDTO pwdChangeRequest) {
        // 1. Verifica coerenza dei dati (Fail Fast)
        if (!codiceFiscale.equalsIgnoreCase(pwdChangeRequest.getCodiceFiscale())) {
            throw new UserNotAuthorizedException("Tentativo di modifica su utente non corrispondente");
        }
        if (pwdChangeRequest.getPassword() == null || pwdChangeRequest.getPassword().trim().isEmpty()) {
           throw new NotValidException("La nuova password non può essere vuota");
        }

        User user = userValidator.getExistingUser(codiceFiscale);
        user.setPassword(pwdChangeRequest.getPassword());
        userMapper.update(user);
   }
}
