package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Helpers.PercursoInnerLocalizacao;
import Models.LocalizacaoModel;
import Models.PercursoModel;

import android.util.Log; // Adicionado para logs de depuração


public class PercursoDAO { // <<<< NÃO ESTENDE MAIS SQLiteOpenHelper

    private OrigemDestinoDAO dbHelper; // Referência ao seu helper centralizado
    private SQLiteDatabase database; // Referência ao objeto SQLiteDatabase

    public static final String TABELA_PERCURSO = "tbPercurso";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ORIGEM = "origem"; // Armazenará o NOME da Localização
    public static final String COLUNA_DESTINO = "destino"; // Armazenará o NOME da Localização

    public PercursoDAO(Context context) {
        // O construtor agora recebe o Context e inicializa o helper centralizado
        this.dbHelper = new OrigemDestinoDAO(context);
        Log.d("PercursoDAO", "Construtor chamado.");
    }

    // Métodos para abrir e fechar a conexão com o banco de dados
    public void open() {
        database = dbHelper.getWritableDatabase(); // Obtém o banco de dados para escrita
        Log.d("PercursoDAO", "Database opened.");
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("PercursoDAO", "Database closed.");
        }
    }

    public long inserirPercurso(PercursoModel percursoModel) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_ORIGEM, percursoModel.getOrigem());
        values.put(COLUNA_DESTINO, percursoModel.getDestino());

        try {
            open(); // Abre a conexão
            result = database.insert(TABELA_PERCURSO, null, values);
            if (result == -1) {
                Log.e("PercursoDAO", "Falha ao inserir percurso: " + percursoModel.getOrigem() + " -> " + percursoModel.getDestino());
            } else {
                Log.d("PercursoDAO", "Percurso inserido com sucesso. ID: " + result);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao inserir percurso: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long atualizarPercurso(PercursoModel percursoModel) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put(COLUNA_ORIGEM, percursoModel.getOrigem());
        values.put(COLUNA_DESTINO, percursoModel.getDestino());

        String[] param = {String.valueOf(percursoModel.getId())};
        try {
            open(); // Abre a conexão
            result = database.update(TABELA_PERCURSO, values, COLUNA_ID + " = ?", param);
            if (result == 0) { // update retorna 0 se nenhuma linha for afetada
                Log.w("PercursoDAO", "Nenhum percurso atualizado para o ID: " + percursoModel.getId());
            } else {
                Log.d("PercursoDAO", "Percurso atualizado com sucesso. Linhas afetadas: " + result);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao atualizar percurso: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarPercurso(int id) { // Adicionando método para apagar um único percurso
        long result = -1;
        String[] param = {String.valueOf(id)};
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_PERCURSO, COLUNA_ID + " = ?", param);
            if (result == 0) { // delete retorna 0 se nenhuma linha for afetada
                Log.w("PercursoDAO", "Nenhum percurso encontrado para apagar com ID: " + id);
            } else {
                Log.d("PercursoDAO", "Percurso com ID: " + id + " apagado com sucesso. Linhas apagadas: " + result);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao apagar percurso: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public long apagarTodosPercurso() {
        long result = -1;
        try {
            open(); // Abre a conexão
            result = database.delete(TABELA_PERCURSO, null, null);
            Log.d("PercursoDAO", "Todos os percursos apagados. Linhas apagadas: " + result);
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao apagar todos os percursos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(); // Garante que a conexão seja fechada
        }
        return result;
    }

    public PercursoModel consultarPercurso(int id) {
        PercursoModel percursoModel = null;
        Cursor cursor = null; // Inicialize o cursor como null
        String[] campos = {COLUNA_ID, COLUNA_ORIGEM, COLUNA_DESTINO};
        String[] param = {String.valueOf(id)};

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_PERCURSO, campos, COLUNA_ID + " = ?", param, null, null, null);

            if (cursor != null && cursor.moveToFirst()) { // Verifica se o cursor não é nulo e se move para a primeira linha
                percursoModel = new PercursoModel();
                percursoModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                percursoModel.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ORIGEM)));
                percursoModel.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESTINO)));
                Log.d("PercursoDAO", "Percurso encontrado: ID " + percursoModel.getId());
            } else {
                Log.w("PercursoDAO", "Nenhum percurso encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar percurso: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return percursoModel;
    }

    public List<PercursoModel> consultarTodosPercursos() {
        List<PercursoModel> percursos = new ArrayList<>();
        Cursor cursor = null; // Inicialize o cursor como null

        try {
            open(); // Abre a conexão
            cursor = database.query(TABELA_PERCURSO, null, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PercursoModel percurso = new PercursoModel();
                    percurso.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                    percurso.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ORIGEM)));
                    percurso.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESTINO)));
                    percursos.add(percurso);
                } while (cursor.moveToNext());
                Log.d("PercursoDAO", "Todos os percursos consultados. Total: " + percursos.size());
            } else {
                Log.d("PercursoDAO", "Nenhum percurso encontrado.");
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar todos os percursos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            close(); // Garante que a conexão seja fechada
        }
        return percursos;
    }

    public PercursoInnerLocalizacao consultarPercursoInnerLocalizacao(int id) {
        PercursoInnerLocalizacao resultado = null;
        Cursor cursor = null;

        // Note que estamos usando as constantes das DAOs para nomes de tabelas/colunas
        // Certifique-se que LocalizacaoDAO.COLUNA_NOME está importado corretamente e acessível
        String query = "SELECT " +
                "p." + COLUNA_ID + " AS percurso_id, " +
                "p." + COLUNA_ORIGEM + " AS percurso_origem_nome, " + // Renomeado para evitar conflito
                "p." + COLUNA_DESTINO + " AS percurso_destino_nome, " + // Renomeado para evitar conflito
                "lo." + LocalizacaoDAO.COLUNA_ID + " AS origem_id, " +
                "lo." + LocalizacaoDAO.COLUNA_NOME + " AS origem_nome, " + // Adicionado!
                "lo." + LocalizacaoDAO.COLUNA_LATITUDE + " AS origem_latitude, " +
                "lo." + LocalizacaoDAO.COLUNA_LONGITUDE + " AS origem_longitude, " +
                "ld." + LocalizacaoDAO.COLUNA_ID + " AS destino_id, " +
                "ld." + LocalizacaoDAO.COLUNA_NOME + " AS destino_nome, " + // Adicionado!
                "ld." + LocalizacaoDAO.COLUNA_LATITUDE + " AS destino_latitude, " +
                "ld." + LocalizacaoDAO.COLUNA_LONGITUDE + " AS destino_longitude " +
                "FROM " + TABELA_PERCURSO + " p " +
                "INNER JOIN " + LocalizacaoDAO.TABELA_LOCALIZACAO + " lo ON p." + COLUNA_ORIGEM + " = lo." + LocalizacaoDAO.COLUNA_NOME + " " +
                "INNER JOIN " + LocalizacaoDAO.TABELA_LOCALIZACAO + " ld ON p." + COLUNA_DESTINO + " = ld." + LocalizacaoDAO.COLUNA_NOME + " " +
                "WHERE p." + COLUNA_ID + " = ?";

        String[] param = {String.valueOf(id)};

        try {
            open(); // Abre a conexão
            cursor = database.rawQuery(query, param);
            if (cursor != null && cursor.moveToFirst()) {
                PercursoModel percurso = new PercursoModel();
                percurso.setId(cursor.getInt(cursor.getColumnIndexOrThrow("percurso_id")));
                // Atribua os nomes de origem/destino do percurso a partir dos campos renomeados
                percurso.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow("percurso_origem_nome")));
                percurso.setDestino(cursor.getString(cursor.getColumnIndexOrThrow("percurso_destino_nome")));

                LocalizacaoModel origem = new LocalizacaoModel();
                origem.setId(cursor.getInt(cursor.getColumnIndexOrThrow("origem_id")));
                origem.setNome(cursor.getString(cursor.getColumnIndexOrThrow("origem_nome"))); // Agora obtém o nome
                origem.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow("origem_latitude")));
                origem.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow("origem_longitude")));

                LocalizacaoModel destino = new LocalizacaoModel();
                destino.setId(cursor.getInt(cursor.getColumnIndexOrThrow("destino_id")));
                destino.setNome(cursor.getString(cursor.getColumnIndexOrThrow("destino_nome"))); // Agora obtém o nome
                destino.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow("destino_latitude")));
                destino.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow("destino_longitude")));

                resultado = new PercursoInnerLocalizacao(percurso, origem, destino);
                Log.d("PercursoDAO", "PercursoInnerLocalizacao consultado para ID: " + id);
            } else {
                Log.w("PercursoDAO", "Nenhum PercursoInnerLocalizacao encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar PercursoInnerLocalizacao: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return resultado;
    }

    public int consultarContagemPercurso() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO;

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem total de percursos: " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de percurso: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemOrigem() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO;

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de origens: " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de origem: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemOrigemAzul() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " LIKE 'a-%'";

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de origens 'a-%': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de origem 'a-%': " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemOrigemEspecificoAzul(String nome) {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " = ?";
        String[] param = {nome};

        try {
            open();
            cursor = database.rawQuery(query, param);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de origem '" + nome + "': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de origem específica: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemOrigemVermelhos() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " LIKE 'v-%'";

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de origens 'v-%': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de origem 'v-%': " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemOrigemEspecificoVermelho(String nome) {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " = ?";
        String[] param = {nome};

        try {
            open();
            cursor = database.rawQuery(query, param);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de origem '" + nome + "': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de origem específica: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemDestino() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO;

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de destinos: " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de destino: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemDestinoAzul() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " LIKE 'a-%'";

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de destinos 'a-%': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de destino 'a-%': " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemDestinoEspecificoAzul(String nome) {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " = ?";
        String[] param = {nome};

        try {
            open();
            cursor = database.rawQuery(query, param);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de destino '" + nome + "': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de destino específica: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemDestinoVermelhos() {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " LIKE 'v-%'";

        try {
            open();
            cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de destinos 'v-%': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de destino 'v-%': " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }

    public int consultarContagemDestinoEspecificoVermelho(String nome) {
        int contagem = 0;
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " = ?";
        String[] param = {nome};

        try {
            open();
            cursor = database.rawQuery(query, param);
            if (cursor != null && cursor.moveToFirst()) {
                contagem = cursor.getInt(0);
                Log.d("PercursoDAO", "Contagem de destino '" + nome + "': " + contagem);
            }
        } catch (Exception e) {
            Log.e("PercursoDAO", "Erro ao consultar contagem de destino específica: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            close();
        }
        return contagem;
    }
}
