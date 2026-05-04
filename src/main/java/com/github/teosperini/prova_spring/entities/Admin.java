package com.github.teosperini.prova_spring.entities;

import jakarta.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne // Un Admin è collegato a un solo User
    @JoinColumn(name = "user_id") // Crea una colonna 'user_id' nella tabella Admin
    private User user;
}
