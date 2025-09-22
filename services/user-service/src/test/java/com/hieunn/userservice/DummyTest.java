/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DummyTest {

  @Test
  void sum() {
    // arrange
    var number1 = 1;
    var number2 = 2;

    // act
    var result = number1 + number2;

    // assert
    assertEquals(3, result);
  }
}
