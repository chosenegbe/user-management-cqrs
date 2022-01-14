package com.springbank.bankacc.query.query;

import com.springbank.bankacc.query.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery {

    private EqualityType equalityType;
    private double balance;
}
