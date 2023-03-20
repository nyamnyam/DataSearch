package com.github.nyamnyam.datasearch.service;

import com.github.nyamnyam.common.config.WebClientConfig;
import com.github.nyamnyam.common.model.CustomPage;
import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.model.ResponseSearchBlogContentsModel;
import com.github.nyamnyam.datasearch.model.kakaoapi.ResponseKakaoMetaModel;
import com.github.nyamnyam.datasearch.model.kakaoapi.ResponseKakaoSearchBlogDocumentsModel;
import com.github.nyamnyam.datasearch.model.kakaoapi.ResponseKakaoSearchBlogModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoDataSearchService implements ApiDataSearch {

    @Value(value = "${api.kakao.uri}")
    private String kakaoUri;

    final private WebClientConfig webClientConfig;


    @Override
    public CustomPage<ResponseSearchBlogContentsModel> searchBlog(RequestSearchBlogModel requestSearchBlogModel) throws Exception {
        ResponseKakaoSearchBlogModel kakaoSearchBlogData = this.kakaoSearchBlog(requestSearchBlogModel);
        CustomPage<ResponseSearchBlogContentsModel> result = this.parsingData(requestSearchBlogModel, kakaoSearchBlogData);
        return result;
    }

    public ResponseKakaoSearchBlogModel kakaoSearchBlog(RequestSearchBlogModel requestSearchBlogModel) throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("query", requestSearchBlogModel.query());
        requestParams.add("sort", requestSearchBlogModel.sortType().getSortType(ApiPlatform.KAKAO_API));
        requestParams.add("page", String.valueOf(requestSearchBlogModel.page()));
        requestParams.add("size", String.valueOf(requestSearchBlogModel.size()));


        WebClient client = webClientConfig.getKakaoWebClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(kakaoUri).queryParams(requestParams).build())
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception()))
                .bodyToMono(new ParameterizedTypeReference<ResponseKakaoSearchBlogModel>() {})
                .block();
    }

    public CustomPage<ResponseSearchBlogContentsModel> parsingData(RequestSearchBlogModel requestSearchBlogModel, ResponseKakaoSearchBlogModel kakaoSearchBlogData) {
        ResponseKakaoMetaModel meta = kakaoSearchBlogData.meta();
        List<ResponseKakaoSearchBlogDocumentsModel> documents = kakaoSearchBlogData.documents();

        List<ResponseSearchBlogContentsModel> responseList = new ArrayList<>();
        documents.stream().forEach(
                document -> {
                    responseList.add(new ResponseSearchBlogContentsModel(document.title(),
                            document.contents(),
                            document.url(),
                            document.blogname(),
                            document.datetime()));
                }
        );
        // request page, page당 row size
        Pageable pageable = PageRequest.of(requestSearchBlogModel.page(), requestSearchBlogModel.size());

        Page<ResponseSearchBlogContentsModel> result = new PageImpl<>(responseList, pageable, meta.total_count());
        return new CustomPage<>(result, ApiPlatform.KAKAO_API);
    }



}
