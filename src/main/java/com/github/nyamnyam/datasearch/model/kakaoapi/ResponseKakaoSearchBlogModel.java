package com.github.nyamnyam.datasearch.model.kakaoapi;

import java.util.List;

public record ResponseKakaoSearchBlogModel(ResponseKakaoMetaModel meta,
                                           List<ResponseKakaoSearchBlogDocumentsModel> documents) {
}
