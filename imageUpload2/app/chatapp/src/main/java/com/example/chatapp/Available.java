package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Available extends AppCompatActivity {

    TextView tv1, tv2;
    EditText mUserName;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button btn_sending;
    Spinner dropdownmenu;
    String textData = "";
    ValueEventListener listener;
    ArrayAdapter<Object> adapter;
    ArrayList<Object> spinnerDataList, alreadydata;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);

        tv1 = findViewById(R.id.txt_head);
        tv2 = findViewById(R.id.txt_user);
        mUserName = findViewById(R.id.username);
        btn_sending = findViewById(R.id.btn_sending);
        dropdownmenu = (Spinner) findViewById(R.id.spinner);


        List<String> list = new ArrayList<>();
        list.add(0, "Select The Doctors");
        list.add("Dr. Seema Verma");
        list.add("Dr. Mehta");
        list.add("Dr. kashyab");

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownmenu.setAdapter(adapter);
        dropdownmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose The Doctors")) {

                } else {
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(Available.this, "Selected Doctor is " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /* alreadydata = new ArrayList<>();
       dataAdapter = new ArrayAdapter(Available.this,android.R.layout.simple_spinner_item,list);
       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          dropdownmenu.setAdapter(dataAdapter);
          dropdownmenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  if(parent.getItemAtPosition(position).equals("Select The Available Doctors")){

                  }else{
                      String item = parent.getItemAtPosition(position).toString();
                      Toast.makeText(Available.this, "You have Selected Dr. "  + item , Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });*/

   /* dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
               dropdownmenu.setAdapter(dataAdapter);

    adapter = new ArrayAdapter<>(Available.this,
            android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
            dropdownmenu.setAdapter(adapter);
            retrievedata();*/

        btn_sending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("Doctors");
                databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
                String dr = mUserName.getText().toString();
                databaseReference.setValue(dr);

                startActivity(new Intent(Available.this, ImageUpload.class));
            }
        });

    }

 /*   public void btnAdd(View view) {
        textData = mUserName.getText().toString().trim();
         databaseReference.push().setValue(textData).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 mUserName.setText("");
                 spinnerDataList.clear();
                 adapter.notifyDataSetChanged();
                 retrievedata();
                 Toast.makeText(Available.this, "Data Inserted ", Toast.LENGTH_SHORT).show();
             }
         });
      
    }*/

  /* public void retrievedata(){
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    spinnerDataList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/





    private boolean validateSpinner() {
        View selectedView = dropdownmenu.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (selectedTextView.getText().equals("Select Academic Year")) {
                selectedTextView.setError("");
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

}
