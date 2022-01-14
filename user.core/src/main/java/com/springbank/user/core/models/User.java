package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @MongoId
    private String id;
    @NotEmpty(message ="firstname is mandatory")
    private String firstname;
    @NotEmpty(message ="lastname is mandatory")
    private String lastname;
    @Email(message ="Please provide a valid email address")
    private String emailAddress;
    @NotNull( message = "Please provide an account for the user to be registered")
    @Valid
    private Account account;
}
