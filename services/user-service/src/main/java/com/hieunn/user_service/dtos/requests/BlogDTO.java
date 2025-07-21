package com.hieunn.user_service.dtos.requests;

import com.hieunn.user_service.models.Blog;
import com.hieunn.user_service.models.Blog.BlogCategory;
import com.hieunn.user_service.models.Blog.BlogTag;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class BlogDTO {

    public record BlogReq(
//        String id,
        BlogCategory category,
        String title,
        String author,
        LocalDateTime date,
        Integer comments,
        String image,
        String content,
        List<BlogTag> tags,
        String readTime,
        String excerpt,
        Integer views,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

    }

    public record BlogRes(
        String id,
        BlogCategory category,
        String title,
        String author,
        Date date,
        Integer comments,
        String image,
        String content,
        List<BlogTag> tags,
        String readTime,
        String excerpt,
        Integer views,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

    }

    public record BlogPageResPB(
        List<BlogReq> items,
        Integer page,
        Integer perPage,
        Integer totalItems,
        Integer totalPages
    ) {

    }

    public record BlogPageRes(
        List<Blog> content,
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages
    ) { }
}
