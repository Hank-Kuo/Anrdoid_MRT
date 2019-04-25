package com.example.guozhenyuan.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }
    private static final String TAG="xxx";
    private Button logout;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView username;
    private TextView money;
    private TextView usersub;
    private FirebaseFirestore firebaseFirestore;
    private String name;
    private String money1;
    private String sub;
    private Button card_bag,feedback,userlimit;

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
                    sub=name.substring(0,1);
                    username.setText(name);
                    money.setText(money1);
                    usersub.setText(sub);
                }
            }
        });
        View view=inflater.inflate(R.layout.fragment_setting, container, false);

        logout=(Button)view.findViewById(R.id.logout);
        userlimit=view.findViewById(R.id.userlimit);
        feedback=view.findViewById(R.id.feedback);
        card_bag=view.findViewById(R.id.card_bag);
        username=(TextView) view.findViewById(R.id.username);
        money=(TextView) view.findViewById(R.id.money);
        usersub=(TextView) view.findViewById(R.id.usersub);

        username.setText(name);
        money.setText(money1);
        usersub.setText(sub);





        setupFirebaseListener();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick : attempting ot sign");

                FirebaseAuth.getInstance().signOut();
            }
        });

        userlimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),user_limitActivity.class);
                startActivity(intent);
            }
        });
        card_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),card_bagActivity.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),feedbackActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void setupFirebaseListener(){
        Log.d(TAG,"setupFireasel   email");
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d(TAG,"on  sign in"+user.getUid());
                }else{
                    Log.d(TAG,"on  sign out");
                    Toast.makeText(getActivity(),"sign up",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthStateListener!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}
