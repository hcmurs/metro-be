package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.BlogDTO;
import com.hieunn.user_service.dtos.requests.BlogDTO.BlogPageRes;
import com.hieunn.user_service.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Page<Blog> getAll(Pageable pageable);

    Blog getById(Integer id);

    void add(BlogDTO.BlogReq req);

    void update(Integer id, BlogDTO.BlogReq req);

    void delete(Integer id);

}
