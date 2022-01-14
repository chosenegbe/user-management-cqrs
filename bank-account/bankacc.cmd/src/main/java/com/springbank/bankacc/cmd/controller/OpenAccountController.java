package com.springbank.bankacc.cmd.controller;

import com.springbank.bankacc.cmd.command.OpenAccountCommand;
import com.springbank.bankacc.cmd.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/open-bank-account")
public class OpenAccountController {
    private final CommandGateway commandGateway;


    @Autowired
    public OpenAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<OpenAccountResponse> openAccount(@Valid @RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new OpenAccountResponse(id, "Bank account successfully open"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeMessage = "Error while processing request for opening a bank account for id " + id;
            return new ResponseEntity<>(new OpenAccountResponse(id, safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
