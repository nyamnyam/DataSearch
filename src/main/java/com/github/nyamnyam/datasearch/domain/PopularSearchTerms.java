package com.github.nyamnyam.datasearch.domain;


import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "popular_search_terms")
public class PopularSearchTerms {

    @Id
    @Column(name = "query")
    String query;

    @Column(name = "query_count", nullable = false)
    Long queryCount;

    public void updateQueryCount(String query) {
        this.query = query;
        this.queryCount = this.queryCount + 1;
    }

}
