package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.Model.PrismaLotes;
import com.lacrose.lc.lacrose.R;
import com.lacrose.lc.lacrose.RupturaPrismaActivity;
import com.lacrose.lc.lacrose.RupturaPrismasListActivity;
import com.lacrose.lc.lacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;

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
                            RupturaPrismaActivity.atualLote = prismaLotes;
                            ScanActivity.ondeEstou = 2;
                            ScanActivity.primeiraVez = true;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaPrismasListActivity.prismasList = new ArrayList<>();
                            context.startActivity(intent);

                }
            });
        }

        return view;
    }

}
