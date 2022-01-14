package com.springbank.bankacc.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderIdQuery {

    private String accountHolderId;
}
