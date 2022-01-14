package com.springbank.bankacc.cmd.controller;

import com.springbank.bankacc.cmd.command.DepositFundCommand;
import com.springbank.bankacc.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/bank-account-funds-deposit")
public class DepositFundController {

    private final CommandGateway commandGateway;

    public DepositFundController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> openAccount(@PathVariable(value= "id") String id, @Valid @RequestBody DepositFundCommand command){

        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds Deposited"), HttpStatus.OK);
        } catch (Exception e) {
            var safeMessage = "Unable to deposit funds for account holder with id: " + id;
            return new ResponseEntity<>(new BaseResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
