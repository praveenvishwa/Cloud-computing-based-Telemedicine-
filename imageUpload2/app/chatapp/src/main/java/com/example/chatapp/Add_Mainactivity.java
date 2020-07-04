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

public class Add_Mainactivity extends AppCompatActivity {

    Add_DatabaseHelper myDb;
    EditText etName;
    EditText etId;
    EditText etAge;
    EditText etGender;
    EditText etPulseRate;
    EditText etWeight;
    EditText etHeight;
    Button AddDetails;
    Button ViewDetails;
    Button DeleteDetails;
    Button UpdateDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__mainactivity);

        myDb = new Add_DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.etName);
        etId = (EditText) findViewById(R.id.etId);
        etAge = (EditText) findViewById(R.id.etAge);
        etGender = (EditText) findViewById(R.id.etgender);
        etPulseRate = (EditText) findViewById(R.id.etPulserate);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);


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
                    Toast.makeText(Add_Mainactivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Add_Mainactivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateTreatData(){
        UpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.UpdateData(etId.getText().toString(),etName.getText().toString(),etAge.getText().toString(),etGender.getText().toString(),etPulseRate.getText().toString(),etWeight.getText().toString(),etHeight.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(Add_Mainactivity.this, "Data updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Add_Mainactivity.this, "Data not updated", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void AddTreatData() {
        AddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etName.getText().toString(), etAge.getText().toString(), etGender.getText().toString(), etPulseRate.getText().toString(), etWeight.getText().toString(), etHeight.getText().toString());
                if (isInserted = true)
                    Toast.makeText(Add_Mainactivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Add_Mainactivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
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
                    buffer.append("AGE :" + res.getString(2) + "\n");
                    buffer.append("GENDER :" + res.getString(3) + "\n");
                    buffer.append("PULSERATE :" + res.getString(4) + "\n");
                    buffer.append("WEIGHT :" + res.getString(5) + "\n\n");
                    buffer.append("HEIGHT :" + res.getString(6) + "\n\n");
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
