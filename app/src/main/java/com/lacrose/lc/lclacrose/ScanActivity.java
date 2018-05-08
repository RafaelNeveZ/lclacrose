package com.lacrose.lc.lclacrose;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.Util.MainActivity;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends MainActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    public static int ondeEstou;
    public static boolean primeiraVez;
    private final Context context = this;
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

    @Override
    public void onBackPressed() {
        if(!primeiraVez) {
            if(ondeEstou ==0){
                if(RupturaCorpoListActivity.CorposList.size()>0){
                    Intent intent = new Intent(context, RupturaCorpoListActivity.class);
                    context.startActivity(intent);
                    finish();

                }else{
                    super.onBackPressed();
                }
            }else if(ondeEstou ==1){
                if(RupturaBlocoListActivity.BlocosList.size()>0){
                    Intent intent = new Intent(context, RupturaBlocoListActivity.class);
                    context.startActivity(intent);
                    finish();

                }else{
                    super.onBackPressed();
                }
            }else if(ondeEstou ==2){
                if(RupturaPrismasListActivity.prismasList.size()>0){
                    Intent intent = new Intent(context, RupturaPrismasListActivity.class);
                    context.startActivity(intent);
                    finish();
                }else{
                    super.onBackPressed();
                }
            }else if(ondeEstou ==3){
                if(RupturaPavimentoListActivity.pavimentoList.size()>0){
                    Intent intent = new Intent(context, RupturaPavimentoListActivity.class);
                    context.startActivity(intent);
                    finish();
                }else{
                    super.onBackPressed();
                }
            }
        }else{
            super.onBackPressed();
        }
    }
    /*public void exit(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setTitle(getString(R.string.dialog_logout_title));
        TextView tv = (TextView) dialog.findViewById(R.id.dialog_title);
        tv.setText(getString(R.string.cancel_dialog_confirm));
        dialog.show();
        Button btCancel = (Button) dialog.findViewById(R.id.button_no);
        Button btLogOut = (Button) dialog.findViewById(R.id.button_yes);
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }*/
}
