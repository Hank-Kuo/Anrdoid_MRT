package com.example.guozhenyuan.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.hitomi.cmlibrary.CircleMenu;

import java.text.BreakIterator;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private FrameLayout frame;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private SettingFragment settingFragment;
    private CardFragment cardFragment;


    private RedFragment redFragment;
    private GreenFragment greenFragment;
    private BrownFragment brownFragment;
    private BlueFragment blueFragment;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView tex1;
    private TextView tex2;

    public HomeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);






        frame = (FrameLayout) findViewById(R.id.frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        settingFragment = new SettingFragment();
        cardFragment=new CardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, homeFragment);
        fragmentTransaction.commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        fragmentTransaction.replace(R.id.frame, homeFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.scanner:
                        Intent intent=new Intent(HomeActivity.this,QRcodeActivity.class);
                        startActivity(intent);
                    case R.id.search:
                        setFragment(searchFragment);
                        return true;
                    case R.id.card:
                        setFragment(cardFragment);
                        // Switch to page three
                        return true;
                    case R.id.setting:
                        setFragment(settingFragment);
                        // Switch to page three
                        return true;


                }
                return true;



            }
        });





    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

    }


}

