package com.github.nyamnyam.datasearch.model;

import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import com.github.nyamnyam.datasearch.enums.SortType;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.*;
import java.util.Objects;

public record RequestSearchBlogModel(@NotBlank String query,
                                     SortType sortType,
                                     @Nullable @Positive @Min(1) @Max(50) Integer page,
                                     @Nullable @Positive @Min(1) @Max(50) Integer size,
                                     ApiPlatform apiPlatform) {

    public RequestSearchBlogModel {
        sortType = Objects.requireNonNullElse(sortType, SortType.ACCURACY);
        page = Objects.requireNonNullElse(page, 1);
        size = Objects.requireNonNullElse(size, 10);
        apiPlatform = Objects.requireNonNullElse(apiPlatform, ApiPlatform.KAKAO_API);
    }

}
