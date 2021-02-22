package com.example.whattodo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;

public interface OnGetDataListener {
    public void onStart();
    public void onSuccess(DataSnapshot data) throws ParseException;
    public void onFailed(DatabaseError databaseError);
}
