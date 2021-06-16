package com.example.waitless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button SignInButton;
    private TextView SignUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        SignInButton = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        SignUpTv = findViewById(R.id.signUpTv);
        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                int countNo = emailEt.getText().length();
                if (!validateEmail() | !validatePassword()) {
                    return;
                } else if (countNo == 3) {
//                    ProgressDialog progressDialog = new ProgressDialog(this);
//                    progressDialog.setMessage("Please wait...");
//                    progressDialog.show();
                    final String userEnteredUsername = emailEt.getText().toString().trim();
                    final String userEnteredPassword = passwordEt.getText().toString().trim();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");

                    Query checkUser = reference.orderByChild("id").equalTo(userEnteredUsername);


                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                emailEt.setError(null);
                                emailEt.setEnabled(false);

                                String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                                if (passwordFromDB.equals(userEnteredPassword)) {
                                    emailEt.setError(null);
                                    emailEt.setEnabled(false);
                                    String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                                    String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                                    String idFromDB = dataSnapshot.child(userEnteredUsername).child("id").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("id", idFromDB);
                                    intent.putExtra("password", passwordFromDB);
//                                    progressDialog.dismiss();
                                    startActivity(intent);
                                    finish();
                                } else {
//                                    progressDialog.dismiss();
                                    passwordEt.setError("Wrong Password!");
                                    passwordEt.requestFocus();
                                }
                            } else {
//                                progressDialog.dismiss();
                                emailEt.setError("No user exists");
                                emailEt.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (countNo == 8){
                    final String userEnteredUsername = emailEt.getText().toString().trim();
                    final String userEnteredPassword = passwordEt.getText().toString().trim();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reciptionist");

                    Query checkUser = reference.orderByChild("phone").equalTo(userEnteredUsername);


                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                emailEt.setError(null);
                                emailEt.setEnabled(false);

                                String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                                if (passwordFromDB.equals(userEnteredPassword)) {
                                    emailEt.setError(null);
                                    emailEt.setEnabled(false);
                                    String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                                    String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                                    String idFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(), ReceptionistDashboard.class);
                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("phone", idFromDB);
                                    intent.putExtra("password", passwordFromDB);
//                                    progressDialog.dismiss();
                                    startActivity(intent);
                                    finish();
                                } else {
//                                    progressDialog.dismiss();
                                    passwordEt.setError("Wrong Password!");
                                    passwordEt.requestFocus();
                                }
                            } else {
//                                progressDialog.dismiss();
                                emailEt.setError("No user exists");
                                emailEt.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    Login();
                }
            }
        });


    }
    private void Login() {
        String email=emailEt.getText().toString();
        String password=passwordEt.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter your password");
            return;
        }
//            progressDialog.setMessage("Please wait");
//            progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "Sign in fail!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }
        });
    }
    public void RegisterAdmin(View view){
        Intent in = new Intent(getApplicationContext(), RegisterAdmin.class);
        startActivity(in);
        finish();
    }
    private Boolean validateEmail() {
        String val = emailEt.getText().toString();

        if (val.isEmpty()) {
            emailEt.setError("Field cannot be empty");
            return false;
        }
        else {
            emailEt.setError(null);
            //emailEt.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = passwordEt.getText().toString();
        if (val.isEmpty()) {
            passwordEt.setError("Field cannot be empty");
            return false;
        } else {
            passwordEt.setError(null);
            //passwordEt.setErrorEnabled(false);
            return true;
        }
    }

}