package ma.fst.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ma.fst.projet.app.util.MySQLiteHelper;

public class EtudiantService {


    private static final String TABLE_NAME = "etudiant";
    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_DATE = "date_naissance";
    private static final String KEY_IMAGE = "image_path";  // ✅ NEW

    private static final String[] COLUMNS = {KEY_ID, KEY_NOM, KEY_PRENOM, KEY_DATE, KEY_IMAGE};


    private final MySQLiteHelper helper;

    public EtudiantService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    public void create(Etudiant e) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        values.put(KEY_DATE, e.getDateNaissance());
        values.put(KEY_IMAGE, e.getImagePath()); // ✅ NEW
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void update(Etudiant e) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRENOM, e.getPrenom());
        values.put(KEY_DATE, e.getDateNaissance());
        values.put(KEY_IMAGE, e.getImagePath());  // ✅ NEW
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public void delete(Etudiant e) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public Etudiant findById(int id) {
        Etudiant e = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, COLUMNS, KEY_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (c.moveToFirst()) {
            e = new Etudiant();
            e.setId(c.getInt(0));
            e.setNom(c.getString(1));
            e.setPrenom(c.getString(2));
            e.setDateNaissance(c.getString(3));
            e.setImagePath(c.getString(4)); // ✅ NEW
        }
        c.close(); // ✅ CLOSE CURSOR
        db.close();
        return e;
    }

    public List<Etudiant> findAll() {
        List<Etudiant> eds = new ArrayList<>();
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Etudiant e = new Etudiant();
                e.setId(c.getInt(0));
                e.setNom(c.getString(1));
                e.setPrenom(c.getString(2));
                e.setDateNaissance(c.getString(3));
                e.setImagePath(c.getString(4)); // ✅ NEW
                eds.add(e);
            } while (c.moveToNext());
        }
        c.close(); // ✅ CLOSE CURSOR
        db.close();
        return eds;
    }
}
