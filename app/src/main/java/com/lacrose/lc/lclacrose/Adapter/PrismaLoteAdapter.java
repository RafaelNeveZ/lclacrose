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
import com.lacrose.lc.lclacrose.Model.PrismaLotes;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaCorpoActivity;
import com.lacrose.lc.lclacrose.RupturaPrismaActivity;
import com.lacrose.lc.lclacrose.RupturaPrismasListActivity;
import com.lacrose.lc.lclacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;

import static com.lacrose.lc.lclacrose.R.string.corpoLotes;

/**
 * Created by rafae on 12/10/2017.
 */

public class PrismaLoteAdapter extends ArrayAdapter<PrismaLotes> {
    Context context;
    public PrismaLoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PrismaLoteAdapter(Context context, int resource, List<PrismaLotes> items) {
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

        final PrismaLotes prismaLotes = getItem(position);

        if (prismaLotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.prisma)+": "+ prismaLotes.getCodigo());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database.getReference(context.getString(R.string.time_stamp_tag)).setValue(ServerValue.TIMESTAMP);
                    database.getReference(context.getString(R.string.time_stamp_tag)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            RupturaPrismaActivity.Hoje = (long) dataSnapshot.getValue();
                            RupturaPrismaActivity.atualLote = prismaLotes;
                            ScanActivity.ondeEstou = 2;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaPrismasListActivity.prismasList = new ArrayList<Prismas>();
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
