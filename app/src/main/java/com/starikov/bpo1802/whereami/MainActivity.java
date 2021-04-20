package com.starikov.bpo1802.whereami;

import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.moagrius.tileview.TileView;
import com.moagrius.widget.ScalingScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*MapWidget map = new MapWidget(this,"second");
        LinearLayout layout = findViewById(R.id.mainLayout);
        layout.addView(map);*/

        TileView tileView = findViewById(R.id.map);
        tileView.setMaximumScale(3f);
        tileView.setMinimumScaleMode(ScalingScrollView.MinimumScaleMode.COVER);
        new TileView.Builder(tileView)
                .setSize(1600 , 1000)
                .defineZoomLevel("second/second_files/11/%d_%d.png")
                .build();
    }
}