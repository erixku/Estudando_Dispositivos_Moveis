package DAO;



public class EntrevistadoDAO {

    public static final String NOME_BANCO = "bdOrigemDestino"; // Mantendo o mesmo nome de banco
    public static final int VERSAO_BANCO = 1;
    public static final String TABELA_ENTREVISTADO = "tbEntrevistado";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_TELEFONE = "telefone";

    public EntrevistadoModelDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABELA_ENTREVISTADO + "(" +
                COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME + " text not null, " +
                COLUNA_TELEFONE + " text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Lógica para atualizar a tabela se a versão do banco mudar
    }

    public void inserirEntrevistado(EntrevistadoModel entrevistado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, entrevistado.getNome());
        values.put(COLUNA_TELEFONE, entrevistado.getTelefone());

        db.insert(TABELA_ENTREVISTADO, null, values);
        db.close();
    }

    public void atualizarEntrevistado(EntrevistadoModel entrevistado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, entrevistado.getNome());
        values.put(COLUNA_TELEFONE, entrevistado.getTelefone());

        String[] param = {String.valueOf(entrevistado.getId())};
        db.update(TABELA_ENTREVISTADO, values, "id = ?", param);
        db.close();
    }

    public void apagarEntrevistado(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] param = {String.valueOf(id)};
        db.delete(TABELA_ENTREVISTADO, "id = ?", param);
        db.close();
    }

    public EntrevistadoModel consultarEntrevistado(int id) {
        EntrevistadoModel entrevistado = null;
        String[] campos = {COLUNA_ID, COLUNA_NOME, COLUNA_TELEFONE};
        String[] param = {String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ENTREVISTADO, campos, "id = ?", param, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            entrevistado = new EntrevistadoModel();
            entrevistado.setId(cursor.getInt(0));
            entrevistado.setNome(cursor.getString(1));
            entrevistado.setTelefone(cursor.getString(2));
            cursor.close();
        }
        db.close();
        return entrevistado;
    }

    public EntrevistadoModel consultarUltimoEntrevistado() {
        EntrevistadoModel entrevistado = null;
        String[] campos = {COLUNA_ID, COLUNA_NOME, COLUNA_TELEFONE};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_ENTREVISTADO, campos, null, null, null, null, COLUNA_ID + " DESC", "1");

        if (cursor != null && cursor.moveToFirst()) {
            entrevistado = new EntrevistadoModel();
            entrevistado.setId(cursor.getInt(0));
            entrevistado.setNome(cursor.getString(1));
            entrevistado.setTelefone(cursor.getString(2));
            cursor.close();
        }
        db.close();
        return entrevistado;
    }

}
