package com.example.add;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.add.QContract.*;
import java.util.ArrayList;
import java.util.List;

public class QDbHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Addition.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public QDbHelper(@Nullable Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_No + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("༢+༢", "༤", "༢", "༣", 1);
        addQuestion(q1);
        Question q2 = new Question("༢+༢+༣", "༥", "༧", "༩", 2);
        addQuestion(q2);
        Question q3 = new Question("༢+༡༢+༢༢", "༣༦", "༢༦", "༢༩", 1);
        addQuestion(q3);
        Question q4 = new Question("༦+༡༠+༦", "༧༦", "༡༩", "༢༢", 3);
        addQuestion(q4);
        Question q5 = new Question("༩+༨+༧", "༡༨", "༢༤", "༡༩", 2);
        addQuestion(q5);
        Question q6 = new Question("༧+༤+༣༡", "༣༢", "༥༢", "༤༢", 3);
        addQuestion(q6);
        Question q7 = new Question("༦༦+༦+༩", "༨༡", "༧༡", "༩༡", 1);
        addQuestion(q7);
        Question q8 = new Question("༡༨+༡༩+༡༧", "༩༠", "༥༤", "༦༣", 2);
        addQuestion(q8);
        Question q9 = new Question("༢༡+༡༢+༣༡", "༤༤", "༥༢", "༦༤", 3);
        addQuestion(q9);
        Question q10 = new Question("༡༤+༤༡+༤༣", "༥༨", "༩༨", "༦༧", 2);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_No, question.getAnswerNo());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNo(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_No)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
