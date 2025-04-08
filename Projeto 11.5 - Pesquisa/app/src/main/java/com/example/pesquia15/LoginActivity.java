package com.example.pesquia15;

import static android.widget.Toast.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button btEntrar;
    EditText edLogin, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btEntrar = findViewById(R.id.btEntrar);
        edLogin = findViewById(R.id.edLogin);
        edSenha = findViewById(R.id.edSenha);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edLogin.getText().toString().trim();
                String senha = edSenha.getText().toString().trim();

                if(edLogin == null || edSenha == null) {
                    Log.e("DEBUG", "EditText vazio");
                } else if(user == null || senha == null) {
                    Log.e("DEBUG", "Variáveis não inicializadas");
                }

                if(user.equals("admin") && senha.equals("admin")) {
                    Intent admin = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(admin);
                    finish();
                } else if(user.equals("pesq") && senha.equals("pesq")) {
                    Intent pesq = new Intent(LoginActivity.this, EspActivity.class);
                    startActivity(pesq);
                    finish();
                } else {
                    makeText(LoginActivity.this, "Login não identiicado", LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edCandidato), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}