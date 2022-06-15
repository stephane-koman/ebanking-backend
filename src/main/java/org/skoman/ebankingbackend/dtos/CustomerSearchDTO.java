package org.skoman.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CustomerSearchDTO extends PaginationDTO {
    List<CustomerDTO> customerDTOS;
}
