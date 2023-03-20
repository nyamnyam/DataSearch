package com.github.nyamnyam.datasearch.service;

import com.github.nyamnyam.common.model.CustomPage;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.model.ResponseSearchBlogContentsModel;

public interface ApiDataSearch {

    CustomPage<ResponseSearchBlogContentsModel> searchBlog(RequestSearchBlogModel requestSearchBlogModel) throws Exception;

}
