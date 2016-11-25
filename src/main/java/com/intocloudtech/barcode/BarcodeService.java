package com.intocloudtech.barcode;

import org.springframework.core.io.Resource;

/**
 * Created by sumeet on 11/25/16.
 */
public interface BarcodeService {
    String read(Resource file);
    void create(String data, String filepath);
}
