package com.example.otp_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
    EditText input1,input2,input3,input4,input5,input6;
    Button submit;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);

        submit = findViewById(R.id.submit);

        TextView userphone = findViewById(R.id.userphone);
        String s1= getIntent().getStringExtra("StringName");
        userphone.setText(String.format("+91-%s",s1));

        otp= getIntent().getStringExtra("otp");

        final ProgressBar progressBar = findViewById(R.id.progressbar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input1.getText().toString().trim().isEmpty() && !input2.getText().toString().trim().isEmpty() && !input3.getText().toString().trim().isEmpty() && !input4.getText().toString().trim().isEmpty() && !input5.getText().toString().trim().isEmpty() && !input6.getText().toString().trim().isEmpty() )
                {
                    String userotp = input1.getText().toString().trim() + input2.getText().toString().trim() + input3.getText().toString().trim() + input4.getText().toString().trim() + input5.getText().toString().trim() + input6.getText().toString().trim();
                    if(otp!=null)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp,userotp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        submit.setVisibility(View.VISIBLE);

                                        if(task.isComplete()){
                                            Intent intent = new Intent(VerifyOtp.this,Dashboard.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(VerifyOtp.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else{
                        Toast.makeText(VerifyOtp.this, "Please Check your Internet Connection and Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(VerifyOtp.this, "Please enter all the numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_text_moment();
        findViewById(R.id.resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + s1, 60, TimeUnit.SECONDS, VerifyOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                submit.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                submit.setVisibility(View.VISIBLE);
                                Toast.makeText(VerifyOtp.this, "Phone Number Verified", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                             otp=s;
                                Toast.makeText(VerifyOtp.this, "New OTP Sended Successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    private void edit_text_moment() {

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}