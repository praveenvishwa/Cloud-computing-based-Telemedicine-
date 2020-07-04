package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.example.chatapp.helper.InputValidation;
import com.example.chatapp.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddPatient extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = AddPatient.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutBeneficiaryName;
    private TextInputLayout textInputLayoutBeneficiaryEmail;
    private TextInputLayout textInputLayoutBeneficiaryAddress;
    private TextInputLayout textInputLayoutBeneficiaryCountry;
    private TextInputLayout textInputLayoutBeneficiaryAge;
    private TextInputLayout textInputLayoutBeneficiaryGender;
    private TextInputLayout textInputLayoutBeneficiaryPulserate;
    private TextInputLayout textInputLayoutBeneficiaryWeight;
    private TextInputLayout textInputLayoutBeneficiaryHeight;
    private TextInputLayout textInputLayoutBeneficiaryBloodGrp;
    private TextInputLayout textInputLayoutBeneficiaryId;

    private TextInputEditText textInputEditTextBeneficiaryName;
    private TextInputEditText textInputEditTextBeneficiaryEmail;
    private TextInputEditText textInputEditTextBeneficiaryAddress;
    private TextInputEditText textInputEditTextBeneficiaryCountry;
    private TextInputEditText textInputEditTextBeneficiaryAge;
    private TextInputEditText textInputEditTextBeneficiaryGender;
    private TextInputEditText textInputEditTextBeneficiaryPulserate;
    private TextInputEditText textInputEditTextBeneficiaryWeight;
    private TextInputEditText textInputEditTextBeneficiaryHeight;
    private TextInputEditText textInputEditTextBeneficiaryBloodGrp;
    private TextInputEditText textInputEditTextBeneficiaryId;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewBenefList;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private com.example.chatapp.Model.Beneficiary beneficiary;

    public AddPatient(TextInputEditText textInputEditTextBeneficiaryName, TextInputEditText textInputEditTextBeneficiaryEmail, TextInputEditText textInputEditTextBeneficiaryAddress, TextInputEditText textInputEditTextBeneficiaryCountry, TextInputEditText textInputEditTextBeneficiaryAge, TextInputEditText textInputEditTextBeneficiaryGender, TextInputEditText textInputEditTextBeneficiaryPulserate, TextInputEditText textInputEditTextBeneficiaryWeight, TextInputEditText textInputEditTextBeneficiaryHeight, TextInputEditText textInputEditTextBeneficiaryBloodGrp, TextInputEditText textInputEditTextBeneficiaryId) {
        this.textInputEditTextBeneficiaryName = textInputEditTextBeneficiaryName;
        this.textInputEditTextBeneficiaryEmail = textInputEditTextBeneficiaryEmail;
        this.textInputEditTextBeneficiaryAddress = textInputEditTextBeneficiaryAddress;
        this.textInputEditTextBeneficiaryCountry = textInputEditTextBeneficiaryCountry;
        this.textInputEditTextBeneficiaryAge = textInputEditTextBeneficiaryAge;
        this.textInputEditTextBeneficiaryGender = textInputEditTextBeneficiaryGender;
        this.textInputEditTextBeneficiaryPulserate = textInputEditTextBeneficiaryPulserate;
        this.textInputEditTextBeneficiaryWeight = textInputEditTextBeneficiaryWeight;
        this.textInputEditTextBeneficiaryHeight = textInputEditTextBeneficiaryHeight;
        this.textInputEditTextBeneficiaryBloodGrp = textInputEditTextBeneficiaryBloodGrp;
        this.textInputEditTextBeneficiaryId = textInputEditTextBeneficiaryId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutBeneficiaryName = findViewById(R.id.textInputLayoutBeneficiaryName);
        textInputLayoutBeneficiaryEmail = findViewById(R.id.textInputLayoutBeneficiaryEmail);
        textInputLayoutBeneficiaryAddress = findViewById(R.id.textInputLayoutBeneficiaryAddress);
        textInputLayoutBeneficiaryCountry = findViewById(R.id.textInputLayoutBeneficiaryCountry);
        textInputLayoutBeneficiaryAge =findViewById(R.id.textInputLayoutBeneficiaryAge);
        textInputLayoutBeneficiaryGender = findViewById(R.id.textInputLayoutBeneficiaryGender);
        textInputLayoutBeneficiaryPulserate =findViewById(R.id.textInputLayoutBeneficiaryPulserate);
        textInputLayoutBeneficiaryWeight = findViewById(R.id.textInputLayoutBeneficiaryWeight);
        textInputLayoutBeneficiaryHeight = findViewById(R.id.textInputLayoutBeneficiaryHeight);
        textInputLayoutBeneficiaryBloodGrp =findViewById(R.id.textInputLayoutBeneficiaryBloodGrp);
        textInputLayoutBeneficiaryId = findViewById(R.id.textInputLayoutBeneficiaryId);



        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewBenefList = findViewById(R.id.appCompatTextViewBenefList);

    }
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewBenefList.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        beneficiary = new com.example.chatapp.Model.Beneficiary();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewBenefList:
                Intent accountsIntent = new Intent(activity, BeneficiaryListActivity.class);
                accountsIntent.putExtra("ID", textInputEditTextBeneficiaryId.getText().toString().trim());
                accountsIntent.putExtra("NAME", textInputEditTextBeneficiaryName.getText().toString().trim());
                accountsIntent.putExtra("EMAIL", textInputEditTextBeneficiaryEmail.getText().toString().trim());
                accountsIntent.putExtra("ADDRESS", textInputEditTextBeneficiaryAddress.getText().toString().trim());
                accountsIntent.putExtra("COUNTRY", textInputEditTextBeneficiaryCountry.getText().toString().trim());
                accountsIntent.putExtra("AGE", textInputEditTextBeneficiaryAge.getText().toString().trim());
                accountsIntent.putExtra("GENDER", textInputEditTextBeneficiaryGender.getText().toString().trim());
                accountsIntent.putExtra("PULSERATE", textInputEditTextBeneficiaryPulserate.getText().toString().trim());
                accountsIntent.putExtra("WEIGHT", textInputEditTextBeneficiaryWeight.getText().toString().trim());
                accountsIntent.putExtra("HEIGHT", textInputEditTextBeneficiaryHeight.getText().toString().trim());
                accountsIntent.putExtra("BLOODGRP", textInputEditTextBeneficiaryBloodGrp.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextBeneficiaryName, textInputLayoutBeneficiaryName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextBeneficiaryEmail, textInputLayoutBeneficiaryEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextBeneficiaryEmail, textInputLayoutBeneficiaryEmail, getString(R.string.error_message_email))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextBeneficiaryEmail.getText().toString().trim())) {

            beneficiary.setId(Integer.parseInt(textInputEditTextBeneficiaryId.getText().toString().trim()));
            beneficiary.setName(textInputEditTextBeneficiaryName.getText().toString().trim());
            beneficiary.setEmail(textInputEditTextBeneficiaryEmail.getText().toString().trim());
            beneficiary.setAddress(textInputEditTextBeneficiaryAddress.getText().toString().trim());
            beneficiary.setCountry(textInputEditTextBeneficiaryCountry.getText().toString().trim());
            beneficiary.setAge(textInputEditTextBeneficiaryAge.getText().toString().trim());
            beneficiary.setGender(textInputEditTextBeneficiaryGender.getText().toString().trim());
            beneficiary.setPulserate(textInputEditTextBeneficiaryPulserate.getText().toString().trim());
            beneficiary.setWeight(textInputEditTextBeneficiaryWeight.getText().toString().trim());
            beneficiary.setHeight(textInputEditTextBeneficiaryHeight.getText().toString().trim());
            beneficiary.setBloodgrp(textInputEditTextBeneficiaryBloodGrp.getText().toString().trim());


            databaseHelper.addBeneficiary(beneficiary);

            // Snack Bar to show success message that record saved successfully
            Intent accountsIntent = new Intent(activity, BeneficiaryListActivity.class);
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT)
                    .show();
            accountsIntent.putExtra("ID", textInputEditTextBeneficiaryId.getText().toString().trim());
            accountsIntent.putExtra("NAME", textInputEditTextBeneficiaryName.getText().toString().trim());
            accountsIntent.putExtra("EMAIL", textInputEditTextBeneficiaryEmail.getText().toString().trim());
            accountsIntent.putExtra("ADDRESS", textInputEditTextBeneficiaryAddress.getText().toString().trim());
            accountsIntent.putExtra("COUNTRY", textInputEditTextBeneficiaryCountry.getText().toString().trim());
            accountsIntent.putExtra("AGE", textInputEditTextBeneficiaryAge.getText().toString().trim());
            accountsIntent.putExtra("GENDER", textInputEditTextBeneficiaryGender.getText().toString().trim());
            accountsIntent.putExtra("PULSERATE", textInputEditTextBeneficiaryPulserate.getText().toString().trim());
            accountsIntent.putExtra("WEIGHT", textInputEditTextBeneficiaryWeight.getText().toString().trim());
            accountsIntent.putExtra("HEIGHT", textInputEditTextBeneficiaryHeight.getText().toString().trim());
            accountsIntent.putExtra("BLOODGRP", textInputEditTextBeneficiaryBloodGrp.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText() {
        textInputEditTextBeneficiaryName.setText(null);
        textInputEditTextBeneficiaryEmail.setText(null);
        textInputEditTextBeneficiaryAddress.setText(null);
        textInputEditTextBeneficiaryCountry.setText(null);
        textInputEditTextBeneficiaryAge.setText(null);
        textInputEditTextBeneficiaryGender.setText(null);
        textInputEditTextBeneficiaryPulserate.setText(null);
        textInputEditTextBeneficiaryWeight.setText(null);
        textInputEditTextBeneficiaryHeight.setText(null);
        textInputEditTextBeneficiaryBloodGrp.setText(null);
    }
}
