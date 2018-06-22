package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lacrose.lc.lacrose.EditRupturaPavimentoActivity;
import com.lacrose.lc.lacrose.Model.Pavimentos;
import com.lacrose.lc.lacrose.R;

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
            RelativeLayout buttonWok = (RelativeLayout) view.findViewById(R.id.button_work);
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView largura = (TextView) view.findViewById(R.id.tv_largura);
            TextView altura = (TextView) view.findViewById(R.id.tv_altura);
            TextView comprimiento = (TextView) view.findViewById(R.id.tv_comprimento);
            TextView res = (TextView) view.findViewById(R.id.tv_res);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(pavimentos.getCodigo());
            }
            if (res != null) {
                res.setText(pavimentos.getResistencia()+"");
            }
            if (carga != null) {
                carga.setText(pavimentos.getCarga()+"");
            }
            if (altura != null) {
                altura.setText(pavimentos.getAltura()+"");
            }
            if (largura != null) {
                largura.setText(pavimentos.getLargura()+"");
            }
            if (comprimiento != null) {
                comprimiento.setText(pavimentos.getComprimento() + "");
            }
                if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(pavimentos.getDataCreate()));
            }
            buttonWok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditRupturaPavimentoActivity.editPavimento = pavimentos;
                    Intent intent = new Intent(context, EditRupturaPavimentoActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

}
