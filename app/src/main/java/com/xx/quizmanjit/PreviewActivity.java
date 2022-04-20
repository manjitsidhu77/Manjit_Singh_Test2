package com.xx.quizmanjit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity {
    Button btnPrevious,btnNext;
    DBHelper mydb;
    TextView txtQues,txtNo;
    TextInputLayout txtAnswer;
    int index = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        txtQues = findViewById(R.id.textViewQues);
        txtAnswer = findViewById(R.id.txtAns);
        txtNo = findViewById(R.id.textViewNum);
        mydb = new DBHelper(this);
       // setData(index);

        Cursor c =  mydb.getDb().rawQuery( "select * from quiz", null );
        c.moveToFirst();
        setData(c);

        btnPrevious.setOnClickListener(v->{
            if (c != null && !c.isBeforeFirst() && !c.isFirst())
            {

                c.moveToPrevious();
                setData(c);
            }
        });
        btnNext.setOnClickListener(v->{
            if (c != null && !c.isAfterLast() && !c.isLast())
            {

                c.moveToNext();
                setData(c);
            }
        });
    }


    @SuppressLint("Range")
    private  void setData(Cursor rs){
        if (rs!= null && rs.getCount() > 0) {
            txtQues.setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_QUESTION)));
            txtNo.setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_NUMBER)));
            txtAnswer.getEditText().setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_ANSWER)));
        }
    }
}