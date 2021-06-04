package com.starikov.bpo1802.whereami;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mainText, hintText;
    AppCompatButton btnRepeat, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        mainText = findViewById(R.id.textResult);
        hintText = findViewById(R.id.textResultHint);
        btnRepeat = findViewById(R.id.btnRepeatGame);
        btnReturn = findViewById(R.id.btnReturnToMap);

        btnRepeat.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        Intent getIntent = getIntent();
        Boolean answer = getIntent.getBooleanExtra("answer", false);
        String cabinet = getIntent.getStringExtra("cabinet");
        String resultGame = answer ? "Вы угадали":"Вы не угадали";

        mainText.setText(resultGame);
        hintText.setText("Это был " + cabinet);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnRepeatGame:
                intent = new Intent(getApplicationContext(), SelectFloor.class);
                break;
            case  R.id.btnReturnToMap:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}