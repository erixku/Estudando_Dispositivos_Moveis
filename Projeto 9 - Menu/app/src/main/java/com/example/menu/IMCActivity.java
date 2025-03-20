package com.example.menu;

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

public class IMCActivity extends AppCompatActivity {
    Button btCalcular;
    EditText edPeso, edAltura;
    TextView tvResultado, tvResultadoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imcactivity);

        btCalcular = (Button) findViewById(R.id.btCalcular);
        edPeso = (EditText) findViewById(R.id.edPeso);
        edAltura = (EditText) findViewById(R.id.edAltura);
        tvResultado = (TextView) findViewById(R.id.tvResultado);
        tvResultadoNum = (TextView) findViewById(R.id.tvResultadoNum);

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double altura, peso, result;
                String imc;

                altura = Double.parseDouble(edAltura.getText().toString());
                peso = Double.parseDouble(edPeso.getText().toString());

                result = peso/(Math.pow(altura, 2));
                tvResultadoNum.setText(String.valueOf(result));

                if (result < 18.5) {
                    tvResultado.setText("Abaixo do Peso");
                } else if (result <= 24.99) {
                    tvResultado.setText("Peso ideal");
                } else if (result <= 29.99) {
                    tvResultado.setText("Sobrepeso");
                } else {
                    tvResultado.setText("Odesidade");
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