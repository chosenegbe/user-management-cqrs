package com.springbank.bankacc.cmd.aggregate;

import com.springbank.bankacc.cmd.command.CloseAccountCommand;
import com.springbank.bankacc.cmd.command.DepositFundCommand;
import com.springbank.bankacc.cmd.command.OpenAccountCommand;
import com.springbank.bankacc.cmd.command.WithdrawFundCommand;
import com.springbank.bankacc.core.event.AccountClosedEvent;
import com.springbank.bankacc.core.event.AccountOpenedEvent;
import com.springbank.bankacc.core.event.FundDepositedEvent;
import com.springbank.bankacc.core.event.FundsWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command) {
        var event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .creationDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on (AccountOpenedEvent event) {
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle (DepositFundCommand command) {
        var amount = command.getAmount();
        var event = FundDepositedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance + amount)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on (FundDepositedEvent event) {
        this.balance += event.getBalance();
    }

    @CommandHandler
    public void handle (WithdrawFundCommand command) {
        var amount = command.getAmount();
        if (this.balance - amount < 0) {
            throw new IllegalStateException("Withdrawal decline. Insufficient fund");
        }
        var event = FundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance - amount)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on (FundsWithdrawnEvent event) {
        this.balance -= event.getBalance();
    }

    @CommandHandler
    public void handle (CloseAccountCommand command) {
        var event = AccountClosedEvent.builder()
                .id(command.getId())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on (AccountClosedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
