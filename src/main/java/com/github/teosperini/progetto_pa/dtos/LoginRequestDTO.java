package com.github.teosperini.progetto_pa.dtos;

public class LoginRequestDTO {
    private String codiceFiscale;
    private String password;

    public LoginRequestDTO(String codiceFiscale, String password) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String username) {
        this.codiceFiscale = username;
    }
}
