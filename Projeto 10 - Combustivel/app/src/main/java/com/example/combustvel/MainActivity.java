package com.example.combustvel;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvResultadoNum, tvResultado;
    EditText edEtanol, edGasolina;
    Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btCalcular = findViewById(R.id.btCalcular);
        tvResultado = findViewById(R.id.tvResultado);
        tvResultadoNum = findViewById(R.id.tvResultadoNum);
        edEtanol = findViewById(R.id.edEtanol);
        edGasolina = findViewById(R.id.edGasolina);

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float gas, etanol, resultado;

                gas = Float.parseFloat(edGasolina.getText().toString());
                etanol = Float.parseFloat(edEtanol.getText().toString());

                resultado = etanol/gas;
                tvResultado.setText(String.valueOf(resultado));

                if(resultado>0.7) {
                    tvResultado.setText("Gasolina.");
                } else {
                    tvResultado.setText("Etanol.");
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