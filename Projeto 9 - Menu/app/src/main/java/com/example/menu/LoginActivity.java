package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    Button btLogin;
    EditText edUser, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btLogin);
        edUser = findViewById(R.id.edUser);
        edSenha = findViewById(R.id.edSenha);

        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String senha, user;

                senha = edSenha.getText().toString();
                user = edUser.getText().toString();

                if(senha.equals("admin") && user.equals("admin")) {
                    Intent menu = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(menu);
                    finish();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}