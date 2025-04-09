package com.example.pesquia15;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import helpers.PrefsHelper;
import models.EspontaneaModel;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EspActivity extends AppCompatActivity {

    EditText edCandidato;
    Button btProximo;
    PrefsHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_esp);

        prefsHelper  = PrefsHelper.getInstance(EspActivity.this);

        edCandidato = findViewById(R.id.edCandidato);
        btProximo = findViewById(R.id.btProximo);

        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidato = edCandidato.getText().toString();

                if(candidato.isEmpty()) {
                    Toast.makeText(EspActivity.this, "Indique um candidato", Toast.LENGTH_SHORT).show();
                } else {
                    EspontaneaModel esp = new EspontaneaModel(candidato);
                    prefsHelper.addToList("espontaneas", esp, EspontaneaModel.class);
                    Intent est = new Intent(EspActivity.this, EstActivity.class);
                    startActivity(est);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edCandidato), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}