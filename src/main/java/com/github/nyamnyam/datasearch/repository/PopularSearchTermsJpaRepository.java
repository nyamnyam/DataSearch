package com.github.nyamnyam.datasearch.repository;

import com.github.nyamnyam.datasearch.domain.PopularSearchTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

public interface PopularSearchTermsJpaRepository extends JpaRepository<PopularSearchTerms, String>  {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    PopularSearchTerms findAllByQuery(String query);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    //@Query("INSERT INTO account (id, amount) VALUES (:id, :someValue) ON DUPLICATE KEY UPDATE amount = account.amount + :someValue")
//    @Query("insert INTO popular_search_terms (query, query_count) VALUES (:query, 1) ON DUPLICATE KEY UPDATE query_count = query + 1")
//    void updateQueryCount(@Param("query") String query);

    List<PopularSearchTerms> findTop10ByOrderByQueryCountDescQueryAsc();

}
