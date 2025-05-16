package DAO;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues; import android.content.Context;
import android.database.Cursor;

import Models.UsuarioModel;

public class UsuarioDAO extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "bdOrigemDestino";
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_USUARIO = "tbUsuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_ROLE = "role";

    public UsuarioDAO (Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABELA_USUARIO + "(" +
                COLUNA_ID + "integer PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_EMAIL + "text not null, " +
                COLUNA_SENHA + " text not null, " +
                COLUNA_ROLE + " text not null)"
        );
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}

    public void inserirUsuario(UsuarioModel usuarioModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COLUNA_EMAIL, usuarioModel.getEmail());
        v.put(COLUNA_SENHA, usuarioModel.getSenha());
        v.put(COLUNA_ROLE, usuarioModel.getRole());

        db.insert(TABELA_USUARIO, null, v);
        db.close();
    }

    public void atualizarUsuario(UsuarioModel usuarioModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put(COLUNA_EMAIL, usuarioModel.getEmail());
        v.put(COLUNA_SENHA, usuarioModel.getSenha());
        v.put(COLUNA_ROLE, usuarioModel.getRole());

        String param[] = {String.valueOf(usuarioModel.getId())};
        db.update(TABELA_USUARIO, null, "id = ?", param);
        db.close();
    }

    public void apagarUsuario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String param[] = {String.valueOf(id)};
        db.delete(TABELA_USUARIO, "id = ?", param);
        db.close();
    }

    public UsuarioModel consultarUsuarios() {
        UsuarioModel usuarioModel;
        usuarioModel = null;
        String campos[] = {"id, email, senha, role"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.query(TABELA_USUARIO, campos, null, null, null, null, null);
        if (cr.moveToFirst()) {
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(cr.getInt(0));
            usuarioModel.setEmail(cr.getString(1));
            usuarioModel.setSenha(cr.getString(2));
            usuarioModel.setRole(cr.getString(3));
        }
        db.close();
        return usuarioModel;
    }
}
