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
import androidx.room.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private AppDatabase db;
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

        db = AppDatabase;
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
        problemsAdapter = new ProblemsAdapter();
        problemsRecycler.setAdapter(problemsAdapter);
    }

    private void loadDashboardData() {
        // Carregar totais
        db.EntrevistadoDAO().getTotalRespondents().observe(this, count -> {
            totalRespondents.setText("Respondentes: " + count);
        });

        // Carregar votos espontâneos
        db.PesquisaDAO().getSpontaneousVotesCount().observe(this, votes -> {
            List<VotesAdapter.VoteItem> voteItems = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                voteItems.add(new VotesAdapter.VoteItem(entry.getKey(), entry.getValue()));
            }
            spontaneousAdapter.setVotes(voteItems);

            // Atualizar total de votos
            int total = voteItems.stream().mapToInt(VotesAdapter.VoteItem::getCount).sum();
            totalVotes.setText("Votos: " + total);
        });

        // Carregar votos estimulados
        db.PesquisaDAO().getStimulatedVotesCount().observe(this, votes -> {
            List<VotesAdapter.VoteItem> voteItems = new ArrayList<>();

            // Mapear IDs para nomes fictícios
            voteItems.add(new VotesAdapter.VoteItem("Candidato 1", votes.getOrDefault(1, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 2", votes.getOrDefault(2, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 3", votes.getOrDefault(3, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 4", votes.getOrDefault(4, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Candidato 5", votes.getOrDefault(5, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Branco", votes.getOrDefault(6, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Nulo", votes.getOrDefault(7, 0)));
            voteItems.add(new VotesAdapter.VoteItem("Não sabe", votes.getOrDefault(8, 0)));

            stimulatedAdapter.setVotes(voteItems);
        });

        // Carregar problemas
        db.PesquisaDAO().getProblemsStatistics().observe(this, problems -> {
            List<ProblemsAdapter.ProblemItem> problemItems = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : problems.entrySet()) {
                String problemName = getProblemName(entry.getKey());
                problemItems.add(new ProblemsAdapter.ProblemItem(problemName, entry.getValue()));
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
}