package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private TextView txtBienvenida;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtBienvenida = (TextView) findViewById(R.id.txtBienvenida);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        for(UserInfo profile : mUser.getProviderData()) {
            String providerId = profile.getProviderId();
            // UID specific to the provider
            String uid = profile.getUid();

            // Name, email address, and profile photo Url
            String name = profile.getDisplayName();
            String email = profile.getEmail();

            txtBienvenida.setText("Hola " + name);
        }

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
}