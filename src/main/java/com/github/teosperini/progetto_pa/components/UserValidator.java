package com.github.teosperini.progetto_pa.components;

import com.github.teosperini.progetto_pa.entities.User;
import com.github.teosperini.progetto_pa.errors.exceptions.CFAlreadyInUseException;
import com.github.teosperini.progetto_pa.errors.exceptions.UserNotFoundException;
import com.github.teosperini.progetto_pa.mappers.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserMapper userMapper;

    public UserValidator(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void ensureCFIsAvailable(String codiceFiscale) {
        if (userMapper.findByCodiceFiscale(codiceFiscale.toUpperCase()) != null){
            throw new CFAlreadyInUseException(codiceFiscale);
        }
    }

    public User getExistingUser(String codiceFiscale){
        codiceFiscale = codiceFiscale.toUpperCase();
        User user = userMapper.findByCodiceFiscale(codiceFiscale);
        if (user == null) {
            throw new UserNotFoundException(codiceFiscale);
        }
        return user;
    }

    public boolean validateCF(String codiceFiscale) {
        return codiceFiscale.length() == 16;
    }
}
