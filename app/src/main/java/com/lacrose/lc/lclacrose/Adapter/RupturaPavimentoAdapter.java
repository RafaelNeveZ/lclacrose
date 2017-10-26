package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.Model.Pavimentos;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaPavimentoActivity;
import com.lacrose.lc.lclacrose.RupturaPrismaActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class RupturaPavimentoAdapter extends ArrayAdapter<Pavimentos> {
    Context context;
    public RupturaPavimentoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RupturaPavimentoAdapter(Context context, int resource, List<Pavimentos> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_ruptura_pavimento, null);
        }

        final Pavimentos pavimentos = getItem(position);

        if (pavimentos != null) {
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView largura = (TextView) view.findViewById(R.id.tv_largura);
            TextView altura = (TextView) view.findViewById(R.id.tv_altura);
            TextView comprimiento = (TextView) view.findViewById(R.id.tv_comprimento);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(pavimentos.getCodigo());
            }
            if (carga != null) {
                carga.setText(pavimentos.getCarga()+"");
            }
            if (altura != null) {
                altura.setText(""+pavimentos.getAltura());
            }
            if (largura != null) {
                largura.setText(""+pavimentos.getLargura());
            }
            if (comprimiento != null) {
                comprimiento.setText(""+pavimentos.getComprimento());
            }
            if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(RupturaPavimentoActivity.Hoje));
            }
        }

        return view;
    }

}
