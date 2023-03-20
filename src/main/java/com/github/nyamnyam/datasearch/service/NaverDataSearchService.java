package com.github.nyamnyam.datasearch.service;

import com.github.nyamnyam.common.config.WebClientConfig;
import com.github.nyamnyam.common.model.CustomPage;
import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.model.ResponseSearchBlogContentsModel;
import com.github.nyamnyam.datasearch.model.naverapi.ResponseNaverSearchBlogItemsModel;
import com.github.nyamnyam.datasearch.model.naverapi.ResponseNaverSearchBlogModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverDataSearchService implements ApiDataSearch {

    @Value(value = "${api.naver.uri}")
    private String naverUri;

    private final WebClientConfig webClientConfig;


    @Override
    public CustomPage<ResponseSearchBlogContentsModel> searchBlog(RequestSearchBlogModel requestSearchBlogModel) {
        ResponseNaverSearchBlogModel naverSearchBlogData = this.naverSearchBlog(requestSearchBlogModel);
        CustomPage<ResponseSearchBlogContentsModel> result = this.parsingData(requestSearchBlogModel, naverSearchBlogData);
        return result;
    }

    public ResponseNaverSearchBlogModel naverSearchBlog(RequestSearchBlogModel requestSearchBlogModel) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("query", requestSearchBlogModel.query());
        requestParams.add("sort", requestSearchBlogModel.sortType().getSortType(ApiPlatform.NAVER_API));
        requestParams.add("start", String.valueOf(requestSearchBlogModel.page()));
        requestParams.add("display", String.valueOf(requestSearchBlogModel.size()));


        WebClient client = webClientConfig.getNaverWebClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(naverUri).queryParams(requestParams).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseNaverSearchBlogModel>() {})
                .block();
    }

    public CustomPage<ResponseSearchBlogContentsModel> parsingData(RequestSearchBlogModel requestSearchBlogModel, ResponseNaverSearchBlogModel naverSearchBlogData) {
        List<ResponseNaverSearchBlogItemsModel> items = naverSearchBlogData.items();

        List<ResponseSearchBlogContentsModel> responseList = new ArrayList<>();
        items.stream().forEach(
                item -> {
                    responseList.add(new ResponseSearchBlogContentsModel(item.title(),
                            item.description(),
                            item.bloggerlink(),
                            item.bloggername(),
                            item.postdate()));
                }
        );
        // request page, page당 row size
        Pageable pageable = PageRequest.of(requestSearchBlogModel.page(), requestSearchBlogModel.size());

        Page<ResponseSearchBlogContentsModel> result = new PageImpl<>(responseList, pageable, naverSearchBlogData.total());
        return new CustomPage<>(result, ApiPlatform.NAVER_API);
    }


}
