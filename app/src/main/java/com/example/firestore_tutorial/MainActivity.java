package com.example.firestore_tutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText name,course,year,email;
    Button savebtn;

    RecyclerView  rv;
    ArrayList<MainModel> mainModelArrayList;
    MainAdapter adapter;
    FirebaseFirestore firestore;


    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv =findViewById(R.id.recycleview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = findViewById(R.id.floatingActionButton);

        firestore = FirebaseFirestore.getInstance();// store all the values of firebasefirsestore in firestore instance

        FirestoreRecyclerOptions<MainModel> options = new FirestoreRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseFirestore.getInstance().collection("student").orderBy("name"), MainModel.class)
                .build();

        adapter = new MainAdapter(options);
        rv.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
  /*  private void EventchangeListner() {
      firestore.collection("student").orderBy("name", Query.Direction.ASCENDING)
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                  @Override
                  public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                      if(error != null)
                      {
                          Log.e("Error message",error.getMessage());
                          return;
                      }
                      for(DocumentChange dc :value.getDocumentChanges())
                      {

                          if(dc.getType()==DocumentChange.Type.ADDED)
                          {
                                mainModelArrayList.add(dc.getDocument().toObject(MainModel.class));
                          }

                          adapter.notifyDataSetChanged();

                      }
                  }
              });

    }*/


    private void insertData()
    {
        Map<String, Object> map =new HashMap<>();
        map.put("name",name.getText().toString().trim());
        map.put("email",email.getText().toString().trim());
        map.put("course",course.getText().toString().trim());
        map.put("year",course.getText().toString().trim());

        firestore.collection("student").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        clear();
                        Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error while inserting data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private  void clear()
    {
        name.setText(" ");
        email.setText(" ");
        course.setText(" ");
        year.setText(" ");
    }
}