package com.example.waitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
    }

    public void Done(View view) {
        Intent intent = new Intent(Appointment.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}