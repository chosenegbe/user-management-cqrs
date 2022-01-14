package com.springbank.bankacc.cmd.controller;

import com.springbank.bankacc.cmd.command.CloseAccountCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/close-bank-account")
public class CloseAccountController {

    private final CommandGateway commandGateway;

    public CloseAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value= "id") String id){
        var bankAccount = CloseAccountCommand.builder();
        try {
            var command = CloseAccountCommand.builder()
                    .id(id)
                    .build();
            commandGateway.send(command).get();
            return new ResponseEntity<>(new BaseResponse("Bank Account successfully Closed"), HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Unable to close bank account with id: " + id;
            return new ResponseEntity<>(new BaseResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
