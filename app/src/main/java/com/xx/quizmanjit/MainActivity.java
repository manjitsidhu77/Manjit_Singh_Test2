package com.xx.quizmanjit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    Button btnSearch,btnDelete,btnUpdate,btnPreview,btnAdd;
    TextInputLayout txtQuestion,txtAnswer,txtNo;
    DBHelper mydb;
    boolean isForDelete = false;
    int questionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnPreview = findViewById(R.id.btnPreview);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtAnswer = findViewById(R.id.txtAnswer);
        txtNo = findViewById(R.id.txtNo);
        mydb = new DBHelper(this);
        btnPreview.setOnClickListener(v->{
            previewQuiz();
        });
        btnAdd.setOnClickListener(v->{
            addQuestion();
        });
        btnDelete.setOnClickListener(v->{
            deleteQuestion();
        });
        btnUpdate.setOnClickListener(v->{
            updateQuestion();
        });
        btnSearch.setOnClickListener(v->{
            submitQuestion();
        });
    }

    private void submitQuestion() {
        //To Delete
        if(isForDelete){
            if(!txtNo.getEditText().getText().toString().equals("")){
                mydb.deleteQuestion(questionID);
                Toast.makeText(this, "Question Deleted!", Toast.LENGTH_SHORT).show();
                clear();
            }
        }
        //To Update
        else{
            if(!txtNo.getEditText().getText().toString().equals("") && !txtQuestion.getEditText().getText().toString().equals("") && !txtAnswer.getEditText().getText().toString().equals("")) {
                if(mydb.updateQuestion(questionID,txtQuestion.getEditText().getText().toString(),txtNo.getEditText().getText().toString(),txtAnswer.getEditText().getText().toString())){
                    Toast.makeText(this, "Question Added!", Toast.LENGTH_SHORT).show();
                    clear();
                }
                else{
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Action to take user to preview screen
    private void previewQuiz() {
        Intent i = new Intent(this,PreviewActivity.class);
        startActivity(i);
    }
    @SuppressLint("Range")
    private void searchQuestion() {
        if(!txtNo.getEditText().getText().toString().equals("")){
            Cursor rs =  mydb.getQuestionByNumber(txtNo.getEditText().getText().toString());
            //Cursor rs =  mydb.getData(1);
            rs.moveToFirst();
            questionID = rs.getInt(rs.getColumnIndex(mydb.CONTACTS_COLUMN_ID));
            txtQuestion.getEditText().setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_QUESTION)));
            txtAnswer.getEditText().setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_ANSWER)));
            txtNo.getEditText().setText(rs.getString(rs.getColumnIndex(mydb.CONTACTS_NUMBER)));
            }
    }

    private void updateQuestion() {
        isForDelete = false;
        searchQuestion();

    }

    private void deleteQuestion() {
        isForDelete = true;
        searchQuestion();

    }

    private void addQuestion() {
        if(mydb.numberOfRows() < 3){
            if(!txtNo.getEditText().getText().toString().equals("") && !txtQuestion.getEditText().getText().toString().equals("") && !txtAnswer.getEditText().getText().toString().equals("")) {
                if(mydb.insertQuestion(txtQuestion.getEditText().getText().toString(),txtNo.getEditText().getText().toString(),txtAnswer.getEditText().getText().toString())){
                    Toast.makeText(this, "Question Added!", Toast.LENGTH_SHORT).show();
                    clear();
                }
                else{
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else{
            Toast.makeText(this, "You can't add more question, Its already three!", Toast.LENGTH_SHORT).show();
        }


    }
    private void clear(){
        txtAnswer.getEditText().setText("");
        txtNo.getEditText().setText("");
        txtQuestion.getEditText().setText("");
        questionID = 0;
    }

}