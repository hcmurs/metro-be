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
        NANG_CAP_DICH_VU,        // Cập nhật dịch vụ
        HUONG_DAN_AN_TOAN,      // Hướng dẫn an toàn
        MEO_CHO_HANH_KHACH,            // Mẹo cho hành khách
        CONG_NGHE_TRONG_HE_THONG_METRO,     // Công nghệ trong hệ thống metro
        THONG_TIN_LICH_TRINH,         // Thông tin lịch trình
        KHUYEN_MAI,             // Khuyến mãi / ưu đãi
        THONG_BAO_CHUNG    // Thông báo chung
    }

    public enum BlogTag {
        KHAI_TRUONG_TUYEN_MOI,
        HUONG_DAN_NHA_GA,
        MẸO_GIO_CAO_DIEM,
        UNG_DUNG_DI_DONG,
        CAP_NHAT_BAN_DO,
        THONG_BAO_BAO_TRI,
        GIAM_GIA,
        TIEN_NGHI_NGUOI_KHUYET_TAT,
        DO_THAT_LAC,
        THONG_BAO_CONG_CONG,
        VE_DIEN_TU
    }

    @Id
    @SequenceGenerator(name = "blogs_seq", sequenceName = "blogs_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blogs_seq")
    @Column(name = "id", unique = true, nullable = false)
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
