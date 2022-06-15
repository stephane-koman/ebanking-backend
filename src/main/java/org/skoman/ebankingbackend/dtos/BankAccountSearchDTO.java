package org.skoman.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BankAccountSearchDTO extends PaginationDTO {
    List<BankAccountDTO> bankAccountDTOS;
}
