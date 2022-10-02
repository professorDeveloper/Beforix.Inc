package com.azamovhudstc.beforixinc.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.azamovhudstc.beforixinc.R;
import com.azamovhudstc.beforixinc.utils.MyMovie;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OtpVerfiyActivity extends AppCompatActivity {
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBacks;
    FirebaseAuth firebaseauth;
    String codesent;
    MyMovie myMovie;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verified);
        db = FirebaseFirestore.getInstance();
//        Admin
//        FileInputStream serviceAccount =
//                new FileInputStream("path/to/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);

        Button btnSet = findViewById(R.id.button_send);
        TextView phone_number = findViewById(R.id.phone_number);
        firebaseauth = FirebaseAuth.getInstance();
//        firebaseauth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
//        firebaseauth.getAccessToken(false);
//        firebaseauth.useEmulator("192.168.1.1", 63342);

// set this to remove reCaptcha web

        ImageView verfied = findViewById(R.id.verfied);
        ImageView verfiedErr = findViewById(R.id.verfiedErorr);
        ProgressBar progerssOtp = findViewById(R.id.progressBarOtp);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((phone_number.getText().toString().trim().length() == 9) && (phone_number.getText().toString().startsWith("91") || phone_number.getText().toString().startsWith(
                        "90") || phone_number.getText().toString().startsWith(
                        "90"))
                ) {
                    System.out.println(phone_number.getText().toString());
                    verfied.setVisibility(View.VISIBLE);
                    progerssOtp.setVisibility(View.VISIBLE);
                    verfiedErr.setVisibility(View.GONE);
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseauth)
                            .setPhoneNumber("+998" + phone_number.getText().toString())
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(OtpVerfiyActivity.this)
                            .setCallbacks(mcallBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);

                } else if (phone_number.getText().toString().trim().length() < 9) {
                    Toast.makeText(getApplicationContext(), "Kichik son iltimos to`gri kiriting.", Toast.LENGTH_SHORT).show();
                    verfied.setVisibility(View.GONE);
                    verfiedErr.setVisibility(View.VISIBLE);


                } else if (phone_number.getText().toString().trim().length() > 9) {
                    Toast.makeText(getApplicationContext(), "Juda uzun !", Toast.LENGTH_SHORT).show();
                    verfied.setVisibility(View.GONE);
                    verfiedErr.setVisibility(View.VISIBLE);


                } else if (!phone_number.getText().toString().trim().startsWith("91") || !phone_number.getText().toString().trim()
                        .startsWith("90")
                ) {
                    verfied.setVisibility(View.GONE);
                    verfiedErr.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Faqat belline abonentlari uchun ruxsat", Toast.LENGTH_SHORT).show();
                } else {
                    verfiedErr.setVisibility(View.INVISIBLE);
                }

            }
        });
        mcallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                System.out.println("On Verification");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                System.out.println("FirebaseExceptionnnnnnnnnnnnnnnnnnnnnnnnnn :" + e.toString());

            }

            @SuppressLint("CommitPrefEdits")
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(), "Habar Yuborildi !", Toast.LENGTH_LONG).show();
                progerssOtp.setVisibility(View.INVISIBLE);
                codesent = s;
                Intent intent = new Intent(getApplicationContext(), OtpVerfiedActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("name", phone_number.getText().toString());
                myEdit.putString("code", codesent);
                myEdit.apply();
                startActivity(intent);


            }
        };
    }

}
