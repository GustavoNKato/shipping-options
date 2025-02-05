package com.kato.challengecart.controller.request;


import com.kato.challengecart.domain.User;

public record UserDto(String firstName, String lastName, int age) {
    public User toDomain() {
        return new User(firstName, lastName, age);
    }
}
