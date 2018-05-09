package com.lacrose.lc.lclacrose;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lclacrose.Model.Blocos;
import com.lacrose.lc.lclacrose.Model.CP;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Model.Pavimentos;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.HashMap;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends MainActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    public static int ondeEstou;
    public static boolean primeiraVez;
    private final Context context = this;
    FirebaseFirestore database;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        database = FireBaseUtil.getFireDatabase();
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        mScannerView.setFormats(BarcodeFormat.ALL_FORMATS);
        setContentView(mScannerView);                // Set the scanner view as the content view
        String id = "";
        switch (ondeEstou){
            case 0:
                id= RupturaCorpoActivity.atualLote.getId();
                break;
            case 1:
                id= RupturaBlocoActivity.atualLote.getId();
                break;
            case 2:
                id= RupturaPrismaActivity.atualLote.getId();
                break;
            case 3:
                id= RupturaPavimentoActivity.atualLote.getId();
                break;
        }

        if(primeiraVez){
            showProgress(getString(R.string.loading));
            database.collection(getString(R.string.corpos_tag)).whereEqualTo(getString(R.string.loteId),id)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().getDocumentChanges().size() >0){
                            for (final DocumentChange change:
                                    task.getResult().getDocumentChanges()) {
                                database.collection(getString(R.string.corpos_tag)+"/"+ change.getDocument().getId()+"/"+getString(R.string.rompimentos_tag))
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int count = 0;
                                            for (DocumentChange change2 :
                                                    task.getResult().getDocumentChanges()) {
                                                count++;
                                                switch (ondeEstou){
                                                    case 0:
                                                        CP cp = change2.getDocument().toObject(CP.class);
                                                        RupturaCorpoListActivity.CorposList.add(cp);
                                                        if (count >= task.getResult().getDocumentChanges().size()) {
                                                            primeiraVez = false;
                                                            RupturaCorpoListActivity.corpo = change.getDocument().toObject(Corpos.class) ;
                                                            RupturaCorpoListActivity.corpo.setId(change.getDocument().getId());
                                                            dismissProgress();
                                                        }
                                                        break;
                                                    case 1:
                                                        Blocos bloco = change2.getDocument().toObject(Blocos.class);
                                                        RupturaBlocoListActivity.BlocosList.add(bloco);
                                                        if (count >= task.getResult().getDocumentChanges().size()) {
                                                            primeiraVez = false;
                                                            RupturaBlocoListActivity.corpo = change.getDocument().toObject(Corpos.class) ;
                                                            RupturaBlocoListActivity.corpo.setId(change.getDocument().getId());
                                                            dismissProgress();
                                                        }
                                                        break;
                                                    case 2:
                                                        Prismas prisma = change2.getDocument().toObject(Prismas.class);
                                                        RupturaPrismasListActivity.prismasList.add(prisma);
                                                        if (count >= task.getResult().getDocumentChanges().size()) {
                                                            primeiraVez = false;
                                                            RupturaPrismasListActivity.corpo = change.getDocument().toObject(Corpos.class) ;
                                                            RupturaPrismasListActivity.corpo.setId(change.getDocument().getId());
                                                            dismissProgress();
                                                        }
                                                        break;
                                                    case 3:
                                                        Pavimentos pavimento = change2.getDocument().toObject(Pavimentos.class);
                                                        RupturaPavimentoListActivity.pavimentoList.add(pavimento);
                                                        if (count >= task.getResult().getDocumentChanges().size()) {
                                                            primeiraVez = false;
                                                            RupturaPavimentoListActivity.corpo = change.getDocument().toObject(Corpos.class) ;
                                                            RupturaPavimentoListActivity.corpo.setId(change.getDocument().getId());
                                                            dismissProgress();
                                                        }
                                                        break;
                                                }

                                            }
                                        }else{
                                            showAlert(getString(R.string.erro),getString(R.string.ocorreu_erro));
                                        }
                                    }
                                });
                            }
                        }else{
                            RupturaPavimentoListActivity.corpo = new Corpos();
                            RupturaPrismasListActivity.corpo = new Corpos();
                            RupturaBlocoListActivity.corpo = new Corpos();
                            RupturaCorpoListActivity.corpo = new Corpos();
                            dismissProgress();
                        }
                    }else{
                        showAlert(getString(R.string.erro),getString(R.string.ocorreu_erro));
                        finish();
                    }
                }
            });
        }
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
        boolean jaTem = false;
        boolean Possui = false;
        switch (ondeEstou){
            case 0:
                for (CP cp:
                        RupturaCorpoListActivity.CorposList) {
                    if(cp.getCodigo().equals(result.getContents())){
                        jaTem=true;
                    }

                }
                for (HashMap <String,String> corpo:
                        RupturaCorpoActivity.atualLote.getCorpos()) {
                    if(corpo.get("codigo").equals(result.getContents())){
                        RupturaCorpoActivity.idade = corpo.get("idade");
                        Possui = true;
                    }
                }
                if(Possui) {
                    if (jaTem) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getString(R.string.aviso));
                        alertDialog.setMessage(getString(R.string.corpo_ja_rompido));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();


                    } else {
                        RupturaCorpoActivity.CODE = result.getContents();
                        final Intent intent1 = new Intent(ScanActivity.this, RupturaCorpoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getString(R.string.aviso));
                    alertDialog.setMessage(getString(R.string.corpo_nao_reg));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }
                break;
            case 1:
                for (Blocos bloco:
                        RupturaBlocoListActivity.BlocosList) {
                    if(bloco.getCodigo().equals(result.getContents())){
                        jaTem=true;
                    }

                }
                for (HashMap <String,String> corpo:
                RupturaBlocoActivity.atualLote.getCorpos()) {
                    if(corpo.get("codigo").equals(result.getContents())){
                        Possui = true;
                    }
                }
                if(Possui) {
                    if (jaTem) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getString(R.string.aviso));
                        alertDialog.setMessage(getString(R.string.corpo_ja_rompido));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();


                    } else {
                        RupturaBlocoActivity.CODE = result.getContents();
                        final Intent intent1 = new Intent(ScanActivity.this, RupturaBlocoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getString(R.string.aviso));
                    alertDialog.setMessage(getString(R.string.corpo_nao_reg));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }
                break;
            case 2:
                for (Prismas prisma:
                        RupturaPrismasListActivity.prismasList) {
                    if(prisma.getCodigo().equals(result.getContents())){
                        jaTem=true;
                    }

                }
                for (HashMap <String,String> corpo:
                        RupturaPrismaActivity.atualLote.getCorpos()) {
                    if(corpo.get("codigo").equals(result.getContents())){
                        Possui = true;
                    }
                }
                if(Possui) {
                    if (jaTem) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getString(R.string.aviso));
                        alertDialog.setMessage(getString(R.string.corpo_ja_rompido));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();


                    } else {
                        RupturaPrismaActivity.CODE = result.getContents();
                        final Intent intent1 = new Intent(ScanActivity.this, RupturaPrismaActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getString(R.string.aviso));
                    alertDialog.setMessage(getString(R.string.corpo_nao_reg));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }
                break;
            case 3:
                for (Pavimentos pavimento:
                        RupturaPavimentoListActivity.pavimentoList) {
                    if(pavimento.getCodigo().equals(result.getContents())){
                        jaTem=true;
                    }

                }
                for (HashMap <String,String> corpo:
                        RupturaPavimentoActivity.atualLote.getCorpos()) {
                    if(corpo.get("codigo").equals(result.getContents())){
                        Possui = true;
                    }
                }
                if(Possui) {
                    if (jaTem) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getString(R.string.aviso));
                        alertDialog.setMessage(getString(R.string.corpo_ja_rompido));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();


                    } else {
                        RupturaPavimentoActivity.CODE = result.getContents();
                        final Intent intent1 = new Intent(ScanActivity.this, RupturaPavimentoActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getString(R.string.aviso));
                    alertDialog.setMessage(getString(R.string.corpo_nao_reg));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_confirm),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                }
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

}
