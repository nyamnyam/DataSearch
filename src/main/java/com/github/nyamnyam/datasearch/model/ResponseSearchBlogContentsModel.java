package com.github.nyamnyam.datasearch.model;

import java.time.Instant;

public record ResponseSearchBlogContentsModel(String title, String contents, String url, String blogName, Instant postDateTime) {
}
