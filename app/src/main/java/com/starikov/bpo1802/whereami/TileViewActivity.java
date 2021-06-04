package com.starikov.bpo1802.whereami;

import android.util.Log;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;
import com.moagrius.tileview.TileView;

public abstract class TileViewActivity extends AppCompatActivity {

  private boolean mShouldFrameToCenterOnReady;
  private int idMapView;
  public void frameToCenterOnReady() {
    mShouldFrameToCenterOnReady = true;
  }


  public void setContentView(int layoutResID, int id) {
    super.setContentView(layoutResID);

    idMapView = id;
    TileView tileView = findViewById(id);
    tileView.addReadyListener(this::frameToCenter);
    tileView.addTouchListener(new TileView.TouchListener() {
      @Override
      public void onTouch(MotionEvent event) {
        float x11 = event.getXPrecision();
        float x1 = event.getX();
        float y12 = event.getYPrecision();
        float y1 = event.getY();
        Log.d("pos", x1 + ";" + y1 + " || " + x11 + ";" + y12);
      }
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (isFinishing()) {
      TileView tileView = findViewById(idMapView);
      tileView.destroy();
    }
  }

  public void frameToCenter(TileView tileView) {
    if (mShouldFrameToCenterOnReady) {
      tileView.post(() -> tileView.scrollTo(
          tileView.getContentWidth() / 2 - tileView.getWidth() / 2,
          tileView.getContentHeight() / 2 - tileView.getHeight() / 2
      ));
    }
  }

}
