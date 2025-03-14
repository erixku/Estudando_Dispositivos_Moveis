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
            "1. Qual das seguintes estruturas de dados é baseada no princípio (o ultimo a entrar, primeiro a sair - LIFO)?",
            "2. Qual é o nome do conceito que define a reutilização de código em Programação Orientada a Objetos?",
            "3. Qual termo descreve um erro inesperado durante a execução de um programa??",
            "4. Qual é o nome do tipo de dado que só pode armazenar valores verdadeiros ou falsos",
            "5. Que estrutura de repetição verifica a condição antes de executar o bloco de código?",
            "6. Qual conceito define a ocultação de detalhes internos de um objeto e a exposição apenas do necessário?",
            "7. Qual nome é dado a um algoritmo que divide um problema em partes menores e resolve cada uma separadamente?",
            "8. Qual estrutura de dados é organizada em forma de classificação com nós e arestas?",
            "9. Qual é o nome do processo de otimização que melhora a eficiência do código removendo redundâncias?",
            "10. Que tipo de erro ocorre quando o programa tenta acessar uma posição de memória inválida?"
    };

    private String[][] options = {
            {"Fila", "Lista", "Pilha", "Árvore"},
            {"Polimorfismo", "Herança", "Abstração", "Recursão"},
            {"Exceção", "Overflow", "Buffer", "Recursão"},
            {"Inteiro", "Booleano", "String", "Float"},
            {"For", "While", "Do-while", "Switch"},
            {"Encapsulação", "Abstração", "Herança", "Composição"},
            {"Iteração", "Recursão", "Polimorfismo", "Compilação"},
            {"Grafo", "Árvore", "Pilha", "Fila"},
            {"Depuração", "Compilação", "Refatoração", "Análise"},
            {"Overflow", "Segmentação", "Underflow", "Exceção"}
    };

    private int[] correctAnswers = {2, 1, 0, 1, 1, 0, 1, 1, 2, 1};

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}