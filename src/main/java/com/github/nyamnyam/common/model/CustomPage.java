package com.github.nyamnyam.common.model;

import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

public class CustomPage<T> {
    public List<T> content;
    public CustomPageable pageable;
    public ApiPlatform apiPlatform;

    public CustomPage(Page<T> page, ApiPlatform apiPlatform) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalPages(),
                page.getTotalElements());
        this.apiPlatform = apiPlatform;
    }

    @Data
    class CustomPageable {
        int pageNumber;
        int pageSize;
        long totalPages;
        long totalElements;

        public CustomPageable(int pageNumber, int pageSize, long totalPages,long totalElements) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
        }
    }
}
