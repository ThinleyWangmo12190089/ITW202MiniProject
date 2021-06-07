package com.example.waitless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class PateintDetails extends AppCompatActivity {
    EditText etName, etGender, etAge, etNumber, etCid, etSympotms, etDisease, etDate, etTime;
    Button btNext;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_details);

        etName = findViewById(R.id.et_name);
        etGender = findViewById(R.id.et_gender);
        etAge = findViewById(R.id.et_age);
        etNumber = findViewById(R.id.et_pnum);
        etCid = findViewById(R.id.et_cid);
        etSympotms = findViewById(R.id.et_symptoms);
        etDisease = findViewById(R.id.et_disease);
        etDate = findViewById(R.id.et_date);
        etTime = findViewById(R.id.et_time);
        btNext = findViewById(R.id.btn_next);

        //Initialize Validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add Validation for Name
        awesomeValidation.addValidation(this,R.id.et_name,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //add Validation for gender
        awesomeValidation.addValidation(this,R.id.et_gender,
                RegexTemplate.NOT_EMPTY,R.string.invalid_gender);

        //add Validation for age
        awesomeValidation.addValidation(this,R.id.et_age,
                RegexTemplate.NOT_EMPTY,R.string.invalid_age);

        //add Validation for mobile number
        awesomeValidation.addValidation(this,R.id.et_pnum,
                "[0-7][8]",R.string.invalid_mobile);

        //For email
        awesomeValidation.addValidation(this,R.id.et_cid,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //add Validation for symptoms
        awesomeValidation.addValidation(this,R.id.et_name,
                RegexTemplate.NOT_EMPTY,R.string.invalid_symptoms);

        //add Validation for disease
        awesomeValidation.addValidation(this,R.id.et_name,
                RegexTemplate.NOT_EMPTY,R.string.invalid_disease);
    }

}