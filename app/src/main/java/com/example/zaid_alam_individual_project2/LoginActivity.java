package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText email_login;
    EditText password_login;
    Button login_btn;
    TextView signUpLink;
    private FirebaseAuth mAuth;
    private FirebaseUser loggedInUser;
    ProgressBar progressBarLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        email_login=findViewById(R.id.email_login);
        password_login=findViewById(R.id.password_login);
        login_btn=findViewById(R.id.login_btn);
        signUpLink=findViewById(R.id.signUpLink);
        progressBarLogin=findViewById(R.id.progressBarLogin);

        // Link to go to sign up page
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(myIntent);
            }
        });


        email_login.addTextChangedListener(new TextWatcher() { // Validates email input
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.isEmpty()) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                        email_login.setError(null);
                    } else {
                        email_login.setError("Enter a valid email address");
                    }
                } else {
                    email_login.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // CHecking login credentials
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLogin.setVisibility(View.VISIBLE);
                String email,password;

                email=email_login.getText().toString();
                password=password_login.getText().toString();

                if(TextUtils.isEmpty(email)){  // to check if email is empty or not
                    progressBarLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Please enter email",
                            Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password)){ // to check if password is empty or not
                    progressBarLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Please enter a password to login",Toast.LENGTH_SHORT).show();
                }


                if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){
                    // code to check credentails

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarLogin.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        loggedInUser=mAuth.getCurrentUser();
                                        if(loggedInUser != null){
                                            CommonSingleton.getInstance().setUserEmail(email);
                                            CommonSingleton.getInstance().setUserUID(loggedInUser.getUid());
                                            Toast.makeText(LoginActivity.this, "Logged In Successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            Log.i("info"," User Name " + CommonSingleton.getInstance().getUserUID());
                                            Intent myIntent=new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(myIntent);
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }



            }
        });




    }
}