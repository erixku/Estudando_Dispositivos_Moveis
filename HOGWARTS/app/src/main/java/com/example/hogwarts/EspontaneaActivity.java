package com.example.hogwarts;

import android.os.Build;
import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.help;
import models.Espontanea;

public class EspontaneaActivity extends AppCompatActivity {

    private EditText edResposta;
    private Button btTudo;

    help help;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_espontanea);


        help  = help.getInstance(EspontaneaActivity.this);

        edResposta = findViewById(R.id.edResposta);
        btTudo = findViewById(R.id.btTudo);

        btTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidato = edResposta.getText().toString();

                if(candidato.isEmpty()) {
                    Toast.makeText(EspontaneaActivity.this, "Indique um candidato", Toast.LENGTH_SHORT).show();
                } else {
                    Espontanea esp = new Espontanea(candidato);
                    help.addToList("espontaneas", esp, Espontanea.class);
                    Intent est = new Intent(EspontaneaActivity.this, EstimuladaActivity.class);
                    startActivity(est);
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