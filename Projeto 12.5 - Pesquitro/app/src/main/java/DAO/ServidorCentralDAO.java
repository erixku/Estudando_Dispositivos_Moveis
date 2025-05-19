package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Models.ServidorCentralModel;

public class ServidorCentralDAO extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "bdOrigemDestino"; // Mantendo o mesmo nome de banco
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_SERVIDOR_CENTRAL = "tbServidorCentral";
    public static final String COLUNA_SER_ID = "ser_id";
    public static final String COLUNA_PER_ORIGEM = "per_origem";
    public static final String COLUNA_PER_DESTINO = "per_destino";
    public static final String COLUNA_ETR_NOME = "etr_nome";
    public static final String COLUNA_ETR_TELEFONE = "etr_telefone";

    public ServidorCentralDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABELA_SERVIDOR_CENTRAL + "(" +
                COLUNA_SER_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_PER_ORIGEM + " text not null, " +
                COLUNA_PER_DESTINO + " text not null, " +
                COLUNA_ETR_NOME + " text not null, " +
                COLUNA_ETR_TELEFONE + " text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void inserirServidorCentral(ServidorCentralModel servidor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PER_ORIGEM, servidor.getPer_origem());
        values.put(COLUNA_PER_DESTINO, servidor.getPer_destino());
        values.put(COLUNA_ETR_NOME, servidor.getEtr_nome());
        values.put(COLUNA_ETR_TELEFONE, servidor.getEtr_telefone());

        db.insert(TABELA_SERVIDOR_CENTRAL, null, values);
        db.close();
    }

    public void atualizarServidorCentral(ServidorCentralModel servidor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PER_ORIGEM, servidor.getPer_origem());
        values.put(COLUNA_PER_DESTINO, servidor.getPer_destino());
        values.put(COLUNA_ETR_NOME, servidor.getEtr_nome());
        values.put(COLUNA_ETR_TELEFONE, servidor.getEtr_telefone());

        String[] param = {String.valueOf(servidor.getSer_id())};
        db.update(TABELA_SERVIDOR_CENTRAL, values, "ser_id = ?", param);
        db.close();
    }

    public void apagarServidorCentral(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] param = {String.valueOf(id)};
        db.delete(TABELA_SERVIDOR_CENTRAL, "ser_id = ?", param);
        db.close();
    }

    public ServidorCentralModel consultarServidorCentral(int id) {
        ServidorCentralModel servidor = null;
        String[] campos = {COLUNA_SER_ID, COLUNA_PER_ORIGEM, COLUNA_PER_DESTINO, COLUNA_ETR_NOME, COLUNA_ETR_TELEFONE};
        String[] param = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_SERVIDOR_CENTRAL, campos, "ser_id = ?", param, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            servidor = new ServidorCentralModel();
            servidor.setSer_id(cursor.getInt(0));
            servidor.setPer_origem(cursor.getString(1));
            servidor.setPer_destino(cursor.getString(2));
            servidor.setEtr_nome(cursor.getString(3));
            servidor.setEtr_telefone(cursor.getString(4));
            cursor.close();
        }
        db.close();
        return servidor;
    }

    public ServidorCentralModel consultarUltimoServidorCentral() {
        ServidorCentralModel servidor = null;
        String[] campos = {COLUNA_SER_ID, COLUNA_PER_ORIGEM, COLUNA_PER_DESTINO, COLUNA_ETR_NOME, COLUNA_ETR_TELEFONE};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_SERVIDOR_CENTRAL, campos, null, null, null, null, COLUNA_SER_ID + " DESC", "1");

        if (cursor != null && cursor.moveToFirst()) {
            servidor = new ServidorCentralModel();
            servidor.setSer_id(cursor.getInt(0));
            servidor.setPer_origem(cursor.getString(1));
            servidor.setPer_destino(cursor.getString(2));
            servidor.setEtr_nome(cursor.getString(3));
            servidor.setEtr_telefone(cursor.getString(4));
            cursor.close();
        }
        db.close();
        return servidor;
    }
}
