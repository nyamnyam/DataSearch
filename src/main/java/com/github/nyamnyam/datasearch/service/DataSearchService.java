package com.github.nyamnyam.datasearch.service;

import com.github.nyamnyam.common.model.CustomPage;
import com.github.nyamnyam.datasearch.domain.PopularSearchTerms;
import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.model.ResponseSearchBlogContentsModel;
import com.github.nyamnyam.datasearch.repository.PopularSearchTermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSearchService {

    private final KakaoDataSearchService kakaoDataSearchService;
    private final NaverDataSearchService naverDataSearchService;
    private final PopularSearchTermsRepository popularSearchTermsRepository;


    @Transactional
    public CustomPage<ResponseSearchBlogContentsModel> searchBlog(RequestSearchBlogModel requestSearchBlogModel) throws Exception {
        CustomPage<ResponseSearchBlogContentsModel> resultSearchBlog = null;
        if(requestSearchBlogModel.apiPlatform() == ApiPlatform.NAVER_API) {
            resultSearchBlog = naverDataSearchService.searchBlog(requestSearchBlogModel);
        } else {
            try {
                resultSearchBlog = kakaoDataSearchService.searchBlog(requestSearchBlogModel);
            } catch (Exception e) {
                log.error("[ERROR] kakaoSearchBlogData Call Exception. query={}", requestSearchBlogModel.query(), e);
                resultSearchBlog = naverDataSearchService.searchBlog(requestSearchBlogModel);
            }
        }

        this.savePopularSearchTerms(requestSearchBlogModel.query());

        return resultSearchBlog;
    }

    @Transactional
    public void savePopularSearchTerms(String query) {
        PopularSearchTerms popularSearchTerms = popularSearchTermsRepository.findAllByQuery(query);
        if (popularSearchTerms == null) popularSearchTermsRepository.save(new PopularSearchTerms(query, 1L));
        else {
            popularSearchTerms.updateQueryCount(query);
        }
    }

    @Transactional(readOnly = true)
    public List<PopularSearchTerms> findTop10ByOrderByQueryCountDescQueryAsc() {
        return popularSearchTermsRepository.findTop10ByOrderByQueryCountDescQueryAsc();
    }

}
