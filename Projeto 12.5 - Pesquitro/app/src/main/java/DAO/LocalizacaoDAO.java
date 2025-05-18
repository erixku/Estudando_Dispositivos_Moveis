package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Models.LocalizacaoModel;
public class LocalizacaoDAO extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "bdOrigemDestino"; // Mantendo o mesmo nome de banco
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_LOCALIZACAO = "tbLocalizacao";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LATITUDE = "latitude";
    public static final String COLUNA_LONGITUDE = "longitude";

    public LocalizacaoDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABELA_LOCALIZACAO + "(" +
                COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME + " text not null, " +
                COLUNA_LATITUDE + " real not null, " +
                COLUNA_LONGITUDE + " real not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void inserirLocalizacao(LocalizacaoModel localizacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, localizacao.getNome());
        values.put(COLUNA_LATITUDE, localizacao.getLatitude());
        values.put(COLUNA_LONGITUDE, localizacao.getLongitude());

        db.insert(TABELA_LOCALIZACAO, null, values);
        db.close();
    }

    public void atualizarLocalizacao(LocalizacaoModel localizacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, localizacao.getNome());
        values.put(COLUNA_LATITUDE, localizacao.getLatitude());
        values.put(COLUNA_LONGITUDE, localizacao.getLongitude());

        String[] param = {String.valueOf(localizacao.getId())};
        db.update(TABELA_LOCALIZACAO, values, "id = ?", param);
        db.close();
    }

    public void apagarLocalizacao(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] param = {String.valueOf(id)};
        db.delete(TABELA_LOCALIZACAO, "id = ?", param);
        db.close();
    }

    public LocalizacaoModel consultarLocalizacao(int id) {
        LocalizacaoModel localizacao = null;
        String[] campos = {COLUNA_ID, COLUNA_NOME, COLUNA_LATITUDE, COLUNA_LONGITUDE};
        String[] param = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_LOCALIZACAO, campos, "id = ?", param, null, null, COLUNA_NOME);

        if (cursor != null && cursor.moveToFirst()) {
            localizacao = new LocalizacaoModel();
            localizacao.setId(cursor.getInt(0));
            localizacao.setNome(cursor.getString(1));
            localizacao.setLatitude(cursor.getFloat(2));
            localizacao.setLongitude(cursor.getFloat(3));
            cursor.close();
        }
        db.close();
        return localizacao;
    }

}
