package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccessActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPass;
    private String email, pass;
    private Button btnAcceder, btnRegistrar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.inputEmail);
        editTextPass = (EditText) findViewById(R.id.inputPass);

        btnAcceder = (Button) findViewById(R.id.btnAcceder);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                pass = editTextPass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    accederUsuario();
                } else {
                    Toast.makeText(AccessActivity.this, "Debe completar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccessActivity.this, MainActivity.class));
            }
        });
    }

    private void accederUsuario() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(AccessActivity.this, ProfileActivity.class));
                    finish();
                } else {
                    Toast.makeText(AccessActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}