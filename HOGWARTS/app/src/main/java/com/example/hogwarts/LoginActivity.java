package com.example.hogwarts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsuario, edSenha;
    private Button btLog;

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "12345";
    private final String USER_USERNAME = "pesq";
    private final String USER_PASSWORD = "67890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edUsuario = findViewById(R.id.edUsuario);
        edSenha = findViewById(R.id.edSenha);
        btLog = findViewById(R.id.btLog);

        btLog.setOnClickListener(v -> {
            String username = edUsuario.getText().toString();
            String password = edSenha.getText().toString();

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            } else if (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD)) {
               // Intent intent = new Intent(LoginActivity.this, PesquiActivity.class);
               // startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}