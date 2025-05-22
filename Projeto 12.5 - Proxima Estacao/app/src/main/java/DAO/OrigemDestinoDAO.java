package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OrigemDestinoDAO extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "bdOrigemDestino"; // Mantendo o mesmo nome de banco
    public static final int VERSAO_BANCO = 1;

    //tabela de Usuario
    public static final String TABELA_USUARIO = "tbUsuario";
    public static final String COLUNA_ID_USUARIO = "id";
    public static final String COLUNA_EMAIL_USUARIO = "email";
    public static final String COLUNA_SENHA_USUARIO = "senha";
    public static final String COLUNA_ROLE_USUARIO = "role";
    public static final String CREATE_TABLE_USUARIO = "create table " + TABELA_USUARIO + "(" +
            COLUNA_ID_USUARIO + " integer PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_EMAIL_USUARIO + " text not null, " +
            COLUNA_SENHA_USUARIO + " text not null, " +
            COLUNA_ROLE_USUARIO + " text not null)";

    //tabela de Localização
    public static final String TABELA_LOCALIZACAO = "tbLocalizacao";
    public static final String COLUNA_ID_LOCALIZACAO = "id";
    public static final String COLUNA_NOME_LOCALIZACAO = "nome";
    public static final String COLUNA_LATITUDE_LOCALIZACAO = "latitude";
    public static final String COLUNA_LONGITUDE_LOCALIZACAO = "longitude";
    public static final String CREATE_TABLE_LOCALIZACAO = "create table " + TABELA_LOCALIZACAO + "(" +
            COLUNA_ID_LOCALIZACAO + " integer PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_NOME_LOCALIZACAO + " text not null unique, " +
            COLUNA_LATITUDE_LOCALIZACAO + " real not null, " +
            COLUNA_LONGITUDE_LOCALIZACAO + " real not null)";
    public static final String INSERT_A_TABLE_LOCALIZACAO = "INSERT INTO " + TABELA_LOCALIZACAO + " (" + COLUNA_NOME_LOCALIZACAO + ", " + COLUNA_LATITUDE_LOCALIZACAO + ", " + COLUNA_LONGITUDE_LOCALIZACAO + ") VALUES \n" +
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
            "                         ('a-Tucuruvi', -23.480512268808553, -46.60386194108796);";
    public static final String INSERT_V_TABLE_LOCALIZACAO = "INSERT INTO " + TABELA_LOCALIZACAO + " (" + COLUNA_NOME_LOCALIZACAO + ", " + COLUNA_LATITUDE_LOCALIZACAO + ", " + COLUNA_LONGITUDE_LOCALIZACAO + ") VALUES \n" +
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
            "                         ('v-Corinthians - Itaquera', -23.542097015739, -46.470665971001665)";

    //tabela de Percurso
    public static final String TABELA_PERCURSO = "tbPercurso";
    public static final String COLUNA_ID_PERCURSO = "id";
    public static final String COLUNA_ORIGEM_PERCURSO = "origem";
    public static final String COLUNA_DESTINO_PERCURSO = "destino";
    public static final String CREATE_TABLE_PERCURSO = "create table " + TABELA_PERCURSO + "(" +
            COLUNA_ID_PERCURSO + " integer PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_ORIGEM_PERCURSO + " text not null, " +
            COLUNA_DESTINO_PERCURSO + " text not null, " +
            "FOREIGN KEY("+ COLUNA_ORIGEM_PERCURSO +") REFERENCES " + TABELA_LOCALIZACAO + "(" + COLUNA_NOME_LOCALIZACAO + "), " +
            "FOREIGN KEY("+ COLUNA_DESTINO_PERCURSO +") REFERENCES " + TABELA_LOCALIZACAO + "(" + COLUNA_NOME_LOCALIZACAO + "))";

    //Tabela de Servidor Central
    public static final String TABELA_SERVIDOR_CENTRAL = "tbServidorCentral";
    public static final String COLUNA_SER_ID_SC = "ser_id";
    public static final String COLUNA_PER_ORIGEM_SC = "per_origem";
    public static final String COLUNA_PER_DESTINO_SC = "per_destino";
    public static final String COLUNA_PER_DATA_SC = "per_data";
    public static final String COLUNA_PER_HORA_SC = "per_hora";
    public static final String COLUNA_ETR_NOME_SC = "etr_nome";
    public static final String COLUNA_ETR_TELEFONE_SC = "etr_telefone";
    public static final String CREATE_TABLE_SERVIDOR_CENTRAL = "create table " + TABELA_SERVIDOR_CENTRAL + "(" +
            COLUNA_SER_ID_SC + " integer PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_PER_ORIGEM_SC + " text not null, " +
            COLUNA_PER_DESTINO_SC + " text not null, " +
            COLUNA_PER_DATA_SC + " text not null, " +
            COLUNA_PER_HORA_SC + " text not null, " +
            COLUNA_ETR_NOME_SC + " text not null, " +
            COLUNA_ETR_TELEFONE_SC + " text not null)";

    //tabela de Entrevistado
    public static final String TABELA_ENTREVISTADO = "tbEntrevistado";
    public static final String COLUNA_ID_ENTREVISTADO = "id";
    public static final String COLUNA_NOME_ENTREVISTADO = "nome";
    public static final String COLUNA_TELEFONE_ENTREVISTADO = "telefone";
    public static final String CREATE_TABLE_ENTREVISTADO = "create table " + TABELA_ENTREVISTADO + "(" +
            COLUNA_ID_ENTREVISTADO + " integer PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_NOME_ENTREVISTADO + " text not null DEFAULT 'Anônimo', " +
            COLUNA_TELEFONE_ENTREVISTADO + " text not null DEFAULT 'Não informado')";

    public OrigemDestinoDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("OrigemDestinoDAO", "onCreate: Criando tabelas...");
        sqLiteDatabase.execSQL(CREATE_TABLE_USUARIO);
        sqLiteDatabase.execSQL(CREATE_TABLE_LOCALIZACAO);
        sqLiteDatabase.execSQL(CREATE_TABLE_PERCURSO);
        sqLiteDatabase.execSQL(CREATE_TABLE_SERVIDOR_CENTRAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ENTREVISTADO);
        Log.d("OrigemDestinoDAO", "onCreate: Tabelas criadas com sucesso!");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("OrigemDestinoDAO", "onUpgrade: Atualizando tabelas de v" + i + " para v" + i1 + "...");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_LOCALIZACAO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_PERCURSO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_SERVIDOR_CENTRAL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_ENTREVISTADO);
        onCreate(sqLiteDatabase);
    }

}
