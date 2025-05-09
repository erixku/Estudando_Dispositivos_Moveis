package com.example.origemedestino;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // Tempo de exibição da splash em milissegundos
    private ImageView trainImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        trainImageView = findViewById(R.id.trainImageView);

        // Inicia a animação do trem
        startTrainAnimation();

        // Usa um Handler para redirecionar para a próxima activity após o tempo da splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cria um Intent para iniciar a próxima activity (Exemplo: NextActivity.class)
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finaliza a MainActivity para que o usuário não possa voltar para ela
            }
        }, SPLASH_DURATION);
    }

    private void startTrainAnimation() {
        // Cria uma animação de translation (movimento) do trem
        TranslateAnimation animation = new TranslateAnimation(
                -1000f,  // Posição inicial X (fora da tela à esquerda)
                getWindowManager().getDefaultDisplay().getWidth() + 1000f,  // Posição final X (fora da tela à direita)
                0f,  // Posição inicial Y
                0f); // Posição final Y
        animation.setDuration(3000); // Duração da animação em milissegundos
        animation.setFillAfter(true); // Mantém o trem na posição final após a animação terminar

        // Listener para tratar o fim da animação.  Pode ser usado para trocar de tela.
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Opcional: Código para executar quando a animação começa
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Opcional: Código para executar quando a animação termina
                // Neste caso, a troca de tela agora está no Handler, então aqui pode ficar vazio ou para outra lógica.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Opcional: Código para executar quando a animação se repete (se você definir repetição)
            }
        });

        // Inicia a animação na ImageView do trem
        trainImageView.startAnimation(animation);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}