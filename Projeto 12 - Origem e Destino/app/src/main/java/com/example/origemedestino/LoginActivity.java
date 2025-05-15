package com.example.origemedestino;

import android.os.Bundle;

import static android.widget.Toast.*;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginActivity extends AppCompatActivity {

    Button btLogar;
    EditText edLogin, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        btLogar = findViewById(R.id.btLogar);
        edLogin = findViewById(R.id.edLogin);
        edSenha = findViewById(R.id.edSenha);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edLogin.getText().toString().trim();
                String senha = edSenha.getText().toString().trim();

                if(user.equals("admin") && senha.equals("admin")) {
                    Intent admin = new Intent(LoginActivity.this, Pesq1Activity.class);
                    startActivity(admin);
                    finish();
                } else {
                    makeText(LoginActivity.this, "Login não identiicado", LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rbTucuruvi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}