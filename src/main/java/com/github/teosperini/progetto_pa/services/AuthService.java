package com.github.teosperini.progetto_pa.services;

import com.github.teosperini.progetto_pa.components.UserValidator;
import com.github.teosperini.progetto_pa.dtos.UserResponseDTO;
import com.github.teosperini.progetto_pa.entities.User;
import com.github.teosperini.progetto_pa.errors.exceptions.NotValidException;
import com.github.teosperini.progetto_pa.errors.exceptions.UserNotAuthorizedException;
import com.github.teosperini.progetto_pa.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    public AuthService(UserMapper userMapper, UserValidator userValidator) {
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    public UserResponseDTO saveUser(User nuovoUtente) {
        String codiceFiscale = nuovoUtente.getCodiceFiscale().trim().toUpperCase();
        String password = nuovoUtente.getPassword().trim();

        if (codiceFiscale.isEmpty() || password.isEmpty() || !userValidator.validateCF(codiceFiscale)) {
            throw new NotValidException("CF and/or password are required");
        }

        userValidator.ensureCFIsAvailable(codiceFiscale);
        nuovoUtente.setCodiceFiscale(codiceFiscale);
        nuovoUtente.setPassword(password);

        String nome = nuovoUtente.getNome();
        if (nome != null && nome.trim().isEmpty()) {
            nuovoUtente.setNome(nome.trim().toLowerCase());
        }

        String ruolo = nuovoUtente.getRuolo();
        if (ruolo == null || ruolo.trim().isEmpty()) {
            if (nuovoUtente.getAnniEsperienza() == 0) {
                nuovoUtente.setRuolo("junior employee");
            } else if (nuovoUtente.getAnniEsperienza() >= 10) {
                nuovoUtente.setRuolo("senior employee");
            } else {
                nuovoUtente.setRuolo("employee");
            }
        } else {
            nuovoUtente.setRuolo(ruolo.trim().toLowerCase());
        }
        userMapper.insert(nuovoUtente);

        return new UserResponseDTO(nuovoUtente.getCodiceFiscale(), nuovoUtente.getNome(), nuovoUtente.getCognome(), nuovoUtente.getRuolo(), nuovoUtente.getAnniEsperienza());
    }

    public UserResponseDTO doLoginByCodiceFiscale(String codiceFiscale, String password) {
        User user = userValidator.getExistingUser(codiceFiscale);
        if (!user.getPassword().equals(password)) {
            throw new UserNotAuthorizedException(codiceFiscale);
        }
        return new UserResponseDTO(user.getCodiceFiscale(), user.getNome(), user.getCognome(), user.getRuolo(), user.getAnniEsperienza());
    }
}
