package com.deesastudio.android.flipanimation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FlipDemo extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    LinearLayout ll = (LinearLayout)this.findViewById(R.id.ll);
    
    //front face for tile1
    RelativeLayout ff = (RelativeLayout) View.inflate(this, R.layout.front_face, null);
    //back face for tile2
    RelativeLayout bf = (RelativeLayout) View.inflate(this, R.layout.back_face, null);
    
    //fron face for tile2
    RelativeLayout ff2 = (RelativeLayout) View.inflate(this, R.layout.front_face, null);
    
    //back face for tile2
    RelativeLayout bf2 = (RelativeLayout) View.inflate(this, R.layout.back_face, null);
    
    
    final FlipView fv = new FlipView(this, bf, ff);
    fv.setAnimationDuration(800);
    fv.setDirection(FlipView.DIRECTION_VERTICAL);
    fv.setPivot (FlipView.PIVOT_BOTTOM);
    ll.addView(fv);
    final FlipView fv2 = new FlipView(this, ff2, bf2);
    fv2.setInterpolator(FlipView.INTERPOLATOR_NONLINEAR);
    fv2.setPivot(FlipView.PIVOT_LEFT);
    fv2.setAnimationDuration(300);
    ll.addView(fv2);
    
    ff.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        fv.flip();
      }
    });
    
    bf.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        fv.flip();
      }
    });
    
    ff2.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        fv2.flip();
      }
    });
    
    bf2.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        fv2.flip();
      }
    });
  }
}