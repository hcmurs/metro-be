package com.hieunn.user_service.repositories;

import com.hieunn.user_service.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    boolean existsByTitle(String title);
    boolean existsByTitleAndIdNot(String title, Integer id);

}
