package org.skoman.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skoman.ebankingbackend.entities.BankAccount;
import org.skoman.ebankingbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;

}
