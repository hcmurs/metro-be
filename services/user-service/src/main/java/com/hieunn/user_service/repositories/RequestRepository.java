package com.hieunn.user_service.repositories;

import com.hieunn.user_service.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUser_UserId(Long userId);
}
