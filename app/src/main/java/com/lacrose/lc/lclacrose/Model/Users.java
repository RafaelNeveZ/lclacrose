package com.lacrose.lc.lclacrose.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafae on 07/10/2017.
 */

public class Users {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String name;
    public int id;
    public int age;
    private final String WORK_TAG ="Obras";


    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String username, int id, int age) {
        this.name = username;
        this.id = id;
        this.age = age;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
      //  result.put(WORK_TAG, uid);
        return result;
    }
}
