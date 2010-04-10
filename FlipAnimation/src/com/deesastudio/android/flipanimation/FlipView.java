package com.deesastudio.android.flipanimation;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class FlipView extends FrameLayout{
  public static final int INTERPOLATOR_LINEAR = 1;
  public static final int INTERPOLATOR_NONLINEAR = 2;
  public static final int DIRECTION_HORIZONTAL = 1;
  public static final int DIRECTION_VERTICAL = 2;
  
  private boolean m_IsFrontFacing = true;
  private boolean m_IsAnimating = false;
  
  private int m_InterpolatorType = INTERPOLATOR_LINEAR;
  private int m_Direction = DIRECTION_HORIZONTAL;
  
  private long m_HalfAnimationDuration = 500;
  
  private RelativeLayout m_FrontFace;
  private RelativeLayout m_BackFace;
  
  public FlipView(Context context, RelativeLayout frontFace, RelativeLayout backFace) {
    super(context);
    
    setFrontFace (frontFace);
    setBackFace (backFace);
    //m_BackFace.setVisibility(View.GONE);
  }
  
  //
  public void setFrontFace (RelativeLayout frontFace) {
    if (m_IsAnimating)
        return;
    
    if (m_FrontFace != null)
      this.removeView(m_FrontFace);
    
    m_FrontFace = frontFace;
    
    if (m_FrontFace != null) {
      this.addView(m_FrontFace);
      
      if (!m_IsFrontFacing)
        m_FrontFace.setVisibility(View.GONE);
    }
  }
  
  //
  public void setBackFace (RelativeLayout backFace) {
    if (m_IsAnimating)
      return;
    
    if (m_BackFace != null)
      this.removeView(m_BackFace);
    
    m_BackFace = backFace;
    
    if (m_BackFace != null) {
      this.addView(m_BackFace);
      
      if (m_IsFrontFacing)
        m_FrontFace.setVisibility(View.GONE);
    }
  }
  
  public void setInterpolator(int interpolator) {
    if (interpolator == INTERPOLATOR_NONLINEAR)
      m_InterpolatorType = INTERPOLATOR_NONLINEAR;
    else
      m_InterpolatorType = INTERPOLATOR_LINEAR;
  }
  
  public void setAnimationDuration(long milliseconds) {
    if (milliseconds > 20)
      m_HalfAnimationDuration = (long)milliseconds / 2;
  }
  
  public void setDirection(int dir) {
    if (m_IsAnimating)
      return;
    
    if (dir==DIRECTION_VERTICAL) 
      m_Direction = DIRECTION_VERTICAL;
    else
      m_Direction = DIRECTION_HORIZONTAL;
  }
	
  public void flip() {
    if (m_IsAnimating || m_FrontFace == null || m_BackFace == null)
      return;
    
    if (m_IsFrontFacing)
      applyRotation(0, 90);
    else
      applyRotation(0, -90);

    m_IsFrontFacing = !m_IsFrontFacing;
  }
	
  private void applyRotation(float start, float end) {
    m_IsAnimating = true;

    final float centerX = m_FrontFace.getWidth() / 2.0f;
    final float centerY = m_FrontFace.getHeight() / 2.0f;
    
    final FlipAnimation rotation = new FlipAnimation(start, end, centerX, centerY, ((m_Direction==DIRECTION_VERTICAL)?false:true));
    rotation.setDuration(m_HalfAnimationDuration);
    rotation.setFillAfter(true);
    
    if (m_InterpolatorType==INTERPOLATOR_LINEAR)
      rotation.setInterpolator(new LinearInterpolator());
    else
      rotation.setInterpolator(new AccelerateInterpolator());
    
    rotation.setAnimationListener(new HalfWayListener(m_IsFrontFacing));
    
    if (m_IsFrontFacing)
      m_FrontFace.startAnimation(rotation);
    else
      m_BackFace.startAnimation(rotation);
  }
	
  private void runSecondAnimation(boolean frontFace) {
    FlipAnimation rotation;
    final float centerX = m_FrontFace.getWidth() / 2.0f;
    final float centerY = m_FrontFace.getHeight() / 2.0f;
    
    if (frontFace) {
      m_FrontFace.setVisibility(View.GONE);
      m_BackFace.setVisibility(View.VISIBLE);
      m_BackFace.requestFocus();
      rotation = new FlipAnimation(-90, 0, centerX, centerY, 
                                    ((m_Direction==DIRECTION_VERTICAL) ? false : true));
    }
    else {
      m_BackFace.setVisibility(View.GONE);
      m_FrontFace.setVisibility(View.VISIBLE);
      m_FrontFace.requestFocus();
      rotation = new FlipAnimation(90, 0, centerX, centerY,
                                    ((m_Direction==DIRECTION_VERTICAL) ? false : true));
    }
    
    rotation.setDuration(m_HalfAnimationDuration);
    rotation.setFillAfter(true);
    
    if (m_InterpolatorType==INTERPOLATOR_LINEAR)
      rotation.setInterpolator(new LinearInterpolator());
    else
      rotation.setInterpolator(new DecelerateInterpolator());
    
    rotation.setAnimationListener(new EndListener());
    
    if (frontFace)
      m_BackFace.startAnimation(rotation);
    else
      m_FrontFace.startAnimation(rotation);
  }
  
  private final class HalfWayListener implements  Animation.AnimationListener {
    private boolean m_FrontFace;
    
    public HalfWayListener(boolean frontFace) {
      m_FrontFace = frontFace;
    }
    
    public void onAnimationStart(Animation animation) {}
    
    public void onAnimationEnd(Animation animation) {
      runSecondAnimation(m_FrontFace);
    }
    
    public void onAnimationRepeat(Animation animation) {}
  }
	
  private final class EndListener implements  Animation.AnimationListener {
		
    public void onAnimationStart(Animation animation) {}
    
    public void onAnimationEnd(Animation animation) {
      m_IsAnimating = false;
    }
    
    public void onAnimationRepeat(Animation animation) {}
  }
}
