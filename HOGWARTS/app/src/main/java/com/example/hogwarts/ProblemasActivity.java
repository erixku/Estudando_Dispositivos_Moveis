package com.example.hogwarts;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.Problem;

public class ProblemasActivity extends AppCompatActivity {

    private EditText edResposta;
    private Button btPro;

    models.help help;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_problemas);


        help  = help.getInstance(ProblemasActivity.this);

        edResposta = findViewById(R.id.edResposta);
        btPro = findViewById(R.id.btPro);

        btPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problemas = edResposta.getText().toString();

                if(problemas.isEmpty()) {
                    Toast.makeText(ProblemasActivity.this, "Indique pelo menos 3 problemas", Toast.LENGTH_SHORT).show();
                } else {
                    Problem pro = new Problem(problemas);
                    help.addToList("problemas", pro, Problem.class);
                    Intent prob = new Intent(ProblemasActivity.this, CadastroActivity.class);
                    startActivity(prob);
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