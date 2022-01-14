package com.springbank.bankacc.query.handler;

import com.springbank.bankacc.core.model.BankAccount;
import com.springbank.bankacc.query.dto.AccountLookupResponse;
import com.springbank.bankacc.query.dto.EqualityType;
import com.springbank.bankacc.query.query.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.query.FindAccountByIdQuery;
import com.springbank.bankacc.query.query.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.query.FindAllAccountQuery;
import com.springbank.bankacc.query.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        var bankAccount =  accountRepository.findById(query.getId());
        var response = bankAccount.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully returned", bankAccount.get())
                : new AccountLookupResponse("No Bank Account found with id " + query.getId() );
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountByHolderIdQuery query) {
        var bankAccount =  accountRepository.findByAccountHolderId(query.getAccountHolderId());
        var response = bankAccount.isPresent()
                ? new AccountLookupResponse("Bank Account Successfully returned for holder id:  " + query.getAccountHolderId(), bankAccount.get())
                : new AccountLookupResponse("No Bank Account found holder id " + query.getAccountHolderId() );
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountQuery query) {
        var bankAccountIterator =  accountRepository.findAll();
        if (!bankAccountIterator.iterator().hasNext()) {
            return new AccountLookupResponse("No bank accounts was found");
        }
        var bankAccounts = new ArrayList<BankAccount>();

        bankAccountIterator.forEach(i -> bankAccounts.add(i));
        var count = bankAccounts.size();
        return new AccountLookupResponse("Successfully returned " + count + " bank accounts", bankAccounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountWithBalance(FindAccountWithBalanceQuery query) {
       var bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN
               ? accountRepository.findByBalanceGreaterThan(query.getBalance())
               : accountRepository.findByBalanceLessThan(query.getBalance());

       var response = bankAccounts != null && bankAccounts.size() > 0
               ? new AccountLookupResponse("Successfully returned " + bankAccounts.size())
               : new AccountLookupResponse("No bank accounts were found");
       return response;
    }
}
