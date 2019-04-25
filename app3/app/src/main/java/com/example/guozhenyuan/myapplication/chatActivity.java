package com.example.guozhenyuan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class chatActivity extends AppCompatActivity {

    private ChatAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> list=new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private Button inputbutton;
    String email;
    private ArrayList<Chatmessage> chat=new ArrayList<>();

    public void onStart(){
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("talk").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                chat.clear();
                for (DocumentSnapshot snapshot :queryDocumentSnapshots){
                    String email=snapshot.getString("email");
                    String context=snapshot.getString("context");
                    String time=snapshot.getString("time");
                    chat.add(new Chatmessage(context,email,time));

                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ListView listView=findViewById(R.id.lis);
        firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        email=firebaseAuth.getCurrentUser().getEmail();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                String inpupt1=input.getText().toString();

                Map<String ,String>talk=new HashMap<>();
                talk.put("context",inpupt1);
                talk.put("email",email);
                talk.put("time",getCurrDate());
                firebaseFirestore.collection("talk").document(getCurrDate())
                        .set(talk)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(chatActivity.this, "Information Saved...",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String error=e.getMessage();
                                Toast.makeText(chatActivity.this, "Error"+error,Toast.LENGTH_LONG).show();
                            }
                        });

                input.setText("");
            }
        });


        ChatAdapter chatAdapter=new ChatAdapter(getApplicationContext(),chat);
        listView.setAdapter(chatAdapter);


    }

    public String getCurrDate()
    {
        String dt;
        Date cal = Calendar.getInstance().getTime();
        dt = cal.toLocaleString();
        return dt;
    }


}
