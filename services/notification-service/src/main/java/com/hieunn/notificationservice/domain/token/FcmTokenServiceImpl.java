/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.domain.token;

import com.hieunn.notificationservice.domain.token.FcmTokenPort.CreateUserDeviceTokenReq;
import com.hieunn.notificationservice.domain.token.FcmTokenPort.UpdateUserDeviceTokenReq;
import com.hieunn.notificationservice.domain.token.FcmTokenPort.UserDeviceTokenResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService {

  private final UserDeviceTokenRepository repository;

  @Override
  public Page<UserDeviceTokenResponse> getAll(Pageable pageable) {
    return repository.findAll(pageable).map(this::toDto);
  }

  @Override
  public UserDeviceTokenResponse getById(Long id) {
    return toDto(repository.findById(id).orElseThrow(() -> new RuntimeException("Not found")));
  }

  @Override
  public UserDeviceTokenResponse create(CreateUserDeviceTokenReq req) {
    var entity =
        UserDeviceTokenEntity.builder()
            .email(req.email())
            .deviceId(req.deviceId())
            .fcmToken(req.fcmToken())
            .deviceName(req.deviceName())
            .platform(req.platform())
            .build();
    return toDto(repository.save(entity));
  }

  @Override
  public UserDeviceTokenResponse update(Long id, UpdateUserDeviceTokenReq req) {
    var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

    if (req.fcmToken() != null) entity.setFcmToken(req.fcmToken());
    if (req.deviceName() != null) entity.setDeviceName(req.deviceName());
    if (req.platform() != null) entity.setPlatform(req.platform());

    return toDto(repository.save(entity));
  }

  @Override
  public UserDeviceTokenResponse createOrUpdate(CreateUserDeviceTokenReq req) {
    // Try to find existing token for this user and device
    Optional<UserDeviceTokenEntity> existingToken =
        repository.findByEmailAndDeviceId(req.email(), req.deviceId());

    if (existingToken.isPresent()) {
      // Update existing token
      var entity = existingToken.get();
      entity.setFcmToken(req.fcmToken());
      entity.setDeviceName(req.deviceName());
      entity.setPlatform(req.platform());
      return toDto(repository.save(entity));
    } else {
      // Create new token
      return create(req);
    }
  }

  @Override
  public List<String> getFcmTokensByEmail(String email) {
    return repository.findAllByEmail(email).stream()
        .map(UserDeviceTokenEntity::getFcmToken)
        .collect(Collectors.toList());
  }

  private UserDeviceTokenResponse toDto(UserDeviceTokenEntity entity) {
    return new UserDeviceTokenResponse(
        entity.getId(),
        entity.getEmail(),
        entity.getDeviceId(),
        entity.getFcmToken(),
        entity.getDeviceName(),
        entity.getPlatform());
  }
}
