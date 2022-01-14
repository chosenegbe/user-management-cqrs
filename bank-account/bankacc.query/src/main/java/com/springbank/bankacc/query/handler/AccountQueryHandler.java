package com.springbank.bankacc.query.handler;

import com.springbank.bankacc.query.dto.AccountLookupResponse;
import com.springbank.bankacc.query.query.FindAccountByHolderIdQuery;
import com.springbank.bankacc.query.query.FindAccountByIdQuery;
import com.springbank.bankacc.query.query.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.query.FindAllAccountQuery;

public interface AccountQueryHandler {

    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse findAccountByHolderId(FindAccountByHolderIdQuery query);
    AccountLookupResponse findAllAccounts(FindAllAccountQuery query);
    AccountLookupResponse findAccountWithBalance(FindAccountWithBalanceQuery query);
}
