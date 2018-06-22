package com.lacrose.lc.lacrose.Util;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class FireBaseUtil  {

    private static FirebaseDatabase mDatabase;
    private static FirebaseFirestore mDatabases;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static FirebaseFirestore getFireDatabase() {
        if (mDatabases == null) {
            mDatabases = FirebaseFirestore.getInstance();
        }
        return mDatabases;
    }


}
