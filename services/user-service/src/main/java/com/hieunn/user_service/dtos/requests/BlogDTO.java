package com.hieunn.user_service.dtos.requests;

import com.hieunn.user_service.models.Blog;
import java.util.List;

public class BlogDTO {

    public record BlogReq(
//        String id,
        String category,
        String title,
        String author,
        String date,
        Integer comments,
        String image,
        String content,
        List<String> tags,
        String readTime,
        String excerpt,
        Integer views,
        String createdAt,
        String updatedAt,
        String collectionId,
        String collectionName
    ) {

    }

    public record BlogRes(
        String id,
        String category,
        String title,
        String author,
        String date,
        Integer comments,
        String image,
        String content,
        List<String> tags,
        String readTime,
        String excerpt,
        Integer views,
        String createdAt,
        String updatedAt
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
