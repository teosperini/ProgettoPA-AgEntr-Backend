package com.github.teosperini.progetto_pa.mappers;

import com.github.teosperini.progetto_pa.entities.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // Questa annotazione serve a dire a MyBatis che questa è l'interfaccia per il DB
public interface UserMapper {
    @Select("<script>" +
            "SELECT * FROM users WHERE 1=1 " +
            "<if test='codiceFiscale != null'> AND codiceFiscale LIKE '%' || #{codiceFiscale} || '%' </if>" +
            "<if test='nome != null'> AND nome LIKE '%' || #{nome} || '%' </if>" +
            "<if test='cognome != null'> AND cognome LIKE '%' || #{cognome} || '%' </if>" +
            "</script>")
    List<User> search(User user);

    @Select("SELECT * FROM users WHERE codiceFiscale = #{codiceFiscale}")
    User findByCodiceFiscale(String codiceFiscale);

    @Insert("INSERT INTO users (codiceFiscale, password, nome, cognome, ruolo, anniEsperienza) " +
        "VALUES (#{codiceFiscale}, #{password}, #{nome}, #{cognome}, #{ruolo}, #{anniEsperienza})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Delete("DELETE FROM users WHERE codiceFiscale = #{codiceFiscale}")
    void deleteByCF(String codiceFiscale);

    @Update("UPDATE users SET codiceFiscale = #{codiceFiscale}, password = #{password}, " +
            "nome = #{nome}, ruolo = #{ruolo}, anniEsperienza = #{anniEsperienza} " +
            "WHERE id = #{id}")
    void update(User user);
}