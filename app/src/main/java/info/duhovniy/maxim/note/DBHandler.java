package info.duhovniy.maxim.note;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by maxduhovniy on 9/13/15.
 */
public class DBHandler extends ListActivity {

    private DBHelper helper;

    public DBHandler(Context context) {

        helper = new DBHelper(context, "NoteBase.db", null, 1);
    }

    public boolean addNote(Note note) {
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put("header", note.getHeader());
            values.put("note", note.getBody());
            values.put("email", note.getmEmail());
            db.insert("notes", null, values);
        } catch (SQLException e) {
            e.getMessage();
            return false;
        } finally {
            if (db.isOpen())
                db.close();
        }
        return true;
    }

    public int delNote(int id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int numRowsDeleted = 0;

        try {
            numRowsDeleted = db.delete("notes", "_id=?",
                    new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return numRowsDeleted;
    }

    public void replaceNote(int id, Note note) {

        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put("header", note.getHeader());
            values.put("note", note.getBody());
            values.put("email", note.getmEmail());

            db.update("notes", values, "_id=?", new String[] { String.valueOf(id)});

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            if (db.isOpen())
                db.close();
        }
    }

    public Cursor listAllNotes() {

        Cursor cursor = null;
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            cursor = db.query("notes", null, null, null, null, null, null);
        } catch (SQLException e) {
            e.getMessage();
        }
        return cursor;
    }
}
