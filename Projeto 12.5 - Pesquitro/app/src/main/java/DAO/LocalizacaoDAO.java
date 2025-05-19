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
                COLUNA_NOME + " text not null unique, " +
                COLUNA_LATITUDE + " real not null, " +
                COLUNA_LONGITUDE + " real not null)"
        );

        sqLiteDatabase.execSQL("INSERT INTO " + TABELA_LOCALIZACAO + " (" + COLUNA_NOME + ", " + COLUNA_LATITUDE + ", " + COLUNA_LONGITUDE + ") VALUES \n" +
                "                         ('a-Jabaquara', -23.64604825807404, -46.640739589624445),\n" +
                "                         ('a-Conceição', -23.63509602598109, -46.64143339250251),\n" +
                "                         ('a-São Judas', -23.625564998916857, -46.640932708609874),\n" +
                "                         ('a-Saúde', -23.61777973377973, -46.639130264132966),\n" +
                "                         ('a-Praça da Árvore',-23.61062317319937, -46.63784280382249),\n" +
                "                         ('a-Santa Cruz', -23.5988565152342, -46.63682356440597),\n" +
                "                         ('a-Vila Mariana', -23.589353938134217, -46.634221821662265),\n" +
                "                         ('a-Ana Rosa', -23.581379697821816, -46.63854554256635),\n" +
                "                         ('a-Paraíso', -23.574929156489098, -46.64103463249994),\n" +
                "                         ('a-Vergueiro', -23.56883231670445, -46.64009049489165),\n" +
                "                         ('a-São Joaquim', -23.56167308561488, -46.639918833513576),\n" +
                "                         ('a-Liberdade', -23.555103557986843, -46.635713129832695),\n" +
                "                         ('a-Sé', -23.55010734992848, -46.63330987057424),\n" +
                "                         ('a-São Bento', -23.54414214030185, -46.634388118597194),\n" +
                "                         ('a-Luz', -23.53645040717775, -46.63436666090174),\n" +
                "                         ('a-Tiradentes', -23.53098133096231, -46.63256421644813),\n" +
                "                         ('a-Armênia', -23.52543333074239, -46.629345565630516),\n" +
                "                         ('a-Portuguesa - Tietê', -23.516127117297472, -46.62516131957949),\n" +
                "                         ('a-Carandiru', -23.509122429727213, -46.62486091216605),\n" +
                "                         ('a-Santana', -23.50258963262155, -46.62459269125646),\n" +
                "                         ('a-Jardim São Paulo - Ayrton Senna', -23.49231020776274, -46.616686694408486),\n" +
                "                         ('a-Parada Inglesa', -23.486934033495594, -46.60888004780503),\n" +
                "                         ('a-Tucuruvi', -23.480512268808553, -46.60386194108796);");

        sqLiteDatabase.execSQL("INSERT INTO " + TABELA_LOCALIZACAO + " (" + COLUNA_NOME + ", " + COLUNA_LATITUDE + ", " + COLUNA_LONGITUDE + ") VALUES \n" +
                "                         ('v-Palmeiras - Barra Funda', -23.52548922033351, -46.66687118198547),\n" +
                "                         ('v-Marechal Deodoro', -23.53389746027724, -46.655836366157985),\n" +
                "                         ('v-Santa Cecília', -23.539169694196666, -46.64927031845924),\n" +
                "                         ('v-República', -23.54420566059048, -46.643004678216016),\n" +
                "                         ('v-Anhangabaú', -23.54756040259907, -46.6383045786772),\n" +
                "                         ('v-Sé', -23.550117590184836, -46.63330494108539),\n" +
                "                         ('v-Pedro II', -23.549675003618173, -46.625902044231516),\n" +
                "                         ('v-Brás', -23.547854514441802, -46.61582771052388),\n" +
                "                         ('v-Bresser - Mooca', -23.546355764340877, -46.607296852153084),\n" +
                "                         ('v-Belém', -23.542705668758053, -46.589650332763924),\n" +
                "                         ('v-Tatuapé', -23.540240892769173, -46.57641340226927),\n" +
                "                         ('v-Carrão - Assaí Atacadista', -23.537796419966597, -46.564163994884694),\n" +
                "                         ('v-Penha - Lojas Besni', -23.533530852278545, -46.5426423266639),\n" +
                "                         ('v-Vila Matilde', -23.531874693024992, -46.53106850271956),\n" +
                "                         ('v-Guilhermina - Esperança', -23.529267312884233, -46.51657956960757),\n" +
                "                         ('v-Patriarca - Vila Ré', -23.531145676292446, -46.50150383901502),\n" +
                "                         ('v-Artur Alvim', -23.540508260788727, -46.48443583472658),\n" +
                "                         ('v-Corinthians - Itaquera', -23.542097015739, -46.470665971001665)");
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
