package org.skoman.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skoman.ebankingbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;

    @Enumerated(value = EnumType.STRING)
    private OperationType type;

    @ManyToOne
    private BankAccount bankAccount;
}
