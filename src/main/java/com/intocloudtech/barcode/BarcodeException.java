package com.intocloudtech.barcode;

/**
 * Created by sumeet on 11/25/16.
 */
public class BarcodeException extends RuntimeException {
    public BarcodeException(String message) {
        super(message);
    }

    public BarcodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
