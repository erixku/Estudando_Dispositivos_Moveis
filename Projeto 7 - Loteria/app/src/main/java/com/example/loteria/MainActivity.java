package com.example.loteria;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btLotofacil, btMegasena;
    private TextView tvResultado;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btLotofacil = findViewById(R.id.btLotofacil);
        btMegasena = findViewById(R.id.btMegasena);
        tvResultado = findViewById(R.id.tvResultado);

        btLotofacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarAposta(15, 25); // Lotofácil: 15 números de 1 a 25
            }
        });

        btMegasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarAposta(6, 60); // Mega-Sena: 6 números de 1 a 60
            }
        });

    private void gerarAposta(int quantidade, int maxNumero) {
        Set<Integer> numerosSorteados = new HashSet<>();
        while (numerosSorteados.size() < quantidade) {
            int numero = random.nextInt(maxNumero) + 1;
            numerosSorteados.add(numero);
        }

        ArrayList<Integer> listaNumeros = new ArrayList<>(numerosSorteados);
        Collections.sort(listaNumeros); // Ordena os números gerados

        tvResultado.setText("Números: " + listaNumeros.toString());
    }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}