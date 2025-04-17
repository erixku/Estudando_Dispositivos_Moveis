package com.example.hogwarts;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

import models.Problem;

public class ProblemasActivity extends AppCompatActivity {

    private EditText edResposta;
    private Button btPro;
    List<CheckBox> rdAlternativas;

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

        rdAlternativas = Arrays.asList(findViewById(R.id.cb1), findViewById(R.id.cb2), findViewById(R.id.cb3), findViewById(R.id.cb4));
        int SELECOES_MAX = 3;
        btPro = findViewById(R.id.btPro);

        for(CheckBox cb: rdAlternativas) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked && getContagem() > SELECOES_MAX) {
                    buttonView.setChecked(false);
                }
            });
        }

        btPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String problemas = edResposta.getText().toString();
                int selecionados = getContagem();

                //agora troca as vairáveis e classes para as do seu projeto

                if(selecionados == 0) {
                    CharSequence msg = "Selecione ao menos uma opção.";
                    Toast.makeText(ProblemasActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else if(selecionados > SELECOES_MAX) {
                    Toast.makeText(ProblemasActivity.this, "Máximo de " + SELECOES_MAX + " opções!", Toast.LENGTH_SHORT).show();
                } else {
                    Problem  prob = new Problem();
                    for (CheckBox cb : rdAlternativas) {
                        if (cb.isChecked())
                            if(problemas != null){
                                prob.getProblem().add(problemas);
                            }
                            prob.getProblem().add(cb.getText().toString());
                            //temo que arruma outra coisa
                    }
                    help.addToList("problemas", prob, Problem.class);
                    Intent pro = new Intent(ProblemasActivity.this, CadastroActivity.class);
                    startActivity(pro);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int getContagem() {
        int count = 0;
        for (CheckBox cb: rdAlternativas) {
            if(cb.isChecked())
                count++;
        }
        return count;
    }
}