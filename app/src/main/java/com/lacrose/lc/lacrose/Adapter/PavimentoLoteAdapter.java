package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.Model.PavimentoLotes;
import com.lacrose.lc.lacrose.R;
import com.lacrose.lc.lacrose.RupturaPavimentoActivity;
import com.lacrose.lc.lacrose.RupturaPavimentoListActivity;
import com.lacrose.lc.lacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class PavimentoLoteAdapter extends ArrayAdapter<PavimentoLotes> {
    Context context;
    public PavimentoLoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PavimentoLoteAdapter(Context context, int resource, List<PavimentoLotes> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        View view = convertView;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_work, null);
        }

        final PavimentoLotes pavimentoLotes = getItem(position);

        if (pavimentoLotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.pavimento_intertravado_reduc)+": "+ pavimentoLotes.getCodigo());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            RupturaPavimentoActivity.atualLote = pavimentoLotes;
                            ScanActivity.ondeEstou = 3;
                            ScanActivity.primeiraVez = true;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaPavimentoListActivity.pavimentoList = new ArrayList<>();
                            context.startActivity(intent);



                }
            });
        }

        return view;
    }

}
