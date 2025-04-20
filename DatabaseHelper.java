


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Booking.db";
    private static final String TABLE_NAME = "bookings";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_CONTACT = "contact";
    private static final String COL_SERVICE = "service";
    private static final String COL_CITY = "city";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_CONTACT + " TEXT, " +
                COL_SERVICE + " TEXT, " +
                COL_CITY + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String contact, String service, String city, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_CONTACT, contact);
        contentValues.put(COL_SERVICE, service);
        contentValues.put(COL_CITY, city);
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_TIME, time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
