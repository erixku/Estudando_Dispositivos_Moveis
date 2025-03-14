
package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btCalcular;
    EditText etNota1, etNota2, etNota3, etFaltas;
    TextView tvCalculo, tvResultado, tvFaltas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btCalcular   = (Button) findViewById(R.id.btCalcular);

        etNota1 = (EditText) findViewById(R.id.etNota1);
        etNota2 = (EditText) findViewById(R.id.etNota2);
        etNota3 = (EditText) findViewById(R.id.etNota3);
        etFaltas  = (EditText) findViewById(R.id.etFaltas);

        tvCalculo      = (TextView) findViewById(R.id.tvCalculo);
        tvResultado   = (TextView) findViewById(R.id.tvResultado);
        tvFaltas   = (TextView) findViewById(R.id.tvFaltas);


        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double nota1, nota2, nota3, media;
                int faltas;

                nota1  = Double.parseDouble(etNota1.getText().toString());
                nota2  = Double.parseDouble(etNota2.getText().toString());
                nota3  = Double.parseDouble(etNota3.getText().toString());
                faltas = Integer.parseInt(etFaltas.getText().toString());
                media  = (nota1 + nota2 + nota3) / 3;
                tvCalculo.setText(String.format("%.2f",media));

                if (media >= 6 && faltas <= 5) {
                    tvResultado.setText("Aprovado");
                }
                else {
                    tvResultado.setText("Reprovado");
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