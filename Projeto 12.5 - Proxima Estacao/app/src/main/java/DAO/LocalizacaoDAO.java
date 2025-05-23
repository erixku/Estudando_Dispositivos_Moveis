package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Models.LocalizacaoModel;
public class LocalizacaoDAO {
    private OrigemDestinoDAO dbHelper; // Referência ao seu helper centralizado
    private SQLiteDatabase database; // Referência ao objeto SQLiteDatabase

    public static final String TABELA_LOCALIZACAO = "tbLocalizacao";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LATITUDE = "latitude";
    public static final String COLUNA_LONGITUDE = "longitude";

    // O construtor agora recebe o Context e inicializa o helper centralizado
    public LocalizacaoDAO(Context context) {
        this.dbHelper = new OrigemDestinoDAO(context);
        Log.d("LocalizacaoDAO", "Construtor chamado.");
    }

    // Métodos para abrir e fechar a conexão com o banco de dados
    public void open() {
        database = dbHelper.getWritableDatabase(); // Obtém o banco de dados para escrita
        Log.d("LocalizacaoDAO", "Database opened.");
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("LocalizacaoDAO", "Database closed.");
        }
    }

    public long inserirLocalizacao(LocalizacaoModel localizacao) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, localizacao.getNome());
        values.put(COLUNA_LATITUDE, localizacao.getLatitude());
        values.put(COLUNA_LONGITUDE, localizacao.getLongitude());

        try {
            open(); // Abre a conexão
            result = database.insert(TABELA_LOCALIZACAO, null, values);
            if (result == -1) {
                Log.e("LocalizacaoDAO", "Falha ao inserir localização: " + localizacao.getNome());
            } else {
                Log.d("LocalizacaoDAO", "Localização inserida com sucesso. ID: " + result);
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao inserir localização: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long atualizarLocalizacao(LocalizacaoModel localizacao) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, localizacao.getNome());
        values.put(COLUNA_LATITUDE, localizacao.getLatitude());
        values.put(COLUNA_LONGITUDE, localizacao.getLongitude());

        String[] param = {String.valueOf(localizacao.getId())};
        try {
            open(); // Abre a conexão
            result = database.update(TABELA_LOCALIZACAO, values, COLUNA_ID + " = ?", param);
            if (result == 0) { // update retorna 0 se nenhuma linha for afetada
                Log.w("LocalizacaoDAO", "Nenhuma localização atualizada para o ID: " + localizacao.getId());
            } else {
                Log.d("LocalizacaoDAO", "Localização atualizada com sucesso. Linhas afetadas: " + result);
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao atualizar localização: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarLocalizacao(int id) {
        long result = -1;
        String[] param = {String.valueOf(id)};
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_LOCALIZACAO, COLUNA_ID + " = ?", param);
            if (result == 0) { // delete retorna 0 se nenhuma linha for afetada
                Log.w("LocalizacaoDAO", "Nenhuma localização encontrada para apagar com ID: " + id);
            } else {
                Log.d("LocalizacaoDAO", "Localização com ID: " + id + " apagada com sucesso. Linhas apagadas: " + result);
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao apagar localização: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public LocalizacaoModel consultarLocalizacao(int id) {
        LocalizacaoModel localizacao = null;
        Cursor cursor = null; // Inicialize o cursor como null
        String[] campos = {COLUNA_ID, COLUNA_NOME, COLUNA_LATITUDE, COLUNA_LONGITUDE};
        String[] param = {String.valueOf(id)};

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_LOCALIZACAO, campos, COLUNA_ID + " = ?", param, null, null, COLUNA_NOME);

            if (cursor != null && cursor.moveToFirst()) {
                localizacao = new LocalizacaoModel();
                // Use getColumnIndexOrThrow para maior robustez
                localizacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                localizacao.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_NOME)));
                localizacao.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LATITUDE)));
                localizacao.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LONGITUDE)));
                Log.d("LocalizacaoDAO", "Localização encontrada: " + localizacao.getNome());
            } else {
                Log.w("LocalizacaoDAO", "Nenhuma localização encontrada para o ID: " + id);
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao consultar localização: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return localizacao;
    }


    // Você pode adicionar um método para consultar todas as localizações se necessário
    public java.util.List<LocalizacaoModel> consultarTodasLocalizacoes() {
        java.util.List<LocalizacaoModel> localizacoes = new java.util.ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            open();
            cursor = database.query(TABELA_LOCALIZACAO, null, null, null, null, null, COLUNA_NOME);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    LocalizacaoModel localizacao = new LocalizacaoModel();
                    localizacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                    localizacao.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_NOME)));
                    localizacao.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LATITUDE)));
                    localizacao.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LONGITUDE)));
                    localizacoes.add(localizacao);
                } while(cursor.moveToNext());
            } else {
                Log.d("LocalizacaoDAO", "Nenhuma localização encontrada.");
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao consultar todas as localizações: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return localizacoes;
    }

    public java.util.List<LocalizacaoModel> consultarTodasLocalizacoesAzul(String prefixo) {
        java.util.List<LocalizacaoModel> localizacoes = new java.util.ArrayList<>();
        Cursor cursor = null;
        try {
            open();

            String[] cols = {COLUNA_ID, COLUNA_NOME, COLUNA_LATITUDE, COLUNA_LONGITUDE};
            String selection = COLUNA_NOME + " LIKE ?"; // Condição para filtrar por nome
            String[] selectionArgs = new String[]{prefixo + "%"}; // Argumento: 'prefixo%' para "começa com"
            String orderBy = COLUNA_NOME + " ASC";

            cursor = database.query(TABELA_LOCALIZACAO, cols, selection, selectionArgs, null, null, orderBy);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    LocalizacaoModel localizacao = new LocalizacaoModel();
                    localizacao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                    localizacao.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_NOME)));
                    localizacao.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LATITUDE)));
                    localizacao.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUNA_LONGITUDE)));
                    localizacoes.add(localizacao);
                } while(cursor.moveToNext());
            } else {
                Log.d("LocalizacaoDAO", "Nenhuma localização encontrada.");
            }
        } catch (Exception e) {
            Log.e("LocalizacaoDAO", "Erro ao consultar todas as localizações: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return localizacoes;
    }

    public String toString() {
        return COLUNA_NOME;
    }

    public String getLinhaMetroByNome(String nomeEstacao) {
        if (nomeEstacao == null || nomeEstacao.isEmpty()) {
            return "Desconhecida";
        }
        if (nomeEstacao.startsWith("a-")) {
            return "Linha Azul";
        } else if (nomeEstacao.startsWith("v-")) {
            return "Linha Vermelha";
        }
        // Adicione mais verificações para outras linhas se existirem (verde, amarela, etc.)
        return "Outra Linha"; // Ou "Desconhecida" se não corresponder a nenhuma
    }



}
