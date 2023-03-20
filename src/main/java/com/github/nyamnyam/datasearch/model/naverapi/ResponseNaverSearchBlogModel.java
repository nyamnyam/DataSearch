package com.github.nyamnyam.datasearch.model.naverapi;

import java.util.List;

public record ResponseNaverSearchBlogModel(Integer total,
                                           Integer start,
                                           Integer display,
                                           List<ResponseNaverSearchBlogItemsModel> items) {
}
