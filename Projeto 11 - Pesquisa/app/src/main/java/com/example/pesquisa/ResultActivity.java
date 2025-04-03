package com.example.pesquisa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import database.AppDatabase;
import database.ResultadoVoto;
import database.ResultadoEspontaneo;
import database.ResultadoProblema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    public AppDatabase db;
    private TextView totalRespondents, totalVotes;
    private RecyclerView spontaneousRecycler, stimulatedRecycler, problemsRecycler;

    private VotesAdapter spontaneousAdapter;
    private VotesAdapter stimulatedAdapter;
    private ProblemsAdapter problemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        db = AppDatabase.getDatabase(this);
        totalRespondents = findViewById(R.id.total_respondents);
        totalVotes = findViewById(R.id.total_votes);

        // Configurar RecyclerViews
        setupRecyclerViews();

        // Carregar dados
        loadDashboardData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupRecyclerViews() {
        // Votos Espontâneos
        spontaneousRecycler = findViewById(R.id.spontaneous_recycler);
        spontaneousRecycler.setLayoutManager(new LinearLayoutManager(this));
        spontaneousAdapter = new VotesAdapter(true);
        spontaneousRecycler.setAdapter(spontaneousAdapter);

        // Votos Estimulados
        stimulatedRecycler = findViewById(R.id.stimulated_recycler);
        stimulatedRecycler.setLayoutManager(new LinearLayoutManager(this));
        stimulatedAdapter = new VotesAdapter(false);
        stimulatedRecycler.setAdapter(stimulatedAdapter);

        // Problemas
        problemsRecycler = findViewById(R.id.problems_recycler);
        problemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        problemsAdapter = new ProblemsAdapter(this);
        problemsRecycler.setAdapter(problemsAdapter);
    }

    private void loadDashboardData() {
        // Carregar totais
        db.entrevistadoDAO().getTodosEntrevistados().observe(this, count -> {
            totalRespondents.setText("Respondentes: " + count);
        });

        // Carregar votos espontâneos
        db.pesquisaDAO().getVotosEspontaneos().observe(this, votes -> {
            List<VotesAdapter.VoteItem> voteItems = new ArrayList<>();
            if (votes != null) {
                for (ResultadoEspontaneo vote : votes) {
                    voteItems.add(new VotesAdapter.VoteItem(vote.esp_candidato, vote.count));
                }
            }
            spontaneousAdapter.setVotes(voteItems);

            // Atualizar total de votos
            int total = voteItems.stream().mapToInt(VotesAdapter.VoteItem::getCount).sum();
            totalVotes.setText("Votos: " + total);
        });

        // Carregar votos estimulados
        db.pesquisaDAO().getVotosEstimulados().observe(this, votes -> {
            List<VotesAdapter.VoteItem> voteItems = new ArrayList<>();

            int[] contagens = new int[9]; // Índices 1-8
            if (votes != null) {
                for (ResultadoVoto vote : votes) {
                    if (vote.est_id >= 1 && vote.est_id <= 8) {
                        contagens[vote.est_id] = vote.count;
                    }
                }
            }

            // Mapear IDs para nomes fictícios
            voteItems.add(new VotesAdapter.VoteItem("Candidato 1", contagens[1]));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 2", contagens[2]));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 3", contagens[3]));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 4", contagens[4]));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 5", contagens[5]));
            voteItems.add(new VotesAdapter.VoteItem("Branco", contagens[6]));
            voteItems.add(new VotesAdapter.VoteItem("Nulo", contagens[7]));
            voteItems.add(new VotesAdapter.VoteItem("Não sabe", contagens[8]));

            stimulatedAdapter.setVotes(voteItems);
        });

        // Carregar problemas
        db.pesquisaDAO().getEstatisticaProblema().observe(this, problems -> {
            List<ProblemsAdapter.ProblemItem> problemItems = new ArrayList<>();
            if (problems != null) {
                for (ResultadoProblema problem : problems) {
                    problemItems.add(new ProblemsAdapter.ProblemItem(problem.prob_nome, problem.contagem));
                }
            }
            problemsAdapter.setProblems(problemItems);
        });
    }

        private String getProblemName(int problemId) {
            // Mapear IDs de problemas para nomes
            switch (problemId) {
                case 1: return "Segurança";
                case 2: return "Saúde";
                case 3: return "Educação";
                case 4: return "Transporte";
                case 5: return "Infraestrutura";
                case 6: return "Emprego";
                default: return "Outro";
            }
        }
    }
