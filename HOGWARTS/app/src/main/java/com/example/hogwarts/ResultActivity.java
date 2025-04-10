package com.example.hogwarts;

import android.os.Bundle;

import android.os.Build;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.help;
import models.Entrevistados;
import models.Espontanea;
import models.Estimulada;
import models.Problem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    private help help;
    private Spinner spTipoDado;
    private Button btnCarregar;
    private TextView tvResultados;
    private String tipoSelecionado;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        help = help.getInstance(ResultActivity.this);
        spTipoDado = findViewById(R.id.spTipoDado);
        btnCarregar = findViewById(R.id.btnCarregar);
        tvResultados = findViewById(R.id.tvResultados);

        configurarSpinner();
        configurarBotao();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configurarSpinner() {
        String[] opcoes = {"Pesquisa Espontânea", "Pesquisa Estimulada", "Problemas", "Cadastros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDado.setAdapter(adapter);

        spTipoDado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSelecionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoSelecionado = "";
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configurarBotao() {
        btnCarregar.setOnClickListener(v -> {
            switch (tipoSelecionado) {
                case "Pesquisa Espontânea":
                    exibirDadosEspontanea();
                    break;
                case "Pesquisa Estimulada":
                    exibirDadosEstimulada();
                    break;
                case "Problemas":
                    exibirProblemas();
                    break;
                case "Cadastros":
                    exibirCadastros();
                    break;
                default:
                    tvResultados.setText("Selecione um tipo de dado");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void exibirDadosEspontanea() {
        List<Espontanea> dados = help.getList("espontaneas", Espontanea.class);

        StringBuilder sb = new StringBuilder();
        if (dados != null && !dados.isEmpty()) {
            for (Espontanea item : dados) {
                sb.append("Candidato: ").append(item.getNome())
                        .append("\n\n");
            }
            tvResultados.setText(sb.toString());
        } else {
            tvResultados.setText("Nenhum dado de pesquisa espontânea encontrado");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void exibirDadosEstimulada() {
        List<Estimulada> dados = help.getList("estimulada", Estimulada.class);

        Map<String, Integer> contagem = new HashMap<>();
        if (dados != null && !dados.isEmpty()) {
            for (Estimulada dado : dados) {
                String candidato = dado.getNome();
                contagem.put(candidato, contagem.getOrDefault(candidato, 0) + 1);
            }
        }

        // 3. Exibição formatada
        StringBuilder sb = new StringBuilder();
        if (!contagem.isEmpty()) {
            sb.append("Total de Votos: ").append(dados.size()).append("\n\n");
            sb.append("Distribuição por Candidato:\n");

            for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
                sb.append("• ").append(entry.getKey())
                        .append(": ").append(entry.getValue())
                        .append(" voto(s)\n");
            }
        } else {
            sb.append("Nenhum voto registrado");
        }

        tvResultados.setText(sb.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void exibirProblemas() {
        StringBuilder sb = new StringBuilder();
        List<Problem> registros = help.getList("problemas", Problem.class);

        Map<String, Integer> contagem = new HashMap<>();

        if (registros != null) {
            for (Problem registro : registros) {
                for (String problema : registro.getProblem()) {
                    contagem.put(problema, contagem.getOrDefault(problema, 0) + 1);
                }
            }
        }

        // Exibir resultados (adapte para sua UI)
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        tvResultados.setText(sb.toString());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void exibirCadastros() {
        List<Entrevistados> pessoas = help.getList("ultimo_cad", Entrevistados.class);
        Log.d("DEBUG", "Pessoas cadastradas: " + pessoas.size());

        StringBuilder sb = new StringBuilder();
        if (pessoas != null && !pessoas.isEmpty()) {
            for (Entrevistados pessoa : pessoas) {
                sb.append("Nome: ").append(pessoa.getNome())
                        .append("\nTelefone: ").append(pessoa.getTelefone())
                        .append("\nData: ").append(pessoa.getData())
                        .append("\nHora: ").append(pessoa.getHora())
                        .append("\nLatitude: ").append(pessoa.getLatitude())
                        .append("\nLongitude: ").append(pessoa.getLongitude())
                        .append("\n\n");
            }
            tvResultados.setText(sb.toString());
        } else {
            tvResultados.setText("Nenhum cadastro encontrado");
        }
    }

}