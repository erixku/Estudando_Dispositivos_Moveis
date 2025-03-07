package com.example.calculodeidade;

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

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btCalcular, btLimpar;
    EditText edNome, edData;
    TextView tvAnos, tvMeses, tvDias, tvHoras, tvMinutos, tvSegundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btCalcular = (Button) findViewById(R.id.btCalcular);
        btLimpar = (Button) findViewById(R.id.btLimpar);
        edNome = (EditText) findViewById(R.id.edNome);
        edData = (EditText) findViewById(R.id.edData);
        tvAnos = (TextView) findViewById(R.id.tvAnos);
        tvMeses = (TextView) findViewById(R.id.tvMeses);
        tvDias = (TextView) findViewById(R.id.tvDias);
        tvHoras = (TextView) findViewById(R.id.tvHoras);
        tvMinutos = (TextView) findViewById(R.id.tvMinutos);
        tvSegundos = (TextView) findViewById(R.id.tvSegundos);
        Date agora = new Date();

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(agora);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}