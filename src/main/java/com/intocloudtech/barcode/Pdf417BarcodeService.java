package com.intocloudtech.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeet on 11/25/16.
 */
@Service
public class Pdf417BarcodeService implements BarcodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pdf417BarcodeService.class);

    @Override
    public String read(Resource file) {
        BufferedImage bufferedImage;
        Map<DecodeHintType, ?> hints = new HashMap<DecodeHintType, ErrorCorrectionLevel>() {{
            put(DecodeHintType.PURE_BARCODE, ErrorCorrectionLevel.L);
        }};

        try {
            bufferedImage = ImageIO.read(file.getInputStream());

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(bufferedImage)));

            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hints);

            return qrCodeResult.getText();

        } catch (IOException e) {
            LOGGER.error("unable to read file", e);
            throw new RuntimeException("unable to read file", e);
        } catch (NotFoundException e) {
            LOGGER.error("unable to decode image", e);
            throw new RuntimeException("unable to decode image", e);
        }
    }

    @Override
    public void create(String data, String filepath) {

    }
}
