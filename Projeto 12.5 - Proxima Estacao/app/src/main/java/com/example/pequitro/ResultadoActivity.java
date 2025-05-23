package com.example.pequitro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.pie.PieRenderer;

import DAO.LocalizacaoDAO;
import Helpers.DestinoListAdapter;
import Helpers.EntrevListAdapter;
import Helpers.OrigemListAdapter;
import Helpers.PercursoListAdapter;
import DAO.EntrevistadoDAO;
import DAO.PercursoDAO;
import DAO.ServidorCentralDAO;
import Models.EntrevistadoModel;
import Models.PercursoModel;
import Models.ServidorCentralModel;

public class ResultadoActivity extends AppCompatActivity {

    Button btLimparDados, btVoltarLogin2, btTodos, btOrigem, btDestino;;
    Spinner spAzul, spVermelho;
    EntrevistadoDAO entrevistadoDAO = new EntrevistadoDAO(this);
    PercursoDAO percursoDAO = new PercursoDAO(this);
    LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(this);
    ServidorCentralDAO servidorCentralDAO = new ServidorCentralDAO(this);
    List<EntrevistadoModel> entrevistados = entrevistadoDAO.consultarTodosEntrevistados();
    List<PercursoModel> percursos = percursoDAO.consultarTodosPercursos();
    private PieChart ptPorEstacao, ptComTotal;
    ListView ltPercursos, ltEntrevistados;
    PercursoListAdapter percursoAdapter;
    private enum ListaTipo { TODOS, ORIGENS, DESTINOS }
    private ListaTipo currentListaTipo = ListaTipo.TODOS;

    EntrevListAdapter entrevAdapter;
    private final Map<String, Integer> lineColors = new HashMap<String, Integer>() {{
        put("a-", Color.BLUE);    // Linha Azul
        put("v-", Color.RED);     // Linha Vermelha
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);


        ptPorEstacao = findViewById(R.id.ptPorEstacao);
        ptComTotal = findViewById(R.id.ptComTotal);

        confiGrafico();

        ltPercursos = findViewById(R.id.ltPercurso);
        ltEntrevistados = findViewById(R.id.ltEntrevistados);

        btLimparDados = findViewById(R.id.btLimparDados);
        btVoltarLogin2 = findViewById(R.id.btVoltarLogin2);
        btTodos = findViewById(R.id.btTodos);
        btOrigem = findViewById(R.id.btOrigem);
        btDestino = findViewById(R.id.btDestino);



        carregarPercursos();
        carregarEntrevistados();

        btLimparDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entrevistados.size() == percursos.size()) {
                    for (int i = 0; i < entrevistados.size(); i++) {
                        EntrevistadoModel entrevistado = entrevistados.get(i);
                        PercursoModel percurso = percursos.get(i);

                        ServidorCentralModel servidorCentral = new ServidorCentralModel();
                        servidorCentral.setEtr_nome(entrevistado.getNome());
                        servidorCentral.setEtr_telefone(entrevistado.getTelefone());
                        servidorCentral.setPer_origem(percurso.getOrigem());
                        servidorCentral.setPer_destino(percurso.getDestino());
                        servidorCentral.setPer_data(percurso.getData());
                        servidorCentral.setPer_hora(percurso.getHora());

                        servidorCentralDAO.inserirServidorCentral(servidorCentral);
                    }
                }
                entrevistadoDAO.apagarTodosEntrevistados();
                percursoDAO.apagarTodosPercurso();
                carregarPercursos();
                carregarEntrevistados();
            }
        });

        btVoltarLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ResultadoActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
        btTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarPercursos();
            }
        });
        btOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarOrigem();
            }
        });
        btDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarDestino();
            }
        });

        ltPercursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PercursoModel percursoClicado = (PercursoModel) parent.getItemAtPosition(position);

                if (percursoClicado != null) {
                    atualizarGraficos(percursoClicado);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void confiGrafico() {
        if (ptPorEstacao.getBackground() == null) {
            ptPorEstacao.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        ptPorEstacao.getBackground().setAlpha(0);
        ptPorEstacao.clear();
        ptPorEstacao.setTitle("Selecione um Percurso ou Estação");
        PieRenderer pr1 = ptPorEstacao.getRenderer(PieRenderer.class);
        if (pr1 != null) { // SAFELY CHECK if renderer is available
            pr1.setDonutSize(0.3f, PieRenderer.DonutMode.PERCENT);
        } else {
            Log.e("ResultadoActivity", "PieRenderer for ptPorEstacao is null.");
        }
        ptPorEstacao.redraw();

        // --- Configure ptComTotal ---
        if (ptComTotal.getBackground() == null) {
            ptComTotal.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        ptComTotal.getBackground().setAlpha(0);
        ptComTotal.clear();
        ptComTotal.setTitle("Selecione um Percurso ou Estação");
        PieRenderer pr2 = ptComTotal.getRenderer(PieRenderer.class); // Get the renderer for ptComTotal
        if (pr2 != null) { // SAFELY CHECK if renderer is available
            pr2.setDonutSize(0.3f, PieRenderer.DonutMode.PERCENT); // <<<<<< CORRECTED LINE!
        } else {
            Log.e("ResultadoActivity", "PieRenderer for ptComTotal is null.");
        }
        ptComTotal.redraw();
    }

    private void atualizarGraficos(PercursoModel percursoClicado) {
        // 1. Obter o total geral de percursos do banco de dados
        int totalGeralPercursos = percursoDAO.getContagemTotalPercursos(); // <--- PRECISA DESTE NOVO MÉTODO NO DAO

        // 2. Limpar os gráficos para começar do zero
        ptPorEstacao.clear();
        ptComTotal.clear();

        // Gerador de cores aleatórias para "Outros" segmentos
        Random rnd = new Random();

        // --- GRÁFICO 1: Percentual do Item Clicado em Relação ao Total Geral ---
        ptPorEstacao.setTitle("Percentual do Item Clicado");

        if (totalGeralPercursos > 0) {
            float percentualClicado = (float) percursoClicado.getContagem() / totalGeralPercursos * 100;
            float percentualOutros = 100 - percentualClicado;

            // Segmento do item clicado (cor principal)
            SegmentFormatter sfClicado = new SegmentFormatter(getCorLinha("Linha Azul")); // Use uma cor distintiva ou a cor da linha do percurso clicado
            sfClicado.getLabelPaint().setTextSize(20f);
            ptPorEstacao.addSegment(
                    new Segment(getLabelForClickedItem(percursoClicado) + " (" + String.format("%.1f%%", percentualClicado) + ")", percursoClicado.getContagem()),
                    sfClicado
            );

            // Segmento para "Outros" (o restante)
            if (percentualOutros > 0) {
                SegmentFormatter sfOutros = new SegmentFormatter(Color.LTGRAY); // Cor neutra para "Outros"
                sfOutros.getLabelPaint().setTextSize(20f);
                ptPorEstacao.addSegment(
                        new Segment("Outros (" + String.format("%.1f%%", percentualOutros) + ")", totalGeralPercursos - percursoClicado.getContagem()),
                        sfOutros
                );
            }
        } else {
            ptPorEstacao.addSegment(new Segment("Sem Dados", 1), new SegmentFormatter(Color.GRAY));
        }
        ptPorEstacao.redraw();

        // --- GRÁFICO 2: Percentual do Item Clicado em Relação aos Itens Similares ---
        ptComTotal.setTitle("Comparativo com Similares");

        List<PercursoModel> listaDeContexto = null;
        String labelItemClicado = "";

        switch (currentListaTipo) {
            case TODOS:
                // Se a lista é de "Todos", os "similares" são os outros percursos completos.
                listaDeContexto = percursoDAO.consultarContagemPercurso();
                labelItemClicado = percursoClicado.getOrigem() + " | " + percursoClicado.getDestino();
                break;
            case ORIGENS:
                // Se a lista é de "Origens", os "similares" são as outras origens.
                listaDeContexto = percursoDAO.consultarContagemOrigem();
                labelItemClicado = percursoClicado.getOrigem();
                break;
            case DESTINOS:
                // Se a lista é de "Destinos", os "similares" são os outros destinos.
                listaDeContexto = percursoDAO.consultarContagemDestino();
                labelItemClicado = percursoClicado.getDestino();
                break;
        }

        if (listaDeContexto != null && !listaDeContexto.isEmpty()) {
            int totalNoContexto = 0;
            for (PercursoModel p : listaDeContexto) {
                totalNoContexto += p.getContagem();
            }

            if (totalNoContexto > 0) {
                // Adicionar o segmento do item clicado
                float percentualNoContexto = (float) percursoClicado.getContagem() / totalNoContexto * 100;
                SegmentFormatter sfCtxClicado = new SegmentFormatter(getCorLinha("Linha Vermelha")); // Outra cor distintiva
                sfCtxClicado.getLabelPaint().setTextSize(20f);
                ptComTotal.addSegment(
                        new Segment(labelItemClicado + " (" + String.format("%.1f%%", percentualNoContexto) + ")", percursoClicado.getContagem()),
                        sfCtxClicado
                );

                // Adicionar os segmentos para os outros itens no mesmo contexto
                for (PercursoModel p : listaDeContexto) {
                    // Evita duplicar o segmento do item clicado, e se a contagem for 0, não adiciona
                    if (!getLabelForContextItem(p, currentListaTipo).equals(labelItemClicado) && p.getContagem() > 0) {
                        float percentualOutro = (float) p.getContagem() / totalNoContexto * 100;
                        SegmentFormatter sfCtxOutro = new SegmentFormatter(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))); // Cor aleatória
                        sfCtxOutro.getLabelPaint().setTextSize(20f);
                        ptComTotal.addSegment(
                                new Segment(getLabelForContextItem(p, currentListaTipo) + " (" + String.format("%.1f%%", percentualOutro) + ")", p.getContagem()),
                                sfCtxOutro
                        );
                    }
                }
            } else {
                ptComTotal.addSegment(new Segment("Sem Dados no Contexto", 1), new SegmentFormatter(Color.GRAY));
            }
        } else {
            ptComTotal.addSegment(new Segment("Nenhum Similar", 1), new SegmentFormatter(Color.GRAY));
        }
        ptComTotal.redraw();
    }

    private String getLabelForClickedItem(PercursoModel percurso) {
        switch (currentListaTipo) {
            case TODOS: return percurso.getOrigem() + " -> " + percurso.getDestino();
            case ORIGENS: return percurso.getOrigem();
            case DESTINOS: return percurso.getDestino();
            default: return "Item";
        }
    }

    // Método auxiliar para obter o label dos itens no contexto para o GRÁFICO 2
    private String getLabelForContextItem(PercursoModel percurso, ListaTipo tipo) {
        switch (tipo) {
            case TODOS: return percurso.getOrigem() + " -> " + percurso.getDestino();
            case ORIGENS: return percurso.getOrigem();
            case DESTINOS: return percurso.getDestino();
            default: return "Item";
        }
    }

    private int getCorLinha(String linha) {
        switch (linha) {
            case "Linha Azul": return Color.BLUE;
            case "Linha Vermelha": return Color.RED;
            default: return Color.GRAY; // Cor padrão para outras/desconhecidas
        }
    }

    private void carregarPercursos() {
        List<PercursoModel> percursos = percursoDAO.consultarContagemPercurso();

        if (percursos != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            percursoAdapter = new PercursoListAdapter(
                    this,
                    R.layout.list_item_percurso, // Seu layout personalizado para cada item
                    percursos
            );
            ltPercursos.setAdapter(percursoAdapter);

        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
        currentListaTipo = ListaTipo.TODOS;
        confiGrafico();
    }

    private void carregarOrigem() {
        List<PercursoModel> percursos = percursoDAO.consultarContagemOrigem();

        if (percursos != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            OrigemListAdapter origemAdapter = new OrigemListAdapter(
                    this,
                    R.layout.list_item_origem, // Seu layout personalizado para cada item
                    percursos
            );
            ltPercursos.setAdapter(origemAdapter);

        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
        currentListaTipo = ListaTipo.ORIGENS;
        confiGrafico();
    }
    private void carregarDestino() {
        List<PercursoModel> percursos = percursoDAO.consultarContagemDestino();

        if (percursos != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            DestinoListAdapter destinoAdapter = new DestinoListAdapter(
                    this,
                    R.layout.list_item_destino, // Seu layout personalizado para cada item
                    percursos
            );
            ltPercursos.setAdapter(destinoAdapter);

        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
        currentListaTipo = ListaTipo.DESTINOS;
        confiGrafico();
    }

    private void carregarEntrevistados() {
        List<EntrevistadoModel> entrevistados = entrevistadoDAO.consultarTodosEntrevistados();

        if (entrevistados != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            if (entrevAdapter == null) {
                entrevAdapter = new EntrevListAdapter(
                        this,
                        R.layout.list_item_entrev, // Seu layout personalizado para cada item
                        entrevistados
                );
                ltEntrevistados.setAdapter(entrevAdapter);
            } else {
                // Se o adapter já existe, apenas limpe e adicione os novos dados
                entrevAdapter.clear();
                entrevAdapter.addAll(entrevistados);
                entrevAdapter.notifyDataSetChanged(); // Notifica o ListView para redesenhar
            }
        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
    }
}