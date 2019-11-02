package com.example.user.healthsupervisor;

/**
 * Created by USER on 11/1/2017.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)
                {
                    //profile activity here
                    finish();
                    startActivity(new Intent(Login.this,Mymeds.class));
                }
            }
        };



        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void userLogin()
        {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                Toast.makeText(this, "Please fill in all the Fields!", Toast.LENGTH_SHORT).show();

            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(!task.isSuccessful())
                                {
                                    //start profile activity
                                    Toast.makeText(Login.this, "Problem in Logging in", Toast.LENGTH_SHORT).show();
                                    //finish();
                                    // startActivity(new Intent(getApplicationContext(),Mymeds.class));
                                }
                            }
                        });

            }
        }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn)
        {
            userLogin();
        }

        if(view == textViewSignUp)
        {
            finish();
            startActivity(new Intent(this, registerpage.class));
        }

    }
}
