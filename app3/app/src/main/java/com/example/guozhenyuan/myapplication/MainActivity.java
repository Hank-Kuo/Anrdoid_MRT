package com.example.guozhenyuan.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCas;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText textlogin;
    private EditText textpassword;
    private FirebaseAuth firebaseAuth;
    private  Boolean loggedin = false;
    private FirebaseAuth.AuthStateListener mAuthStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textlogin = (EditText) findViewById(R.id.textlogin);
        textpassword = (EditText) findViewById(R.id.textpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent i=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                }
            }

        };
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);

    }

    public void buttonUserLogin_Click(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "processing ", true);
        (firebaseAuth.signInWithEmailAndPassword(textlogin.getText().toString(),
                textpassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "login sucessful", Toast.LENGTH_SHORT).show();//顯示
                } else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }});
    }


    public void buttonUserRegister_Click(View v){
        Intent i=new Intent(MainActivity.this,registActivity.class);
        startActivity(i);
    }
}

