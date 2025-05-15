package com.example.origemedestino;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Models.Selecao;

public class Pesq2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesq2);

        CheckBox checkBoxTucuruvi = findViewById(R.id.radioButton2); // exemplo de um CheckBox

        checkBoxTucuruvi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (Selecao.totalSelecionados >= 2) {
                    Toast.makeText(this, "Você só pode selecionar até 2 estações no total.", Toast.LENGTH_SHORT).show();
                    checkBoxTucuruvi.setChecked(false);
                } else {
                    Selecao.totalSelecionados++;
                    if (Selecao.origem == null) {
                        Selecao.origem = checkBoxTucuruvi.getText().toString();
                    } else {
                        Selecao.destino = checkBoxTucuruvi.getText().toString();
                    }
                }
            } else {
                Selecao.totalSelecionados--;
                String nomeEstacao = checkBoxTucuruvi.getText().toString();
                if (nomeEstacao.equals(Selecao.origem)) {
                    Selecao.origem = null;
                } else if (nomeEstacao.equals(Selecao.destino)) {
                    Selecao.destino = null;
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rbTucuruvi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}