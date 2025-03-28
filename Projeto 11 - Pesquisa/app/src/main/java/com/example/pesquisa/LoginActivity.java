package com.example.pesquisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    EditText edLogin, edSenha;

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
                String login[] = {"Pesquisador", "Administrador"};
                String senha[] = {"123", "admin"};
                String acesso = edLogin.getText().toString();
                String pwd = edSenha.getText().toString();

                for(int i = 0; i < login.length; i++) {
                    if(acesso.equals("Administrador") && pwd.equals("admin")) {
                        Intent menu = new Intent(LoginActivity.this, MenuAdminActivity.class);
                        startActivity(menu);
                        finish();
                    } else if(acesso.equals("Pesquisador") && pwd.equals("123")){
                        Intent menu = new Intent(LoginActivity.this, MenuPesqActivity.class);
                        startActivity(menu);
                        finish();
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