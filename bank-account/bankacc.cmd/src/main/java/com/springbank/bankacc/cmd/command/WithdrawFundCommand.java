package com.springbank.bankacc.cmd.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class WithdrawFundCommand {

    @TargetAggregateIdentifier
    private String id;
    @Min(value=1, message = "The minimum fund to withdraw must be at least 1 dollars")
    private double amount;
}
