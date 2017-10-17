package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Model.Lotes;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaActivity;
import com.lacrose.lc.lclacrose.ScanActivity;

import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class RupturaAdapter extends ArrayAdapter<Corpos> {
    Context context;
    public RupturaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RupturaAdapter(Context context, int resource, List<Corpos> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_ruptura, null);
        }

        final Corpos corpos = getItem(position);

        if (corpos != null) {
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView tipo = (TextView) view.findViewById(R.id.tv_type);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(corpos.getCodigo());
            }
            if (carga != null) {
                carga.setText(corpos.getCarga()+"");
            }
            if (tipo != null) {
                tipo.setText(corpos.getTipo());
            }
            if (data != null) {
                //data.setText(corpos.getData());
            }
        }

        return view;
    }

}
