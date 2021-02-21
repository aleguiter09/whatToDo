package com.example.whattodo.model;

import androidx.annotation.NonNull;

import com.example.whattodo.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    private DatabaseReference databaseReference;

    public DatabaseReference getDatabaseReference() {
        return this.databaseReference;
    }

    public Database() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void mReadDataOnce(String child, final OnGetDataListener listener) {
        listener.onStart();
        databaseReference.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }
}
