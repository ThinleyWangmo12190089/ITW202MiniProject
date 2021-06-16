package com.example.waitless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class PateintDetails extends AppCompatActivity {
    EditText etName, etGender, etAge, etNumber, etCid, etSympotms, etDisease;
    TextView etdate, tvTimer;
    Button btNext;
    int hour, minute;
    DatabaseReference reference;

    //For navigation
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


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
        etdate = findViewById(R.id.txt_date);
        tvTimer = findViewById(R.id.txt_time);
        btNext = findViewById(R.id.btn_enterr);


        reference = FirebaseDatabase.getInstance().getReference().child("PatientDetails");

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String gender = etGender.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String cid = etCid.getText().toString().trim();
                String number = etNumber.getText().toString().trim();
                String symtoms = etSympotms.getText().toString().trim();
                String describe = etDisease.getText().toString();
                String date = etdate.getText().toString();
                String time = tvTimer.getText().toString();

                PatientDetailsHelperClass patient = new PatientDetailsHelperClass(name, age, cid, gender, number, symtoms, describe, date, time);
                reference.child(cid).setValue(patient);
                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //for navigation
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_use:

                        Intent intent = new Intent(getApplicationContext(), TermsofUse.class);
                        startActivity(intent);
                        break;

                    case  R.id.nav_pDetails:
                        Intent intent2 = getIntent();
                        Intent obj = new Intent(getApplicationContext(), PateintDetails.class);
                        startActivity(obj);
                        break;

                    case  R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent1 = new Intent(PateintDetails.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;

                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                    break;
                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    //to show the date picker dialog box
    public void showDatePicker(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void processDatePickerResult(int year, int month, int dayOfMonth) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);

        String date_message = (month_string + "/" + day_string + "/" + year_string);
        etdate.setText(date_message);
//        Toast.makeText(this, "Date: "+date_message, Toast.LENGTH_SHORT).show();
    }

    public void time(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");timePickerDialog.show();
    }
    private boolean validateName(){
        String val = etName.getText().toString().trim();
        if (val.isEmpty()){
            etName.setError("Field Cannot be empty");
            return false;
        }
        else if (!val.matches("[a-zA-Z ]+")){
            etName.setError("Name cannot be digits or numbers");
            return false;
        }
        else{
            etName.setError(null);
            return true;
        }

    }
    private boolean validateId() {
        String val = etCid.getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            etCid.setError("Field Cannot be empty");
            return false;
        } else if (val.length() != 11) {
            etCid.setError("userId should be their enrolment number");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etCid.setError("White Spaces are not allowed");
            return false;
        }
        else {
            etCid.setError(null);
//            etCid.setErrorEnabled(false);
            return true;

        }
    }
    private boolean validateDate(){
        String val = etdate.getText().toString();
        if (val.isEmpty()) {
            etdate.setError("Field cannot be empty");
            return false;
        }
        else {
            etdate.setError(null);
//            etdate.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validateDisease(){
        String val = etDisease.getText().toString();
        if (val.isEmpty()) {
            etDisease.setError("Field cannot be empty");
            return false;
        } else {
            etDisease.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }

    }
    private  boolean validateNumber(){
        String val = etNumber.getText().toString();
        if (val.isEmpty()) {
            etNumber.setError("Field cannot be empty");
            return false;
        } else {
            etNumber.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }
    }
    private  boolean validateSymptoms(){
        String val = etSympotms.getText().toString();
        if (val.isEmpty()) {
            etSympotms.setError("Field cannot be empty");
            return false;
        } else {
            etSympotms.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }
    }
    private  boolean validateGender(){
        String val = etGender.getText().toString();
        if (val.isEmpty()) {
            etGender.setError("Field cannot be empty");
            return false;
        } else {
            etGender.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }
    }

    private  boolean validateAge(){
        String val = etAge.getText().toString();
        if (val.isEmpty()) {
            etAge.setError("Field cannot be empty");
            return false;
        } else {
            etAge.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }
    }

    private  boolean validateTime(){
        String val = tvTimer.getText().toString();
        if (val.isEmpty()) {
            tvTimer.setError("Field cannot be empty");
            return false;
        } else {
            tvTimer.setError(null);
//            etDisease.setErrorEnabled(false);
            return true;
        }
    }
}