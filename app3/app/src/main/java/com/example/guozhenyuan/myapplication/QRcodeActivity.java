package com.example.guozhenyuan.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class QRcodeActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    TextView textView1;

    BarcodeDetector barcodeDetector;//條碼偵測器
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;//要求相機使用權限的ID
    private FirebaseFirestore firebaseFirestore;
    private  int money;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        final String uid = user.getUid();

        DocumentReference docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    money = Integer.valueOf(document.getString("money"));
                }
            }
        });


        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);
        textView1 = (TextView) findViewById(R.id.textView1);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(250, 250).build();

        //add event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //request permission
                    ActivityCompat.requestPermissions(QRcodeActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            int runFirst;//先建立一個測試數值來判定是否為第一次掃描，避免重複掃描
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();

                if (qrcode.size() != 0){
                    txtResult.post(new Runnable() {

                        @Override
                        public void run() {
                            if (runFirst == 0) {//一開始的初始值為零，但一經跑過以後便會出現其他數值(1或2)
                                runFirst += 1;//當是第一次掃描時則加一
                            }
                            if (runFirst == 1){//判定是否為第一次掃描
                                //Create vibrate(震動)
                                Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(500);
                                runFirst+=1;//如果進入掃描，則加一，此時判定值為2
                                //顯示掃描到的訊息
                                //txtResult.setText(qrcode.valueAt(0).displayValue);
                                //掃描到QR-code，彈跳式視窗
                                final AlertDialog dialog = new AlertDialog.Builder(QRcodeActivity.this)
                                        .setTitle("Congratulation ! ")
                                        .setMessage("You win LHC/5 ! ")
                                        .setPositiveButton("OK!",null)
                                        .setNegativeButton("So?",null)
                                        .show();

                                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                positiveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(QRcodeActivity.this, "Let's go to the other restaurant.Enjoy your meal time and using our application:)", Toast.LENGTH_SHORT).show();


                                        firebaseFirestore.collection("users").document(uid)
                                                .update(
                                                        "money",String.valueOf(money+10)
                                                );
                                        dialog.dismiss();
                                        Intent intent=new Intent(QRcodeActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                        runFirst=0;//如果按下按鈕，則使判定值回到零
                                    }
                                });

                                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                                negativeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(QRcodeActivity.this, "So you earn LHC/5 , how surprise , isn't it? hahaha:D", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        runFirst=0;//如果按下按鈕，則使判定值回到零
                                    }
                                });

                            }}
                    });
                }
            }
        });
    }
}
