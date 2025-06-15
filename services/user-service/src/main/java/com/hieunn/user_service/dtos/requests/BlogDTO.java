package com.hieunn.user_service.dtos.requests;

import java.util.List;

public class BlogDTO {

    public record BlogReq(
        String id,
        String category,
        String title,
        String author,
        String date,
        Integer comments,
        String image,
        String content,
        String[] tags,
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
        String[] tags,
        String readTime,
        String excerpt,
        Integer views,
        String createdAt,
        String updatedAt
    ) {

    }

    public record BlogPageRes(
        List<BlogReq> items,
        Integer page,
        Integer perPage,
        Integer totalItems,
        Integer totalPages
    ) {

    }
}
