package com.example.pesquisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText edLogin, edSenha;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edLogin = findViewById(R.id.edLogin);
        edSenha = findViewById(R.id.edSenha);
        btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opcao[] = {"Administrador", "Pesquisador"};
                String senhas[] = {"admin", "pesq"};
                String user = edLogin.getText().toString();
                String senha = edSenha.getText().toString();

                for(int i = 0; i < opcao.length; i++) {
                    if(opcao.equals("Administrador") && senhas.equals("admin")) {
                        Intent admin = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(admin);
                    } else if(opcao.equals("Administrador") && senhas.equals("admin")) {
                        Intent pesq = new Intent(LoginActivity.this, PesqActivity.class);
                        startActivity(pesq);
                    }
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