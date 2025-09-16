/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.BlogDTO;
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
