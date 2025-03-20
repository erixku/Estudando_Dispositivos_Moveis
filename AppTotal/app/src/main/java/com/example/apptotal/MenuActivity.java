package com.example.apptotal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    Button btIMC, btIdade, btConversor, btFinalizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        btIMC = findViewById(R.id.btIMC);
        btIdade = findViewById(R.id.btIdade);
        btConversor = findViewById(R.id.btConversor);

        btIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IMC;
                IMC = new Intent(MenuActivity.this, IMCActivity.class);
                startActivity(IMC);
               // finish();
            }
        });

        btIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idade;
                idade = new Intent(MenuActivity.this, IdadeActivity.class);
                startActivity(idade);
                // finish();
            }
        });

        btConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversor;
                conversor = new Intent(MenuActivity.this, ConversorActivity.class);
                startActivity(conversor);
                // finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}