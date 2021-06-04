package com.starikov.bpo1802.whereami;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;

public class SelectSide extends AppCompatActivity implements View.OnClickListener {

    TextView textCabinet;
    AppCompatButton btnCentreSide, btnLeftSide, btnRightSide;
    String side, floor, cabinet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_side);
        getSupportActionBar().hide();

        btnCentreSide = findViewById(R.id.btnCentreSide);
        btnLeftSide = findViewById(R.id.btnLeftside);
        btnRightSide = findViewById(R.id.btnRightSide);
        textCabinet = findViewById(R.id.textSelect2);

        btnCentreSide.setOnClickListener(this);
        btnLeftSide.setOnClickListener(this);
        btnRightSide.setOnClickListener(this);

        Intent getIntent = getIntent();
        floor = getIntent.getStringExtra("floor");
        side = getIntent.getStringExtra("side");
        cabinet = getIntent.getStringExtra("cabinet");

        if(floor.equals("3") || floor.equals("5")) {
            btnRightSide.setVisibility(View.VISIBLE);
        }
        textCabinet.setText("Найди: " + cabinet);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        switch (v.getId()){
            case R.id.btnCentreSide:
                if(side.equals("C")){
                    goToActivityGame();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверная часть здания", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLeftside:
                if(side.equals("L")){
                    goToActivityGame();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверная часть здания", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnRightSide:
                if(side.equals("R")){
                    goToActivityGame();
                } else {
                    Toast.makeText(getApplicationContext(), "Неверная часть здания", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void goToActivityGame() {
        Intent intent = new Intent(this, GameActivity.class);

        intent.putExtra("side", side);
        intent.putExtra("cabinet", cabinet);

        startActivity(intent);
        finish();
    }
}