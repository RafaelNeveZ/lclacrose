package com.lacrose.lc.lclacrose;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.lacrose.lc.lclacrose.Util.MainActivity;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends MainActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    public final String TAG_CÓDIGO ="LIDO";
    public final String TAG_TIPO="TIPO";
    public static int ondeEstou;
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
        Intent intent;
        switch (ondeEstou){
            case 0:
                RupturaCorpoActivity.CODE = result.getContents();
                intent = new Intent(ScanActivity.this, RupturaCorpoActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                RupturaBlocoActivity.CODE = result.getContents();
                intent = new Intent(ScanActivity.this, RupturaBlocoActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                RupturaPrismaActivity.CODE = result.getContents();
                intent = new Intent(ScanActivity.this, RupturaPrismaActivity.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                RupturaPavimentoActivity.CODE = result.getContents();
                intent = new Intent(ScanActivity.this, RupturaPavimentoActivity.class);
                startActivity(intent);
                finish();
                break;
        }


    }
}
