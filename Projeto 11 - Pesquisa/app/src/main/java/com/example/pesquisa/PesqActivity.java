package com.example.pesquisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.UUID;

import database.AppDatabase;
import database.EspontaneaModel;

public class PesqActivity extends AppCompatActivity {

    private EditText edPesqEsp;
    private Button btProx = findViewById(R.id.btProx);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesq);

        edPesqEsp = findViewById(R.id.edPesqEsp);

        btProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edPesqEsp.getText().toString().isEmpty()) {
                    salvarVotoEspontaneo();
                    Intent esti = new Intent(PesqActivity.this, EstiActivity.class);
                    startActivity(esti);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void salvarVotoEspontaneo() {
        new Thread(() -> {
            EspontaneaModel voto = new EspontaneaModel();
            voto.esp_candidato = edPesqEsp.getText().toString();

            AppDatabase.getDatabase(this).pesquisaDAO().insertEspontaea(voto);
        });
    }

}