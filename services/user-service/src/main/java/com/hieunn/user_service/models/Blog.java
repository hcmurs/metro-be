package com.hieunn.user_service.models;

import com.hieunn.user_service.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "blogs")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Blog extends BaseEntity {

    public enum BlogCategory {
        SERVICE_UPDATE,        // Cập nhật dịch vụ
        SAFETY_GUIDELINE,      // Hướng dẫn an toàn
        RIDER_TIPS,            // Mẹo cho hành khách
        TECH_BEHIND_METRO,     // Công nghệ trong hệ thống metro
        SCHEDULE_INFO,         // Thông tin lịch trình
        PROMOTION,             // Khuyến mãi / ưu đãi
        PUBLIC_ANNOUNCEMENT    // Thông báo chung
    }

    public enum BlogTag {
        STATION_GUIDE,         // Hướng dẫn nhà ga
        ELECTRONIC_TICKETING,  // Hướng dẫn dùng vé điện tử
        MAP_UPDATE,            // Cập nhật bản đồ tuyến
        DISCOUNT,              // Ưu đãi vé
        MAINTENANCE_NOTICE,    // Thông báo bảo trì
        PEAK_HOUR_TIPS,        // Mẹo đi tàu giờ cao điểm
        NEW_LINE_OPENING,      // Tuyến mới khai trương
        MOBILE_APP,            // Hướng dẫn dùng app mobile
        LOST_AND_FOUND,        // Thất lạc đồ
        PUBLIC_ANNOUNCEMENT, ACCESSIBILITY          // Hỗ trợ người khuyết tật
    }

    @Id
    @SequenceGenerator(name = "blogs_seq", sequenceName = "blogs_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blogs_seq")
    @Column(name="id", unique=true, nullable=false)
    Integer id;

    @Enumerated(EnumType.STRING)
    BlogCategory category;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String author;
    LocalDateTime date;
    Integer comments;

    @Column(nullable = false)
    String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<BlogTag> tags;

    String readTime;
    String excerpt;
    Integer views;

}
