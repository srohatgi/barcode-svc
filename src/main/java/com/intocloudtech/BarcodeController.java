package com.intocloudtech;

import com.intocloudtech.barcode.BarcodeService;
import com.intocloudtech.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @RequestMapping(value = "/parse/files/{filename:.+}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Map<String, String> processFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return barcodeService.read(file);
    }
}
