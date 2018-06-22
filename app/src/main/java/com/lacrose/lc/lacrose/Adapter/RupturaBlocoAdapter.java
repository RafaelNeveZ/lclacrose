package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lacrose.lc.lacrose.EditRupturaBlocoActivity;
import com.lacrose.lc.lacrose.Model.Blocos;
import com.lacrose.lc.lacrose.R;

import java.text.SimpleDateFormat;
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
            RelativeLayout buttonWok = (RelativeLayout) view.findViewById(R.id.button_work);
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView largura = (TextView) view.findViewById(R.id.tv_largura);
            TextView altura = (TextView) view.findViewById(R.id.tv_altura);
            TextView comprimiento = (TextView) view.findViewById(R.id.tv_comprimento);
            TextView res = (TextView) view.findViewById(R.id.tv_res);
            TextView esp_long = (TextView) view.findViewById(R.id.tv_esp_long);
            TextView esp_trans = (TextView) view.findViewById(R.id.tv_esp_trans);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(blocos.getCodigo());
            }
            if (carga != null) {
                carga.setText(blocos.getCarga()+"");
            }
            if (res != null) {
                res.setText(blocos.getResistencia()+"");
            }
            if (altura != null) {
                altura.setText(""+blocos.getAltura()+"");
            }
            if (largura != null) {
                largura.setText(""+blocos.getLargura()+"");
            }
            if (comprimiento != null) {
                comprimiento.setText(""+blocos.getComprimento()+"");
            }
            if (esp_long != null) {
                esp_long.setText(""+blocos.getEsspesura_Long());
            }
            if (esp_trans != null) {
                esp_trans.setText(""+blocos.getEsspesura_Tranv());
            }
            if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(blocos.getDataCreate()));
            }
            buttonWok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditRupturaBlocoActivity.editBloco = blocos;
                    Intent intent = new Intent(context, EditRupturaBlocoActivity.class);
                    context.startActivity(intent);
                }
            });

        }

        return view;
    }

}
