package DAO;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues; import android.content.Context;
import android.database.Cursor;

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
                COLUNA_DESTINO + " text not null)"
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

    public void apagarPercurso(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] param = {String.valueOf(id)};
        db.delete(TABELA_PERCURSO, "id = ?", param);
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

}
