package com.example.owner.testint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class StudentClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("users/" + user + "/Courses/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int)dataSnapshot.getChildrenCount();
                int position = 0;
                String[] courseNames = new String[count];
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    courseNames[position] = child.getKey();
                    position++;
                }

                displayCourses(courseNames);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



    }

    public void displayCourses(String[] courseNames)
    {
        ListAdapter adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseNames);
        ListView listview  = (ListView)findViewById(R.id.viewer);
        listview.setAdapter(adpt);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(getBaseContext(), courseView.class);
                        intent.putExtra("COURSE", food);
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }
}
