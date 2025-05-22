package com.example.pequitro;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import DAO.EntrevistadoDAO;
import Models.EntrevistadoModel;

public class CadastroActivity extends AppCompatActivity {

    EditText edNome, edTelefone;
    Button btNovaPesq, btVoltarLogin;
    EntrevistadoDAO dao = new EntrevistadoDAO(this);
    EntrevistadoModel entrev = new EntrevistadoModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        edNome = findViewById(R.id.edNome);
        edTelefone = findViewById(R.id.edTelefone);
        btNovaPesq = findViewById(R.id.btNovaPesq);
        btVoltarLogin = findViewById(R.id.btVoltarLogin);

        btNovaPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edNome.getText().toString().isEmpty() || edTelefone.getText().toString().isEmpty()) {
                    entrev.setNome("Anônimo");
                    entrev.setTelefone("Não informado");

                    try{
                        dao.inserirEntrevistado(entrev);
                    } catch (SQLiteException e) {
                        Toast.makeText(CadastroActivity.this, "Erro ao inserir Entrevistado", Toast.LENGTH_SHORT).show();
                    }
                    finally {
                        dao.close();
                    }
                } else {
                    entrev.setNome(edNome.getText().toString());
                    entrev.setTelefone(formatarTelefone(edTelefone.getText().toString()));
                    dao.inserirEntrevistado(entrev);
                    dao.close();
                }
                Intent pesq = new Intent(CadastroActivity.this, PesquisaActivity.class);
                startActivity(pesq);
                finish();
            }
        });

        btVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edNome.getText().toString().isEmpty() || edTelefone.getText().toString().isEmpty()) {
                    entrev.setNome("Anônimo");
                    entrev.setTelefone("Não informado");

                    try{
                        dao.inserirEntrevistado(entrev);
                    } catch (SQLiteException e) {
                        Toast.makeText(CadastroActivity.this, "Erro ao inserir Entrevistado", Toast.LENGTH_SHORT).show();
                    }
                    finally {
                        dao.close();
                    }
                } else {
                    entrev.setNome(edNome.getText().toString());
                    entrev.setTelefone(formatarTelefone(edTelefone.getText().toString()));
                    dao.inserirEntrevistado(entrev);
                    dao.close();
                }
                Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public String formatarTelefone(String telefone) {
        String ddd = telefone.substring(0, 2);
        String primParte = telefone.substring(2, 7);
        String segParte = telefone.substring(7, 11);
        String telefoneFormatado = "(" + ddd + ") " + primParte + "-" + segParte;
        Toast.makeText(this, telefoneFormatado, Toast.LENGTH_SHORT).show();
        return telefoneFormatado;
    }
}