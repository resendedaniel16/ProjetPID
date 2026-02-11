package com.projetReservations.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 255, message = "Le titre doit contenir entre 2 et 255 caractères")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "La description est obligatoire")
    @Size(min = 5, message = "La description doit contenir au moins 5 caractères")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Show() {
        this.createdAt = LocalDateTime.now();
    }

    public Show(String title, String description) {
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}