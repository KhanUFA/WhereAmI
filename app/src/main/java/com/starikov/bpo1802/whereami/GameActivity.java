package com.starikov.bpo1802.whereami;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import com.moagrius.tileview.TileView;
import com.moagrius.tileview.plugins.MarkerPlugin;
import com.moagrius.widget.ScalingScrollView;
import com.starikov.bpo1802.whereami.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton btnSelectPlace;
    TileView tileView;
    TextView gameHint;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private ArrayList<int[]> cabinetMarkers = new ArrayList<>();
    String side, floor, cabinet, selectedMarker;
    int prevMarkerID = -1, id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        btnSelectPlace = findViewById(R.id.btnSelectPlace);
        gameHint = findViewById(R.id.textGameHint);
        btnSelectPlace.setOnClickListener(this);

        Intent getIntent = getIntent();
        side = getIntent.getStringExtra("side");
        cabinet = getIntent.getStringExtra("cabinet");
        floor = String.valueOf(cabinet.charAt(0));

        gameHint.setText("Найди: " + cabinet);

        mDBHelper = new DatabaseHelper(getApplicationContext());
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        String dir = floor + side;
        String path = String.format("%s/%s_files", dir, dir);
        tileView = findViewById(R.id.mapGame);
        tileView.addReadyListener(this::onReady);
        tileView.setMaximumScale(1f);
        tileView.setMinimumScaleMode(ScalingScrollView.MinimumScaleMode.COVER);
        new TileView.Builder(tileView)
                .setSize(2481 , 3508)
                .defineZoomLevel(path + "/12/%d_%d.png")
                .installPlugin(new MarkerPlugin(this))
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onReady(TileView tileView) {
        tileView.post(() -> tileView.scrollTo(
                tileView.getContentWidth() / 2 - tileView.getWidth() / 2,
                tileView.getContentHeight() / 2 - tileView.getHeight() / 2
            ));

        //Создание маркреров
        MarkerPlugin markerPlugin = tileView.getPlugin(MarkerPlugin.class);
        View.OnClickListener markerClickListener = view -> {
            if(prevMarkerID != -1) {
                ImageView prevMarker = findViewById(prevMarkerID);
                prevMarker.setImageResource(R.drawable.marker);
            }
            prevMarkerID = view.getId();
            selectedMarker = (String) view.getTag();
            ImageView marker = (ImageView) view;
            marker.setImageResource(R.drawable.selectedmarker);

            btnSelectPlace.setVisibility(View.VISIBLE);
        };

        floor += "%";
        String query = String.format("SELECT cabinet, x1, y1 FROM cabinets WHERE cabinet LIKE '%s' AND pos = '%s'", floor, side);
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String cabinet = cursor.getString(0);
            int x = cursor.getInt(1);
            int y = cursor.getInt(2);

            ImageView marker = new ImageView(getApplicationContext());
            marker.setImageResource(R.drawable.marker);
            marker.setTag(cabinet);
            marker.setOnClickListener(markerClickListener);
            marker.setId(id);
            id++;

            markerPlugin.addMarker(marker, x, y, -0.5f, -1f, 0, 0);
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        Boolean answer = cabinet.equals(selectedMarker);

        intent.putExtra("cabinet", selectedMarker);
        intent.putExtra("answer", answer);
        startActivity(intent);
    }
}