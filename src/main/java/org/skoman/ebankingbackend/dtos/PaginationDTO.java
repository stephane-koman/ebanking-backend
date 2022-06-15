package org.skoman.ebankingbackend.dtos;

import lombok.Data;

@Data
public abstract class PaginationDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
}
