package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.Model.Blocos;
import com.lacrose.lc.lclacrose.Model.Prismas;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RupturaBlocoActivity;
import com.lacrose.lc.lclacrose.RupturaPrismaActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class RupturaPrismaAdapter extends ArrayAdapter<Prismas> {
    Context context;
    public RupturaPrismaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RupturaPrismaAdapter(Context context, int resource, List<Prismas> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_ruptura_prisma, null);
        }

        final Prismas prismas = getItem(position);

        if (prismas != null) {
            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView carga = (TextView) view.findViewById(R.id.tv_carga);
            TextView largura = (TextView) view.findViewById(R.id.tv_largura);
            TextView altura = (TextView) view.findViewById(R.id.tv_altura);
            TextView comprimiento = (TextView) view.findViewById(R.id.tv_comprimento);
            TextView data = (TextView) view.findViewById(R.id.tv_data);

            if (code != null) {
                code.setText(prismas.getCodigo());
            }
            if (carga != null) {
                carga.setText(prismas.getCarga()+"");
            }
            if (altura != null) {
                altura.setText(""+prismas.getAltura());
            }
            if (largura != null) {
                largura.setText(""+prismas.getLargura());
            }
            if (comprimiento != null) {
                comprimiento.setText(""+prismas.getComprimento());
            }
            if (data != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
                data.setText(fmtOut.format(RupturaPrismaActivity.Hoje));
            }
        }

        return view;
    }

}
