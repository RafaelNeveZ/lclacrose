package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Adapter.WorkAdapter;
import com.lacrose.lc.lclacrose.Model.Obras;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity {
    private FirebaseAuth Auth;
    private final Context context = this;
    FirebaseDatabase database;
    DatabaseReference user_works_ref ,works_ref;
    private ProgressBar spinner;
    public TextView textEmpty;


    private final String TAG = "LOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        textEmpty=(TextView) findViewById(R.id.empyt_text);
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        showListOfWorks();


    }

    private void showListOfWorks() {
        textEmpty.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        final List<Obras> worksList = new ArrayList<>();
        final ListView workListView = (ListView) findViewById(R.id.normal_list);
        workListView.setDivider(null);
        final WorkAdapter workAdapter = new WorkAdapter(this, R.layout.item_work, worksList);
        workListView.setAdapter(workAdapter);
        user_works_ref = database.getReference(getString(R.string.user_tag)).child(Auth.getCurrentUser().getUid()).child(getString(R.string.work_tag));
        user_works_ref.keepSynced(true);
        works_ref = database.getReference(getString(R.string.work_tag));
        Log.e(TAG,"before");
        user_works_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG,"added");
                works_ref.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e(TAG,"not existis");
                        if (dataSnapshot.exists()) {
                            //    for(DataSnapshot d : dataSnapshot.getChildren()) {
                                    Obras obras = dataSnapshot.getValue(Obras.class);
                                    obras.setId(dataSnapshot.getKey());
                                    worksList.add(obras);
                                    Log.e(TAG,"foreach");
                           //     }
                                        Log.e(TAG,"sai do foreach");
                                    spinner.setVisibility(View.GONE);
                                    workAdapter.notifyDataSetChanged();
                            }else{
                                workAdapter.notifyDataSetChanged();
                                textEmpty.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        spinner.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Add the corresponding code for this case
                workAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Add the corresponding code for this case
                workAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //Add the corresponding code for this case
                workAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                spinner.setVisibility(View.GONE);
            }
        });


        /*work_lotes_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            spinner.setVisibility(View.GONE);
                            for(DataSnapshot d : dataSnapshot.getChildren()) {
                                Obras obras = d.getValue(Obras.class);
                                obras.setId(d.getKey());
                                worksList.add(obras);
                            }
                           workAdapter.notifyDataSetChanged();
                        }else{
                            workAdapter.notifyDataSetChanged();
                            textEmpty.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                            spinner.setVisibility(View.GONE);
                    }
                });*/
    }


    //================MENU============================\\
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.work_menu, menu);
        return true;
    }

    public void logOut(MenuItem item) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.setTitle(getString(R.string.dialog_logout_title));
        dialog.show();
        Button btCancel = (Button) dialog.findViewById(R.id.button_no);
        Button btLogOut = (Button) dialog.findViewById(R.id.button_yes);
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.signOut();
                Intent intent = new Intent(context,LoginActivity.class);
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
    }

    public void update(MenuItem item) {
        showListOfWorks();
    }
}
