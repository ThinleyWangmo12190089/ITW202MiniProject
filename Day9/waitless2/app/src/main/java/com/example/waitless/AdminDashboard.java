package com.example.waitless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
//    //For navigation
//    DrawerLayout drawerLayout;
//    ActionBarDrawerToggle actionBarDrawerToggle;
//    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

//        //for navigation
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
//                    case  R.id.nav_logout:
//                        FirebaseAuth.getInstance().signOut();
//                        Intent intent1 = new Intent(AdminDashboard.this,MainActivity.class);
//                        startActivity(intent1);
//                        finish();
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
   }
//    public void setUpToolbar() {
//        drawerLayout = findViewById(R.id.drawerLayout);
//        Toolbar toolbar = findViewById(R.id.toolbar_user);
//        setSupportActionBar(toolbar);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
   // }

    public void addReciptionist(View view) {
        Intent obj = new Intent(getApplicationContext(), RegisterReciptionist.class);
        startActivity(obj);
        finish();
    }
}