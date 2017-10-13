package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lacrose.lc.lclacrose.HomeActivity;
import com.lacrose.lc.lclacrose.Model.Lotes;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.MoldActivity;
import com.lacrose.lc.lclacrose.R;
import com.lacrose.lc.lclacrose.RulpturaActivity;
import com.lacrose.lc.lclacrose.ScanActivity;

import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class LoteAdapter extends ArrayAdapter<Lotes> {
    Context context;
    public LoteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LoteAdapter(Context context, int resource, List<Lotes> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater viewLayout;
            viewLayout = LayoutInflater.from(getContext());
            view = viewLayout.inflate(R.layout.item_work, null);
        }

        final Lotes lotes = getItem(position);

        if (lotes != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(context.getString(R.string.lote)+": "+lotes.getCodigo());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanActivity.class);
                    RulpturaActivity.LOTE_ID = lotes.getId();
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

}
