package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.PayOSRequest;
import vn.payos.type.CheckoutResponseData;

public interface PayOSService {
    CheckoutResponseData createPaymentLink(PayOSRequest payOSRequest) throws Exception;

}
