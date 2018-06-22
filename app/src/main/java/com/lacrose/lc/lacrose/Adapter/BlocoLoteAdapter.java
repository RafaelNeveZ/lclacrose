package com.lacrose.lc.lacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.lacrose.lc.lacrose.Model.BlocoLotes;
import com.lacrose.lc.lacrose.R;
import com.lacrose.lc.lacrose.RupturaBlocoActivity;
import com.lacrose.lc.lacrose.RupturaBlocoListActivity;
import com.lacrose.lc.lacrose.ScanActivity;

import java.util.ArrayList;
import java.util.List;


public class BlocoLoteAdapter extends ArrayAdapter<BlocoLotes> {
    Context context;
    public BlocoLoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public BlocoLoteAdapter(Context context, int resource, List<BlocoLotes> items) {
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
            view = viewLayout.inflate(R.layout.item_work, null);
        }

        final BlocoLotes blocoLotes = getItem(position);

        if (blocoLotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.bloco)+": "+ blocoLotes.getCodigo());
            }
            work_name.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            RupturaBlocoActivity.atualLote = blocoLotes;
                            ScanActivity.ondeEstou = 1;
                            ScanActivity.primeiraVez = true;
                            Intent intent = new Intent(context, ScanActivity.class);
                            RupturaBlocoListActivity.BlocosList = new ArrayList<>();
                            context.startActivity(intent);


                }
            });
        }

        return view;
    }

}
