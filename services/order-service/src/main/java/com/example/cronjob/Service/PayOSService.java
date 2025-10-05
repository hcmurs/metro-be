package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.PayOSRequest;
import com.example.cronjob.DTO.Request.PayOSWebhookRequest;
import com.example.cronjob.DTO.Response.PayOSResponse;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.Webhook;

import java.util.Map;

public interface PayOSService {
    CheckoutResponseData createPaymentLink(PayOSRequest payOSRequest) throws Exception;
//Map<String, Object> handlePaymentReturn(PayOSResponse returnObject) throws Exception;
  void handlePayOSWebhook(Webhook webhookRequest) throws Exception;


}
