package com.feng.androidtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidtest.R;

public class MainActivity extends Activity {
	private RelativeLayout mContentRl;
	private TextView mLogoIv;
	private TextView mDescTv;
	private Button mOpenBtn;
	
	private int centerX;
	private int centerY;
	private int depthZ =0;
	private int duration = 500;
	private Rotate3dAnimation openAnimation;
	private Rotate3dAnimation closeAnimation;
	
	private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContentRl = (RelativeLayout) findViewById(R.id.rl_content);
        mLogoIv = (TextView) findViewById(R.id.iv_logo);
        mDescTv = (TextView) findViewById(R.id.tv_desc);
        mOpenBtn = (Button) findViewById(R.id.btn_open);
        
    }
    
    private void initOpenAnim() {
    	openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, false);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mLogoIv.setVisibility(View.GONE);
				mDescTv.setVisibility(View.VISIBLE);
				
				Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
				rotateAnimation.setDuration(duration);
				rotateAnimation.setFillAfter(true);
				rotateAnimation.setInterpolator(new DecelerateInterpolator());
				mContentRl.startAnimation(rotateAnimation);
			}
		});
	}
    
    private void initCloseAnim() {
    	closeAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, false);
		closeAnimation.setDuration(duration);
		closeAnimation.setFillAfter(true);
		closeAnimation.setInterpolator(new AccelerateInterpolator());
		closeAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mLogoIv.setVisibility(View.VISIBLE);
				mDescTv.setVisibility(View.GONE);
				
				Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, true);
				rotateAnimation.setDuration(duration);
				rotateAnimation.setFillAfter(true);
				rotateAnimation.setInterpolator(new DecelerateInterpolator());
				mContentRl.startAnimation(rotateAnimation);
			}
		});
	}
    
    public void onClickView(View v) {
    	centerX = (mContentRl.getRight()-mContentRl.getLeft())/2;
     	centerY = (mContentRl.getBottom()-mContentRl.getTop())/2;
     	if (openAnimation == null) {
			initOpenAnim();
			initCloseAnim();
		}
     	
    	if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
			return;
		}
    	if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) {
    		return;
    	}
    	
    	if (isOpen) {
    		mContentRl.startAnimation(closeAnimation);
			
		}else {
			
			mContentRl.startAnimation(openAnimation);
		}
    	
    	isOpen = !isOpen;
    	mOpenBtn.setText(isOpen ?"正面":"反面");
	}

	//获取屏幕宽度
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager)context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	//获取屏幕高度
	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager)context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}
}
