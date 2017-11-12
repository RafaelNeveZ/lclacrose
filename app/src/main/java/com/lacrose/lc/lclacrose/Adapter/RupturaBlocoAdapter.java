package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ServerValue;
import com.lacrose.lc.lclacrose.Model.Blocos;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaBlocoActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class RupturaBlocoAdapter extends ArrayAdapter<Blocos> {
    Context context;
    public RupturaBlocoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RupturaBlocoAdapter(Context context, int resource, List<Blocos> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_ruptura_bloco, null);
        }

        final Blocos blocos = getItem(position);

        if (blocos != null) {
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView largura = (TextView) view.findViewById(R.id.tv_largura);
            TextView altura = (TextView) view.findViewById(R.id.tv_altura);
            TextView comprimiento = (TextView) view.findViewById(R.id.tv_comprimento);
            TextView esp_long = (TextView) view.findViewById(R.id.tv_esp_long);
            TextView esp_trans = (TextView) view.findViewById(R.id.tv_esp_trans);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(blocos.getCodigo());
            }
            if (carga != null) {
                carga.setText(blocos.getCarga()+"");
            }
            if (altura != null) {
                altura.setText(""+blocos.getDim().get("altura"));
            }
            if (largura != null) {
                largura.setText(""+blocos.getDim().get("largura"));
            }
            if (comprimiento != null) {
                comprimiento.setText(""+blocos.getDim().get("comprimento"));
            }
            if (esp_long != null) {
                esp_long.setText(""+blocos.getEspessura_longitudinal());
            }
            if (esp_trans != null) {
                esp_trans.setText(""+blocos.getEspessura_transvessal());
            }
            if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(RupturaBlocoActivity.Hoje));
            }
        }

        return view;
    }

}
