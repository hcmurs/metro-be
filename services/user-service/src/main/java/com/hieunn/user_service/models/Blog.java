package com.hieunn.user_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "blogs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Blog {

    @Id
    @SequenceGenerator(name = "blogs_seq", sequenceName = "blogs_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blogs_seq")
    @Column(name="id", unique=true, nullable=false)
    Integer id;
    String category;
    String title;
    String author;
    String date;
    Integer comments;
    String image;
    String content;
    List<String> tags;
    String readTime;
    String excerpt;
    Integer views;
    String createdAt;
    String updatedAt;

}
