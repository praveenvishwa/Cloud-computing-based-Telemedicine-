package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UrbanDoctorButton extends AppCompatActivity {

    private Button btnShowupload, btnUrbanChat,btnUrbanVideoConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urban_doctor_button);

        btnShowupload = findViewById(R.id.btnShowUpload);
        btnUrbanChat = findViewById(R.id.btnUrbanChat);
        btnUrbanVideoConf = findViewById(R.id.btnUrbanVideoConf);

        btnShowupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UrbanDoctorButton.this,ImagesActivity.class);
                startActivity(intent);
            }
        });

        btnUrbanChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrbanDoctorButton.this,StartActivity.class);
                startActivity(intent);
            }
        });

        btnUrbanVideoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrbanDoctorButton.this,VideoChatApp.class);
                startActivity(intent);
            }
        });
    }


}
