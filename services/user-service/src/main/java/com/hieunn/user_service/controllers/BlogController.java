package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.BlogDTO;
import com.hieunn.user_service.dtos.requests.BlogDTO.BlogPageRes;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.models.Blog;
import com.hieunn.user_service.services.BlogService;
import com.hieunn.user_service.services.PocketBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/blogs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "/users/blogs", description = "Operations related to blogs")
public class BlogController {

    PocketBaseService pocketBaseService;
    BlogService blogService;


    @GetMapping("/pocketbase")
    @PreAuthorize("permitAll()")
    @Operation(
        summary = "List all blogs",
        description = "Retrieve a paginated list of blogs with optional pagination parameters"
    )
    @Deprecated
    public ResponseEntity<ApiResponse<BlogDTO.BlogPageResPB>> listBlogs(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int perPage
    ) {

        return ResponseEntity.ok(
            ApiResponse.success(pocketBaseService.getBlogs(page, perPage),
                                "Blogs retrieved successfully")
        );
    }

    @GetMapping("/pocketbase/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Get blog by ID",
        description = "Retrieve a blog by its unique identifier"
    )
    @Deprecated
    public ResponseEntity<ApiResponse<BlogDTO.BlogRes>> getBlogById(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponse.success(pocketBaseService.getById(id), "Blog retrieved successfully")
        );
    }

    @GetMapping("")
    @PreAuthorize("permitAll()")
    @Operation(
        summary = "List all blogs",
        description = "Retrieve a paginated list of blogs with optional pagination parameters"
    )
    @Parameter(name = "page", description = "Page number (0-based)", example = "0")
    @Parameter(name = "size", description = "Number of records per page", example = "10")
    @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). " +
        "Default sort order is ascending. " +
        "Multiple sort criteria are supported.", example = "id,asc")
    public ResponseEntity<ApiResponse<Page<Blog>>> getAll(Pageable pageable) {
        var blogPageRes = blogService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(blogPageRes, "Blogs retrieved successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(
        summary = "Get blog by ID",
        description = "Retrieve a blog by its unique identifier"
    )
    public ResponseEntity<ApiResponse<Blog>> getById(
        @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
            ApiResponse.success(blogService.getById(id), "Blog retrieved successfully")
        );
    }

    @PostMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new blog",
        description = "Create a new blog entry"
    )
    public ResponseEntity<ApiResponse<Blog>> createBlog(
        @Valid @RequestBody BlogDTO.BlogReq request
    ) {
        blogService.add(request);
        return ResponseEntity.ok(ApiResponse.success("Blog created successfully"));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update a blog",
        description = "Update an existing blog by its ID"
    )
    public ResponseEntity<ApiResponse<Blog>> updateBlog(
        @PathVariable Integer id,
        @Valid @RequestBody BlogDTO.BlogReq request
    ) {
        blogService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Blog updated successfully"));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete a blog",
        description = "Delete a blog by its ID"
    )
    public ResponseEntity<ApiResponse<Void>> deleteBlog(
        @PathVariable Integer id
    ) {
        blogService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Blog deleted successfully"));
    }


}
