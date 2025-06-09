package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.BlogDTO;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.services.PocketBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Blogs", description = "Operations related to blogs")
public class BlogController {

    PocketBaseService pocketBaseService;


    @GetMapping
    @Operation(
        summary = "List all blogs",
        description = "Retrieve a paginated list of blogs with optional pagination parameters"
    )
    public ResponseEntity<ApiResponse<BlogDTO.BlogPageRes>> listBlogs(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int perPage
    ) {

        return ResponseEntity.ok(
            ApiResponse.success(pocketBaseService.getBlogs(page, perPage),
                                "Blogs retrieved successfully")
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get blog by ID",
        description = "Retrieve a blog by its unique identifier"
    )
    public ResponseEntity<ApiResponse<BlogDTO.BlogRes>> getBlogById(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponse.success(pocketBaseService.getById(id), "Blog retrieved successfully")
        );
    }

}
