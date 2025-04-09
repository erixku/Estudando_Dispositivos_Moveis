package com.example.pesquia15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import helpers.PrefsHelper;
import models.EstimuladaModel;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EstActivity extends AppCompatActivity {

    RadioGroup rgCandidatos;
    RadioButton rbAfrodite, rbAres, rbArtemis, rbHefesto, rbNyx, rbBranco, rbNulo, rbNaoSei;
    PrefsHelper prefsHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_est);

        prefsHelper = PrefsHelper.getInstance(EstActivity.this);

        rgCandidatos = findViewById(R.id.rgCandidatos);
        rbAfrodite = findViewById(R.id.rbAfrodite);
        rbAres = findViewById(R.id.rbAres);
        rbArtemis = findViewById(R.id.rbArtemis);
        rbHefesto = findViewById(R.id.rbHefesto);
        rbNyx = findViewById(R.id.rbNyx);
        rbBranco = findViewById(R.id.rbBranco);
        rbNulo = findViewById(R.id.rbNulo);
        rbNaoSei = findViewById(R.id.rbNaoSei);

        rgCandidatos.setOnCheckedChangeListener((grupo, idMarcado) -> {
            String nome = "";

            if(idMarcado == R.id.rbAfrodite) {
                nome = rbAfrodite.getText().toString();
            } else if (idMarcado == R.id.rbAres) {
                nome = rbAres.getText().toString();
            } else if (idMarcado == R.id.rbArtemis) {
                nome = rbArtemis.getText().toString();
            } else if (idMarcado == R.id.rbHefesto) {
                nome = rbHefesto.getText().toString();
            } else if (idMarcado == R.id.rbNyx) {
                nome = rbNyx.getText().toString();
            } else if (idMarcado == R.id.rbBranco) {
                nome = rbBranco.getText().toString();
            } else if (idMarcado == R.id.rbNulo) {
                nome = rbNulo.getText().toString();
            } else if (idMarcado == R.id.rbNaoSei) {
                nome = rbNaoSei.getText().toString();
            }

            EstimuladaModel est = new EstimuladaModel(nome);
            prefsHelper.addToList("estimulada", est, EstimuladaModel.class);

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