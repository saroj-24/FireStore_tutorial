package com.example.firestore_tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name,email,course,imageurl,year;
    Button addbutton,backbutton;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.nametxt);
        email = findViewById(R.id.emailtxt);
        course = findViewById(R.id.coursetxt);
        year = findViewById(R.id.yeartxt);
        imageurl = findViewById(R.id.imageurl);
        addbutton = findViewById(R.id.addbtn);
        backbutton = findViewById(R.id.bckbtn);

        firestore= FirebaseFirestore.getInstance();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
                clean();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    private  void insertdata()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("email",email.getText().toString());
        map.put("course",course.getText().toString());
        map.put("year", year.getText().toString());
        map.put("image",imageurl.getText().toString());

            firestore.collection("student").add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Error while inserting Data", Toast.LENGTH_SHORT).show();
                        }
                    });
    }
    private  void clean()
    {
        name.setText(" ");
        email.setText(" ");
        imageurl.setText(" ");
        course.setText(" ");
    }
}