package com.hieunn.user_service.configs;

import com.hieunn.user_service.repositories.ChatMessageRepository;
import com.hieunn.user_service.utils.PostgresChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    @Bean
    public ChatMemoryRepository chatMemoryRepository(ChatMessageRepository jpaRepo) {
        return new PostgresChatMemoryRepository(jpaRepo);
    }

    @Bean
    public ChatMemory chatMemory(@Qualifier("chatMemoryRepository") ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(repository)
            .maxMessages(20)
            .build();
    }

    @Bean
    public SystemMessage systemPrompt(ChatBotPromptProperties promptProperties) {
        String jsonMetadata = """
            {
              "system": "Metro HCM official fare data (Line 1 as reference)",
              "stations": [
                "Bến Thành",
                "Nhà hát thành phố",
                "Ba Son",
                "Công viên Văn Thánh",
                "Tân Cảng",
                "Thảo Điền",
                "An Phú",
                "Rạch Chiếc",
                "Phước Long",
                "Bình Thái",
                "Thủ Đức",
                "Khu Công nghệ cao",
                "Trường đại học Quốc gia",
                "Bến xe miền đông"
              ],
              "fare_rules": {
                "single_ride_cash_range_vnd": [12000, 21000],
                "day_pass_vnd": 20000,
                "three_day_pass_vnd": 50000,
                "week_pass_vnd": 100000,
                "monthly_pass_general_vnd": 300000,
                "monthly_pass_student_vnd": 150000
              },
              "detailed_fare_matrix": [
                   {
                       "name": "Bến xe Miền Đông-Trường đại học Quốc gia",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Bến xe Miền Đông-Khu Công nghệ cao",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Bến xe Miền Đông-Thủ Đức",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Bến xe Miền Đông-Bình Thái",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Bến xe Miền Đông-Phước Long",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Bến xe Miền Đông-Rạch Chiếc",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Bến xe Miền Đông-An Phú",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "Bến xe Miền Đông-Thảo Điền",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Bến xe Miền Đông-Tân Cảng",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Bến xe Miền Đông-Công viên Văn Thánh",
                       "distance": 15,
                       "price": 18000
                   },
                   {
                       "name": "Bến xe Miền Đông-Ba Son",
                       "distance": 16,
                       "price": 21000
                   },
                   {
                       "name": "Bến xe Miền Đông-Nhà hát Thành phố",
                       "distance": 17,
                       "price": 21000
                   },
                   {
                       "name": "Bến xe Miền Đông-Bến Thành",
                       "distance": 18,
                       "price": 21000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Bến xe Miền Đông",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Khu Công nghệ cao",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Thủ Đức",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Bình Thái",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Phước Long",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Rạch Chiếc",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Trường đại học Quốc gia-An Phú",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Thảo Điền",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Tân Cảng",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Công viên Văn Thánh",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Ba Son",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Nhà hát Thành phố",
                       "distance": 15,
                       "price": 18000
                   },
                   {
                       "name": "Trường đại học Quốc gia-Bến Thành",
                       "distance": 16,
                       "price": 21000
                   },
                   {
                       "name": "Khu Công nghệ cao-Bến xe Miền Đông",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Khu Công nghệ cao-Trường đại học Quốc gia",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Khu Công nghệ cao-Thủ Đức",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Khu Công nghệ cao-Bình Thái",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Khu Công nghệ cao-Phước Long",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Khu Công nghệ cao-Rạch Chiếc",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Khu Công nghệ cao-An Phú",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Khu Công nghệ cao-Thảo Điền",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Khu Công nghệ cao-Tân Cảng",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Khu Công nghệ cao-Công viên Văn Thánh",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Khu Công nghệ cao-Ba Son",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Khu Công nghệ cao-Nhà hát Thành phố",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Khu Công nghệ cao-Bến Thành",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Thủ Đức-Bến xe Miền Đông",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Thủ Đức-Trường đại học Quốc gia",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Thủ Đức-Khu Công nghệ cao",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Thủ Đức-Bình Thái",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Thủ Đức-Phước Long",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Thủ Đức-Rạch Chiếc",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Thủ Đức-An Phú",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Thủ Đức-Thảo Điền",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Thủ Đức-Tân Cảng",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Thủ Đức-Công viên Văn Thánh",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Thủ Đức-Ba Son",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Thủ Đức-Nhà hát Thành phố",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Thủ Đức-Bến Thành",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "Bình Thái-Bến xe Miền Đông",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Trường đại học Quốc gia",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Khu Công nghệ cao",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-Thủ Đức",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-Phước Long",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-Rạch Chiếc",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-An Phú",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-Thảo Điền",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Bình Thái-Tân Cảng",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Công viên Văn Thánh",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Ba Son",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Nhà hát Thành phố",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Bình Thái-Bến Thành",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Bến xe Miền Đông",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Trường đại học Quốc gia",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Khu Công nghệ cao",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Thủ Đức",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-Bình Thái",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-Rạch Chiếc",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-An Phú",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-Thảo Điền",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-Tân Cảng",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Phước Long-Công viên Văn Thánh",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Ba Son",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Nhà hát Thành phố",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Phước Long-Bến Thành",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Rạch Chiếc-Bến xe Miền Đông",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Rạch Chiếc-Trường đại học Quốc gia",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Rạch Chiếc-Khu Công nghệ cao",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Rạch Chiếc-Thủ Đức",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Bình Thái",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Phước Long",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-An Phú",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Thảo Điền",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Tân Cảng",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Công viên Văn Thánh",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Rạch Chiếc-Ba Son",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Rạch Chiếc-Nhà hát Thành phố",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Rạch Chiếc-Bến Thành",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "An Phú-Bến xe Miền Đông",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "An Phú-Trường đại học Quốc gia",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "An Phú-Khu Công nghệ cao",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "An Phú-Thủ Đức",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "An Phú-Bình Thái",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Phước Long",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Rạch Chiếc",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Thảo Điền",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Tân Cảng",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Công viên Văn Thánh",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Ba Son",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "An Phú-Nhà hát Thành phố",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "An Phú-Bến Thành",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Thảo Điền-Bến xe Miền Đông",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Thảo Điền-Trường đại học Quốc gia",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Thảo Điền-Khu Công nghệ cao",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Thảo Điền-Thủ Đức",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Thảo Điền-Bình Thái",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Phước Long",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Rạch Chiếc",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-An Phú",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Tân Cảng",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Công viên Văn Thánh",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Ba Son",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Nhà hát Thành phố",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Thảo Điền-Bến Thành",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Tân Cảng-Bến xe Miền Đông",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Tân Cảng-Trường đại học Quốc gia",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "Tân Cảng-Khu Công nghệ cao",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Tân Cảng-Thủ Đức",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Tân Cảng-Bình Thái",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Tân Cảng-Phước Long",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Rạch Chiếc",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-An Phú",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Thảo Điền",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Công viên Văn Thánh",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Ba Son",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Nhà hát Thành phố",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Tân Cảng-Bến Thành",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Bến xe Miền Đông",
                       "distance": 15,
                       "price": 18000
                   },
                   {
                       "name": "Công viên Văn Thánh-Trường đại học Quốc gia",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Công viên Văn Thánh-Khu Công nghệ cao",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Công viên Văn Thánh-Thủ Đức",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Công viên Văn Thánh-Bình Thái",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Công viên Văn Thánh-Phước Long",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Công viên Văn Thánh-Rạch Chiếc",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-An Phú",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Thảo Điền",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Tân Cảng",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Ba Son",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Nhà hát Thành phố",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Công viên Văn Thánh-Bến Thành",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Bến xe Miền Đông",
                       "distance": 16,
                       "price": 21000
                   },
                   {
                       "name": "Ba Son-Trường đại học Quốc gia",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Ba Son-Khu Công nghệ cao",
                       "distance": 13,
                       "price": 18000
                   },
                   {
                       "name": "Ba Son-Thủ Đức",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Ba Son-Bình Thái",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Ba Son-Phước Long",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Ba Son-Rạch Chiếc",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Ba Son-An Phú",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Thảo Điền",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Tân Cảng",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Công viên Văn Thánh",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Nhà hát Thành phố",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Ba Son-Bến Thành",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Nhà hát Thành phố-Bến xe Miền Đông",
                       "distance": 17,
                       "price": 21000
                   },
                   {
                       "name": "Nhà hát Thành phố-Trường đại học Quốc gia",
                       "distance": 15,
                       "price": 18000
                   },
                   {
                       "name": "Nhà hát Thành phố-Khu Công nghệ cao",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Nhà hát Thành phố-Thủ Đức",
                       "distance": 11,
                       "price": 18000
                   },
                   {
                       "name": "Nhà hát Thành phố-Bình Thái",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Nhà hát Thành phố-Phước Long",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Nhà hát Thành phố-Rạch Chiếc",
                       "distance": 7,
                       "price": 15000
                   },
                   {
                       "name": "Nhà hát Thành phố-An Phú",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Nhà hát Thành phố-Thảo Điền",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Nhà hát Thành phố-Tân Cảng",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Nhà hát Thành phố-Công viên Văn Thánh",
                       "distance": 3,
                       "price": 12000
                   },
                   {
                       "name": "Nhà hát Thành phố-Ba Son",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Nhà hát Thành phố-Bến Thành",
                       "distance": 1,
                       "price": 12000
                   },
                   {
                       "name": "Bến Thành-Bến xe Miền Đông",
                       "distance": 18,
                       "price": 21000
                   },
                   {
                       "name": "Bến Thành-Trường đại học Quốc gia",
                       "distance": 16,
                       "price": 21000
                   },
                   {
                       "name": "Bến Thành-Khu Công nghệ cao",
                       "distance": 14,
                       "price": 18000
                   },
                   {
                       "name": "Bến Thành-Thủ Đức",
                       "distance": 12,
                       "price": 18000
                   },
                   {
                       "name": "Bến Thành-Bình Thái",
                       "distance": 10,
                       "price": 15000
                   },
                   {
                       "name": "Bến Thành-Phước Long",
                       "distance": 9,
                       "price": 15000
                   },
                   {
                       "name": "Bến Thành-Rạch Chiếc",
                       "distance": 8,
                       "price": 15000
                   },
                   {
                       "name": "Bến Thành-An Phú",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Bến Thành-Thảo Điền",
                       "distance": 6,
                       "price": 15000
                   },
                   {
                       "name": "Bến Thành-Tân Cảng",
                       "distance": 5,
                       "price": 12000
                   },
                   {
                       "name": "Bến Thành-Công viên Văn Thánh",
                       "distance": 4,
                       "price": 12000
                   },
                   {
                       "name": "Bến Thành-Ba Son",
                       "distance": 2,
                       "price": 12000
                   },
                   {
                       "name": "Bến Thành-Nhà hát Thành phố",
                       "distance": 1,
                       "price": 12000
                   }
               ],
              "schedule": {
                "operating_hours": "05:00-23:00",
                "frequency_minutes": 15,
                "notes": "Trains run every 15 minutes daily from 5:00 AM to 11:00 PM"
              },
              "notes": "Bot uses 'detailed_fare_matrix' to look up exact fares for each route segment. If a specific route is not found, bot responds: 'I don't have data for this route — would you like me to check/guide you on how to check?' For schedule inquiries, trains run every 15 minutes from 5:00 AM to 11:00 PM daily."
            }
            """;

        String systemPromptText = String.format("""
            You are %s, an AI assistant for the %s.

            METRO FARE AND ROUTE DATA:
            %s

            Your primary role is to help passengers with:
            • Route planning and navigation using the provided station data
            • Real-time service updates and delays
            • Station facilities and accessibility information
            • Emergency assistance and safety guidance

            FARE CALCULATION INSTRUCTIONS:
            - Use the "detailed_fare_matrix" above to provide exact fare prices
            - For routes not in the matrix, inform users you don't have that specific data
            - Single ride prices range from 12,000 VND to 21,000 VND
            - Day pass: 20,000 VND, 3-day pass: 50,000 VND, Weekly: 100,000 VND
            - Monthly pass: 300,000 VND (general), 150,000 VND (student)

            SCHEDULE INFORMATION:
            - Operating hours: 5:00 AM to 11:00 PM daily
            - Train frequency: Every 15 minutes
            - Always provide estimated travel times based on stations and frequency

            Communication style: %s

            Important guidelines:
            - Always be helpful, accurate, and courteous
            - Use the rprovided fare matix for exact pricing
            - If you don't know something, say so and suggest contacting %s
            - For emergencies, immediately direct users to call %s
            - For security issues, refer to %s
            - Provide step-by-step route instructions when helpful
            - Include estimated travel times when possible
            - Mention nearby landmarks for easier navigation
            - Answer in Vietnamese if the user asks in Vietnamese
            - mapping direct name to station name in the fare matrix then provide fare and distance
            - station list in the fare matrix: Bến xe Miền Đông - 1, Trường đại học Quốc gia - 2, Khu Công nghệ cao - 3, Thủ Đức -4, Bình Thái - 5, Phước Long - 6, Rạch Chiếc - 7, An Phú - 8, Thảo Điền - 9, Tân Cảng - 10, Công viên Văn Thánh - 11, Ba Son - 12, Nhà hát Thành phố - 13, Bến Thành - 14, name have map exactly the order of the station in this metro system if you want to count the stations between two stations use the order please

            Remember: You are specifically designed to assist with Ho Chi Minh Metro Line 1 queries.
            If asked about topics outside your scope, politely redirect to transit-related assistance.
            """,
            promptProperties.getBotName(),
            promptProperties.getSystemName(),
            jsonMetadata,
            promptProperties.getBehavior().getResponseStyle(),
            promptProperties.getCustomerServiceContact(),
            promptProperties.getEmergencyContact(),
            promptProperties.getMetroSecurityContact()
        );

        return new SystemMessage(systemPromptText);
    }
}
