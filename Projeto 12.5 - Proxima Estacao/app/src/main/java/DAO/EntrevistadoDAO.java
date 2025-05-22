package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Models.EntrevistadoModel;

public class EntrevistadoDAO {

    private OrigemDestinoDAO dbHelper; // Referência ao seu helper centralizado
    private SQLiteDatabase database; // Referência ao objeto SQLiteDatabase

    public static final String TABELA_ENTREVISTADO = "tbEntrevistado";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_TELEFONE = "telefone";

    public EntrevistadoDAO(Context context) {
        // O construtor agora recebe o Context e inicializa o helper centralizado
        this.dbHelper = new OrigemDestinoDAO(context);
        Log.d("EntrevistadoDAO", "Construtor chamado.");
    }

    // Métodos para abrir e fechar a conexão com o banco de dados
    public void open() {
        database = dbHelper.getWritableDatabase(); // Obtém o banco de dados para escrita
        Log.d("EntrevistadoDAO", "Database opened.");
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("EntrevistadoDAO", "Database closed.");
        }
    }

    public long inserirEntrevistado(EntrevistadoModel entrevistado) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, entrevistado.getNome());
        values.put(COLUNA_TELEFONE, entrevistado.getTelefone());

        try {
            open(); // Abre a conexão
            result = database.insert(TABELA_ENTREVISTADO, null, values);
            if (result == -1) {
                Log.e("EntrevistadoDAO", "Falha ao inserir entrevistado: " + entrevistado.getNome());
            } else {
                Log.d("EntrevistadoDAO", "Entrevistado inserido com sucesso. ID: " + result);
            }
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao inserir entrevistado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long atualizarEntrevistado(EntrevistadoModel entrevistado) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, entrevistado.getNome());
        values.put(COLUNA_TELEFONE, entrevistado.getTelefone());

        String[] param = {String.valueOf(entrevistado.getId())};
        try {
            open(); // Abre a conexão
            result = database.update(TABELA_ENTREVISTADO, values, COLUNA_ID + " = ?", param);
            if (result == 0) { // update retorna 0 se nenhuma linha for afetada
                Log.w("EntrevistadoDAO", "Nenhum entrevistado atualizado para o ID: " + entrevistado.getId());
            } else {
                Log.d("EntrevistadoDAO", "Entrevistado atualizado com sucesso. Linhas afetadas: " + result);
            }
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao atualizar entrevistado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarEntrevistado(int id) {
        long result = -1;
        String[] param = {String.valueOf(id)};
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_ENTREVISTADO, COLUNA_ID + " = ?", param);
            if (result == 0) { // delete retorna 0 se nenhuma linha for afetada
                Log.w("EntrevistadoDAO", "Nenhum entrevistado encontrado para apagar com ID: " + id);
            } else {
                Log.d("EntrevistadoDAO", "Entrevistado com ID: " + id + " apagado com sucesso. Linhas apagadas: " + result);
            }
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao apagar entrevistado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarTodosEntrevistados() {
        long result = -1;
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_ENTREVISTADO, null, null);
            Log.d("EntrevistadoDAO", "Todos os entrevistados apagados. Linhas apagadas: " + result);
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao apagar todos os entrevistados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public EntrevistadoModel consultarEntrevistado(int id) {
        EntrevistadoModel entrevistado = null;
        Cursor cursor = null; // Inicialize o cursor como null
        String[] campos = {COLUNA_ID, COLUNA_NOME, COLUNA_TELEFONE};
        String[] param = {String.valueOf(id)};

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_ENTREVISTADO, campos, COLUNA_ID + " = ?", param, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                entrevistado = new EntrevistadoModel();
                // Use getColumnIndexOrThrow para maior robustez
                entrevistado.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                entrevistado.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_NOME)));
                entrevistado.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TELEFONE)));
                Log.d("EntrevistadoDAO", "Entrevistado encontrado: " + entrevistado.getNome());
            } else {
                Log.w("EntrevistadoDAO", "Nenhum entrevistado encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao consultar entrevistado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return entrevistado;
    }

    public List<EntrevistadoModel> consultarTodosEntrevistados() {
        List<EntrevistadoModel> entrevistados = new ArrayList<>();
        Cursor cursor = null; // Inicialize o cursor como null

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_ENTREVISTADO, null, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    EntrevistadoModel entrevistado = new EntrevistadoModel();
                    entrevistado.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                    entrevistado.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_NOME)));
                    entrevistado.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TELEFONE)));
                    entrevistados.add(entrevistado);
                } while(cursor.moveToNext());
                Log.d("EntrevistadoDAO", "Todos os entrevistados consultados. Total: " + entrevistados.size());
            } else {
                Log.d("EntrevistadoDAO", "Nenhum entrevistado encontrado.");
            }
        } catch (Exception e) {
            Log.e("EntrevistadoDAO", "Erro ao consultar todos os entrevistados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return entrevistados;
    }

}
