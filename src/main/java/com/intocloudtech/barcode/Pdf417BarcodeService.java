package com.intocloudtech.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sumeet on 11/25/16.
 */
@Service
public class Pdf417BarcodeService implements BarcodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pdf417BarcodeService.class);

    @Override
    public Map<String, String> read(Resource file) {
        BufferedImage bufferedImage;
        Map<DecodeHintType, ?> hints = new HashMap<DecodeHintType, ErrorCorrectionLevel>() {{
            put(DecodeHintType.PURE_BARCODE, ErrorCorrectionLevel.L);
        }};

        try {
            bufferedImage = ImageIO.read(file.getInputStream());

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(bufferedImage)));

            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hints);

            AamvaFields aamvaFields = new AamvaFields(qrCodeResult.getText());

            return aamvaFields.parsedInfo;

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

class AamvaFields {
    private static final Logger LOGGER = LoggerFactory.getLogger(AamvaFields.class);

    static final Map<String, String> fields = new LinkedHashMap<String, String>() {{
        put("DAA", "Name");
        put("DLDAA", "Name");
        put("DAB", "LastName");
        put("DCS", "LastName");
        put("DAC", "FirstName");
        put("DCT", "FirstName");
        put("DAD", "MiddleName");

        put("DBC", "Sex");
        put("DAU", "Height");
        put("DAY", "EyeColor");

        put("DAG", "Address");
        put("DAI", "City");
        put("DAN", "City");
        put("DAJ", "State");
        put("DAO", "State");
        put("DAK", "ZipCode");
        put("DAP", "Zipcode");
        put("DCG", "Country");

        put("DBB", "DOB");
        put("DAQ", "DriverLicenseNumber");
        put("DBD", "LicenseIssuedDate");
        put("DBA", "LicenseExpirationDate");
    }};

    Map<String, String> parsedInfo;

    AamvaFields(String barcodeInfo) {
        if (!barcodeInfo.startsWith("@")) {
            throw new BarcodeException("illegal document, doesn't start with @");
        }

        String[] records = barcodeInfo.split("\\n");

        parsedInfo = new HashMap<>();

        for(String record : records) {
            if (record.length() < 3) continue;

            String key = record.substring(0, 3);
            String value = record.substring(3);
            if (fields.containsKey(key)) {
                parsedInfo.put(fields.get(key), value);
            }
        }
    }

}