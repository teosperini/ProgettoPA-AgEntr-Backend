package com.github.teosperini.progetto_pa.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// Classe POJO di prova (Plain Old Java Object)
public class User {

    private Long id;

    @NotBlank
    private String codiceFiscale;

    @NotBlank
    private String password;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    private String ruolo;

    @Min(0)
    private int anniEsperienza;

    public User(){
    }

    public User(String nome, String ruolo, int anniEsperienza, String codiceFiscale, String password) {
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.anniEsperienza = anniEsperienza;
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public String getNome(){return this.nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getRuolo(){return this.ruolo;}
    public void setRuolo(String ruolo){this.ruolo = ruolo;}
    public int getAnniEsperienza(){return this.anniEsperienza;}
    public String getCodiceFiscale(){ return this.codiceFiscale;}
    public void setCodiceFiscale(String codiceFiscale){this.codiceFiscale = codiceFiscale;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    public void setAnniEsperienza(int anniEsperienza) {
        this.anniEsperienza = anniEsperienza;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
