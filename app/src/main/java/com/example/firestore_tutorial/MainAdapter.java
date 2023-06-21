package com.example.firestore_tutorial;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainAdapter extends FirestoreRecyclerAdapter<MainModel,MainAdapter.myview>
{
    private FirebaseFirestore firestore;
     List<MainModel> mainModelList;
    private myview holder;
    private int position;
    private MainModel model;

    public MainAdapter(@NonNull FirestoreRecyclerOptions<MainModel> options) {
        super(options);
        this.firestore = firestore;
    }

    @Override
    protected void onBindViewHolder(@NonNull myview holder,  int  position, @NonNull MainModel model) {
        holder.name_text.setText(model.getName());
           holder.email_text.setText(model.getEmail());
           holder.course_text.setText(model.getCourse());
           holder.year_text.setText(model.getYear());

        Glide.with(holder.img.getContext())
                .load(model.getImage())
                .into(holder.img);

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext()).setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1900)// set the apperence values
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText name=view.findViewById(R.id.nametxt);
                EditText email=view.findViewById(R.id.emailtxt);
                EditText course=view.findViewById(R.id.coursetxt);
                EditText year=view.findViewById(R.id.yeartxt);
                EditText img = view.findViewById(R.id.imageurl);



                name.setText(model.getName());
                email.setText(model.getEmail());
                course.setText(model.getCourse());
                year.setText(model.getYear());
                img.setText(model.getImage());

                dialogPlus.show();

                Button upate_button = view.findViewById(R.id.updatebtn);

               upate_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference documentRef = getSnapshots().getSnapshot(holder.getAdapterPosition()).getReference();
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("course", course.getText().toString());
                        map.put("year", year.getText().toString());
                        map.put("image", img.getText().toString());

                        documentRef.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.course_text.getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                                         dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.course_text.getContext(), "Data not update Error", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });



            }
        });
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name_text.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted Data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentReference documentRef = getSnapshots().getSnapshot(holder.getAdapterPosition()).getReference();
                        documentRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.name_text.getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name_text.getContext(), "not deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name_text.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });




    }


    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new myview(view);
    }

    public MainModel getItem(int position) {
        return super.getItem(position);
    }

    public class myview extends RecyclerView.ViewHolder{

        TextView name_text,email_text,course_text,year_text;
        ImageView img;

        Button editbutton,deletebutton;
        public myview(@NonNull View itemView) {
            super(itemView);
            img =  (ImageView) itemView.findViewById(R.id.imagepic);
            name_text = (TextView) itemView.findViewById(R.id.nametxt);
            email_text =  (TextView)itemView.findViewById(R.id.emailtxt);
            course_text = (TextView) itemView.findViewById(R.id.coursetxt);
            year_text = (TextView) itemView.findViewById(R.id.yeartxt);

            editbutton =  (Button) itemView.findViewById(R.id.editbtn);
            deletebutton =  (Button) itemView.findViewById(R.id.deletebtn);

        }
    }



}