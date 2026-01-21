package com.projetReservations.model;

public enum UserRole {
    ADMIN("admin"),
    MEMBER("member"),
    AFFILIATE("affiliate"),
    PRESS("press"),
    PRODUCER("producer");

    private final String role;

    UserRole(String role) { this.role = role; }

    public String getValue() { return role; }
}
