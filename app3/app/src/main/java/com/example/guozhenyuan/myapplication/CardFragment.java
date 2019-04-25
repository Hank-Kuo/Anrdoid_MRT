package com.example.guozhenyuan.myapplication;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import static com.google.ads.AdRequest.LOGTAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    private Button take;
    private ImageView wheel;
    private TextView textView;
    Random random;
    int degree = 0;
    int degree_old = 0;
    private int money,card30,card50,card70,card90;
    private FirebaseFirestore firebaseFirestore;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String uid=user.getUid();
    String flag;
    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_card, container, false);

        firebaseFirestore=FirebaseFirestore.getInstance();

        take = (Button)view.findViewById(R.id.take);
        wheel = (ImageView) view.findViewById(R.id.wheel);

        random = new Random();

        DocumentReference doc=firebaseFirestore.collection("users").document(uid);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                     card30= Integer.valueOf(document.getString("card30"));
                    card50= Integer.valueOf(document.getString("card50"));
                    card70= Integer.valueOf(document.getString("card70"));
                    card90= Integer.valueOf(document.getString("card90"));
                    money=Integer.valueOf(document.getString("money"));
                }
            }
        });


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Card ")
                        .setMessage("hey guys, do you want to play the game ? ")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();
                final Dialog dialog1 = new Dialog(getActivity());

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if(money>=50){
                            money=money-50;
                            degree_old = degree % 360;
                            degree =random.nextInt(3600) + 720;
                            RotateAnimation rota = new RotateAnimation(degree_old, degree,
                                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                            rota.setDuration(3600);
                            rota.setFillAfter(true);
                            rota.setInterpolator(new DecelerateInterpolator());
                            rota.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    Toast.makeText(getActivity(),flag,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                            wheel.startAnimation(rota);
                            int number=360-(degree%360);
                            System.out.println(number);/*選轉的角度*/
                            if(number<90){
                                card30+=1;
                                flag="30";
                            }
                            else if(number>=90&&number<180){
                                card50+=1;
                                flag="50";
                            }
                            else if(number>=180&&number<270){
                                card70+=1;
                                flag="70";
                            }
                            else if(number>=270){
                                card90+=1;
                                flag="90";
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"no",Toast.LENGTH_LONG).show();
                        }
                        firebaseFirestore.collection("users").document(uid)
                                .update(
                                        "money",String.valueOf(money),
                                        "card30",String.valueOf(card30),
                                        "card50",String.valueOf(card50),
                                        "card70",String.valueOf(card70),
                                        "card90",String.valueOf(card90)
                                );

                    }
                });








            }
        });




        return view;

    }






}
