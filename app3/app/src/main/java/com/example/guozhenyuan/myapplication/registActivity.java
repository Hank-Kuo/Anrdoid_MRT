package com.example.guozhenyuan.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.RecoverySystem;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class registActivity extends AppCompatActivity {

    private EditText textPersonName;
    private EditText textEmail;
    private EditText textPassword;
    private EditText textPassword2;
    private FirebaseAuth firebaseAuth;
    private Button button3;
    private FirebaseFirestore firebaseFirestoreStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//態數據以key-value的形式放入
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);//實現不同Layout的轉換功能

        firebaseAuth = FirebaseAuth.getInstance();
        textPersonName = (EditText) findViewById(R.id.textPersonNameRegist);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPersonpasswordRegist);//找xml布局文件下的具体widget控件(如Button、TextView等)
        textPassword2 = (EditText) findViewById(R.id.textPassword2);
        firebaseAuth = FirebaseAuth.getInstance();//使用getInstance()获取到你的数据库实例，当你打算写库的时候使用它来引用你的写入位置。
        button3 = (Button) findViewById(R.id.button3);

        firebaseFirestoreStore=FirebaseFirestore.getInstance();

    }


    public void button3User_Click(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(registActivity.this, "Please wait", "processing ",true);
        Task<AuthResult> authResultTask = (firebaseAuth.createUserWithEmailAndPassword(textEmail.getText().toString(),
                textPassword.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(registActivity.this, "regist sucessful", Toast.LENGTH_SHORT).show();//顯示


                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Map<String ,String>userMap=new HashMap<>() ;
                    String name=textPersonName.getText().toString();
                    String email= textEmail.getText().toString();
                    userMap.put("name",name);
                    userMap.put("money","0");
                    userMap.put("card30","0");
                    userMap.put("card50","0");
                    userMap.put("card70","0");
                    userMap.put("card90","0");
                    userMap.put("email",email);
                    String userId = user.getUid();
                    firebaseFirestoreStore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(registActivity.this, "Information Saved...",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error=e.getMessage();
                                    Toast.makeText(registActivity.this, "Error"+error,Toast.LENGTH_LONG).show();
                                }
                            });





                    Intent i = new Intent(registActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(registActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
