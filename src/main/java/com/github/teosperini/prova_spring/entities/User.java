package com.github.teosperini.prova_spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// Classe POJO di prova (Plain Old Java Object)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    private String nome;

    private String ruolo;

    @Min(0)
    private int anniEsperienza;

    public User(){
    }

    public User(String nome, String ruolo, int anniEsperienza, String username, String password) {
        this.password = password;
        this.username = username;
        this.anniEsperienza = anniEsperienza;
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public String getNome(){return this.nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getRuolo(){return this.ruolo;}
    public void setRuolo(String ruolo){this.ruolo = ruolo;}
    public int getAnniEsperienza(){return this.anniEsperienza;}
    public String getUsername(){ return this.username;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    public void setAnniEsperienza(int anniEsperienza) {
        this.anniEsperienza = anniEsperienza;
    }
}
