package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lclacrose.Adapter.RupturaAdapter;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.List;


public class RupturaListActivity extends MainActivity {
    public static  List<Corpos> CorposList;
    private final Context context=this;
    DatabaseReference corpo_ref;
    FirebaseDatabase database;
    int ListSize = 0;


    //TODO LAYOOUT SCROLLVIEW
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        database = FirebaseDatabase.getInstance();
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);

        rupturaListView.setDivider(null);
        RupturaAdapter corposAdapter = new RupturaAdapter(this, R.layout.item_ruptura, CorposList);
        rupturaListView.setAdapter(corposAdapter);
    }

    public void saveRuptura(View view) {
        showProgress(getString(R.string.saving));
        corpo_ref = database.getReference(getString(R.string.work_tag)).child(MoldActivity.WorkId+"").child(getString(R.string.lote_tag)).child(RupturaActivity.atualLote.getId());
        for (Corpos corpo:CorposList) {
            corpo_ref.child(getString(R.string.corpos)).push().setValue(corpo).addOnCompleteListener(this,new OnCompleteListener(){
                        @Override
                        public void onComplete(@NonNull Task task) {
                            ListSize++;
                            if(task.isSuccessful()) {
                                if(ListSize>=CorposList.size()) {
                                    dismissProgress();
                                    Toast.makeText(context,getString(R.string.rupturas_create_sucess),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RupturaListActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(context,getString(R.string.server_error),Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                dismissProgress();
                                Log.e(TAG,task.getException().toString());
                            }
                        };
        });

    }
}

    public void onCancel(View view) {
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
