package com.example.telalogin;

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

public class MainActivity extends AppCompatActivity {

    Button btLogin;
    EditText edUser, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btLogin   = (Button) findViewById(R.id.btLogin);

        edUser = (EditText) findViewById(R.id.edUser);
        edSenha = (EditText) findViewById(R.id.edSenha);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int senha;
                String login;

                senha  = Integer.parseInt(edSenha.getText().toString());
                login = edUser.getText().toString();


                if (senha == 123 && login.equals("Usuario")) {
                    Intent telacadastro;
                    telacadastro = new Intent(MainActivity.this,CadastroActivity.class);
                    startActivity(telacadastro);
                    finish();
                }
                else {

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