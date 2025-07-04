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
                .title("Metro Line 1 Ben Thanh - Suoi Tien Now Officially Open")
                .content(
                    "Ho Chi Minh City's first metro line is officially operational, connecting Ben Thanh Market to Suoi Tien with 14 stations. This milestone marks a new era in public transportation for the city. The line features modern trains with air conditioning, digital displays, and accessibility features for all passengers.")
                .author("HCMC Metro Authority")
                .tags(List.of(Blog.BlogTag.NEW_LINE_OPENING, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-line1-opening.jpg")
                .date(LocalDateTime.now().minusDays(2))
                .excerpt(
                    "Ho Chi Minh City's first metro line connecting Ben Thanh to Suoi Tien is now operational with 14 modern stations.")
                .views(1250)
                .comments(89)
                .readTime("3 min read")
                .category(Blog.BlogCategory.SERVICE_UPDATE)
                .build();

            var blog2 = Blog.builder()
                .title("How to Use Your Metro Card: Complete Guide")
                .content(
                    "Learn how to purchase, top up, and use your metro card effectively. This guide covers everything from first-time setup to troubleshooting common issues. Metro cards can be purchased at any station and topped up using cash or bank cards at designated machines.")
                .author("Customer Service Team")
                .tags(List.of(Blog.BlogTag.ELECTRONIC_TICKETING, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-card-guide.jpg")
                .date(LocalDateTime.now().minusDays(5))
                .excerpt(
                    "Complete step-by-step guide on using metro cards, from purchase to daily usage.")
                .views(2100)
                .comments(156)
                .readTime("5 min read")
                .category(Blog.BlogCategory.RIDER_TIPS)
                .build();

            var blog3 = Blog.builder()
                .title("Safety Guidelines for Metro Passengers")
                .content(
                    "Your safety is our priority. Please follow these important guidelines: stand clear of closing doors, hold handrails while moving, keep belongings secure, and report any suspicious activities to station staff. Emergency buttons are located throughout each train car.")
                .author("Safety Department")
                .tags(List.of(Blog.BlogTag.PEAK_HOUR_TIPS, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-safety.jpg")
                .date(LocalDateTime.now().minusDays(7))
                .excerpt(
                    "Essential safety guidelines every metro passenger should know for a safe and comfortable journey.")
                .views(890)
                .comments(23)
                .readTime("4 min read")
                .category(Blog.BlogCategory.SAFETY_GUIDELINE)
                .build();

            var blog4 = Blog.builder()
                .title("New Mobile App Features: Plan Your Journey Better")
                .content(
                    "Our updated mobile app now includes real-time train tracking, route planning, and fare calculation. Download the latest version to access live station information, service announcements, and estimated arrival times. The app also supports multiple languages including Vietnamese and English.")
                .author("Tech Team")
                .tags(List.of(Blog.BlogTag.MOBILE_APP, Blog.BlogTag.MAP_UPDATE))
                .image("https://example.com/metro-app.jpg")
                .date(LocalDateTime.now().minusDays(10))
                .excerpt(
                    "Discover the new features in our mobile app that make metro travel more convenient and efficient.")
                .views(1450)
                .comments(78)
                .readTime("3 min read")
                .category(Blog.BlogCategory.TECH_BEHIND_METRO)
                .build();

            var blog5 = Blog.builder()
                .title("Weekend Maintenance Schedule - Line 1")
                .content(
                    "Regular maintenance work will be conducted on Line 1 every Sunday from 11 PM to 5 AM. During this time, limited shuttle bus service will be available between affected stations. We apologize for any inconvenience and appreciate your understanding as we maintain the highest safety standards.")
                .author("Maintenance Team")
                .tags(List.of(Blog.BlogTag.MAINTENANCE_NOTICE))
                .image("https://example.com/metro-maintenance.jpg")
                .date(LocalDateTime.now().minusDays(12))
                .excerpt("Important maintenance schedule information for Line 1 passengers.")
                .views(680)
                .comments(45)
                .readTime("2 min read")
                .category(Blog.BlogCategory.SCHEDULE_INFO)
                .build();

            var blog6 = Blog.builder()
                .title("Student Discount Program - 50% Off Metro Fares")
                .content(
                    "Students can now enjoy 50% discount on metro fares with valid student ID. Register at any station or through our mobile app. This program supports students' daily commute to schools and universities across the city. Valid for all students from high school to university level.")
                .author("Marketing Team")
                .tags(List.of(Blog.BlogTag.DISCOUNT, Blog.BlogTag.ELECTRONIC_TICKETING))
                .image("https://example.com/student-discount.jpg")
                .date(LocalDateTime.now().minusDays(15))
                .excerpt("Students can now save 50% on metro fares with our new discount program.")
                .views(1850)
                .comments(234)
                .readTime("3 min read")
                .category(Blog.BlogCategory.PROMOTION)
                .build();

            var blog7 = Blog.builder()
                .title("Accessibility Features for Passengers with Disabilities")
                .content(
                    "Our metro system is designed to be accessible for all passengers. Each station features elevators, tactile guidance strips, audio announcements, and wheelchair-accessible platforms. Staff are trained to assist passengers with disabilities. Contact us in advance for special assistance requests.")
                .author("Accessibility Team")
                .tags(List.of(Blog.BlogTag.ACCESSIBILITY, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-accessibility.jpg")
                .date(LocalDateTime.now().minusDays(18))
                .excerpt(
                    "Learn about accessibility features and support services available for passengers with disabilities.")
                .views(420)
                .comments(18)
                .readTime("4 min read")
                .category(Blog.BlogCategory.RIDER_TIPS)
                .build();

            var blog8 = Blog.builder()
                .title("Peak Hour Travel Tips: Beat the Rush")
                .content(
                    "Make your rush hour commute smoother with these helpful tips: travel before 7 AM or after 9 AM when possible, move to the center of the car, prepare your card in advance, and be patient with fellow passengers. Consider flexible work hours to avoid peak times.")
                .author("Customer Experience Team")
                .tags(List.of(Blog.BlogTag.PEAK_HOUR_TIPS, Blog.BlogTag.MOBILE_APP))
                .image("https://example.com/peak-hour-tips.jpg")
                .date(LocalDateTime.now().minusDays(20))
                .excerpt(
                    "Smart tips to make your peak hour metro travel more comfortable and efficient.")
                .views(1120)
                .comments(67)
                .readTime("3 min read")
                .category(Blog.BlogCategory.RIDER_TIPS)
                .build();

            var blog9 = Blog.builder()
                .title("Lost and Found: How to Recover Your Items")
                .content(
                    "Lost something on the metro? Visit our Lost and Found office at Ben Thanh Station or report it through our mobile app. Items are kept for 30 days before being donated to charity. Common lost items include phones, wallets, and umbrellas. Check with station staff immediately if you notice a missing item.")
                .author("Station Operations")
                .tags(List.of(Blog.BlogTag.LOST_AND_FOUND, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/lost-found.jpg")
                .date(LocalDateTime.now().minusDays(25))
                .excerpt("Quick guide on how to recover lost items from metro stations and trains.")
                .views(560)
                .comments(29)
                .readTime("2 min read")
                .category(Blog.BlogCategory.RIDER_TIPS)
                .build();

            var blog10 = Blog.builder()
                .title("Metro Route Map Update: New Connections Added")
                .content(
                    "Our route map has been updated with new connections to bus lines and other transportation services. The integrated map shows optimal transfer points and estimated travel times between major destinations. Digital maps are available at all stations and in our mobile app.")
                .author("Route Planning Team")
                .tags(List.of(Blog.BlogTag.MAP_UPDATE, Blog.BlogTag.MOBILE_APP))
                .image("https://example.com/route-map-update.jpg")
                .date(LocalDateTime.now().minusDays(30))
                .excerpt(
                    "Updated route map with new connections and improved transfer information.")
                .views(980)
                .comments(52)
                .readTime("3 min read")
                .category(Blog.BlogCategory.SERVICE_UPDATE)
                .build();

            var blog11 = Blog.builder()
                .title("Emergency Procedures: What to Do in Case of Emergency")
                .content(
                    "In case of emergency, remain calm and follow these procedures: use emergency intercoms to contact the driver, locate nearest emergency exits, follow staff instructions, and evacuate in an orderly manner. Emergency contact numbers are displayed throughout each car and at every station.")
                .author("Emergency Response Team")
                .tags(List.of(Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/emergency-procedures.jpg")
                .date(LocalDateTime.now().minusDays(35))
                .excerpt("Important emergency procedures every metro passenger should know.")
                .views(720)
                .comments(34)
                .readTime("4 min read")
                .category(Blog.BlogCategory.SAFETY_GUIDELINE)
                .build();

            var blog12 = Blog.builder()
                .title("Holiday Schedule Changes")
                .content(
                    "Metro service schedule will be modified during upcoming holidays. Extended hours on New Year's Eve, reduced frequency on Tet holidays, and normal service on other public holidays. Check our website or mobile app for specific holiday schedules and any temporary service changes.")
                .author("Operations Team")
                .tags(List.of(Blog.BlogTag.MAINTENANCE_NOTICE))
                .image("https://example.com/holiday-schedule.jpg")
                .date(LocalDateTime.now().minusDays(40))
                .excerpt("Important holiday schedule changes and service modifications.")
                .views(640)
                .comments(28)
                .readTime("2 min read")
                .category(Blog.BlogCategory.SCHEDULE_INFO)
                .build();

            var blog13 = Blog.builder()
                .title("Customer Feedback: How We're Improving")
                .content(
                    "Based on customer feedback, we've implemented several improvements: increased train frequency during peak hours, improved station cleanliness, enhanced mobile app features, and better multilingual support. We continue to value your input and suggestions for service improvement.")
                .author("Customer Relations")
                .tags(List.of(Blog.BlogTag.MOBILE_APP, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/customer-feedback.jpg")
                .date(LocalDateTime.now().minusDays(45))
                .excerpt(
                    "Learn about recent improvements made based on valuable customer feedback.")
                .views(890)
                .comments(112)
                .readTime("3 min read")
                .category(Blog.BlogCategory.SERVICE_UPDATE)
                .build();

            var blog14 = Blog.builder()
                .title("Environmental Impact: Metro's Green Initiative")
                .content(
                    "Metro transportation reduces carbon emissions by 40% compared to private vehicles. Our trains use clean energy, stations feature LED lighting, and we've implemented comprehensive recycling programs. Join us in making Ho Chi Minh City greener by choosing public transportation.")
                .author("Sustainability Team")
                .tags(List.of(Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/green-initiative.jpg")
                .date(LocalDateTime.now().minusDays(50))
                .excerpt(
                    "Discover how metro transportation contributes to environmental sustainability.")
                .views(450)
                .comments(31)
                .readTime("3 min read")
                .category(Blog.BlogCategory.PUBLIC_ANNOUNCEMENT)
                .build();

            var blog15 = Blog.builder()
                .title("Monthly Pass Benefits: Save More on Regular Commutes")
                .content(
                    "Monthly passes offer significant savings for regular commuters. Save up to 30% compared to daily tickets, enjoy unlimited rides within the month, and get priority customer support. Monthly passes can be purchased at stations or through our mobile app with automatic renewal options.")
                .author("Fare Management")
                .tags(List.of(Blog.BlogTag.DISCOUNT, Blog.BlogTag.ELECTRONIC_TICKETING))
                .image("https://example.com/monthly-pass.jpg")
                .date(LocalDateTime.now().minusDays(55))
                .excerpt("Learn about monthly pass benefits and how to maximize your savings.")
                .views(1340)
                .comments(95)
                .readTime("3 min read")
                .category(Blog.BlogCategory.PROMOTION)
                .build();

            var blog16 = Blog.builder()
                .title("Metro Etiquette: Tips for a Pleasant Journey")
                .content(
                    "Help us maintain a pleasant environment for all passengers by following these simple etiquette tips: give up your seat to those in need, keep noise levels down, avoid eating strong-smelling food, and dispose of trash properly. Let's work together to keep our metro clean and comfortable.")
                .author("Community Relations")
                .tags(List.of(Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-etiquette.jpg")
                .date(LocalDateTime.now().minusDays(60))
                .excerpt("Simple tips to ensure a pleasant journey for all metro passengers.")
                .views(780)
                .comments(40)
                .readTime("2 min read")
                .category(Blog.BlogCategory.RIDER_TIPS)
                .build();

            var blog17 = Blog.builder()
                .title("Upcoming Metro Expansion Plans")
                .content(
                    "Exciting news! The metro network is set to expand with new lines connecting key districts. Construction will begin next year, with expected completion in 2025. This expansion aims to reduce traffic congestion and provide more efficient public transport options for residents.")
                .author("Planning Department")
                .tags(List.of(Blog.BlogTag.NEW_LINE_OPENING, Blog.BlogTag.STATION_GUIDE))
                .image("https://example.com/metro-expansion.jpg")
                .date(LocalDateTime.now().minusDays(65))
                .excerpt(
                    "Learn about the upcoming metro expansion plans and how they will benefit the city.")
                .views(920)
                .comments(55)
                .readTime("4 min read")
                .category(Blog.BlogCategory.SERVICE_UPDATE)
                .build();

            var blog18 = Blog.builder()
                .title("Metro Art Program: Showcasing Local Talent")
                .content(
                    "Our metro stations will feature rotating art exhibitions from local artists. This initiative aims to beautify public spaces and support the local art community. Artists interested in showcasing their work can apply through our website. The first exhibition opens next month at Ben Thanh Station.")
                .author("Cultural Affairs")
                .tags(List.of(Blog.BlogTag.STATION_GUIDE, Blog.BlogTag.PUBLIC_ANNOUNCEMENT))
                .image("https://example.com/metro-art.jpg")
                .date(LocalDateTime.now().minusDays(70))
                .excerpt(
                    "Discover how the metro is supporting local artists through public exhibitions.")
                .views(600)
                .comments(22)
                .readTime("3 min read")
                .category(Blog.BlogCategory.PUBLIC_ANNOUNCEMENT)
                .build();

            var blog19 = Blog.builder()
                .title("Metro Loyalty Program: Earn Rewards for Frequent Travel")
                .content(
                    "Join our loyalty program to earn points for every metro ride. Points can be redeemed for free rides, discounts at partner stores, and exclusive offers. Sign up through our mobile app or at any station. The more you ride, the more you save!")
                .author("Marketing Team")
                .tags(List.of(Blog.BlogTag.DISCOUNT, Blog.BlogTag.ELECTRONIC_TICKETING))
                .image("https://example.com/metro-loyalty.jpg")
                .date(LocalDateTime.now().minusDays(75))
                .excerpt("Sign up for our loyalty program and start earning rewards today!")
                .views(1100)
                .comments(88)
                .readTime("3 min read")
                .category(Blog.BlogCategory.PROMOTION)
                .build();

            var blog20 = Blog.builder()
                .title("Metro's Commitment to Sustainability: Green Initiatives")
                .content(
                    "The metro system is committed to sustainability through various initiatives: using energy-efficient trains, implementing waste reduction programs, and promoting public transport as a greener alternative to cars. Learn more about our efforts to reduce environmental impact on our website.")
                .author("Sustainability Team")
                .tags(List.of(Blog.BlogTag.STATION_GUIDE, Blog.BlogTag.PUBLIC_ANNOUNCEMENT))
                .image("https://example.com/metro-sustainability.jpg")
                .date(LocalDateTime.now().minusDays(80))
                .excerpt(
                    "Discover how the metro is contributing to a sustainable future for Ho Chi Minh City.")
                .views(750)
                .comments(36)
                .readTime("4 min read")
                .category(Blog.BlogCategory.PUBLIC_ANNOUNCEMENT)
                .build();

            blogRepository.saveAll(List.of(blog1, blog2, blog3, blog4, blog5, blog6, blog7, blog8,
                                           blog9, blog10, blog11, blog12, blog13, blog14, blog15,
                                           blog16, blog17, blog18, blog19, blog20));
        }
    }
}