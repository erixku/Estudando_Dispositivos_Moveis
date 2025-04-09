package com.example.pesquia15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {

    Button btPesq, btEstatistica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        btPesq = findViewById(R.id.btPesq);
        btEstatistica = findViewById(R.id.btEstatistica);

        btPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesq = new Intent(AdminActivity.this, EspActivity.class);
                startActivity(pesq);
            }
        });

        btEstatistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent esta = new Intent(AdminActivity.this, EstatActivity.class);
                startActivity(esta);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}