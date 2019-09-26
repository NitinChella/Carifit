package com.example.android.carifit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CharityActivity extends AppCompatActivity {


    int totsteps = 0;
    double amount = 0;
    ArrayList<Integer> stepList;
    /*private String mUserId1;
    private FirebaseUser mfu;
    private DatabaseReference mdata;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity);

        stepList = new ArrayList<Integer>();

        //mdata = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase.getInstance().getReference().child("users").child("Person")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            int stepvalue = snapshot.getValue(Integer.class);

                            /*if(snapshot.getValue()==null)
                            {
                            }*/

                            //add person to your list
                            stepList.add(stepvalue);
                            //create a list view, and add the apapter, passing in your list


                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        for(int i: stepList){

            totsteps = totsteps+i;

        }

        amount = totsteps*0.1;

        TextView t = (TextView)findViewById(R.id.textView3);
        t.setText(""+amount);

    }

    public void donateCharity(View view){

        Intent intent = new Intent(this,DonateCharityActivity.class);

        startActivity(intent);

    }



}
