package com.example.pesquia15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EstActivity extends AppCompatActivity {

    RadioGroup rgCandidatos;
    RadioButton rbAfrodite, rbAres, rbArtemis, rbHefesto, rbNyx, rbBranco, rbNulo, rbNaoSei;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_est);

        rgCandidatos = findViewById(R.id.rgCandidatos);
//        rbAfrodite = findViewById(R.id.rbAfrodite);
//        rbAres = findViewById(R.id.rbAres);
//        rbArtemis = findViewById(R.id.rbArtemis);
//        rbHefesto = findViewById(R.id.rbHefesto);
//        rbNyx = findViewById(R.id.rbNyx);
//        rbBranco = findViewById(R.id.rbBranco);
//        rbNulo = findViewById(R.id.rbNulo);
//        rbNaoSei = findViewById(R.id.rbNaoSei);

        rgCandidatos.setOnCheckedChangeListener((grupo, idMarcado) -> {
            if(idMarcado == R.id.rbAfrodite) {

            } else if (idMarcado == R.id.rbAres) {

            } else if (idMarcado == R.id.rbArtemis) {

            } else if (idMarcado == R.id.rbHefesto) {

            } else if (idMarcado == R.id.rbNyx) {

            } else if (idMarcado == R.id.rbBranco) {

            } else if (idMarcado == R.id.rbNulo) {

            } else if (idMarcado == R.id.rbNaoSei) {

            }

            Intent prob = new Intent(EstActivity.this, ProbActivity.class);
            startActivity(prob);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}