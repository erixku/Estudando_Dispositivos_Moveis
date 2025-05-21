package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Models.ServidorCentralModel;

public class ServidorCentralDAO {
    private OrigemDestinoDAO dbHelper; // Referência ao seu helper centralizado
    private SQLiteDatabase database; // Referência ao objeto SQLiteDatabase

    public static final String TABELA_SERVIDOR_CENTRAL = "tbServidorCentral";
    public static final String COLUNA_SER_ID = "ser_id";
    public static final String COLUNA_PER_ORIGEM = "per_origem";
    public static final String COLUNA_PER_DESTINO = "per_destino";
    public static final String COLUNA_PER_DATA = "per_data";
    public static final String COLUNA_PER_HORA = "per_hora";
    public static final String COLUNA_ETR_NOME = "etr_nome";
    public static final String COLUNA_ETR_TELEFONE = "etr_telefone";

    public ServidorCentralDAO(Context context) {
        // O construtor agora recebe o Context e inicializa o helper centralizado
        this.dbHelper = new OrigemDestinoDAO(context);
        Log.d("ServidorCentralDAO", "Construtor chamado.");
    }

    // Métodos para abrir e fechar a conexão com o banco de dados
    public void open() {
        database = dbHelper.getWritableDatabase(); // Obtém o banco de dados para escrita
        Log.d("ServidorCentralDAO", "Database opened.");
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("ServidorCentralDAO", "Database closed.");
        }
    }

    public long inserirServidorCentral(ServidorCentralModel servidor) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_PER_ORIGEM, servidor.getPer_origem());
        values.put(COLUNA_PER_DESTINO, servidor.getPer_destino());
        values.put(COLUNA_PER_DATA, servidor.getPer_data());
        values.put(COLUNA_PER_HORA, servidor.getPer_hora());
        values.put(COLUNA_ETR_NOME, servidor.getEtr_nome());
        values.put(COLUNA_ETR_TELEFONE, servidor.getEtr_telefone());

        try {
            open(); // Abre a conexão
            result = database.insert(TABELA_SERVIDOR_CENTRAL, null, values);
            if (result == -1) {
                Log.e("ServidorCentralDAO", "Falha ao inserir ServidorCentral.");
            } else {
                Log.d("ServidorCentralDAO", "ServidorCentral inserido com sucesso. ID: " + result);
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao inserir ServidorCentral: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long atualizarServidorCentral(ServidorCentralModel servidor) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_PER_ORIGEM, servidor.getPer_origem());
        values.put(COLUNA_PER_DESTINO, servidor.getPer_destino());
        values.put(COLUNA_PER_DATA, servidor.getPer_data());
        values.put(COLUNA_PER_HORA, servidor.getPer_hora());
        values.put(COLUNA_ETR_NOME, servidor.getEtr_nome());
        values.put(COLUNA_ETR_TELEFONE, servidor.getEtr_telefone());

        String[] param = {String.valueOf(servidor.getSer_id())};
        try {
            open(); // Abre a conexão
            result = database.update(TABELA_SERVIDOR_CENTRAL, values, COLUNA_SER_ID + " = ?", param);
            if (result == 0) { // update retorna 0 se nenhuma linha for afetada
                Log.w("ServidorCentralDAO", "Nenhum ServidorCentral atualizado para o ID: " + servidor.getSer_id());
            } else {
                Log.d("ServidorCentralDAO", "ServidorCentral atualizado com sucesso. Linhas afetadas: " + result);
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao atualizar ServidorCentral: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarServidorCentral(int id) {
        long result = -1;
        String[] param = {String.valueOf(id)};
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_SERVIDOR_CENTRAL, COLUNA_SER_ID + " = ?", param);
            if (result == 0) { // delete retorna 0 se nenhuma linha for afetada
                Log.w("ServidorCentralDAO", "Nenhum ServidorCentral encontrado para apagar com ID: " + id);
            } else {
                Log.d("ServidorCentralDAO", "ServidorCentral com ID: " + id + " apagado com sucesso. Linhas apagadas: " + result);
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao apagar ServidorCentral: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public ServidorCentralModel consultarServidorCentral(int id) {
        ServidorCentralModel servidor = null;
        Cursor cursor = null; // Inicialize o cursor como null
        // Removi COLUNA_PER_DATA e COLUNA_PER_HORA dos campos, pois não estavam sendo atribuídos no modelo
        String[] campos = {COLUNA_SER_ID, COLUNA_PER_ORIGEM, COLUNA_PER_DESTINO, COLUNA_PER_DATA, COLUNA_PER_HORA, COLUNA_ETR_NOME, COLUNA_ETR_TELEFONE};
        String[] param = {String.valueOf(id)};

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_SERVIDOR_CENTRAL, campos, COLUNA_SER_ID + " = ?", param, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                servidor = new ServidorCentralModel();
                servidor.setSer_id(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_SER_ID)));
                servidor.setPer_origem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_ORIGEM)));
                servidor.setPer_destino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DESTINO)));
                servidor.setPer_data(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DATA)));
                servidor.setPer_hora(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_HORA)));
                servidor.setEtr_nome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_NOME)));
                servidor.setEtr_telefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_TELEFONE)));
                Log.d("ServidorCentralDAO", "ServidorCentral encontrado para ID: " + id);
            } else {
                Log.w("ServidorCentralDAO", "Nenhum ServidorCentral encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao consultar ServidorCentral: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return servidor;
    }

    public ServidorCentralModel consultarUltimoServidorCentral() {
        ServidorCentralModel servidor = null;
        Cursor cursor = null; // Inicialize o cursor como null
        // Removi COLUNA_PER_DATA e COLUNA_PER_HORA dos campos, pois não estavam sendo atribuídos no modelo
        String[] campos = {COLUNA_SER_ID, COLUNA_PER_ORIGEM, COLUNA_PER_DESTINO, COLUNA_PER_DATA, COLUNA_PER_HORA, COLUNA_ETR_NOME, COLUNA_ETR_TELEFONE};

        try {
            open(); // Abre a conexão
            // Consulta o último registro ordenando por ID de forma descendente e limitando a 1
            cursor = database.query(TABELA_SERVIDOR_CENTRAL, campos, null, null, null, null, COLUNA_SER_ID + " DESC", "1");

            if (cursor != null && cursor.moveToFirst()) {
                servidor = new ServidorCentralModel();
                servidor.setSer_id(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_SER_ID)));
                servidor.setPer_origem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_ORIGEM)));
                servidor.setPer_destino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DESTINO)));
                servidor.setPer_data(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DATA)));
                servidor.setPer_hora(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_HORA)));
                servidor.setEtr_nome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_NOME)));
                servidor.setEtr_telefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_TELEFONE)));
                Log.d("ServidorCentralDAO", "Último ServidorCentral consultado. ID: " + servidor.getSer_id());
            } else {
                Log.w("ServidorCentralDAO", "Nenhum ServidorCentral encontrado na tabela.");
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao consultar o último ServidorCentral: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return servidor;
    }

    // Se necessário, adicione um método para consultar todos os registros
    public java.util.List<ServidorCentralModel> consultarTodosServidoresCentrais() {
        java.util.List<ServidorCentralModel> servidores = new java.util.ArrayList<>();
        Cursor cursor = null;

        try {
            open();
            cursor = database.query(TABELA_SERVIDOR_CENTRAL, null, null, null, null, null, COLUNA_SER_ID + " ASC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ServidorCentralModel servidor = new ServidorCentralModel();
                    servidor.setSer_id(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_SER_ID)));
                    servidor.setPer_origem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_ORIGEM)));
                    servidor.setPer_destino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DESTINO)));
                    servidor.setPer_data(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_DATA)));
                    servidor.setPer_hora(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_PER_HORA)));
                    servidor.setEtr_nome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_NOME)));
                    servidor.setEtr_telefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ETR_TELEFONE)));
                    servidores.add(servidor);
                } while (cursor.moveToNext());
                Log.d("ServidorCentralDAO", "Todos os Servidores Centrais consultados. Total: " + servidores.size());
            } else {
                Log.d("ServidorCentralDAO", "Nenhum ServidorCentral encontrado.");
            }
        } catch (Exception e) {
            Log.e("ServidorCentralDAO", "Erro ao consultar todos os Servidores Centrais: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return servidores;
    }
}
