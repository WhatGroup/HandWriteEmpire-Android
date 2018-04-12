package com.sinovoice.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class SecondActivity extends AppCompatActivity {

  private FrameLayout mMainLayout;
  private PaintView mPaintView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_second );
    mMainLayout = findViewById( R.id.main_layout );
    View view = LayoutInflater.from( this ).inflate( R.layout.paint_broad, null );
    mPaintView = view.findViewById( R.id.pain_view );
    mPaintView.setBgColor( 0x00000000 );
    mPaintView.setColor( 0xffff0000 );
    mPaintView.setStrokeWidth( 20 );
    mMainLayout.addView( view );
  }
}
