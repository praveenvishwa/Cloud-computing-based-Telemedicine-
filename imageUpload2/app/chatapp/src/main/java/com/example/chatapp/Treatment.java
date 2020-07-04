package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Treatment extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etName;
    EditText etId;
    EditText etDisease;
    EditText etSymptoms;
    EditText etdateofadmit;
    EditText etdateoftreatment;
    EditText ettimeoftreatment;
    EditText etpulserate;
    EditText etheartbeatrate;
    EditText ettreatmentgiven;
    Button AddDetails;
    Button ViewDetails;
    Button DeleteDetails;
    Button UpdateDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        myDb = new DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.etName);
        etId = (EditText) findViewById(R.id.etId);
        etDisease = (EditText) findViewById(R.id.etdisease);
        etSymptoms = (EditText) findViewById(R.id.etsymptoms);
        etdateofadmit = (EditText) findViewById(R.id.etdateofadmit);
        etdateoftreatment = (EditText) findViewById(R.id.etdateoftreatment);
        ettimeoftreatment = (EditText) findViewById(R.id.ettimeoftreatment);
        etpulserate = (EditText) findViewById(R.id.etPulserate);
        etheartbeatrate = (EditText) findViewById(R.id.etHeartbeatrate);
        ettreatmentgiven = (EditText) findViewById(R.id.ettreatmentGiven);
        AddDetails = (Button) findViewById(R.id.Addd);
        ViewDetails = (Button) findViewById(R.id.View);
        DeleteDetails = (Button) findViewById(R.id.Delete);
        UpdateDetails = (Button) findViewById(R.id.Update);

        AddTreatData();
        ViewTreatData();
        DeleteTreatData();
        UpdateTreatData();
    }

    public void DeleteTreatData(){
        DeleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(etId.getText().toString());
                if(deletedRows > 0)
                    Toast.makeText(Treatment.this, "Data deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Treatment.this, "Data not deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateTreatData(){
        UpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.UpdateData(etId.getText().toString(),etName.getText().toString(),etDisease.getText().toString(),etSymptoms.getText().toString(),etdateofadmit.getText().toString(),etdateoftreatment.getText().toString(),ettimeoftreatment.getText().toString(),etpulserate.getText().toString(),etheartbeatrate.getText().toString(),ettreatmentgiven.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(Treatment.this, "Data updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Treatment.this, "Data not updated", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void AddTreatData() {
        AddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etName.getText().toString(), etDisease.getText().toString(), etSymptoms.getText().toString(), etdateofadmit.getText().toString(), etdateoftreatment.getText().toString(), ettimeoftreatment.getText().toString(), etpulserate.getText().toString(),
                        etheartbeatrate.getText().toString(), ettreatmentgiven.getText().toString());
                if (isInserted = true)
                    Toast.makeText(Treatment.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Treatment.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ViewTreatData() {
        ViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showmessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n");
                    buffer.append("NAME :" + res.getString(1) + "\n");
                    buffer.append("DISEASE :" + res.getString(2) + "\n");
                    buffer.append("SYMPTOMS :" + res.getString(3) + "\n");
                    buffer.append("DATE OF ADMIT :" + res.getString(4) + "\n");
                    buffer.append("DATE OF TREATMENT :" + res.getString(5) + "\n");
                    buffer.append("TIME OF TREATMENT :" + res.getString(6) + "\n");
                    buffer.append("PULSE RATE :" + res.getString(7) + "\n");
                    buffer.append("HEART BEAT RATE:" + res.getString(8) + "\n\n");
                    buffer.append("TREATMENT GIVEN:" + res.getString(9) + "\n\n");
                }
                showmessage("Data", buffer.toString());

            }
        });

    }

    public void showmessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
