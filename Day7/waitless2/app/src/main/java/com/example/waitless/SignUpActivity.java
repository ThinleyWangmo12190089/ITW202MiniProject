package com.example.waitless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class  SignUpActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt1,passwordEt2;
    private CheckBox showpassword;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseDatabase rootNode;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        rootNode = FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();
        emailEt=findViewById(R.id.email);
        passwordEt1=findViewById(R.id.password1);
        passwordEt2=findViewById(R.id.password2);
        SignUpButton=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        SignInTv=findViewById(R.id.signInTv);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean Register() {
        String email=emailEt.getText().toString();
        String password1=passwordEt1.getText().toString();
        String password2=passwordEt2.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern EMAIL_PATTERN = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        if (email.isEmpty()) {
            emailEt.setError("Field cannot be empty");
            return false;
        }else if (!EMAIL_PATTERN.matcher(email).matches()){
            emailEt.setError("Invalid email address");
            return false;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Enter your password");
            return false;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordEt2.setError("Confirm your password");
            return false;
        }
        else if(!password1.equals(password2)){
            passwordEt2.setError("Different password");
            return false;
        }
        else if(password1.length()<4){
            passwordEt1.setError("Length should be > 4");
            return false;
        }
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // send verification link

                    FirebaseUser fuser = firebaseAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "User Registered. Verification Email Has been Sent." +
                                    "\nVerify Your Email To Login.", Toast.LENGTH_LONG).show();
                            emailEt.setText("");
                            passwordEt1.setText("");
                            passwordEt2.setText("");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                        }
                    });

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference reference = rootNode.getReference().child("Users");
                    Map<String, Object> user = new HashMap<>();
                    user.put("Email", email);
                    user.put("password", password2);
                    String key = reference.push().getKey();
                    reference.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: user Profile is created");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: " + e.toString());
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return false;
    }
//    private Boolean validateEmail() {
//        String val = emailEt.getText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        Pattern EMAIL_PATTERN = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
//        if (val.isEmpty()) {
//            emailEt.setError("Field cannot be empty");
//            return false;
//        }else if (!EMAIL_PATTERN.matcher(val).matches()){
//            emailEt.setError("Invalid email address");
//            return false;
//        }
//
//        else {
//            emailEt.setError(null);
//            //emailEt.setErrorEnabled(false);
//            return true;
//        }
//    }
//    private Boolean validatePassword() {
//        String val = passwordEt1.getText().toString();
//        if (val.isEmpty()) {
//            passwordEt1.setError("Field cannot be empty");
//            return false;
//        } else {
//            passwordEt1.setError(null);
//            //passwordEt.setErrorEnabled(false);
//            return true;
//        }
//    }
//    private Boolean validateConfirmPassword() {
//        String val = passwordEt2.getText().toString();
//        if (val.isEmpty()) {
//            passwordEt2.setError("Field cannot be empty");
//            return false;
//        } else {
//            passwordEt2.setError(null);
//            //passwordEt.setErrorEnabled(false);
//            return true;
//        }
//    }
}