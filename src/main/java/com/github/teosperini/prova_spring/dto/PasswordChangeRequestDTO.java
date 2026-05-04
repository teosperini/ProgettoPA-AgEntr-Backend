package com.github.teosperini.prova_spring.dto;

public class PasswordChangeRequestDTO extends LoginRequestDTO {
    private String oldPassword;

    public PasswordChangeRequestDTO(String username, String password, String oldPassword) {
        super(username, password);
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {this.oldPassword = oldPassword;}
}
