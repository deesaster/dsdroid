package com.deesastudio.android.flipanimation;

import android.app.Activity;
import android.os.Bundle;
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
    
    
    final FlipView fv = new FlipView(this, ff, bf);
    fv.setAnimationDuration(2000);
    fv.setDirection(FlipView.DIRECTION_VERTICAL);
    ll.addView(fv);
    
    final FlipView fv2 = new FlipView(this, ff2, bf2);
    fv2.setInterpolator(FlipView.INTERPOLATOR_NONLINEAR);
    fv2.setAnimationDuration(500);
    ll.addView(fv2);
    
    ff.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        // TODO Auto-generated method stub
        fv.flip();
      }
    });
    
    bf.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        // TODO Auto-generated method stub
        fv.flip();
      }
    });
    
    ff2.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        // TODO Auto-generated method stub
        fv2.flip();
      }
    });
    
    bf2.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v) {
        // TODO Auto-generated method stub
        fv2.flip();
      }
    });
  }
}