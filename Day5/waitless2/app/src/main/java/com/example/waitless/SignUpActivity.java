package com.example.waitless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
            public void onClick(View v) {
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
    private void Register() {
        String email=emailEt.getText().toString();
        String password1=passwordEt1.getText().toString();
        String password2=passwordEt2.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordEt2.setError("Confirm your password");
            return;
        }
        else if(!password1.equals(password2)){
            passwordEt2.setError("Different password");
            return;
        }
        else if(password1.length()<4){
            passwordEt1.setError("Length should be > 4");
            return;
        }
//        else if(!isVallidEmail(email)){
//            emailEt.setError("Invalid email");
//            return;
//        }

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
                    user.put("isUser", "0");

                    reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    }
//    private Boolean isVallidEmail(CharSequence target){
//        return (!TextUtils.isEmpty(target)  && Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
}