package com.github.nyamnyam.datasearch.repository;

import com.github.nyamnyam.datasearch.domain.PopularSearchTerms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopularSearchTermsRepository {

    final private PopularSearchTermsJpaRepository popularSearchTermsJpaRepository;

    public PopularSearchTerms findAllByQuery(String query) {
        return popularSearchTermsJpaRepository.findAllByQuery(query);
    }

    public void save(PopularSearchTerms popularSearchTerms) {
        popularSearchTermsJpaRepository.save(popularSearchTerms);
    }

    public List<PopularSearchTerms> findTop10ByOrderByQueryCountDescQueryAsc() {
        return popularSearchTermsJpaRepository.findTop10ByOrderByQueryCountDescQueryAsc();
    }

}
