package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView tvQuestao;
    private RadioGroup rgOpcao;
    private Button btProximo;

    private String[] questions = {
            "1. Qual linguagem é usada no Android Studio?",
            "2. Qual banco de dados é nativo do Android?",
            "3. Qual comando SQL é usado para recuperar dados?",
            "4. O que significa IDE?",
            "5. Qual estrutura de repetição em Java?",
            "6. O que é uma chave primária em um banco de dados?",
            "7. Qual método cria a interface do usuário no Android?",
            "8. O que é um Fragment no Android?",
            "9. Como se chama um banco de dados não relacional?",
            "10. O que significa API?"
    };

    private String[][] options = {
            {"Java", "Python", "C++", "PHP"},
            {"SQLite", "PostgreSQL", "MongoDB", "MySQL"},
            {"SELECT", "INSERT", "DELETE", "UPDATE"},
            {"Interface de Desenvolvimento", "Ambiente de Execução", "Sistema Operacional", "Nenhuma das anteriores"},
            {"for", "if", "switch", "return"},
            {"Identificador único", "Tabela de dados", "Código fonte", "Interface gráfica"},
            {"onCreate()", "main()", "init()", "setup()"},
            {"Parte do layout", "Um botão", "Uma imagem", "Nenhuma das anteriores"},
            {"SQL", "MongoDB", "JSON", "Firebase"},
            {"Interface de Programação", "Código de Execução", "Aplicação Web", "Protocolo de Internet"}
    };

    private int[] correctAnswers = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}; // Índices das respostas corretas

    private int currentQuestion = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvQuestao = findViewById(R.id.tvQuestao);
        rgOpcao = findViewById(R.id.rgOpcao);
        btProximo = findViewById(R.id.btProximo);

        loadQuestion();

        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            tvQuestao.setText(questions[currentQuestion]);
            rgOpcao.removeAllViews();

            for (int i = 0; i < options[currentQuestion].length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options[currentQuestion][i]);
                radioButton.setId(i);
                rgOpcao.addView(radioButton);
            }
        } else {
            Toast.makeText(this, "Quiz finalizado! Você acertou " + score + " de " + questions.length, Toast.LENGTH_LONG).show();
            btProximo.setEnabled(false);
        }
    }

    private void checkAnswer() {
        int selectedId = rgOpcao.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Escolha uma resposta!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedId == correctAnswers[currentQuestion]) {
            score++;
        }

        currentQuestion++;
        loadQuestion();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}