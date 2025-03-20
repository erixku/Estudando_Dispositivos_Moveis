package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    Button btIMC, btMedia, btConversor, btFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        btIMC = findViewById(R.id.btIMC);
        btMedia = findViewById(R.id.btMedia);
        btConversor = findViewById(R.id.btConversor);
        btFinalizar = findViewById(R.id.btFechar);

        btIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imc = new Intent(MenuActivity.this, IMCActivity.class);
                startActivity(imc);
            }
        });

        btMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent media = new Intent(MenuActivity.this, MediaActivity.class);
                startActivity(media);
            }
        });

        btConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversor = new Intent(MenuActivity.this, ConversorActivity.class);
                startActivity(conversor);
            }
        });

        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}