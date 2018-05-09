package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.EditRupturaBlocoActivity;
import com.lacrose.lc.lclacrose.EditRupturaCorpoActivity;
import com.lacrose.lc.lclacrose.Model.CP;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaCorpoActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class RupturaCorpoAdapter extends ArrayAdapter<CP> {
    Context context;
    public RupturaCorpoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RupturaCorpoAdapter(Context context, int resource, List<CP> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_ruptura_corpo, null);
        }

        final CP corpos = getItem(position);

        if (corpos != null) {
            RelativeLayout buttonWok = (RelativeLayout) view.findViewById(R.id.button_work);
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView tipo = (TextView) view.findViewById(R.id.tv_type);
            TextView data = (TextView) view.findViewById(R.id.tv_data);
            TextView res = (TextView) view.findViewById(R.id.tv_res);
            TextView idade = (TextView) view.findViewById(R.id.tv_idade);

            if (code != null) {
                code.setText(corpos.getCodigo());
            }
            if (carga != null) {
                carga.setText(corpos.getCarga()+"");
            }
            if (res != null) {
                res.setText(corpos.getResistencia()+"");
            }
            if (tipo != null) {
                tipo.setText(corpos.getType());
            }
            if (idade != null) {
                tipo.setText(corpos.getIdade()+"");
            }
            if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(corpos.getDataCreate()));
            }
            buttonWok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditRupturaCorpoActivity.editCorpo = corpos;
                    Intent intent = new Intent(context, EditRupturaCorpoActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

}
