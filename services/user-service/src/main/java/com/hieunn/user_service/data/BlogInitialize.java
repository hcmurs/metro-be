package com.hieunn.user_service.data;

import com.hieunn.user_service.models.Blog;
import com.hieunn.user_service.repositories.BlogRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"h2", "prod"})
@Component
@RequiredArgsConstructor
public class BlogInitialize implements CommandLineRunner {

    private final BlogRepository blogRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Blog> blogs = blogRepository.findAll();
        if (blogs.isEmpty()) {
            var blog1 = Blog.builder()
                .title("Tuyến Metro số 1 Bến Thành - Suối Tiên chính thức đi vào hoạt động")
                .content(
                    "Tuyến Metro số 1 kết nối Bến Thành và Suối Tiên đã chính thức khai trương với 14 ga hiện đại. Đây là cột mốc quan trọng mở ra kỷ nguyên giao thông công cộng mới cho TP.HCM.")
                .author("Ban Quản lý Metro TP.HCM")
                .tags(List.of(Blog.BlogTag.KHAI_TRUONG_TUYEN_MOI, Blog.BlogTag.HUONG_DAN_NHA_GA))
                .image("https://hcmc-metro.com/wp-content/uploads/2024/11/metro-3.jpg")
                .date(LocalDateTime.now().minusDays(2))
                .excerpt(
                    "Tuyến Metro số 1 chính thức khai trương với 14 ga hiện đại kết nối Bến Thành và Suối Tiên.")
                .views(1250)
                .comments(89)
                .readTime("3 phút đọc")
                .category(Blog.BlogCategory.NANG_CAP_DICH_VU)
                .build();

            var blog2 = Blog.builder()
                .title("Hướng dẫn sử dụng thẻ Metro từ A đến Z")
                .content(
                    "Hướng dẫn chi tiết cách mua, nạp tiền và sử dụng thẻ Metro. Thẻ có thể mua tại các ga và nạp tiền bằng tiền mặt hoặc thẻ ngân hàng.")
                .author("Bộ phận Chăm sóc Khách hàng")
                .tags(List.of(Blog.BlogTag.VE_DIEN_TU, Blog.BlogTag.HUONG_DAN_NHA_GA))
                .image("https://hcmc-metro.com/wp-content/uploads/2024/11/metro-3.jpg")
                .date(LocalDateTime.now().minusDays(5))
                .excerpt(
                    "Hướng dẫn đầy đủ cách sử dụng thẻ Metro, từ lúc mua đến khi sử dụng hàng ngày.")
                .views(2100)
                .comments(156)
                .readTime("5 phút đọc")
                .category(Blog.BlogCategory.MEO_CHO_HANH_KHACH)
                .build();

            var blog3 = Blog.builder()
                .title("Quy tắc an toàn khi đi Metro")
                .content(
                    "Vui lòng đứng cách cửa khi đóng, nắm tay vịn khi di chuyển, giữ tài sản cẩn thận và báo ngay cho nhân viên nếu phát hiện hành vi khả nghi.")
                .author("Phòng An toàn")
                .tags(List.of(Blog.BlogTag.HUONG_DAN_NHA_GA))
                .image("https://hcmc-metro.com/wp-content/uploads/2024/11/metro-3.jpg")
                .date(LocalDateTime.now().minusDays(7))
                .excerpt("Những quy tắc an toàn quan trọng bạn cần biết khi đi Metro.")
                .views(890)
                .comments(23)
                .readTime("4 phút đọc")
                .category(Blog.BlogCategory.HUONG_DAN_AN_TOAN)
                .build();

            var blog4 = Blog.builder()
                .title("Ứng dụng Metro phiên bản mới - Lên kế hoạch di chuyển dễ dàng")
                .content(
                    "Ứng dụng Metro vừa được cập nhật với các tính năng mới như theo dõi thời gian thực, tính toán lộ trình và thông báo dịch vụ. Giao diện hiện đại và hỗ trợ đa ngôn ngữ.")
                .author("Đội ngũ Kỹ thuật")
                .tags(List.of(Blog.BlogTag.UNG_DUNG_DI_DONG, Blog.BlogTag.CAP_NHAT_BAN_DO))
                .image("https://example.com/metro-app-update.jpg")
                .date(LocalDateTime.now().minusDays(3))
                .excerpt(
                    "Khám phá những tính năng mới trên ứng dụng Metro giúp bạn di chuyển thuận tiện hơn.")
                .views(1320)
                .comments(67)
                .readTime("3 phút đọc")
                .category(Blog.BlogCategory.CONG_NGHE_TRONG_HE_THONG_METRO)
                .build();

            var blog5 = Blog.builder()
                .title("Lịch bảo trì cuối tuần tuyến Metro số 1")
                .content(
                    "Metro số 1 sẽ tiến hành bảo trì định kỳ vào Chủ Nhật hàng tuần từ 23h đến 5h sáng hôm sau. Vui lòng theo dõi thông báo để sắp xếp lộ trình phù hợp.")
                .author("Bộ phận Bảo trì")
                .tags(List.of(Blog.BlogTag.THONG_BAO_BAO_TRI))
                .image("https://example.com/maintenance.jpg")
                .date(LocalDateTime.now().minusDays(6))
                .excerpt(
                    "Thông báo bảo trì tuyến Metro số 1 vào cuối tuần, quý khách lưu ý lịch trình.")
                .views(890)
                .comments(41)
                .readTime("2 phút đọc")
                .category(Blog.BlogCategory.THONG_TIN_LICH_TRINH)
                .build();

            var blog6 = Blog.builder()
                .title("Ưu đãi học sinh – sinh viên: Giảm 50% giá vé Metro")
                .content(
                    "Chương trình ưu đãi dành cho học sinh, sinh viên với mức giảm 50% khi xuất trình thẻ sinh viên tại quầy hoặc đăng ký qua ứng dụng.")
                .author("Phòng Marketing")
                .tags(List.of(Blog.BlogTag.GIAM_GIA, Blog.BlogTag.VE_DIEN_TU))
                .image("https://example.com/student-discount.jpg")
                .date(LocalDateTime.now().minusDays(10))
                .excerpt(
                    "Đăng ký ngay chương trình ưu đãi giá vé cho học sinh - sinh viên trên toàn hệ thống Metro.")
                .views(1570)
                .comments(96)
                .readTime("3 phút đọc")
                .category(Blog.BlogCategory.KHUYEN_MAI)
                .build();

            var blog7 = Blog.builder()
                .title("Mẹo tránh đông người giờ cao điểm")
                .content(
                    "Hãy khởi hành trước 7h hoặc sau 9h để tránh chen lấn. Ưu tiên di chuyển vào giữa toa và chuẩn bị sẵn thẻ để tiết kiệm thời gian.")
                .author("Bộ phận Trải nghiệm khách hàng")
                .tags(List.of(Blog.BlogTag.THONG_BAO_CONG_CONG))
                .image("https://example.com/rush-hour-tips.jpg")
                .date(LocalDateTime.now().minusDays(8))
                .excerpt("Một số mẹo giúp bạn đi lại thoải mái hơn trong khung giờ cao điểm.")
                .views(1210)
                .comments(54)
                .readTime("2 phút đọc")
                .category(Blog.BlogCategory.MEO_CHO_HANH_KHACH)
                .build();

            var blog8 = Blog.builder()
                .title("Khai trương tuyến Metro số 2 - Nối liền trung tâm thành phố")
                .content(
                    "Tuyến Metro số 2 sẽ khai trương vào tháng tới, kết nối trung tâm TP.HCM với các quận phía Tây. Hệ thống hiện đại và thân thiện với môi trường.")
                .author("Ban Điều hành Dự án")
                .tags(List.of(Blog.BlogTag.KHAI_TRUONG_TUYEN_MOI))
                .image("https://example.com/new-line.jpg")
                .date(LocalDateTime.now().minusDays(14))
                .excerpt(
                    "Tuyến Metro số 2 sẽ chính thức đi vào hoạt động trong tháng tới, mở rộng mạng lưới giao thông TP.HCM.")
                .views(1420)
                .comments(72)
                .readTime("3 phút đọc")
                .category(Blog.BlogCategory.THONG_BAO_CHUNG)
                .build();

            var blog9 = Blog.builder()
                .title("Bản đồ tuyến Metro cập nhật - Kết nối nhanh hơn")
                .content(
                    "Chúng tôi vừa cập nhật bản đồ tuyến với các điểm kết nối xe buýt mới. Bản đồ kỹ thuật số có sẵn tại ga và trong ứng dụng.")
                .author("Đội quy hoạch lộ trình")
                .tags(List.of(Blog.BlogTag.CAP_NHAT_BAN_DO, Blog.BlogTag.UNG_DUNG_DI_DONG))
                .image("https://example.com/map-update.jpg")
                .date(LocalDateTime.now().minusDays(5))
                .excerpt(
                    "Bản đồ tuyến Metro mới nhất với các điểm kết nối mới giữa các phương tiện công cộng.")
                .views(970)
                .comments(31)
                .readTime("3 phút đọc")
                .category(Blog.BlogCategory.NANG_CAP_DICH_VU)
                .build();

            var blog10 = Blog.builder()
                .title("Thất lạc đồ trên Metro? Đây là cách xử lý")
                .content(
                    "Nếu bạn để quên đồ trên tàu hoặc tại ga, hãy liên hệ phòng 'Mất & Tìm' tại ga Bến Thành hoặc báo cáo qua ứng dụng. Vật phẩm sẽ được lưu giữ trong 30 ngày.")
                .author("Bộ phận Vận hành Ga")
                .tags(List.of(Blog.BlogTag.DO_THAT_LAC))
                .image("https://example.com/lost-found.jpg")
                .date(LocalDateTime.now().minusDays(9))
                .excerpt(
                    "Hướng dẫn cách xử lý nhanh chóng khi thất lạc vật dụng cá nhân trong hệ thống Metro.")
                .views(540)
                .comments(19)
                .readTime("2 phút đọc")
                .category(Blog.BlogCategory.MEO_CHO_HANH_KHACH)
                .build();

            blogRepository.saveAll(List.of(blog1, blog2, blog3, blog4, blog5, blog6, blog7, blog8
                , blog9, blog10));
        }
    }
}