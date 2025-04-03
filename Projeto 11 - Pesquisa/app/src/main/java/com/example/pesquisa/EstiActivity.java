package com.example.pesquisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.AppDatabase;
import database.EstimuladaModel;

public class EstiActivity extends AppCompatActivity {
    private int candidatoSelecionado = -1;
    private AppDatabase db;
    private RadioGroup grupoOpcoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_esti);

        db = AppDatabase.getDatabase(this);
        grupoOpcoes = findViewById(R.id.rgOpcoesEspeciais);
        Button proximo = findViewById(R.id.btnProximo);

        setupCandidateButtons();
        setupSpecialOptions();

        proximo.setOnClickListener(v -> {
            if (candidatoSelecionado != -1) {
                saveStimulatedVote();
                Intent problema = new Intent(EstiActivity.this, ProbActivity.class);
                startActivity(problema);
            } else {
                Toast.makeText(this, "Selecione uma opção", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupCandidateButtons() {
        int[] candidateButtons = {R.id.cardCandidato1, R.id.cardCandidato2 /* adicione outros */};
        int[] candidateImageViews = {R.id.ivCandidato1, R.id.ivCandidato2 /* adicione outros */};

        for (int i = 0; i < candidateButtons.length; i++) {
            int candidateId = i + 1; // IDs começam em 1
            View card = findViewById(candidateButtons[i]);

            // Carregar imagem do candidato (substitua por sua lógica)
            ImageView imageView = findViewById(candidateImageViews[i]);
            loadCandidateImage(imageView, candidateId);

            card.setOnClickListener(v -> {
                candidatoSelecionado = candidateId;
                grupoOpcoes.clearCheck();
                updateSelectionUI();
            });
        }
    }

    private void setupSpecialOptions() {
        grupoOpcoes.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                candidatoSelecionado = mapRadioToCandidateId(checkedId);
                clearCandidateSelection();
            }
        });
    }

    private int mapRadioToCandidateId(int radioId) {
        if (radioId == R.id.rbBranco) return 6;
        if (radioId == R.id.rbNulo) return 7;
        if (radioId == R.id.rbNaoSabe) return 8;
        return -1;
    }

    private void updateSelectionUI() {
        // Resetar todas seleções visuais
        resetAllSelections();

        // Destacar seleção atual
        if (candidatoSelecionado >= 1 && candidatoSelecionado <= 5) {
            int cardId = getResources().getIdentifier("cardCandidato" + candidatoSelecionado, "id", getPackageName());
            CardView selectedCard = findViewById(cardId);
            selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.selected_color));
        }
    }

    private void resetAllSelections() {
        // Resetar cards de candidatos
        for (int i = 1; i <= 5; i++) {
            int cardId = getResources().getIdentifier("cardCandidato" + i, "id", getPackageName());
            CardView card = findViewById(cardId);
            card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    private void clearCandidateSelection() {
        candidatoSelecionado = mapRadioToCandidateId(grupoOpcoes.getCheckedRadioButtonId());
        resetAllSelections();
    }

    private void loadCandidateImage(ImageView imageView, int candidateId) {
        // Substitua por sua lógica de carregamento de imagens
        // Exemplo com imagens fictícias:
        int[] candidateImages = {
                R.mipmap.gato_maconha,
                R.mipmap.wolverinw_rasta
                // Adicione mais imagens
        };

        if (candidateId <= candidateImages.length) {
            imageView.setImageResource(candidateImages[candidateId - 1]);
        }
    }

    private void saveStimulatedVote() {
        new Thread(() -> {
            String candidatoNome = "";

            switch (candidatoSelecionado) {
                case 1: candidatoNome = "Gato Maconha";
                case 2: candidatoNome = "Logan Rasta";
                case 6: candidatoNome = "Branco";
                case 7: candidatoNome = "Nulo";
                case 8: candidatoNome = "Não sei";
            }

            EstimuladaModel vote = new EstimuladaModel();
            vote.est_id = candidatoSelecionado;
            vote.est_voto = candidatoNome;

            db.pesquisaDAO().insertEstimulada(vote);
        }).start();
    }
}