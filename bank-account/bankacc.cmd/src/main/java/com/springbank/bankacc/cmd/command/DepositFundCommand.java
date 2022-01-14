package com.springbank.bankacc.cmd.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class DepositFundCommand {

    @TargetAggregateIdentifier
    private String id;
    @Min(value = 1, message = "The account to deposit must be at least 1 dollars")
    private double amount;
}
