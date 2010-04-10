package com.deesastudio.android.flipanimation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class FlipDemo extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    LinearLayout ll = (LinearLayout)this.findViewById(R.id.ll);
    
    FlipView fv = new FlipView(this);
    fv.setAnimationDuration(2000);
    fv.setDirection(FlipView.DIRECTION_VERTICAL);
    ll.addView(fv);
    FlipView fv2 = new FlipView(this);
    fv2.setInterpolator(FlipView.INTERPOLATOR_NONLINEAR);
    fv2.setAnimationDuration(500);
    ll.addView(fv2);
  }
}