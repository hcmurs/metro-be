package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.BlogDTO;

public interface BlogService {

    void add(BlogDTO.BlogReq req);

    void update(BlogDTO.BlogReq req);

    void delete(String id);

}
