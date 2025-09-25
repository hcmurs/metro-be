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
import com.hieunn.notificationservice.domain.token.FcmTokenPort.UserDeviceTokenDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FcmTokenService {
  Page<UserDeviceTokenDto> getAll(Pageable pageable);

  UserDeviceTokenDto getById(Long id);

  UserDeviceTokenDto create(CreateUserDeviceTokenReq req);

  UserDeviceTokenDto update(Long id, UpdateUserDeviceTokenReq req);

  UserDeviceTokenDto createOrUpdate(CreateUserDeviceTokenReq req);

  List<String> getFcmTokensByEmail(String email);
}
