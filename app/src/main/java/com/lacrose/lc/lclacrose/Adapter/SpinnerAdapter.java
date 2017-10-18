package com.lacrose.lc.lclacrose.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lacrose.lc.lclacrose.R;

/**
 * Created by rafae on 18/10/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        String item = getItem(position);


        return view;
    }



}
