package com.xx.quizmanjit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizDB.db";
    public static final String TABLE_NAME = "quiz";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_QUESTION = "question";
    public static final String CONTACTS_ANSWER = "answer";
    public static final String CONTACTS_NUMBER = "number";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table quiz " +
                        "(id integer primary key, question text,answer text,number text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertQuestion (String question, String no, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", question);
        contentValues.put("answer", answer);
        contentValues.put("number", no);
        db.insert("quiz", null, contentValues);
        return true;
    }
    public Cursor getQuestionByNumber(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from quiz where number="+id+"", null );
        return res;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from quiz where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateQuestion (Integer id, String question, String no, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", question);
        contentValues.put("answer", answer);
        contentValues.put("number", no);
        db.update("quiz", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteQuestion (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("quiz",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Cursor> getAllQuestion() {
        ArrayList<Cursor> array_list = new ArrayList<Cursor>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from quiz", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
           array_list.add(res);
            res.moveToNext();
        }
        return array_list;
    }
    public SQLiteDatabase getDb(){
        return  this.getReadableDatabase();
    }
}
