package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Adapter.LoteAdapter;
import com.lacrose.lc.lclacrose.Adapter.WorkAdapter;
import com.lacrose.lc.lclacrose.Model.Lotes;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.R;

import java.util.ArrayList;
import java.util.List;

public class LotesActivity extends AppCompatActivity {
    private FirebaseAuth Auth;
    private final Context context = this;
    FirebaseDatabase database;
    DatabaseReference user_works_ref;
    private ProgressBar spinner;
    public TextView textEmpty;

    private final String TAG = "LOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        textEmpty=(TextView) findViewById(R.id.empyt_text);
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        showListOfLotes();

    }

    private void showListOfLotes() {
        textEmpty.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        final List<Lotes> worksList = new ArrayList<>();
        final ListView lotesListView = (ListView) findViewById(R.id.work_list);
        lotesListView.setDivider(null);
        final LoteAdapter loteAdapter = new LoteAdapter(this, R.layout.item_work, worksList);
        lotesListView.setAdapter(loteAdapter);
        user_works_ref = database.getReference(getString(R.string.work_tag)).child(MoldActivity.WorkId).child(getString(R.string.lote_tag));
        user_works_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    spinner.setVisibility(View.GONE);
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Lotes lotes = d.getValue(Lotes.class);
                        lotes.setId(d.getKey());
                        worksList.add(lotes);
                    }
                    loteAdapter.notifyDataSetChanged();
                } else {
                    loteAdapter.notifyDataSetChanged();
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


    //================MENU============================\\
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lote_menu, menu);
        return true;
    }
    public void update(MenuItem item) {
        showListOfLotes();
    }
}
