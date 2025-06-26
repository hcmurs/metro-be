package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.BlogDTO.BlogPageRes;
import com.hieunn.user_service.dtos.requests.BlogDTO.BlogReq;
import com.hieunn.user_service.models.Blog;
import com.hieunn.user_service.repositories.BlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public BlogPageRes getAll(Pageable pageable) {
        Page<Blog> page = blogRepository.findAll(pageable);

        return new BlogPageRes(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }

    @Override
    public Blog getById(Integer id) {
        return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + id));
    }

    @Override
    public void add(BlogReq req) {
        if (blogRepository.existsByTitle(req.title())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title already exists");
        }

        var blog = Blog.builder()
            .title(req.title())
            .content(req.content())
            .author(req.author())
            .tags(req.tags())
            .image(req.image())
            .date(req.date())
            .excerpt(req.excerpt())
            .views(req.views())
            .comments(req.comments())
            .readTime(req.readTime())
            .category(req.category())
            .createdAt(req.createdAt())
            .updatedAt(req.updatedAt())
            .build();
        blogRepository.save(blog);
    }

    @Override
    public void update(Integer id, BlogReq req) {

        Blog blog = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + id));

        if (blogRepository.existsByTitleAndIdNot(req.title(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title already exists");
        }

        blog.setTitle(req.title());
        blog.setContent(req.content());
        blog.setAuthor(req.author());
        blog.setTags(req.tags());
        blog.setImage(req.image());
        blog.setDate(req.date());
        blog.setExcerpt(req.excerpt());
        blog.setViews(req.views());
        blog.setComments(req.comments());
        blog.setReadTime(req.readTime());
        blog.setCategory(req.category());
        blog.setCreatedAt(req.createdAt());
        blog.setUpdatedAt(req.updatedAt());

        blogRepository.save(blog);
    }

    @Override
    public void delete(Integer id) {
        var existingBlog = this.getById(id);
        blogRepository.deleteById(existingBlog.getId());
    }
}
