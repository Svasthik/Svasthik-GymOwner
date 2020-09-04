package com.example.svasthik_gymowner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button buLogin;

    private EditText etEmail;
    private TextView tvForgotPassword;
    private EditText etPassword;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buLogin = (Button) findViewById(R.id.buLogin);
        etEmail = (EditText) findViewById(R.id.etForgotEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);



        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


//Login Onclick Listener
        buLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = etEmail.getText().toString();
                String password_text = etPassword.getText().toString();
                loginUser(email_text, password_text);
            }
        });
//Forgot Password On Click Listener
//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SigninActivity.this, ForgotActivity.class));
//            }
//        });
    }
    private void loginUser(String email_text, String password_text) {


//ProgressDialog  show
        progressDialog.setMessage("Welcome to Svasthik");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email_text,password_text).addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

//progressDialog dismiss
                progressDialog.dismiss();
                //Toast.makeText(SigninActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                //checkEmailVerificarion();
                finish();
            }
        });
    }
//    private void checkEmailVerificarion(){
//        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
//        Boolean emailflag=firebaseUser.isEmailVerified();
//
//        if(emailflag){
//            finish();
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//        }else{
//            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
//            auth.signOut();
//        }
//    }
    // Always Logged In
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
}