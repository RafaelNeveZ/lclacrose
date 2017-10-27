package com.lacrose.lc.lclacrose;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Adapter.BlocoLoteAdapter;
import com.lacrose.lc.lclacrose.Adapter.CorpoLoteAdapter;
import com.lacrose.lc.lclacrose.Adapter.PavimentoLoteAdapter;
import com.lacrose.lc.lclacrose.Adapter.PrismaLoteAdapter;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.Model.CorpoLotes;
import com.lacrose.lc.lclacrose.Model.PavimentoLotes;
import com.lacrose.lc.lclacrose.Model.PrismaLotes;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LotesActivity extends MainActivity {
    FirebaseDatabase database;
    DatabaseReference work_lotes_corpo_ref;
    private ProgressBar spinner;
    public TextView textEmpty;
    public Spinner spinner_tipo_lote;
    ArrayList<String> tipoLoteList;
    public int ondeEstou = 0;
    List<CorpoLotes> corpoList;
    CorpoLoteAdapter corpoAdapter;
    List<BlocoLotes> blocoList;
    BlocoLoteAdapter blocoAdapter;
    List<PrismaLotes> prismaList;
    PrismaLoteAdapter prismaAdapter;
    List<PavimentoLotes> pavimentoList;
    PavimentoLoteAdapter pavimentoAdapter;
    private String tipoLote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        textEmpty=(TextView) findViewById(R.id.empyt_text);
        spinner_tipo_lote = (Spinner)findViewById(R.id.spinner_tipo_lote);
        database = FireBaseUtil.getDatabase();
        tipoLoteList = new ArrayList<>();
        tipoLoteList.add(getString(R.string.body));
        tipoLoteList.add(getString(R.string.bloco));
        tipoLoteList.add(getString(R.string.prisma));
        tipoLoteList.add(getString(R.string.pavimento_intertravado));
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_lote.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(tipoLoteList);
        spinnerAdapter.notifyDataSetChanged();
        spinner_tipo_lote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ondeEstou = position;
                switch (position) {
                    case 0:
                        tipoLote = getString(R.string.lote_corpo_tag);
                        showListOfLotes(getString(R.string.lote_corpo_tag),position);
                        break;
                    case 1:
                        tipoLote = getString(R.string.lote_bloco_tag);
                        showListOfLotes(getString(R.string.lote_bloco_tag),position);
                        break;
                    case 2:
                        tipoLote = getString(R.string.lote_prisma_tag);
                        showListOfLotes(getString(R.string.lote_prisma_tag),position);
                        break;
                    case 3:
                        tipoLote = getString(R.string.lote_pavimento_tag);
                        showListOfLotes(getString(R.string.lote_pavimento_tag),position);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showListOfLotes(String LoteTypetag, final int ondeEstou) {
        textEmpty.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        switch (ondeEstou) {
            case 0:
                corpoList = new ArrayList<>();
                final ListView lotesListView = (ListView) findViewById(R.id.normal_list);
                lotesListView.setDivider(null);
                corpoAdapter = new CorpoLoteAdapter(this, R.layout.item_work, corpoList);
                lotesListView.setAdapter(corpoAdapter);
                break;
            case 1:
                blocoList = new ArrayList<>();
                final ListView blocoListView = (ListView) findViewById(R.id.normal_list);
                blocoListView.setDivider(null);
                blocoAdapter = new BlocoLoteAdapter(this, R.layout.item_work, blocoList);
                blocoListView.setAdapter(blocoAdapter);
                break;
            case 2:
                prismaList = new ArrayList<>();
                final ListView prismaListView = (ListView) findViewById(R.id.normal_list);
                prismaListView.setDivider(null);
                prismaAdapter = new PrismaLoteAdapter(this, R.layout.item_work, prismaList);
                prismaListView.setAdapter(prismaAdapter);
                break;
            case 3:
                pavimentoList = new ArrayList<>();
                final ListView pavimentoListView = (ListView) findViewById(R.id.normal_list);
                pavimentoListView.setDivider(null);
                pavimentoAdapter = new PavimentoLoteAdapter(this, R.layout.item_work, pavimentoList);
                pavimentoListView.setAdapter(pavimentoAdapter);
                break;
        }
        work_lotes_corpo_ref = database.getReference(getString(R.string.work_tag)).child(HomeActivity.WorkId).child(LoteTypetag);
        work_lotes_corpo_ref.keepSynced(true);
        work_lotes_corpo_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    spinner.setVisibility(View.GONE);
                    switch (ondeEstou){
                        case 0:
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                CorpoLotes corpoLotes = d.getValue(CorpoLotes.class);
                                corpoLotes.setId(d.getKey());
                                corpoList.add(corpoLotes);
                            }
                            corpoAdapter.notifyDataSetChanged();
                            break;
                        case 1:
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                BlocoLotes blocoLotes = d.getValue(BlocoLotes.class);
                                blocoLotes.setId(d.getKey());
                                blocoList.add(blocoLotes);
                            }
                            blocoAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                PrismaLotes prismaLotes = d.getValue(PrismaLotes.class);
                                prismaLotes.setId(d.getKey());
                                prismaList.add(prismaLotes);
                            }
                            prismaAdapter.notifyDataSetChanged();
                            break;
                        case 3:
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                PavimentoLotes pavimentoLotes = d.getValue(PavimentoLotes.class);
                                pavimentoLotes.setId(d.getKey());
                                pavimentoList.add(pavimentoLotes);
                            }
                            pavimentoAdapter.notifyDataSetChanged();
                            break;
                    }
                } else {
                    switch (ondeEstou) {
                        case 0:
                            corpoAdapter.notifyDataSetChanged();
                            break;
                        case 1:
                            blocoAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            prismaAdapter.notifyDataSetChanged();
                            break;
                        case 3:
                            pavimentoAdapter.notifyDataSetChanged();
                            break;
                    }
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
        showListOfLotes(tipoLote,ondeEstou);
    }
}
