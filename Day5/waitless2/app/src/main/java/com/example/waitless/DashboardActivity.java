package com.example.waitless;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {
    private Button logout;
    //For navigation
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    TextView tvTimer;
    int tHour,tMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        tvTimer = findViewById(R.id.tv_timer);

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DashboardActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tHour = hourOfDay;
                                tMinute = minute;

                                String time = tHour + ":" + tMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    tvTimer.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tHour,tMinute);
                timePickerDialog.show();
            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //for navigation
//        setUpToolbar();
//        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId())
//                {
//                    case  R.id.nav_use:
//
//                        Intent intent = new Intent(getApplicationContext(), TermsofUse.class);
//                        startActivity(intent);
//                        break;
//
//                    case  R.id.nav_pDetails:
//                        Intent intent2 = getIntent();
//                        Intent obj = new Intent(getApplicationContext(), PateintDetails.class);
//                        startActivity(obj);
//                        break;
//
//                    case  R.id.nav_appointment:
//                        Intent intent1 = new Intent(getApplicationContext(), Appointment.class);
//                        startActivity(intent1);
//                        break;
//
//                    case  R.id.nav_share:{
//
//                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                        sharingIntent.setType("text/plain");
//                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
//                        String shareSub = "Try now";
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
//                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                    }
//                    break;
//                }
//                return false;
//            }
//        });
//    }
//    public void setUpToolbar() {
//        drawerLayout = findViewById(R.id.drawerLayout);
//        Toolbar toolbar = findViewById(R.id.toolbar_user);
//        setSupportActionBar(toolbar);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
  }


}
