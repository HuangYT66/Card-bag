package com.example.cardbag.Scan;

import android.graphics.Bitmap;

public class BarcodeValues {
    private final String mFormat;
    private final String mContent;
    private Bitmap bitmap;

    public BarcodeValues(String format, String content) {
        mFormat = format;
        mContent = content;
    }

    public String format() {
        return mFormat;
    }

    public String content() {
        return mContent;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isEmpty() {
        return mFormat == null && mContent == null;
    }
}
