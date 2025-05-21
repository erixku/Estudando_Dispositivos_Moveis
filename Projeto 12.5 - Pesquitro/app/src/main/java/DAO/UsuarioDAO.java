package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Models.UsuarioModel;

public class UsuarioDAO {
    private OrigemDestinoDAO ogDAO;
    private SQLiteDatabase db;

    public static final String TABELA_USUARIO = "tbUsuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_ROLE = "role";

    public UsuarioDAO (Context context) {
        this.ogDAO = new OrigemDestinoDAO(context);
    }

    public void open() {
        db = ogDAO.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long inserirUsuario(UsuarioModel usuarioModel) {
        long result = -1;
        ContentValues v = new ContentValues();
        v.put(COLUNA_EMAIL, usuarioModel.getEmail());
        v.put(COLUNA_SENHA, usuarioModel.getSenha());
        v.put(COLUNA_ROLE, usuarioModel.getRole());

       try {
           open();
           result = db.insert(TABELA_USUARIO, null, v);
           if (result == -1) {
               Log.e("UsuarioDAO","Erro ao inserir usuário " + usuarioModel.getEmail());
           } else {
               Log.d("UsuarioDAO","Usuário " + usuarioModel.getEmail() + " inserido com sucesso. ID: " + result);
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           close();
       }
       return result;
    }

    public long atualizarUsuario(UsuarioModel usuarioModel) {
        long result = -1;
        ContentValues v = new ContentValues();

        v.put(COLUNA_EMAIL, usuarioModel.getEmail());
        v.put(COLUNA_SENHA, usuarioModel.getSenha());
        v.put(COLUNA_ROLE, usuarioModel.getRole());

        try{
            open();
            result = db.update(TABELA_USUARIO, v, "id = ?", new String[]{String.valueOf(usuarioModel.getId())});
            if (result == -1) {
                Log.e("UsuarioDAO","Erro ao atualizar usuário " + usuarioModel.getEmail());
            } else {
                Log.d("UsuarioDAO","Usuário " + usuarioModel.getEmail() + " atualizado com sucesso. ID: " + result);
            }
        } catch (Exception e) {
            Log.e("UsuarioDAO","Erro ao atualizar usuário " + usuarioModel.getEmail());
        } finally {
            close();
        }

        return result;
    }

    public long apagarUsuario(int id) {
        long result = -1;
        String param[] = {String.valueOf(id)};

        try{
            open();
            result = db.delete(TABELA_USUARIO, "id = ?", param);
            if (result == -1) {
                Log.e("UsuarioDAO","Erro ao apagar usuário com ID: " + id);
            } else {
                Log.d("UsuarioDAO","Usuário com ID: " + id + " apagado com sucesso.");
            }
        } catch (Exception e) {
            Log.e("UsuarioDAO","Erro ao apagar usuário com ID: " + id);
        } finally {
            close();
        }

        return result;
    }

    public UsuarioModel consultarUsuariosPorEmail(String email) {
        UsuarioModel usuarioModel;
        usuarioModel = null;
        Cursor cr = null;

        try {
            open();
            String param[] = { email };
            String campos[] = {COLUNA_ID, COLUNA_EMAIL, COLUNA_SENHA, COLUNA_ROLE};
            cr = db.query(TABELA_USUARIO, campos, "email = ?", param, null, null, null);

            if (cr.moveToFirst()) {
                usuarioModel = new UsuarioModel();
                usuarioModel.setId(cr.getInt(cr.getColumnIndexOrThrow(COLUNA_ID)));
                usuarioModel.setEmail(cr.getString(cr.getColumnIndexOrThrow(COLUNA_EMAIL)));
                usuarioModel.setSenha(cr.getString(cr.getColumnIndexOrThrow(COLUNA_SENHA)));
                usuarioModel.setRole(cr.getString(cr.getColumnIndexOrThrow(COLUNA_ROLE)));
            } else {
                Log.e("UsuarioDAO","Erro ao consultar usuário com email: " + email);
            }
        } catch (Exception e) {
            Log.e("UsuarioDAO","Erro ao consultar usuário: " + e.getMessage());
        } finally {
            if (cr != null && !cr.isClosed()) {
                cr.close();
            }
            close();
        }


        db.close();
        return usuarioModel;
    }
}
