package com.github.nyamnyam.datasearch.controller;

import com.github.nyamnyam.common.enums.ResponseCode;
import com.github.nyamnyam.common.model.BasicResponse;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.service.DataSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class DataSearchController {

    final private DataSearchService dataSearchService;


    @GetMapping("/search/blog")
    public BasicResponse searchBlog(@Valid RequestSearchBlogModel requestSearchBlogModel) throws Exception {
        return BasicResponse.toResponse(ResponseCode.SUCCESS, dataSearchService.searchBlog(requestSearchBlogModel));
    }

    @GetMapping("/search/blog/popular-search-terms")
    public BasicResponse searchBlogPopularSearchTerms() {
        return BasicResponse.toResponse(ResponseCode.SUCCESS, dataSearchService.findTop10ByOrderByQueryCountDescQueryAsc());
    }

}
