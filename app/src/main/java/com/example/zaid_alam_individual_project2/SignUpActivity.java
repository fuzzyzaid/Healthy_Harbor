package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText email_signup;
    EditText password_signup;
    EditText name_signup;
    Button signup_btn ;
    TextView loginButton;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar progress_bar;
    Boolean isValidEmail =true;
    Boolean isValidPaswword=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Users");

        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        name_signup=findViewById(R.id.name_signup);
        signup_btn=findViewById(R.id.signup_btn);
        progress_bar=findViewById(R.id.progress_bar);
        loginButton=findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(myIntent);
            }
        });

        email_signup.addTextChangedListener(new TextWatcher() { // Validates email input
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.isEmpty()) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                        email_signup.setError(null);
                        isValidEmail=true;
                    } else {
                        email_signup.setError("Enter a valid email address");
                        isValidEmail=false;
                    }
                } else {
                    email_signup.setError(null);
                    isValidEmail=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        password_signup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String input = charSequence.toString();

                if (!input.isEmpty()) {
                    if (input.length() >= 6 && !input.contains(" ")) {
                        password_signup.setError(null);
                        isValidPaswword=true;
                    } else {
                        if (input.length() < 6) {
                            password_signup.setError("Password must be at least 6 characters long");
                            isValidPaswword=false;
                        } else if (input.contains(" ")) {
                            password_signup.setError("Password cannot contain white spaces");
                            isValidPaswword=false;
                        }
                    }
                } else {
                    password_signup.setError(null);
                    isValidPaswword=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                String name,email,password;

                email=email_signup.getText().toString();
                password=password_signup.getText().toString();
                name=name_signup.getText().toString();

                if(TextUtils.isEmpty(name)){ // to check if Name is empty or not
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this,"Please enter a Name",Toast.LENGTH_SHORT).show();
                }

               else if(TextUtils.isEmpty(email)){  // to check if email is empty or not
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this,"Please enter an email address",
                            Toast.LENGTH_SHORT).show();
                }


               else if(TextUtils.isEmpty(password)){ // to check if password is empty or not
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this,"Please enter a password",Toast.LENGTH_SHORT).show();
                }
               else if(isValidEmail == false){
                    Toast.makeText(SignUpActivity.this,"Please enter a valid email address",
                            Toast.LENGTH_SHORT).show();

                }
                else if(isValidPaswword == false){
                    Toast.makeText(SignUpActivity.this,"Please enter a valid password",
                            Toast.LENGTH_SHORT).show();

                }

               else{
                    // code to create new users
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progress_bar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Users user=new Users(name,email,password);
                                        Toast.makeText(SignUpActivity.this, "Account created successfully.Please Login to continue",
                                                Toast.LENGTH_SHORT).show();
                                        myRef.child(name).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent myIntent=new Intent(SignUpActivity.this,LoginActivity.class);
                                                startActivity(myIntent);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Cannot create an account.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}