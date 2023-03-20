package com.github.nyamnyam.datasearch.model.kakaoapi;

import java.time.Instant;

public record ResponseKakaoSearchBlogDocumentsModel(
        String title,
        String contents,
        String url,
        String blogname,
        String thumbnail,
        Instant datetime
) {
}
