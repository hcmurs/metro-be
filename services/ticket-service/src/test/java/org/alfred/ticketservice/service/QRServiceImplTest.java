package org.alfred.ticketservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.alfred.ticketservice.exception.QrProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QRServiceImplTest {


    /*
    Với class bạn muốn test trực tiếp (SUT – System Under Test, ở đây là QRServiceImpl) → không dùng @Mock, mà tạo instance thật (new hoặc @InjectMocks).
    Với các dependency bên trong class đó (ví dụ: UserRepository, RestTemplate, KafkaTemplate, …) → mới dùng @Mock.
    * */

    /*
    * @Mock
    private AuditService auditService; // mock dependency
    * */

    @InjectMocks
    private QRServiceImpl qrService;



    @Test
    void generateQrCode_ShouldReturnNonEmptyBytes_WhenContentIsValid() {
        // Arrange
        String content = "HelloTicket";

        // Act
        byte[] qrCodeBytes = qrService.generateQrCode(content);

        // Assert
        assertNotNull(qrCodeBytes);
        assertTrue(qrCodeBytes.length > 0, "QR code bytes should not be empty");
    }

    @Test
    void generateAndReadQrCode_ShouldReturnOriginalContent() {
        // Arrange
        String content = "ticket-12345";

        // Act
        byte[] qrCodeBytes = qrService.generateQrCode(content);
        String decoded = qrService.readQrCode(qrCodeBytes);

        // Assert
        assertEquals(content, decoded);
    }

    @Test
    void readQrCode_ShouldThrowException_WhenInvalidBytesProvided() {
        // Arrange
        byte[] invalidBytes = new byte[] {1, 2, 3, 4}; // not an image

        // Act & Assert
        assertThrows(QrProcessingException.class, () -> qrService.readQrCode(invalidBytes));
    }

    @Test
    void generateQrCode_ShouldThrowException_WhenContentIsNull() {
        // Arrange
        String content = null;

        // Act & Assert
        assertThrows(QrProcessingException.class, () -> qrService.generateQrCode(content));
    }
}
