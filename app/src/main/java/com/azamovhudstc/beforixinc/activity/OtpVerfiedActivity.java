package com.azamovhudstc.beforixinc.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.azamovhudstc.beforixinc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pdrozz.view.PinView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class OtpVerfiedActivity extends AppCompatActivity {
    TextView countdown;

    ProgressBar verfied_progress;
    LinearLayout resent;
    PinView pinView;
    PinView getOtp;
    String enterOdp;
    String codesent;

    FirebaseAuth auth;
    FirebaseFirestore db;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBacks;
    double time = 60;
    String s1 = "";
    String codereceviewr = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_enter);
        countdown = findViewById(R.id.countDown);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getOtp = findViewById(R.id.getOtp);
        verfied_progress = findViewById(R.id.verfied_progress);
        Button verfiedOtp = findViewById(R.id.button_send);
        resent = findViewById(R.id.resent_code);
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        s1 = sh.getString("name", "");
        codereceviewr = sh.getString("code", "");


        TextView phoneText = findViewById(R.id.getPhones);
        phoneText.setText("Biz shu +998" + s1 + " telefon raqamga Tasdiqlash Kodi Yubordik");
        new CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                countdown.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                countdown.setText("00:00");
                resent.setVisibility(View.VISIBLE);

            }
        }.start();

        resent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(30000, 1000) {
                    @SuppressLint("SetTextI18n")
                    public void onTick(long millisUntilFinished) {
                        // Used for formatting digit to be in 2 digits only
                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 600000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        countdown.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }

                    // When the task is over it will print 00:00:00 there
                    public void onFinish() {
                        countdown.setText("00:00");
                        resent.setVisibility(View.VISIBLE);

                    }
                }.start();

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+998" + s1)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OtpVerfiedActivity.this)
                        .setCallbacks(mcallBacks)
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
                mcallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        Toast.makeText(getApplicationContext(), "Habar Yuborildi !", Toast.LENGTH_LONG).show();
                    }
                };
            }
        });
        verfiedOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOdp = getOtp.getText().toString();
                if (enterOdp.trim().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kodni Kiriting !", Toast.LENGTH_SHORT).show();
                } else {

                    verfied_progress.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codereceviewr, enterOdp);
                    signInWithPhone(credential);
                }
            }
        });
        mcallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(getApplicationContext(), "Habar Yuborildi !", Toast.LENGTH_LONG).show();
                verfied_progress.setVisibility(View.INVISIBLE);
                codesent = s;
                System.out.println(codesent);


            }
        };
    }

    //    auth = Firebase.auth
//    val user = auth.currentUser
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//    googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//            if (user != null) {
//        findNavController().popBackStack()
//        findNavController().navigate(R.id.bottomFragment)
//
//    } else {
//        googleSignInClient.signOut()
//    }

    private void signInWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                    userRef.orderByChild("phoneNumber").equalTo("+998"+s1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue()!=null){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), "Success !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProfilActivitys.class);
                                intent.putExtra("phone", "+998" + s1);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                } else {
                    verfied_progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Tasdiqlanmadingiz Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}


