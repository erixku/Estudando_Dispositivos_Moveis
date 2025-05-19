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

public class PercursoDAO extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "bdOrigemDestino"; // Mantendo o mesmo nome de banco
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_PERCURSO = "tbPercurso";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ORIGEM = "origem";
    public static final String COLUNA_DESTINO = "destino";

    public PercursoDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABELA_PERCURSO + "(" +
                COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_ORIGEM + " text not null, " +
                COLUNA_DESTINO + " text not null, " +
                "FOREIGN KEY("+ COLUNA_ORIGEM +") REFERENCES " + LocalizacaoDAO.TABELA_LOCALIZACAO + "(" + LocalizacaoDAO.COLUNA_NOME + "), " +
                "FOREIGN KEY("+ COLUNA_DESTINO +") REFERENCES " + LocalizacaoDAO.TABELA_LOCALIZACAO + "(" + LocalizacaoDAO.COLUNA_NOME + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void inserirPercurso(PercursoModel percursoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_ORIGEM, percursoModel.getOrigem());
        values.put(COLUNA_DESTINO, percursoModel.getDestino());

        db.insert(TABELA_PERCURSO, null, values);
        db.close();
    }

    public void atualizarPercurso(PercursoModel percursoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_ORIGEM, percursoModel.getOrigem());
        values.put(COLUNA_DESTINO, percursoModel.getDestino());

        String[] param = {String.valueOf(percursoModel.getId())};
        db.update(TABELA_PERCURSO, values, "id = ?", param);
        db.close();
    }

    public void apagarTodosPercurso() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_PERCURSO,null, null);
        db.close();
    }

    public PercursoModel consultarPercurso(int id) {
        PercursoModel percursoModel = null;
        String[] campos = {COLUNA_ID, COLUNA_ORIGEM, COLUNA_DESTINO};
        String[] param = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_PERCURSO, campos, "id = ?", param, null, null, null);

        if (cursor.moveToFirst()) {
            percursoModel = new PercursoModel();
            percursoModel.setId(cursor.getInt(0));
            percursoModel.setOrigem(cursor.getString(1));
            percursoModel.setDestino(cursor.getString(2));
            cursor.close();
        }
        db.close();
        return percursoModel;
    }

    public List<PercursoModel> consultarTodosPercursos() {
        List<PercursoModel> percursos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_PERCURSO, null, null, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            do {
                PercursoModel percurso = new PercursoModel();
                percurso.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUNA_ID)));
                percurso.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_ORIGEM)));
                percurso.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESTINO)));
                percursos.add(percurso);
            }while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return percursos;
    }

    public PercursoInnerLocalizacao consultarPercursoInnerLocalizacao(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        PercursoInnerLocalizacao resultado = null;
        Cursor cursor = null;

        String query = "SELECT " +
                "p." + COLUNA_ID + " AS percurso_id, " +
                "p." + COLUNA_ORIGEM + " AS percurso_origem, " +
                "p." + COLUNA_DESTINO + " AS percurso_destino, " +
                "lo." + LocalizacaoDAO.COLUNA_ID + " AS origem_id, " +
                "lo." + LocalizacaoDAO.COLUNA_LATITUDE + " AS origem_latitude, " +
                "lo." + LocalizacaoDAO.COLUNA_LONGITUDE + " AS origem_longitude, " +
                "ld." + LocalizacaoDAO.COLUNA_ID + " AS destino_id, " +
                "ld." + LocalizacaoDAO.COLUNA_LATITUDE + " AS destino_latitude, " +
                "ld." + LocalizacaoDAO.COLUNA_LONGITUDE + " AS destino_longitude " +
                "FROM " + TABELA_PERCURSO + " p " +
                "INNER JOIN " + LocalizacaoDAO.TABELA_LOCALIZACAO + " lo ON p." + COLUNA_ORIGEM + " = lo." + LocalizacaoDAO.COLUNA_NOME + " " +
                "INNER JOIN " + LocalizacaoDAO.TABELA_LOCALIZACAO + " ld ON p." + COLUNA_DESTINO + " = ld." + LocalizacaoDAO.COLUNA_NOME + " " +
                "WHERE p." + COLUNA_ID + " = ?";

        String[] param = {String.valueOf(id)};

        cursor = db.rawQuery(query, param);
        if (cursor != null && cursor.moveToFirst()) {
            PercursoModel percurso = new PercursoModel();
            percurso.setId(cursor.getInt(cursor.getColumnIndexOrThrow("percurso_id")));
            percurso.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow("percurso_origem")));
            percurso.setDestino(cursor.getString(cursor.getColumnIndexOrThrow("percurso_destino")));

            LocalizacaoModel origem = new LocalizacaoModel();
            origem.setId(cursor.getInt(cursor.getColumnIndexOrThrow("origem_id")));
            origem.setNome(cursor.getString(cursor.getColumnIndexOrThrow("origem_nome")));
            origem.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow("origem_latitude")));
            origem.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow("origem_longitude")));

            LocalizacaoModel destino = new LocalizacaoModel();
            destino.setId(cursor.getInt(cursor.getColumnIndexOrThrow("destino_id")));
            destino.setNome(cursor.getString(cursor.getColumnIndexOrThrow("destino_nome")));
            destino.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow("destino_latitude")));
            destino.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow("destino_longitude")));

            resultado = new PercursoInnerLocalizacao(percurso, origem, destino);
            cursor.close();
        }
        db.close();
        return resultado;
    }

    public int consultarContagemPercurso() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO;

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemOrigem() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO;

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemOrigemAzul() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " LIKE 'a-%'";

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemOrigemEspecificoAzul(String nome) {
        int contagem = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " = ?";
        String[] param = {nome};

        cursor = db.rawQuery(query, param);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemOrigemVermelhos() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_ORIGEM + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " LIKE 'v-%'";

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemOrigemEspecificoVermelho(String nome) {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_ORIGEM + " = ?";
        String[] param = {nome};

        cursor = db.rawQuery(query, param);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemDestino() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO;

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemDestinoAzul() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " LIKE 'a-%'";

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemDestinoEspecificoAzul(String nome) {
        int contagem = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " = ?";
        String[] param = {nome};

        cursor = db.rawQuery(query, param);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemDestinoVermelhos() {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(" + COLUNA_DESTINO + ") FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " LIKE 'v-%'";

        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }

    public int consultarContagemDestinoEspecificoVermelho(String nome) {
        int contagem = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT COUNT(*) FROM " + TABELA_PERCURSO + " WHERE " + COLUNA_DESTINO + " = ?";
        String[] param = {nome};

        cursor = db.rawQuery(query, param);
        if (cursor != null && cursor.moveToFirst()) {
            contagem = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return contagem;
    }
}
