package com.hieunn.notificationservice.services;

import com.hieunn.notificationservice.utils.OtpUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    RedisTemplate<String, String> redisTemplate;
    EmailService emailService;
    OtpUtil otpUtil;

    //5 minutes
    static int OTP_EXPIRE_SECONDS = 300;

    public void generateAndSendOtp(String email, String purpose) {
        String otp = otpUtil.generateOtp(6);
        String key = buildKey(email, purpose);

        redisTemplate.opsForValue().set(key, otp, OTP_EXPIRE_SECONDS, TimeUnit.SECONDS);

        emailService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp, String purpose) {
        String key = buildKey(email, purpose);
        String storedOtp = redisTemplate.opsForValue().get(key);

        if (otp.equals(storedOtp)) {
            redisTemplate.delete(key);
            redisTemplate.opsForValue().set(email, "Verified", OTP_EXPIRE_SECONDS, TimeUnit.SECONDS);
            return true;
        }

        return false;
    }

    private String buildKey(String email, String purpose) {
        return "OTP:" + purpose + ":" + email;
    }
}
