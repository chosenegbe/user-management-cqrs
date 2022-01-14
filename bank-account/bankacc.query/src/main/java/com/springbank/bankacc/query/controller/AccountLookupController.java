package com.springbank.bankacc.query.controller;

import com.springbank.bankacc.query.dto.AccountLookupResponse;
import com.springbank.bankacc.query.dto.EqualityType;
import com.springbank.bankacc.query.query.FindAccountByIdQuery;
import com.springbank.bankacc.query.query.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.query.FindAllAccountQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/bankacc-lookup")
public class AccountLookupController {

    private final QueryGateway gateway;

    @Autowired
    public AccountLookupController(QueryGateway gateway) {
        this.gateway = gateway;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try
        {
            var query = new FindAllAccountQuery();
            var response = gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            var safeMessage = "Failed to get all bank accounts query request";
            System.out.println(safeMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
        try
        {
            var query = new FindAccountByIdQuery(id);
            var response = gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            var safeMessage = "Failed to get account by id request";
            System.out.println(safeMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{accountHolderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable(value = "accountHolderId") String accountHolderId) {
        try
        {
            var query = new FindAccountByIdQuery(accountHolderId);
            var response = gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            var safeMessage = "Failed to query account by holder id request";
            System.out.println(safeMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/balance")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType, double balance) {
        try
        {
            var query = new FindAccountWithBalanceQuery(equalityType,balance);
            var response = gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();

            if (response == null || response.getAccounts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            var safeMessage = "Failed to query accounts with balance request";
            System.out.println(safeMessage);
            return new ResponseEntity<>(new AccountLookupResponse(safeMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
