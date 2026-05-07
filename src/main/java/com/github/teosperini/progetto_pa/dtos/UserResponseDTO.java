package com.github.teosperini.progetto_pa.dtos;

public class UserResponseDTO {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String ruolo;
    private int anniEsperienza;
    //private String email;

    public UserResponseDTO(String codiceFiscale, String nome, String cognome, String ruolo, int anniEsperienza) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.anniEsperienza = anniEsperienza;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale) {this.codiceFiscale = codiceFiscale;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getCognome() {return cognome;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public String getRuolo() {return ruolo;}
    public void setRuolo(String ruolo) {this.ruolo = ruolo;}
    public int getAnniEsperienza() {return anniEsperienza;}
    public void setAnniEsperienza()  {this.anniEsperienza = anniEsperienza;}
}
