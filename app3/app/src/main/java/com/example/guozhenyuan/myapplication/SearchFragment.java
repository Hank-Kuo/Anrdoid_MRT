package com.example.guozhenyuan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestoreStore;
    private EditText storename;
    private EditText introduct;
    private EditText phone;
    private EditText location;
    private EditText mrtstation;
    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);


        storename=(EditText)view.findViewById(R.id.storename);
        introduct=(EditText)view.findViewById(R.id.introduction);
        phone=(EditText)view.findViewById(R.id.phone);
        location=(EditText)view.findViewById(R.id.location);
        mrtstation=(EditText)view.findViewById(R.id.mrtstation);
        button=(Button)view.findViewById(R.id.button);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseFirestoreStore=FirebaseFirestore.getInstance();

            button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String store=storename.getText().toString();
                String intr=introduct.getText().toString();
                String loc=location.getText().toString();
                String mrt=mrtstation.getText().toString();
                String pho=phone.getText().toString();

                if(store.matches("") && intr.matches("") && loc.matches("") && pho.matches("")||mrt.matches("")){
                    Toast toast = Toast.makeText(getActivity(), "欄位不能是空白!!", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Hello  ! ")
                            .setMessage("Are you sure to add new store ? ")
                            .setPositiveButton("OK!",null)
                            .setNegativeButton("NO!",null).show();


                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String store=storename.getText().toString();
                            String intr=introduct.getText().toString();
                            String loc=location.getText().toString();
                            String mrt=mrtstation.getText().toString();
                            String pho=phone.getText().toString();

                            Map<String ,String> newstore=new HashMap<>() ;
                            newstore.put("name",store);
                            newstore.put("introduction",intr);
                            newstore.put("locatoin",loc);
                            newstore.put("station",mrt);
                            newstore.put("phone",pho);
                            firebaseFirestoreStore.collection("newstore").document(mrt)
                                    .set(newstore)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                            storename.setText("");
                            introduct.setText("");
                            phone.setText("");
                            location.setText("");
                            mrtstation.setText("");
                            Toast.makeText(getActivity(), "Thank you for your submit", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    });
                }



            }
        });






        return view;
    }





}
