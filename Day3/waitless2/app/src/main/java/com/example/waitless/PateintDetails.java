package com.example.waitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PateintDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_details);
    }

    public void NextAppointment(View view) {
        Intent intent = new Intent(PateintDetails.this, Appointment.class);
        startActivity(intent);
        finish();
    }
}