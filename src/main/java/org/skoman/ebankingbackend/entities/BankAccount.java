package org.skoman.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    @Enumerated(value = EnumType.STRING)
    private AccountCurrency currency;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
