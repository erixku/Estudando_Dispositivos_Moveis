package com.example.pequitro;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import DAO.UsuarioDAO;
import Models.UsuarioModel;

public class LoginActivity extends AppCompatActivity {

    private EditText edLogin, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edLogin = findViewById(R.id.edLogin);
        edSenha = findViewById(R.id.edSenha);
        Button btLogar = findViewById(R.id.btLogar);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioModel usuario = new UsuarioModel();
                UsuarioDAO dao = new UsuarioDAO(LoginActivity.this);
                usuario = dao.consultarUsuariosPorEmail(edLogin.getText().toString());
                if (usuario != null) {
                    if(usuario.getSenha().equals(edSenha.getText().toString())) {
                        if (usuario.getEmail().equals("admin@metro.sp.gov.br")) {
                            Intent resultado = new Intent(LoginActivity.this, ResultadoActivity.class);
                            startActivity(resultado);
                        } else {
                            Intent pesquisa = new Intent(LoginActivity.this, PesquisaActivity.class);
                            startActivity(pesquisa);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Senha Incorreta", LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário não encontrado", LENGTH_SHORT).show();
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