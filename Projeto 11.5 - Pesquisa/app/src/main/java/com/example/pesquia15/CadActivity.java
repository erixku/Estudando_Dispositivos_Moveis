package com.example.pesquia15;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;
import java.time. LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import helpers.LocationHelper;
import helpers.PrefsHelper;
import models.EntrevistadoModel;


public class CadActivity extends AppCompatActivity {

    EditText edNome, edTelefone;
    Button btNova, btVoltar;
    private PrefsHelper prefsHelper;
    private LocationHelper locationHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cad);

        prefsHelper = new PrefsHelper(CadActivity.this);
        locationHelper = new LocationHelper(CadActivity.this);

        edNome = findViewById(R.id.edNome);
        edTelefone = findViewById(R.id.edTelefone);
        btNova = findViewById(R.id.btNova);
        btVoltar = findViewById(R.id.btVoltar);

        btNova.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
            @Override
            public void onClick(View v) {
                salvar(edNome.getText().toString(), edTelefone.getText().toString());
                Intent nova = new Intent(CadActivity.this, EspActivity.class);
                startActivity(nova);
                finish();
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
            @Override
            public void onClick(View v) {
                salvar(edNome.getText().toString(), edTelefone.getText().toString());
                Intent voltar = new Intent(CadActivity.this, LoginActivity.class);
                startActivity(voltar);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void salvar(String nome, String telefone) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDate data = agora.toLocalDate();
        LocalTime hora = agora.toLocalTime();
        locationHelper.obterLocalizacao(localizacao -> {
            EntrevistadoModel entre = new EntrevistadoModel(nome, telefone, data, hora, localizacao.getLatitude(), localizacao.getLongitude());
            prefsHelper.saveObject("ultimo_cad", entre);
        });
    }
}