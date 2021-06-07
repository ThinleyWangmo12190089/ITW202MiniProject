package com.example.waitless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReceptionistDashboard extends AppCompatActivity {
    //For navigation
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    RecyclerView recyclerView;
    PateintHelperAdapter adapter;
    private ArrayList<PatientDetailsHelperClass> pateints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_dashboard);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pateints = new ArrayList<>();

        FirebaseRecyclerOptions<PatientDetailsHelperClass> options =
                new FirebaseRecyclerOptions.Builder<PatientDetailsHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("PatientDetails"), PatientDetailsHelperClass.class)
                        .build();
        adapter=new PateintHelperAdapter(options);
        recyclerView.setAdapter(adapter);
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
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
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
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}