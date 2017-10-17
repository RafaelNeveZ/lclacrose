package com.lacrose.lc.lclacrose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.lacrose.lc.lclacrose.Adapter.RupturaAdapter;
import com.lacrose.lc.lclacrose.Adapter.WorkAdapter;
import com.lacrose.lc.lclacrose.Model.Corpos;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class RupturaListActivity extends MainActivity {
        public static  List<Corpos> CorposList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruptura_list);
        List<Corpos> newVo=new ArrayList<>();
      /*  Corpos corpo = new Corpos();
        corpo.setCodigo("12");
        corpo.setCarga(52);
        corpo.setTipo("A");
        newVo.add(corpo);*/
        ListView rupturaListView = (ListView) findViewById(R.id.ruptura_list);
        rupturaListView.setDivider(null);
        RupturaAdapter corposAdapter = new RupturaAdapter(this, R.layout.item_ruptura, newVo);
        rupturaListView.setAdapter(corposAdapter);
        for (Corpos item: CorposList
             ) {
            newVo.add(item);

        }
        corposAdapter.notifyDataSetChanged();
    }
}
