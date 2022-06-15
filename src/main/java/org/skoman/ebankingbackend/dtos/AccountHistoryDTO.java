package org.skoman.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO extends PaginationDTO {
    private String accountId;
    private double balance;
    private List<AccountOperationDTO> accountOperationDTOS;
}
