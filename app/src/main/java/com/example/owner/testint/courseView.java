package com.example.owner.testint;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class courseView extends AppCompatActivity  implements View.OnClickListener{

    private Button buttonBack;
    private Button buttonRegister;
    private Button buttonDrop;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);


        s = getIntent().getStringExtra("COURSE");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Course/" + s);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String prof = dataSnapshot.child("Professor").getValue(String.class);
                String name = dataSnapshot.child("CourseName").getValue(String.class);
                String end = dataSnapshot.child("TimeEnd").getValue(String.class);
                String start = dataSnapshot.child("TimeStart").getValue(String.class);

                displayCourse(prof, name, start, end);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonBack = (Button)findViewById(R.id.button3);
        buttonDrop = (Button)findViewById(R.id.button4);
        buttonRegister = (Button)findViewById(R.id.button2);


        buttonBack.setOnClickListener(this);
        buttonDrop.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    public void displayCourse(String prof, String name, String start, String end)
    {

        EditText par = (EditText)this.findViewById(R.id.professor);
        par.setText(prof);

        EditText pr = (EditText)this.findViewById(R.id.name);
        pr.setText(name);

        EditText praov = (EditText)this.findViewById(R.id.start);
        praov.setText(start);

        EditText golden = (EditText)this.findViewById(R.id.ender);
        golden.setText(end);


    }

    @Override
    public void onClick(View v) {
        if (v == buttonBack)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), courseList.class));
        }
        if (v == buttonRegister)
        {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users/" + user);
            myRef.child("Courses").child(s).setValue("Blargh? Blorggg");
            Toast.makeText(this, "You have joined this course",Toast.LENGTH_SHORT).show();
        }

        if (v==buttonDrop)
        {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users/" + user);
            myRef.child("Courses").child(s).setValue(null);
            String test =  myRef.child("Courses").child(s).getKey();
            Toast.makeText(this, "You have dropped this course",Toast.LENGTH_SHORT).show();
        }

    }
}
