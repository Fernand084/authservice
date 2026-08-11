package com.fernando84.authservice.dto;

import com.fernando84.authservice.model.Role;

public record RegisterRequest(String username, String password, Role role) {

}
