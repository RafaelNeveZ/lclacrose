package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class WorkActivity extends AppCompatActivity {
    private FirebaseAuth Auth;
    private final Context context = this;
    FirebaseDatabase database;
    DatabaseReference user_works_ref;
    private final String TAG = "LOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        showListOfWorks();
    }

    private void showListOfWorks() {
       // user_works_ref = database.getReference(getString(R.string.user_tag));
        user_works_ref = database.getReference(getString(R.string.user_tag)).child(Auth.getCurrentUser().getUid()).child("Obras");
        user_works_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<Integer> teste = (ArrayList<Integer>) dataSnapshot.getValue();
                        Log.e(TAG," "+ teste);
                        DatabaseReference works_ref;
                   //     works_ref = database.getReference("Obras").equalTo();
                      //  showIds((Map<String,Object>) dataSnapshot.getValue());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }
    private void showIds(Map<String,Object> users) {

        ArrayList<ArrayList<Integer>> ids = new ArrayList<>();
        int i=0;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            ids.add((ArrayList<Integer>) singleUser.get("Obras"));
            Log.e(TAG,"entrei " +i);
            i++;
        }

        Log.e(TAG,ids.toString());
    }


    //================MENU============================\\
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.work_menu, menu);
        return true;
    }

    public void logOut(MenuItem item) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setTitle(getString(R.string.dialog_logout_title));
        dialog.show();
        Button btCancel = (Button) dialog.findViewById(R.id.cancel_logout);
        Button btLogOut = (Button) dialog.findViewById(R.id.confirm_logout);
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.signOut();
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);
                finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
