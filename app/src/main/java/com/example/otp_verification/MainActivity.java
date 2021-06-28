package com.example.otp_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "PhoneAuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        final EditText phonenumber = findViewById(R.id.mobile);
        final Button getOtp = findViewById(R.id.get_otp);
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String value = phonenumber.getText().toString().trim();
                if (!value.isEmpty()) {
                    if (value.length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getOtp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + value, 60, TimeUnit.SECONDS, MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);
                                        signInWithPhoneAuthCredential(phoneAuthCredential);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationcode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtp.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, "otp sent successful", Toast.LENGTH_SHORT).show();
                                        Intent myintent = new Intent(MainActivity.this, VerifyOtp.class);
                                        myintent.putExtra("StringName", value);
                                        myintent.putExtra("otp", verificationcode);
                                        startActivity(myintent);

                                    }
                                });
                    } else {
                        Toast.makeText(MainActivity.this, "Enter 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(MainActivity.this, "You are verified! No Need of OTP ", Toast.LENGTH_LONG).show();
                            // Update UI

                            Intent myintent = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(myintent);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}