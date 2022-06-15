package org.skoman.ebankingbackend.utils;

import org.skoman.ebankingbackend.dtos.PaginationDTO;

public class MappeDTOData {
    public static <T extends PaginationDTO> void mappePagination(T data, int currentPage, int pageSize, int totalPages){
        data.setCurrentPage(currentPage);
        data.setPageSize(pageSize);
        data.setTotalPages(totalPages);
    }
}
