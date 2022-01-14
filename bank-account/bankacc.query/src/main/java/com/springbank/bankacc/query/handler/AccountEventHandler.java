package com.springbank.bankacc.query.handler;

import com.springbank.bankacc.core.event.AccountClosedEvent;
import com.springbank.bankacc.core.event.AccountOpenedEvent;
import com.springbank.bankacc.core.event.FundDepositedEvent;
import com.springbank.bankacc.core.event.FundsWithdrawnEvent;

public interface AccountEventHandler {

    void on(AccountOpenedEvent event);
    void on(FundDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
