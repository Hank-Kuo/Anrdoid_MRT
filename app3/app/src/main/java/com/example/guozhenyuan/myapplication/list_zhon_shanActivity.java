package com.example.guozhenyuan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class list_zhon_shanActivity extends AppCompatActivity {



    ListView listView;
    private ArrayList<String> list=new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private String storename;
    public void onStart(){
        super.onStart();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("輔大").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                list.clear();
                for (DocumentSnapshot snapshot :queryDocumentSnapshots){
                    list.add(snapshot.getString("name"));

                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_zhon_shan);

        listView = (ListView) findViewById(R.id.listView);


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>
                (list_zhon_shanActivity.this,android.R.layout.simple_list_item_1,list
                        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Intent intent = new Intent(list_zhon_shanActivity.this,rest_braisedActivity.class);
                        startActivity(intent);
                }



            }
        });

        listView.setAdapter(mAdapter);
    }
}
