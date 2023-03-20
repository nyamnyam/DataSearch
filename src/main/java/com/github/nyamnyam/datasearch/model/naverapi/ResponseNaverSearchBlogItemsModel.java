package com.github.nyamnyam.datasearch.model.naverapi;

import java.time.Instant;

public record ResponseNaverSearchBlogItemsModel(String title,
                                                String link,
                                                String description,
                                                String bloggername,
                                                String bloggerlink,
                                                Instant postdate) {
}
