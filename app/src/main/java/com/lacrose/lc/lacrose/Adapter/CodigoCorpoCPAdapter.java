package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.R;

import java.util.HashMap;
import java.util.List;


public class CodigoCorpoCPAdapter extends ArrayAdapter<HashMap<String,String>> {
    Context context;
    public CodigoCorpoCPAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CodigoCorpoCPAdapter(Context context, int resource, List<HashMap<String,String>> items) {
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
            view = viewLayout.inflate(R.layout.item_codigo_corpos_cp, null);
        }

        final String codigo = getItem(position).get("codigo");
        final String idade = getItem(position).get("idade");
        final int pos = position;
        if (codigo != null) {
            TextView tv_codigo = (TextView) view.findViewById(R.id.tv_code);
            tv_codigo.setText(codigo);
            TextView tv_idade = (TextView) view.findViewById(R.id.idade_tv);
            tv_idade.setText(idade);
            Button bt_delete = (Button) view.findViewById(R.id.delete_bt);
            bt_delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(getItem(pos));

                }
            });
        }

        return view;
    }

}
