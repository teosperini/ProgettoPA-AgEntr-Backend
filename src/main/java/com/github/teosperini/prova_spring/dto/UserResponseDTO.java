package com.github.teosperini.prova_spring.dto;

public class UserResponseDTO {
    private String username;
    private String nome;
    private String ruolo;
    private int anniEsperienza;
    //private String email;

    public UserResponseDTO(String username, String nome, String ruolo, int anniEsperienza) {
        this.username = username;
        this.nome = nome;
        this.ruolo = ruolo;
        this.anniEsperienza = anniEsperienza;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {this.username = username;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getRuolo() {return ruolo;}
    public void setRuolo(String ruolo) {this.ruolo = ruolo;}
    public int getAnniEsperienza() {return anniEsperienza;}
    public void setAnniEsperienza()  {this.anniEsperienza = anniEsperienza;}
}
