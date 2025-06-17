package org.alfred.ticketservice.service;

        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.BinaryBitmap;
        import com.google.zxing.MultiFormatReader;
        import com.google.zxing.client.j2se.MatrixToImageWriter;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.common.HybridBinarizer;
        import com.google.zxing.qrcode.QRCodeWriter;
        import com.google.zxing.LuminanceSource;
        import com.google.zxing.Result;
        import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
        import org.alfred.ticketservice.exception.QrProcessingException;
        import org.springframework.stereotype.Service;

        import javax.imageio.ImageIO;
        import java.awt.image.BufferedImage;
        import java.io.ByteArrayInputStream;
        import java.io.ByteArrayOutputStream;
@Service
        public class QRServiceImpl implements QRService {

            @Override
                public byte[] generateQrCode(String content) {
                    try {
                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
                        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
                        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
                        return pngOutputStream.toByteArray();
                    } catch (Exception e) {
                        throw new QrProcessingException("Failed to generate QR code", e);
                    }
                }

            @Override
            public String readQrCode(byte[] imageBytes) {
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    BufferedImage bufferedImage = ImageIO.read(bais);
                    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);
                    return result.getText();
                } catch (Exception e) {
                    throw new QrProcessingException("Failed to read QR code", e);
                }
            }
        }