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

public class ResultActivity extends AppCompatActivity {

    private help help;
    private Button btEstimulada, btEspontanea, btProblemas, btCadastro;
    private TextView tvResultados;
    private String tipoSelecionado;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        help = help.getInstance(ResultActivity.this);
        btEspontanea = findViewById(R.id.btEspontanea);
        btEstimulada = findViewById(R.id.btEstimulada);
        btCadastro = findViewById(R.id.btCadastro);
        btProblemas = findViewById(R.id.btProblemas);
        tvResultados = findViewById(R.id.tvResultados);



        btEstimulada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSelecionado = "Pesquisa Estimulada";
                configurarBotao();
            }
        });

        btEspontanea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSelecionado = "Pesquisa Espontânea";
                configurarBotao();
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSelecionado = "Cadastros";
                configurarBotao();
            }
        });

        btProblemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSelecionado = "Problemas";
                configurarBotao();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configurarBotao() {
        switch (tipoSelecionado) {
            case "Pesquisa Espontânea":
                exibirDadosEspontanea();
                break;
            case "Pesquisa Estimulada":
                exibirDadosEstimulada();
                break;
            case "Problemas":
                exibirDadosProblem();
                break;
            case "Cadastros":
                exibirCadastros();
                break;
            default:
                tvResultados.setText("Selecione um tipo de dado");
        }
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
    private void exibirDadosProblem() {
        List<Problem> dados = help.getList("problemas", Problem.class);

        StringBuilder sb = new StringBuilder();
        if (dados != null && !dados.isEmpty()) {
            for (Problem item : dados) {
                sb.append("pro: ").append(item.getProblem())
                        .append("\n\n");
            }
            tvResultados.setText(sb.toString());
        } else {
            tvResultados.setText("Nenhum dado de problema encontrado");
        }
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
                        .append("\n\n");
            }
            tvResultados.setText(sb.toString());
        } else {
            tvResultados.setText("Nenhum cadastro encontrado");
        }
    }

}