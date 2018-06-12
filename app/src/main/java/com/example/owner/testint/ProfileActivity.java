package com.example.owner.testint;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView email;
    private Button buttonLogout;
    private Button courseList;
    private Button schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();


        email = (TextView)findViewById(R.id.displayEmail);
        email.setText(user.getEmail());
        buttonLogout = (Button)findViewById(R.id.buttonSignin);
        courseList = (Button)findViewById(R.id.courseList);
        schedule = (Button)findViewById(R.id.button5);

        buttonLogout.setOnClickListener(this);
        courseList.setOnClickListener(this);
        schedule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==buttonLogout)
        {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if(v==courseList)
        {
            finish();
            startActivity(new Intent(this, courseList.class));
        }
        if(v==schedule)
        {
            finish();
            startActivity(new Intent(this,StudentClass.class));
        }
    }
}
