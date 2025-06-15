package org.alfred.ticketservice.service;

public interface QRService {
    byte[] generateQrCode(String content);
    String readQrCode(byte[] imageBytes);
}
