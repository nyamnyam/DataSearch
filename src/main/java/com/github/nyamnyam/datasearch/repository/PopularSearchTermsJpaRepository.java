package com.github.nyamnyam.datasearch.repository;

import com.github.nyamnyam.datasearch.domain.PopularSearchTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface PopularSearchTermsJpaRepository extends JpaRepository<PopularSearchTerms, String>  {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PopularSearchTerms findAllByQuery(String query);

    List<PopularSearchTerms> findTop10ByOrderByQueryCountDescQueryAsc();

}
