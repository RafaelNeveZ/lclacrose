package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.Model.CorpoLotes;
import com.lacrose.lc.lacrose.R;
import com.lacrose.lc.lacrose.RupturaCorpoActivity;
import com.lacrose.lc.lacrose.RupturaCorpoListActivity;
import com.lacrose.lc.lacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;



public class CorpoLoteAdapter extends ArrayAdapter<CorpoLotes> {
    Context context;
    public CorpoLoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CorpoLoteAdapter(Context context, int resource, List<CorpoLotes> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override @NonNull
    public View getView(int position, final View convertView,@NonNull ViewGroup parent) {

        View view = convertView;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_work, null);
        }

        final CorpoLotes corpoLotes = getItem(position);

        if (corpoLotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.body)+": "+ corpoLotes.getCodigo());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View v) {


                            RupturaCorpoActivity.atualLote = corpoLotes;
                            ScanActivity.ondeEstou = 0;
                            ScanActivity.primeiraVez = true;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaCorpoListActivity.CorposList = new ArrayList<>();
                            context.startActivity(intent);


                }
            });
        }

        return view;
    }



}
