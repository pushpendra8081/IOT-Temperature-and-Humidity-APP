package com.example.iottemphumidity;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {
    DatabaseReference mydb;
    TextView temp,hum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        temp = findViewById(R.id.temperature);
        hum= findViewById(R.id.humidity);
        mydb= FirebaseDatabase.getInstance().getReference().child("Sensor");
        try {

//            mydb.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    String tempdata = Objects.requireNonNull(dataSnapshot.child("temp").getValue()).toString();
//                    String humdata = Objects.requireNonNull(dataSnapshot.child("hum").getValue()).toString();
//                    temp.setText(tempdata);
//                    hum.setText(humdata);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Failed to read value
//
//                }

            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("temperature").exists() && dataSnapshot.child("humidity").exists()) {
                        // Retrieve temperature and humidity values
                        String tempdata = dataSnapshot.child("temperature").getValue(String.class);
                        String humdata = dataSnapshot.child("humidity").getValue(String.class);

                        // Update TextViews
                        temp.setText(tempdata);
                        hum.setText(humdata);
                        Log.d(TAG, "Value is: " + temp);
                        Log.d(TAG, "Value is: " + hum);
                    } else {
                        // Handle the case when the values are not available
                        // Display an error message or take appropriate action
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    Log.w(TAG,"failed to read value",error.toException());
                }
            });
        } catch(Exception ignored)
        {


        }


    }
}