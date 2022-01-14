package com.springbank.bankacc.query.handler;

import com.springbank.bankacc.core.event.AccountClosedEvent;
import com.springbank.bankacc.core.event.AccountOpenedEvent;
import com.springbank.bankacc.core.event.FundDepositedEvent;
import com.springbank.bankacc.core.event.FundsWithdrawnEvent;
import com.springbank.bankacc.core.model.BankAccount;
import com.springbank.bankacc.query.repository.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);

    }

    @EventHandler
    @Override
    public void on(FundDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) return;

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) return;

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
