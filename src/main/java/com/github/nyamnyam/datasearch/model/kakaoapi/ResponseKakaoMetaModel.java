package com.github.nyamnyam.datasearch.model.kakaoapi;

public record ResponseKakaoMetaModel(Integer total_count,
                                     Integer pageable_count,
                                     Boolean is_end) {
}
