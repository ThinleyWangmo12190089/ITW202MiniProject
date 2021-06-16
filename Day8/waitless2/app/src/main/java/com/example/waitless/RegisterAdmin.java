package com.example.waitless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterAdmin extends AppCompatActivity {
    private TextInputLayout regUsername, regUserid, regEmail, regPassd;
    private Button btnAdd;
    private ProgressDialog progressDialog;
    //   FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        regUsername = findViewById(R.id.username);
        regUserid = findViewById(R.id.userID);
        regEmail = findViewById(R.id.email);
        regPassd = findViewById(R.id.password);
        //   confrimPssd = findViewById(R.id.passwordConfirm);
        btnAdd = findViewById(R.id.add_user);
        progressDialog = new ProgressDialog(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() |!validatePassword() | !validateEmail() | !validateId()) {
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference= rootNode.getReference().child("Admin");

                //Get All the values
                String name = regUsername.getEditText().getText().toString().trim();
                String email = regEmail.getEditText().getText().toString().trim();
                String id = regUserid.getEditText().getText().toString().trim();
                String password = regPassd.getEditText().getText().toString().trim();

                Admin admin = new Admin(name, email, id, password);
                reference.child(id).setValue(admin);
                Toast.makeText(RegisterAdmin.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent obj = new Intent(RegisterAdmin.this, MainActivity.class);
                startActivity(obj);
            }
        });
    }
    private boolean validateName(){
        String val = regUsername.getEditText().getText().toString().trim();
        if (val.isEmpty()){
            regUsername.setError("Field Cannot be empty");
            return false;
        }
        else{
            regUsername.setError(null);
            return true;
        }

    }
    private boolean validateId() {
        String val = regUserid.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{3,20}\\z";
        if (val.isEmpty()) {
            regUserid.setError("Field Cannot be empty");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUserid.setError("White Spaces are not allowed");
            return false;
        } else {
            regUserid.setError(null);
            regUsername.setErrorEnabled(false);
            return true;

        }
    }
    private boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = regPassd.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
//                "(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassd.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassd.setError("Password is too weak");
            return false;
        } else {
            regPassd.setError(null);
            regPassd.setErrorEnabled(false);
            return true;
        }
    }
}