package com.example.waitless;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterReciptionist extends AppCompatActivity {
    private EditText nameEt, phoneNumberEt, emailEt,passwordEt1,passwordEt2;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reciptionist);


        rootNode = FirebaseDatabase.getInstance();

        nameEt=findViewById(R.id.name);
        phoneNumberEt=findViewById(R.id.phoneNumber);
        emailEt=findViewById(R.id.email);
        phoneNumberEt = findViewById(R.id.phoneNumber);
        passwordEt1=findViewById(R.id.password1);
        passwordEt2=findViewById(R.id.password2);
        SignUpButton=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() |!validatePassword() | !validateEmail() | !validateId()) {
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference= rootNode.getReference().child("Reciptionist");

                //Get All the values
                String name = nameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String phone = phoneNumberEt.getText().toString().trim();
                String pass = passwordEt1.getText().toString().trim();
                String password = passwordEt2.getText().toString().trim();

                Users users = new Users(name, email, phone, password);
                reference.child(phone).setValue(users);
                Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private boolean validateName(){
        String val = nameEt.getText().toString().trim();
        if (val.isEmpty()){
            nameEt.setError("Field Cannot be empty");
            return false;
        }
        else if (!val.matches("[a-zA-Z ]+")){
            nameEt.setError("Name cannot be digits or numbers");
            return false;
        }
        else{
            nameEt.setError(null);
            return true;
        }

    }
    private boolean validateId() {
        String val = phoneNumberEt.getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            phoneNumberEt.setError("Field Cannot be empty");
            return false;
        } else if (val.length() != 8) {
            phoneNumberEt.setError("userId should be their enrolment number");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            phoneNumberEt.setError("White Spaces are not allowed");
            return false;
        }
        else {
            phoneNumberEt.setError(null);
            return true;

        }
    }
    private boolean validateEmail(){
        String val = emailEt.getText().toString();

        if (val.isEmpty()) {
            emailEt.setError("Field cannot be empty");
            return false;
        }
        else {
            emailEt.setError(null);
//            emailEt.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePassword(){
        String val = passwordEt1.getText().toString();
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
            passwordEt1.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwordEt1.setError("Password is too weak, use atleast 1 alphabet and digit");
            return false;
        } else { ;
            return true;
        }

    }
}