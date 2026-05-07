package com.github.teosperini.progetto_pa.components;

import com.github.teosperini.progetto_pa.entities.User;
import com.github.teosperini.progetto_pa.errors.exceptions.UserNotFoundException;
import com.github.teosperini.progetto_pa.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    private User userProva;
    private String cfValido;

    @BeforeEach
    void setUp() {
        cfValido = "RSSMRA80A01H501U";
        userProva = new User();
        userProva.setCodiceFiscale(cfValido);
        userProva.setPassword("password");
        userProva.setNome("Mario");
        userProva.setCognome("Rossi");
        userProva.setRuolo("dipendente");
        userProva.setAnniEsperienza(5);
    }

    @Mock
    private UserMapper userMapper;  // Dobbiamo fare il mock dello userMapper anche se il
                                    // primo metodo da testare in UserValidator (validateCF)
                                    // non lo usa
    @InjectMocks
    private UserValidator userValidator;    // Questo ci serve essendo la classe effettiva da
                                            // testare, quindi facciamo l'injection
    @Test
    void validateCF_ShouldReturnTrue_WhenLengthIs16(){
        assertTrue(userValidator.validateCF(cfValido), "Il CF da 16 caratteri" +
                "dovrebbe essere valido");
    }

    @Test
    void validateCF_ShouldReturnFalse_WhenLengthIsNot16(){
        String cfNonValido = "RSSMRA80A";
        assertFalse(userValidator.validateCF(cfNonValido), "Il CF corto non dovrebbe" +
                "essere valido");
    }

    @Test
    void getExistingUser_ShouldReturnUser_WhenUserExists(){
        when(userMapper.findByCodiceFiscale(cfValido)).thenReturn(userProva);

        User user = userValidator.getExistingUser(cfValido);

        assertNotNull(user);
        assertEquals(cfValido, user.getCodiceFiscale());
        assertEquals("password", user.getPassword());
    }

    @Test
    void getExistingUser_ShouldThrowException_WhenUserNotFound(){
        String cfValidoMaNonPresente = "BNCLUG80A01H501L";
        when(userMapper.findByCodiceFiscale(cfValidoMaNonPresente)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userValidator.getExistingUser(cfValidoMaNonPresente));
    }
}
