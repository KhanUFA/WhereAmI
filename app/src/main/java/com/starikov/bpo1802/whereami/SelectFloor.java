package com.starikov.bpo1802.whereami;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import com.starikov.bpo1802.whereami.Database.DatabaseHelper;

import java.io.IOException;
import java.util.Random;

public class SelectFloor extends AppCompatActivity implements View.OnClickListener {

    TextView cabinetFind;
    AppCompatButton btnFloor1, btnFloor2, btnFloor3, btnFloor4, btnFloor5;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String cabinet, side;
    String floor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_floor);
        getSupportActionBar().hide();

        cabinetFind = findViewById(R.id.textSelect);
        btnFloor1 = findViewById(R.id.btnSelectFloor1);
        btnFloor2 = findViewById(R.id.btnSelectFloor2);
        btnFloor3 = findViewById(R.id.btnSelectFloor3);
        btnFloor4 = findViewById(R.id.btnSelectFloor4);
        btnFloor5 = findViewById(R.id.btnSelectFloor5);

        btnFloor1.setOnClickListener(this);
        btnFloor2.setOnClickListener(this);
        btnFloor3.setOnClickListener(this);
        btnFloor4.setOnClickListener(this);
        btnFloor5.setOnClickListener(this);

        mDBHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Random random = new Random();
        int randomId = random.nextInt(29) + 1;
        String query = "SELECT * FROM cabinets WHERE _id = " + randomId;
        query += " AND pos = 'R'";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        cabinet = cursor.getString(1);
        side = cursor.getString(2);
        cursor.close();

        floor = String.valueOf(cabinet.charAt(0));
        Log.d("database out","Floor: " + floor);
        cabinetFind.setText("Найди: " + cabinet);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectFloor1:
                if(floor.equals("1")){
                    goToActivitySelectSide();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный этаж", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSelectFloor2:
                if(floor.equals("2")){
                    goToActivitySelectSide();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный этаж", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSelectFloor3:
                if(floor.equals("3")){
                    goToActivitySelectSide();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный этаж", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSelectFloor4:
                if(floor.equals("4")){
                    goToActivitySelectSide();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный этаж", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSelectFloor5:
                if(floor.equals("5")){
                    goToActivitySelectSide();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный этаж", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void goToActivitySelectSide () {
        Intent intent = new Intent(this, SelectSide.class);

        intent.putExtra("cabinet", cabinet);
        intent.putExtra("side", side);
        intent.putExtra("floor", floor);

        startActivity(intent);
        finish();
    }
}