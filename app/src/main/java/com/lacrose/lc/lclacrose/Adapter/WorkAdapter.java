package com.lacrose.lc.lclacrose.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.lacrose.lc.lclacrose.HomeActivity;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.CorpoMoldActivity;
import com.lacrose.lc.lclacrose.R;


import java.util.List;

/**
 * Created by rafae on 12/10/2017.
 */

public class WorkAdapter extends ArrayAdapter<Obras> {
    Context context;
    public WorkAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public WorkAdapter(Context context, int resource, List<Obras> items) {
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

        final Obras obras = getItem(position);

        if (obras != null) {
            TextView work_name = (TextView) view.findViewById(R.id.button_work);
            if (work_name != null) {
                work_name.setText(obras.getNome());
            }
            work_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,HomeActivity.class);
                    HomeActivity.WorkId = obras.getId();
                    HomeActivity.label = obras.getNome();
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

}
