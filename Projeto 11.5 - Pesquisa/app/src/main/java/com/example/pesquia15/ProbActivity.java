package com.example.pesquia15;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

import helpers.PrefsHelper;
import models.ProblemasModel;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ProbActivity extends AppCompatActivity {

    List<CheckBox> opcoes;
    Button btNext;
    private PrefsHelper prefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prob);

        prefsHelper = PrefsHelper.getInstance(ProbActivity.this);

        opcoes = Arrays.asList(findViewById(R.id.cbEducacao), findViewById(R.id.cbEmprego), findViewById(R.id.cbInfra), findViewById(R.id.cbSaude), findViewById(R.id.cbSeguranca), findViewById(R.id.cbTransporte));
        int SELECOES_MAX = 3;
        btNext = findViewById(R.id.btNext);

        for(CheckBox cb: opcoes) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked && getContagem() > SELECOES_MAX) {
                    buttonView.setChecked(false);
                }
            });
        }

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selecionados = getContagem();

                if(selecionados == 0) {
                    CharSequence msg = "Selecione ao menos uma opção.";
                    Toast.makeText(ProbActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else if(selecionados > SELECOES_MAX) {
                    Toast.makeText(ProbActivity.this, "Máximo de " + SELECOES_MAX + " opções!", Toast.LENGTH_SHORT).show();
                } else {
                    ProblemasModel prob = new ProblemasModel();
                    for (CheckBox cb : opcoes) {
                        if(cb.isChecked())
                            prob.getProblemas().add(cb.getText().toString());
                        //Toast.makeText(ProbActivity.this, cb.getText().toString(), Toast.LENGTH_LONG).show();
                        Log.d("SAVE_DEBUG", "Problemas salvos: " + prob.getProblemas());
                    }
                    prefsHelper.addToList("problemas", prob, ProblemasModel.class);
                    Intent cad = new Intent(ProbActivity.this, CadActivity.class);
                    startActivity(cad);
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
        for (CheckBox cb: opcoes) {
            if(cb.isChecked())
                count++;
        }
        return count;
    }
}