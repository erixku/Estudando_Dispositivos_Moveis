package com.example.pequitro;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.media.MediaPlayer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.animation.AccelerateDecelerateInterpolator;

import DAO.UsuarioDAO;
import Models.UsuarioModel;

public class MainActivity extends AppCompatActivity {

    private ImageView ivMetro;
    private int larguraInicial;
    private int alturaInicial;
    private int larguraFinal;
    private int alturaFinal;
    private final long tempoAnimacao = 5000;
    private final float fatorDeAumento = 50.0f;
    private float deslocamentoInicialX;
    private MediaPlayer mp;

    private final UsuarioModel admin = new UsuarioModel();
    private final UsuarioModel entrev = new UsuarioModel();
    private final UsuarioDAO usuDao = new UsuarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        admin.setEmail("admin@metro.sp.gov.br");
        admin.setSenha("Administração283");
        admin.setRole("Administrador");

        entrev.setEmail("entrev@metro.sp.gov.br");
        entrev.setSenha("Entrevistador283");
        entrev.setRole("Entrevistador");

        usuDao.inserirUsuario(admin);
        usuDao.inserirUsuario(entrev);
        usuDao.close();

        ivMetro = findViewById(R.id.ivMetro);

        ivMetro.post(new Runnable() {
            @Override
            public void run() {
                larguraInicial = ivMetro.getWidth();
                alturaInicial = ivMetro.getHeight();
                android.util.Log.d("MainActivity", "Largura Inicial: " + larguraInicial + ", Altura Inicial: " + alturaInicial);
                larguraFinal = (int) (larguraInicial * fatorDeAumento);
                alturaFinal = (int) (alturaInicial * fatorDeAumento);

                ivMetro.setTranslationX(deslocamentoInicialX);

                iniciarAnimacaoDeChegada();
            }
        });

        mp = MediaPlayer.create(this, R.raw.metro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mp != null) {
                    mp.start(); //toca o som
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            Intent login = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(login);
                            finish();
                        }
                    });
                } else {
                    Intent login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        }, tempoAnimacao + 500);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void iniciarAnimacaoDeChegada() {
        AnimatorSet animatorSet = new AnimatorSet();

        // Calcula a posição inicial X um pouco à esquerda do centro
        float posicaoInicialX = -1000 * 0.5f; // Começa metade da largura à esquerda do centro
        ivMetro.setTranslationX(posicaoInicialX); // Define a posição inicial

        float distanciaTotalX = -posicaoInicialX;

        // Cria um ValueAnimator para animar a largura
        ValueAnimator larguraAnimator = ValueAnimator.ofInt(larguraInicial, larguraFinal);
        larguraAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int larguraAnimada = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivMetro.getLayoutParams();
                layoutParams.width = larguraAnimada;
                ivMetro.setLayoutParams(layoutParams);

                float larguraPercorrida = (larguraAnimada - larguraInicial);
                float proporcao = (float) larguraPercorrida/(larguraFinal - larguraInicial);
                ivMetro.setTranslationX(posicaoInicialX + distanciaTotalX * proporcao);
            }
        });
        larguraAnimator.setDuration(tempoAnimacao);
        larguraAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Cria um ValueAnimator para animar a altura
        ValueAnimator alturaAnimator = ValueAnimator.ofInt(alturaInicial, alturaFinal);
        alturaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alturaAnimada = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivMetro.getLayoutParams();
                layoutParams.height = alturaAnimada;
                ivMetro.setLayoutParams(layoutParams);
            }
        });
        alturaAnimator.setDuration(tempoAnimacao);
        alturaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());


        // Inicia as animações simultaneamente
        animatorSet.playTogether(larguraAnimator, alturaAnimator);
        animatorSet.start();
    }
}