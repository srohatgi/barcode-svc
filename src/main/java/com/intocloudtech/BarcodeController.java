package com.intocloudtech;

import com.intocloudtech.barcode.BarcodeService;
import com.intocloudtech.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sumeet on 11/25/16.
 */
@RestController
public class BarcodeController {

    private final StorageService storageService;

    private final BarcodeService barcodeService;

    @Autowired
    public BarcodeController(StorageService storageService, BarcodeService barcodeService) {
        this.storageService = storageService;
        this.barcodeService = barcodeService;
    }

    @RequestMapping(value = "/parse/files/{filename:.+}", method = RequestMethod.POST)
    public String processFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        return "{ \"text\": \"" + barcodeService.read(file) + "\"}";
    }
}
