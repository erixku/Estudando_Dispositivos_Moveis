package com.example.calculodemedia;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btCalcular, btLimpar;
    EditText edDisciplina, edNota1, edNota2, edFaltas;
    TextView tvMedia, tvSituacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btCalcular   = (Button) findViewById(R.id.btCalcular);
        btLimpar     = (Button) findViewById(R.id.btLimpar);

        edDisciplina = (EditText) findViewById(R.id.edDisciplina);
        edNota1      = (EditText) findViewById(R.id.edNota1);
        edNota2      = (EditText) findViewById(R.id.edNota2);
        edFaltas     = (EditText) findViewById(R.id.edFaltas);

        tvMedia      = (TextView) findViewById(R.id.tvMedia);
        tvSituacao   = (TextView) findViewById(R.id.tvSituacao);


        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double nota1, nota2, media;
                int faltas;
                String situacao;

                nota1  = Double.parseDouble(edNota1.getText().toString());
                nota2  = Double.parseDouble(edNota2.getText().toString());
                faltas = Integer.parseInt(edFaltas.getText().toString());
                media  = (nota1 + nota2) / 2;
                tvMedia.setText(String.valueOf(media));

                // tvSituacao.setText("Aprovado".toString());
                if (media >= 7 && faltas <= 5) {
                    tvSituacao.setText("Aprovado");
                }
                else {
                    tvSituacao.setText("Retido");
                }
            }
        });

        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edDisciplina.setText("");
                edNota1.setText("");
                edNota2.setText("");
                edFaltas.setText("");
                tvMedia.setText("");
                tvSituacao.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}