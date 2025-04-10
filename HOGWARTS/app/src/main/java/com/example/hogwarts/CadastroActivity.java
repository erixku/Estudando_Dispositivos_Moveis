package com.example.hogwarts;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import models.help;
import models.Entrevistados;

public class CadastroActivity extends AppCompatActivity {


    EditText edNome, edTelefone;
    Button btVoltar;
    private help help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);


        help = help.getInstance(CadastroActivity.this);

        edNome = findViewById(R.id.edNome);
        edTelefone = findViewById(R.id.edTelefone);
        btVoltar = findViewById(R.id.btVoltar);


        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar(edNome.getText().toString(), edTelefone.getText().toString(), () -> {
                    Intent voltar = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(voltar);
                    finish();
                });
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvar(String nome, String telefone, Runnable onFinish) {



        String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        Entrevistados entre = new Entrevistados(
                nome, telefone, data, hora
        );

        help.addToList("ultimo_cad", entre, Entrevistados.class);

            onFinish.run();
        }
}



