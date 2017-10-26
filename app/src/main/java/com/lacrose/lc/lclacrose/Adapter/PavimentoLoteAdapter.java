package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Model.PavimentoLotes;
import com.lacrose.lc.lclacrose.Model.Pavimentos;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaCorpoActivity;
import com.lacrose.lc.lclacrose.RupturaPavimentoActivity;
import com.lacrose.lc.lclacrose.RupturaPavimentoListActivity;
import com.lacrose.lc.lclacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class PavimentoLoteAdapter extends ArrayAdapter<PavimentoLotes> {
    Context context;
    public PavimentoLoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PavimentoLoteAdapter(Context context, int resource, List<PavimentoLotes> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        View view = convertView;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_work, null);
        }

        final PavimentoLotes pavimentoLotes = getItem(position);

        if (pavimentoLotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.pavimento_intertravado_reduc)+": "+ pavimentoLotes.getCodigo());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database.getReference(context.getString(R.string.time_stamp_tag)).setValue(ServerValue.TIMESTAMP);
                    database.getReference(context.getString(R.string.time_stamp_tag)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            RupturaPavimentoActivity.Hoje = (long) dataSnapshot.getValue();
                            RupturaPavimentoActivity.atualLote = pavimentoLotes;
                            ScanActivity.ondeEstou = 3;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaPavimentoListActivity.pavimentoList = new ArrayList<Pavimentos>();
                            context.startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });
        }

        return view;
    }

}
