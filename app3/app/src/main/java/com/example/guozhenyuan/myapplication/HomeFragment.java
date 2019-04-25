package com.example.guozhenyuan.myapplication;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FrameLayout frame;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String name;
    private String money1;
    private TextView username;
    private TextView money;
    private ViewPager viewPager;

    private RedFragment redFragment;
    private GreenFragment greenFragment;
    private BrownFragment brownFragment;
    private BlueFragment blueFragment;
    private int flag=0;
    String arraysName[] = {"orange", "blue", "green", "red", "brown"};

    public HomeFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user.getUid();
        DocumentReference docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    name = document.getString("name");
                    money1 = document.getString("money");
                    username.setText(name);
                    money.setText(money1);
                }
            }
        });

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        username = (TextView) view.findViewById(R.id.username);
        money = (TextView) view.findViewById(R.id.money);

        username.setText(name);
        money.setText(money1);



        frame = (FrameLayout) view.findViewById(R.id.fragmentred);

        if (flag == 0) {

            Fragment fragment = new GreenFragment();
            FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
            transaction1.replace(R.id.fragmentred, fragment);
            transaction1.addToBackStack(null);
            transaction1.commit();
            flag=1;
        }



        Button buttonorange = (Button) view.findViewById(R.id.buttonorange);
        buttonorange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OrangeFragment();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragmentred, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();


            }
        });


        Button buttonred = (Button) view.findViewById(R.id.buttonred);
        buttonred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RedFragment();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragmentred, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();


            }
        });


        Button buttongreen = (Button) view.findViewById(R.id.buttongreen);
        buttongreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new GreenFragment();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragmentred, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();

            }
        });
        Button buttonbrown = (Button) view.findViewById(R.id.buttonbrown);
        buttonbrown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BrownFragment();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragmentred, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();

            }
        });
        Button buttonblue = (Button) view.findViewById(R.id.buttonblue);
        buttonblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BlueFragment();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();

                transaction1.replace(R.id.fragmentred, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();

            }
        });


        return view;

    }


    private class ArcLayout {
    }
}

