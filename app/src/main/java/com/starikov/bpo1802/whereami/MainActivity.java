package com.starikov.bpo1802.whereami;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import com.moagrius.tileview.TileView;
import com.moagrius.widget.ScalingScrollView;
import com.starikov.bpo1802.whereami.Database.DatabaseHelper;

import java.io.IOException;

public class MainActivity extends TileViewActivity implements View.OnClickListener {

    TileView tileView;
    AppCompatButton btnFloor1, btnFloor2, btnFloor3, btnFloor4, btnFloor5, btnGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, R.id.map);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        btnFloor1 = findViewById(R.id.btnMapFloor1);
        btnFloor2 = findViewById(R.id.btnMapFloor2);
        btnFloor3 = findViewById(R.id.btnMapFloor3);
        btnFloor4 = findViewById(R.id.btnMapFloor4);
        btnFloor5 = findViewById(R.id.btnMapFloor5);
        btnGame = findViewById(R.id.btnGame);

        btnFloor1.setOnClickListener(this);
        btnFloor2.setOnClickListener(this);
        btnFloor3.setOnClickListener(this);
        btnFloor4.setOnClickListener(this);
        btnFloor5.setOnClickListener(this);
        btnGame.setOnClickListener(this);

        tileView = findViewById(R.id.map);
        tileView.setMaximumScale(1f);
        frameToCenterOnReady();
        tileView.setMinimumScaleMode(ScalingScrollView.MinimumScaleMode.COVER);
        new TileView.Builder(tileView)
                .setSize(5790 , 3508)
                .defineZoomLevel("2floor/2floor_files/13/%d_%d.png")
                .build();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        tileView.addReadyListener(this::frameToCenter);
        switch (v.getId()){
            case R.id.btnMapFloor1:
                new TileView.Builder(tileView)
                        .setSize(5790 , 3508)
                        .defineZoomLevel("1floor/1floor_files/13/%d_%d.png")
                        .build();
                break;

            case R.id.btnMapFloor2:
                new TileView.Builder(tileView)
                        .setSize(5790 , 3508)
                        .defineZoomLevel("2floor/2floor_files/13/%d_%d.png")
                        .build();
                break;

            case R.id.btnMapFloor3:
                new TileView.Builder(tileView)
                        .setSize(5790 , 3508)
                        .defineZoomLevel("3floor/3floor_files/13/%d_%d.png")
                        .build();
                break;

            case R.id.btnMapFloor4:
                new TileView.Builder(tileView)
                        .setSize(5790 , 3508)
                        .defineZoomLevel("4floor/4floor_files/13/%d_%d.png")
                        .build();
                break;

            case R.id.btnMapFloor5:
                frameToCenterOnReady();
                tileView.setMinimumScaleMode(ScalingScrollView.MinimumScaleMode.COVER);
                new TileView.Builder(tileView)
                        .setSize(5790 , 3508)
                        .defineZoomLevel("5floor/5floor_files/13/%d_%d.png")
                        .build();
                break;
            case R.id.btnGame:
                intent = new Intent(this, SelectFloor.class);
                startActivity(intent);
                finish();
        }
    }
}