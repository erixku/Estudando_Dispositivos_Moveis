package com.example.pequitro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import DAO.PercursoDAO;
import Models.PercursoModel;

public class PesquisaActivity extends AppCompatActivity {

    Button btA_Tucuruvi;
    Button btA_ParadaInglesa;
    Button btA_JardimSaoPaulo;
    Button btV_PalmeirasBarraFunda;
    Button btV_MarechalDeodoro;
    Button btV_SantaCecilia;
    Button btA_Santana;
    Button btA_Carandiru;
    Button btA_PortuguesaTiete;
    Button btV_Republica;
    Button btV_Anhangabau;
    Button btV_Se;
    Button btA_Armenia;
    Button btA_Tiradentes;
    Button btA_Luz;
    Button btV_PedroII;
    Button btV_Bras;
    Button btV_BresserMooca;
    Button btA_SaoBento;
    Button btA_Se;
    Button btA_Liberdade;
    Button btV_Belem;
    Button btV_Tatuape;
    Button btV_CarraoAssai;
    Button btA_SaoJoaquim;
    Button btA_Vergueiro;
    Button btA_Paraiso;
    Button btV_PenhaLojasBesni;
    Button btV_VilaMatilde;
    Button btV_GuilherminaEsperanca;
    Button btA_AnaRosa;
    Button btA_VilaMariana;
    Button btA_SantaCruz;
    Button btV_PatriarcaVilaRe;
    Button btV_ArturAlvin;
    Button btV_CorinthiansItaquera;
    Button btA_PracaArvore;
    Button btA_Saude;
    Button btA_SaoJudas;
    Button btA_Conceicao;
    Button btA_Jabaquara;

    private int contador = 0;
    private final PercursoModel percurso = new PercursoModel();
    private PercursoDAO percursoDao = new PercursoDAO(this);
    private String origem;
    private String destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesquisa);

        btA_Tucuruvi = findViewById(R.id.btA_Tucuruvi);
        btA_ParadaInglesa = findViewById(R.id.btA_ParadaInglesa);
        btA_JardimSaoPaulo = findViewById(R.id.btA_JardimSaoPaulo);
        btV_PalmeirasBarraFunda = findViewById(R.id.btV_PalmeirasBarraFunda);
        btV_MarechalDeodoro = findViewById(R.id.btV_MarechalDeodoro);
        btV_SantaCecilia = findViewById(R.id.btV_SantaCecilia);
        btA_Santana = findViewById(R.id.btA_Santana);
        btA_Carandiru = findViewById(R.id.btA_Carandiru);
        btA_PortuguesaTiete = findViewById(R.id.btA_PortuguesaTiete);
        btV_Republica = findViewById(R.id.btV_Republica);
        btV_Anhangabau = findViewById(R.id.btV_Anhangabau);
        btV_Se = findViewById(R.id.btV_Se);
        btA_Armenia = findViewById(R.id.btA_Armênia);
        btA_Tiradentes = findViewById(R.id.btA_Tiradentes);
        btA_Luz = findViewById(R.id.btA_Luz);
        btV_PedroII = findViewById(R.id.btV_PedroII);
        btV_Bras = findViewById(R.id.btV_Bras);
        btV_BresserMooca = findViewById(R.id.btV_BresserMooca);
        btA_SaoBento = findViewById(R.id.btA_SaoBento);
        btA_Se = findViewById(R.id.btA_Se);
        btA_Liberdade = findViewById(R.id.btA_Liberdade);
        btV_Belem = findViewById(R.id.btV_Belem);
        btV_Tatuape = findViewById(R.id.btV_Tatuape);
        btV_CarraoAssai = findViewById(R.id.btV_CarraoAssai);
        btA_SaoJoaquim = findViewById(R.id.btA_SaoJoaquim);
        btA_Vergueiro = findViewById(R.id.btA_Vergueiro);
        btA_Paraiso = findViewById(R.id.btA_Paraiso);
        btV_PenhaLojasBesni = findViewById(R.id.btV_PenhaLojasBesni);
        btV_VilaMatilde = findViewById(R.id.btV_VilaMatilde);
        btV_GuilherminaEsperanca = findViewById(R.id.btV_GuilherminaEsperanca);
        btA_AnaRosa = findViewById(R.id.btA_AnaRosa);
        btA_VilaMariana = findViewById(R.id.btA_VilaMariana);
        btA_SantaCruz = findViewById(R.id.btA_SantaCruz);
        btV_PatriarcaVilaRe = findViewById(R.id.btV_PatriarcaVilaRe);
        btV_ArturAlvin = findViewById(R.id.btV_ArturAlvin);
        btV_CorinthiansItaquera = findViewById(R.id.btV_CorinthiansItaquera);
        btA_PracaArvore = findViewById(R.id.btA_PracaArvore);
        btA_Saude = findViewById(R.id.btA_Saude);
        btA_SaoJudas = findViewById(R.id.btA_SaoJudas);
        btA_Conceicao = findViewById(R.id.btA_Conceicao);
        btA_Jabaquara = findViewById(R.id.btA_Jabaquara);

        btA_Tucuruvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Tucuruvi.getText().toString());
            }
        });

        btA_ParadaInglesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_ParadaInglesa.getText().toString());
            }
        });

        btA_JardimSaoPaulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_JardimSaoPaulo.getText().toString());
            }
        });

        btV_PalmeirasBarraFunda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_PalmeirasBarraFunda.getText().toString());
            }
        });

        btV_MarechalDeodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_MarechalDeodoro.getText().toString());
            }
        });

        btV_SantaCecilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_SantaCecilia.getText().toString());
            }
        });

        btA_Santana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Santana.getText().toString());
            }
        });

        btA_Carandiru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Carandiru.getText().toString());
            }
        });

        btA_PortuguesaTiete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_PortuguesaTiete.getText().toString());
            }
        });

        btV_Republica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Republica.getText().toString());
            }
        });

        btV_Anhangabau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Anhangabau.getText().toString());
            }
        });

        btV_Se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Se.getText().toString());
            }
        });

        btA_Armenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Armenia.getText().toString());
            }
        });

        btA_Tiradentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Tiradentes.getText().toString());
            }
        });

        btA_Luz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Luz.getText().toString());
            }
        });

        btV_PedroII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_PedroII.getText().toString());
            }
        });

        btV_Bras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Bras.getText().toString());
            }
        });

        btV_BresserMooca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_BresserMooca.getText().toString());
            }
        });

        btA_SaoBento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_SaoBento.getText().toString());
            }
        });

        btA_Se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Se.getText().toString());
            }
        });

        btA_Liberdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Liberdade.getText().toString());
            }
        });

        btV_Belem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Belem.getText().toString());
            }
        });

        btV_Tatuape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_Tatuape.getText().toString());
            }
        });

        btV_CarraoAssai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_CarraoAssai.getText().toString());
            }
        });

        btA_SaoJoaquim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_SaoJoaquim.getText().toString());
            }
        });

        btA_Vergueiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Vergueiro.getText().toString());
            }
        });

        btA_Paraiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Paraiso.getText().toString());
            }
        });

        btV_PenhaLojasBesni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_PenhaLojasBesni.getText().toString());
            }
        });

        btV_VilaMatilde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_VilaMatilde.getText().toString());
            }
        });

        btV_GuilherminaEsperanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_GuilherminaEsperanca.getText().toString());
            }
        });

        btA_AnaRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_AnaRosa.getText().toString());
            }
        });

        btA_VilaMariana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_VilaMariana.getText().toString());
            }
        });

        btA_SantaCruz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_SantaCruz.getText().toString());
            }
        });

        btV_PatriarcaVilaRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_PatriarcaVilaRe.getText().toString());
            }
        });

        btV_ArturAlvin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_ArturAlvin.getText().toString());
            }
        });

        btV_CorinthiansItaquera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("v", btV_CorinthiansItaquera.getText().toString());
            }
        });

        btA_PracaArvore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_PracaArvore.getText().toString());
            }
        });

        btA_Saude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Saude.getText().toString());
            }
        });

        btA_SaoJudas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_SaoJudas.getText().toString());
            }
        });

        btA_Conceicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Conceicao.getText().toString());
            }
        });

        btA_Jabaquara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipularESalvar("a", btA_Jabaquara.getText().toString());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void manipularESalvar (String linha, String textoBotao) {
        String nome;
        if (linha.equals("a")) {
            nome = "a-" + textoBotao;
            Toast.makeText(this, nome, Toast.LENGTH_SHORT).show();
        } else {
            nome = "v-" + textoBotao;
            Toast.makeText(this, nome, Toast.LENGTH_SHORT).show();
        }

        if (contador == 0) {
            origem = nome;
            contador++;
            // Opcional: Fornecer feedback visual para a origem
        } else if (contador == 1) {
            destino = nome;
            if (origem.equals(destino)) {
                Toast.makeText(this, "Origem e destino não podem ser iguais", Toast.LENGTH_SHORT).show();
                contador--;
            } else {
                percurso.setOrigem(origem);
                percurso.setDestino(destino);
                Toast.makeText(this, destino + " - " + origem, Toast.LENGTH_SHORT).show();
                try {
                    percursoDao.inserirPercurso(percurso);
                    Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Erro ao salvar o percurso", Toast.LENGTH_SHORT).show();
                }
                Intent cadastro = new Intent(PesquisaActivity.this, CadastroActivity.class);
                startActivity(cadastro);
                finish();
            }
        }
    }
}