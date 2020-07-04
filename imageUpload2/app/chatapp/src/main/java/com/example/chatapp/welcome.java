package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {
    Button ruralLogin,urbanLogin;
    ViewPager viewPager;
    int currentPageCunter=0;
    int images[]={R.drawable.communication,R.drawable.doctor,R.drawable.telemedicine_rural,R.drawable.clouding};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ruralLogin=findViewById(R.id.RuralLogin);
        urbanLogin=findViewById(R.id.UrbanLogin);

        ruralLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcome.this,RuralLogin.class));
            }
        });

        urbanLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcome.this,UrbanLogin.class));
            }
        });

        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(new SliderAdapter(images,welcome.this));

        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (currentPageCunter==images.length){
                    currentPageCunter=0;
                }
                viewPager.setCurrentItem(currentPageCunter++,true);
            }
        };

        Timer timer=new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                handler.post(update);
            }
        },2000,2000);
    }
}