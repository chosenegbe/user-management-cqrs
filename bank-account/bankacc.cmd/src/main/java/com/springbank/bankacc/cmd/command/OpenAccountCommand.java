package com.springbank.bankacc.cmd.command;

import com.springbank.bankacc.core.model.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OpenAccountCommand {

    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "No Account holder Id was supplied")
    private String accountHolderId;
    @NotNull(message = "No Account Type was supplied")
    private AccountType accountType;
    @Min(value = 50, message = "Opening balance must be at least 50 USD")
    private double openingBalance;
}
