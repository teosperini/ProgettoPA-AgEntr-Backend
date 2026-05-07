package com.github.teosperini.progetto_pa.dtos;

public class PasswordChangeRequestDTO extends LoginRequestDTO {
    private String oldPassword;

    public PasswordChangeRequestDTO(String codiceFiscale, String password, String oldPassword) {
        super(codiceFiscale, password);
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {this.oldPassword = oldPassword;}
}
