package com.intocloudtech.barcode;

import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * Created by sumeet on 11/25/16.
 */
public interface BarcodeService {
    Map<String, String> read(Resource file);
    void create(String data, String filepath);
}
