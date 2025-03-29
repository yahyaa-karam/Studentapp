package ma.fst.projet.app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3; // 👈 bumped to trigger onUpgrade
    // bumped version
    private static final String DATABASE_NAME = "ecole";

    // Updated table creation with date_naissance
    private static final String CREATE_TABLE_ETUDIANT = "CREATE TABLE etudiant (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nom TEXT, " +
            "prenom TEXT, " +
            "date_naissance TEXT, " +
            "image_path TEXT" +
            ")";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ETUDIANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS etudiant");
        onCreate(db);
    }
}
