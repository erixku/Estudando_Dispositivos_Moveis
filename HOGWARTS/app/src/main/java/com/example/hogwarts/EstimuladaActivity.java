package com.example.hogwarts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import models.help;
import models.Estimulada;

public class EstimuladaActivity extends AppCompatActivity {

    RadioGroup rgCandidatos;
    RadioButton rbAfrodite, rbAres, rbArtemis, rbHefesto, rbNyx, rbBranco, rbNulo, rbNaoSei;
    private help help;

    Button btConfirmar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estimulada);


            help = help.getInstance(EstimuladaActivity.this);

            rgCandidatos = findViewById(R.id.rgCandidatos);
            rbAfrodite = findViewById(R.id.rbGriffyndor);
            rbAres = findViewById(R.id.rbSlytherin);
            rbArtemis = findViewById(R.id.rbHufflepuff);
            rbHefesto = findViewById(R.id.rbRavenclaw);
            rbBranco = findViewById(R.id.rbBranco);
            rbNulo = findViewById(R.id.rbNulo);
            rbNaoSei = findViewById(R.id.rbNaoSei);
            btConfirmar = findViewById(R.id.btConfirmar);

            btConfirmar.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    String nome;
                    nome = "";
                    int idMarcado = rgCandidatos.getCheckedRadioButtonId();
                    if(idMarcado == R.id.rbGriffyndor) {
                        nome = rbAfrodite.getText().toString();
                    } else if (idMarcado == R.id.rbSlytherin) {
                        nome = rbAres.getText().toString();
                    } else if (idMarcado == R.id.rbHufflepuff) {
                        nome = rbArtemis.getText().toString();
                    } else if (idMarcado == R.id.rbRavenclaw) {
                        nome = rbHefesto.getText().toString();
                    } else if (idMarcado == R.id.rbBranco) {
                        nome = rbBranco.getText().toString();
                    } else if (idMarcado == R.id.rbNulo) {
                        nome = rbNulo.getText().toString();
                    } else if (idMarcado == R.id.rbNaoSei) {
                        nome = rbNaoSei.getText().toString();
                    }

                    Estimulada est = new Estimulada(nome);
                    help.addToList("estimulada", est, Estimulada.class);

                  Intent prob = new Intent(EstimuladaActivity.this, ProblemasActivity.class);
                  startActivity(prob);
                }
            });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}