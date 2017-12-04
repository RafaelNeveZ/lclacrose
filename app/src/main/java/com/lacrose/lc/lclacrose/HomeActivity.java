package com.lacrose.lc.lclacrose;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;
import static android.Manifest.permission.CAMERA;


public class HomeActivity extends MainActivity {
    private static final int PERMISSION_REQUEST_CODE = 7;
    public static String label;
    public static  String WorkId;

   // FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(label);
        ScanActivity.primeiraVez = true;
        RupturaBlocoActivity.jaPerguntei = false;
        RupturaCorpoActivity.jaPerguntei = false;
        RupturaPavimentoActivity.jaPerguntei = false;
        RupturaPrismaActivity.jaPerguntei = false;
       /* DatabaseReference user_works_ref;
        database = FireBaseUtil.getDatabase();
        user_works_ref = database.getReference(getString(R.string.work_tag)).child(WorkId);
        user_works_ref.keepSynced(true);*/
    }

    public void scanButton(View view) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Intent intent = new Intent(HomeActivity.this, LotesActivity.class);
                startActivity(intent);
            } else {
                requestPermission();
            }
        }
    }
    public void moldButton(View view) {
        Intent intent = new Intent(HomeActivity.this, ChooseMoldActivity.class);
        startActivity(intent);
    }

    //PERMISSIONS===============================================================
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Intent intent = new Intent(HomeActivity.this, LotesActivity.class);
                        startActivity(intent);
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel(getString(R.string.dialog_permission),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    //========================================================================

}
