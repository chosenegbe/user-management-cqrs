package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Size(min = 2, message = "Username must be at least two characters")
    private String username;
    @Size(min = 7, message = "Password must be at least seven characters")
    private String password;
    @NotNull(message = "You should specify at least one role for the user")
    private List<Role> roles;

}
