package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.hbb20.CountryCodePicker;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class VideoChatApp extends AppCompatActivity {
    private CountryCodePicker ccp;
    private EditText phoneText;
    private EditText codeText;
    private Button continueAndNextBtn;
    private String checker ="", phoneNumber ="";
    private RelativeLayout relativeLayout;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerification;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat_app);

        mAuth =FirebaseAuth.getInstance();
        loadingbar=new ProgressDialog(this);

        phoneText = findViewById(R.id.phoneText);
        codeText = findViewById(R.id.codeText);
        continueAndNextBtn = findViewById(R.id.continueNextButton);
        relativeLayout = findViewById(R.id.phoneAuth);
        ccp = (CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneText);

        continueAndNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(continueAndNextBtn.getText().equals("Submit")||checker.equals("Code Sent"))
                {
                  String verificationCode = codeText.getText().toString();
                  if(verificationCode.equals("")){
                      Toast.makeText(VideoChatApp.this, "Please Write Verification code", Toast.LENGTH_SHORT).show();
                  }
                  else{
                      loadingbar.setTitle("Code Verification");
                      loadingbar.setMessage("Please Wait, We are Verifying your code Number");
                      loadingbar.setCanceledOnTouchOutside(false);
                      loadingbar.show();

                      PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerification,verificationCode);
                      signInWithPhoneAuthCredential(credential);
                  }
                }
                else{
                    phoneNumber =ccp.getFullNumberWithPlus();
                    if(!phoneNumber.equals("")){
                        loadingbar.setTitle("Phone Number Verification");
                        loadingbar.setMessage("Please Wait, We are Verifying your Number");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS,VideoChatApp.this, mCallbacks);
                    }
                    else{
                        Toast.makeText(VideoChatApp.this, "Please Write Phone No,", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                 signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VideoChatApp.this, "Invalid Phone No....", Toast.LENGTH_SHORT).show();

                loadingbar.dismiss();
                relativeLayout.setVisibility(View.VISIBLE);
                continueAndNextBtn.setText("Continue");
                codeText.setVisibility(View.GONE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerification = s;
                mResendToken =forceResendingToken;

                relativeLayout.setVisibility(View.GONE);
                checker ="Code Sent";
                continueAndNextBtn.setText("Submit");
                codeText.setVisibility(View.VISIBLE);

                 loadingbar.dismiss();
                Toast.makeText(VideoChatApp.this, "Code has been sent, please check... ", Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser !=null){

            Intent homeintent = new Intent(VideoChatApp.this,VideoConferencing.class);
            startActivity(homeintent);
            finish();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            loadingbar.dismiss();
                            Toast.makeText(VideoChatApp.this, "Congratuation You are LoggedIn Successfully ", Toast.LENGTH_SHORT).show();
                            sendUserToVideoConferencing();
                            // ...
                        } else {
                            loadingbar.dismiss();
                            String e =task.getException().toString();
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(VideoChatApp.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendUserToVideoConferencing(){
        Intent intent = new Intent(VideoChatApp.this,VideoConferencing.class);
        startActivity(intent);
        finish();
    }
}
