package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lacrose.lc.lclacrose.Adapter.RupturaBlocoAdapter;
import com.lacrose.lc.lclacrose.Model.Blocos;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.Collection;
import java.util.List;


public class RupturaBlocoListActivity extends MainActivity {
    public static List<Blocos> BlocosList;
    private final Context context = this;
    CollectionReference corpo_ref;
    FirebaseFirestore database;
    int ListSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        database = FireBaseUtil.getFireDatabase();
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);
        rupturaListView.setDivider(null);
        RupturaBlocoAdapter blocosAdapter = new RupturaBlocoAdapter(this, R.layout.item_ruptura_bloco, BlocosList);
        rupturaListView.setAdapter(blocosAdapter);
    }

    public void saveRuptura(View view) {
        showProgress(getString(R.string.saving));
        corpo_ref = database.collection(getString(R.string.work_tag) + "/" + HomeActivity.WorkId + "/" + getString(R.string.lote_tag) + "/" + RupturaBlocoActivity.atualLote.getId() + "/corpos");
        for (Blocos blocos : BlocosList) {
            corpo_ref.add(blocos).addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    ListSize++;
                    if (task.isSuccessful()) {
                        if(ListSize>=BlocosList.size()) {
                        dismissProgress();
                        Toast.makeText(context,getString(R.string.rupturas_create_sucess),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RupturaBlocoListActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        }else{
                            dismissProgress();
                        }
                    } else {
                        dismissProgress();
                    }

                }
            });
        }



}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setTitle(getString(R.string.dialog_cancel_ruptura));
        dialog.show();
        TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
        title.setText(getString(R.string.dialog_cancel_ruptura));
        Button btCancel = (Button) dialog.findViewById(R.id.button_no);
        Button btYes = (Button) dialog.findViewById(R.id.button_yes);
        btYes.setText(getString(R.string.yes));
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}
