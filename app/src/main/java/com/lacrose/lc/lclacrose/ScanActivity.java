package com.lacrose.lc.lclacrose;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.lacrose.lc.lclacrose.Util.MainActivity;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends MainActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    public final String TAG_CÓDIGO ="LIDO";
    public final String TAG_TIPO="TIPO";
    //camera permission is needed.

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        mScannerView.setFormats(BarcodeFormat.ALL_FORMATS);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        final MediaPlayer barcodeSound = MediaPlayer.create(this, R.raw.barcode_reader);
        barcodeSound.start();
        Log.e(TAG_CÓDIGO, result.getContents()); // Prints scan results
        Log.e(TAG_TIPO, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        RupturaActivity.CODE = result.getContents();
        Intent intent = new Intent(ScanActivity.this, RupturaActivity.class);
        startActivity(intent);
        finish();

    }
}
