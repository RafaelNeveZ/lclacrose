package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.lacrose.lc.lclacrose.Model.BlocoLotes;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaBlocoActivity;
import com.lacrose.lc.lclacrose.RupturaBlocoListActivity;
import com.lacrose.lc.lclacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;


public class CodigoCorpoAdapter extends ArrayAdapter<String> {
    Context context;
    public CodigoCorpoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CodigoCorpoAdapter(Context context, int resource, List<String> items) {
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
            view = viewLayout.inflate(R.layout.item_codigo_corpos, null);
        }

        final String codigo = getItem(position);

        if (codigo != null) {
            TextView tv_codigo = (TextView) view.findViewById(R.id.tv_code);
            tv_codigo.setText(codigo);
            Button bt_delete = (Button) view.findViewById(R.id.delete_bt);
            bt_delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(codigo);

                }
            });
        }

        return view;
    }

}
