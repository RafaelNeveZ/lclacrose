package com.lacrose.lc.lclacrose.Util;

import com.google.firebase.database.FirebaseDatabase;


public class FireBaseUtil  {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
