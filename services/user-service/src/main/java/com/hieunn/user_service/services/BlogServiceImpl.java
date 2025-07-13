package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.BlogDTO.BlogPageRes;
import com.hieunn.user_service.dtos.requests.BlogDTO.BlogReq;
import com.hieunn.user_service.models.Blog;
import com.hieunn.user_service.repositories.BlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    public Page<Blog> getAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
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

        if (StringUtils.isNotBlank(req.title())) {
            if (blogRepository.existsByTitleAndIdNot(req.title(), id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title already exists");
            }
            blog.setTitle(req.title());
        }

        if (StringUtils.isNotBlank(req.content())) {
            blog.setContent(req.content());
        }

        if (StringUtils.isNotBlank(req.author())) {
            blog.setAuthor(req.author());
        }

        if (req.tags() != null) {
            blog.setTags(req.tags());
        }

        if (StringUtils.isNotBlank(req.image())) {
            blog.setImage(req.image());
        }

        if (req.date() != null) {
            blog.setDate(req.date());
        }

        if (StringUtils.isNotBlank(req.excerpt())) {
            blog.setExcerpt(req.excerpt());
        }

        if (req.views() != null) {
            blog.setViews(req.views());
        }

        if (req.comments() != null) {
            blog.setComments(req.comments());
        }

        if (req.readTime() != null) {
            blog.setReadTime(req.readTime());
        }

        if (req.category() != null) {
            blog.setCategory(req.category());
        }

        if (req.createdAt() != null) {
            blog.setCreatedAt(req.createdAt());
        }

        if (req.updatedAt() != null) {
            blog.setUpdatedAt(req.updatedAt());
        }

        blogRepository.save(blog);
    }

    @Override
    public void delete(Integer id) {
        var existingBlog = this.getById(id);
        blogRepository.deleteById(existingBlog.getId());
    }
}
