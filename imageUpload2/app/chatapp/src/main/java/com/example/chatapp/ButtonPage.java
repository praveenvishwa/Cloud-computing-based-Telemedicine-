package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ButtonPage extends AppCompatActivity {
    Button live,add_report,add_patient,treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_page);

        live=findViewById(R.id.consult);
        add_report=findViewById(R.id.Add_report);
        treatment = findViewById(R.id.treatment);
        add_patient=findViewById(R.id.addpatient);
       // add_patient=findViewById(R.id.Add_patient);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonPage.this,Liveconsult.class));
            }
        });


        add_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonPage.this,ImageUpload.class));
            }
        });

        treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonPage.this,Treatment.class));
            }
        });

        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ButtonPage.this,Add_Mainactivity.class));
            }
        });




    }

}
