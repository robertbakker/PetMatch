package com.example.robert.petmatch;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.robert.petmatch.models.Pet;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DBHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "petmatch.db";

    // Table Names
    private static final String TABLE_PETS = "pets";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // PETS Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_SPECIES = "species";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_STATUS = "status";

    // Table Create Statements
    private static final String CREATE_TABLE_PETS =
            "CREATE TABLE " + TABLE_PETS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT,"
                    + KEY_SPECIES + " TEXT,"
                    + KEY_DESCRIPTION + " TEXT,"
                    + KEY_IMAGE + " TEXT,"
                    + KEY_STATUS + " INTEGER,"
                    + KEY_CREATED_AT + " DATETIME"
                    + ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PETS);
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (1, 'Johan', 'giraffe', 'Lief', 'image1', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (2, 'Kiki', 'frog', 'Lief', 'image2', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (3, 'Betty', 'baboon', 'Lief', 'image3', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (4, 'Johan', 'giraffe', 'Lief', 'image1', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (5, 'Kiki', 'frog', 'Lief', 'image2', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
        db.execSQL("INSERT INTO " + TABLE_PETS + " VALUES (6, 'Betty', 'baboon', 'Lief', 'image3', '" + Pet.STATUS_UNDECIDED + "', '2015-02-03 18:00:00')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    /**
     * Get single pet
     */
    public Pet getPet(long petId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PETS + " WHERE "
                + KEY_ID + " = " + petId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return createPetModel(c);
    }

    private Pet createPetModel(Cursor c) {
        int id = c.getInt(c.getColumnIndex(KEY_ID));
        String name = c.getString(c.getColumnIndex(KEY_NAME));
        String species = c.getString(c.getColumnIndex(KEY_SPECIES));
        String description = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
        String image = c.getString(c.getColumnIndex(KEY_IMAGE));
        String createdAt = c.getString(c.getColumnIndex(KEY_CREATED_AT));
        Pet pet = new Pet(id, name, species, description, image, createdAt);

        if (c.getInt(c.getColumnIndex(KEY_STATUS)) == Pet.STATUS_LIKED) {
            pet.like();
        }
        if (c.getInt(c.getColumnIndex(KEY_STATUS)) == Pet.STATUS_DISLIKED) {
            pet.dislike();
        }

        return pet;
    }

    /**
     * Getting all pets
     */
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<Pet>();
        String selectQuery = "SELECT * FROM " + TABLE_PETS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Pet pet = createPetModel(c);
                pets.add(pet);
            } while (c.moveToNext());
        }

        return pets;
    }

    /**
     * Get all pets that are not matched yet
     */
    public List<Pet> getAllPetsByStatus(int status) {
        List<Pet> pets = new ArrayList<Pet>();

        String selectQuery =
                "SELECT * FROM " + TABLE_PETS + " p WHERE status = '" + status + "'";
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                pets.add(createPetModel(c));
            } while (c.moveToNext());
        }

        return pets;
    }

    public int updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pet.getName());
        values.put(KEY_SPECIES, pet.getSpecies());
        values.put(KEY_DESCRIPTION, pet.getDescription());
        values.put(KEY_IMAGE, pet.getImage());
        values.put(KEY_STATUS, pet.getStatus());

        // updating row
        return db.update(TABLE_PETS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(pet.getId())});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}