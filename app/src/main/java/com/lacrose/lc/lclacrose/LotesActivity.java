package com.lacrose.lc.lclacrose;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Adapter.BlocoLoteAdapter;
import com.lacrose.lc.lclacrose.Adapter.CorpoLoteAdapter;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LotesActivity extends MainActivity {
    private FirebaseAuth Auth;
    private final Context context = this;
    FirebaseDatabase database;
    DatabaseReference work_lotes_corpo_ref, work_lotes_bloco_ref;
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
        final List<CorpoLotes> corpoLotesList = new ArrayList<>();
        final ListView lotesListView = (ListView) findViewById(R.id.normal_list);
        lotesListView.setDivider(null);
        final CorpoLoteAdapter corpoLoteAdapter = new CorpoLoteAdapter(this, R.layout.item_work, corpoLotesList);
        lotesListView.setAdapter(corpoLoteAdapter);

        final List<BlocoLotes> blocoLotesList = new ArrayList<>();
        final ListView blocoListView = (ListView) findViewById(R.id.bloco_list);
        blocoListView.setDivider(null);
        final BlocoLoteAdapter blocoAdapter = new BlocoLoteAdapter(this, R.layout.item_work, blocoLotesList);
        blocoListView.setAdapter(blocoAdapter);

        work_lotes_corpo_ref = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId).child(getString(R.string.lote_corpo_tag));
        work_lotes_corpo_ref.keepSynced(true);
        work_lotes_corpo_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    spinner.setVisibility(View.GONE);
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        CorpoLotes corpoLotes = d.getValue(CorpoLotes.class);
                        corpoLotes.setId(d.getKey());
                        corpoLotesList.add(corpoLotes);
                    }
                    corpoLoteAdapter.notifyDataSetChanged();
                } else {
                    corpoLoteAdapter.notifyDataSetChanged();
                    textEmpty.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                spinner.setVisibility(View.GONE);
            }
        });
        work_lotes_bloco_ref = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId).child(getString(R.string.lote_bloco_tag));
        work_lotes_bloco_ref.keepSynced(true);
        work_lotes_bloco_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    spinner.setVisibility(View.GONE);
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        BlocoLotes blocoLotes = d.getValue(BlocoLotes.class);
                        blocoLotes.setId(d.getKey());
                        blocoLotesList.add(blocoLotes);
                    }
                    blocoAdapter.notifyDataSetChanged();
                } else {
                    blocoAdapter.notifyDataSetChanged();
                    textEmpty.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
