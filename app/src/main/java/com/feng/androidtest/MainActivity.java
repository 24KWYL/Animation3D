package com.feng.androidtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.example.androidtest.R;

public class MainActivity extends Activity {
	private RelativeLayout mContentRl;
	private int centerX;
	private int centerY;
	private int depthZ =0; //Z轴深度
	private int duration = 500; //旋转时间
	private Rotate3dAnimation openAnimation;
	private Rotate3dAnimation closeAnimation;
	private float mPosX =0,mPosY=0,mCurPosX=0,mCurPosY=0;
	private boolean isFade=true;//是否是正面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentRl = (RelativeLayout) findViewById(R.id.rl_content);
		mContentRl.setOnTouchListener(scrollTouchListener);
    }

	private void initOpenAnim(final View view) {
    	openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, false);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
		final View fade=view.findViewById(R.id.fade);
		final View reverse=view.findViewById(R.id.reverse);
        openAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isFade=!isFade;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				fade.setVisibility(isFade?View.VISIBLE:View.GONE);
				reverse.setVisibility(!isFade?View.VISIBLE:View.GONE);
				fade.setOnClickListener(onClickListener);
				reverse.setOnClickListener(onClickListener);
				Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
				rotateAnimation.setDuration(duration);
				rotateAnimation.setFillAfter(true);
				rotateAnimation.setInterpolator(new DecelerateInterpolator());
				view.startAnimation(rotateAnimation);
			}
		});
	}
    
    private void initCloseAnim(final View view) {
    	closeAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, false);
		closeAnimation.setDuration(duration);
		closeAnimation.setFillAfter(true);
		closeAnimation.setInterpolator(new AccelerateInterpolator());
		final View fade=view.findViewById(R.id.fade);
		final View reverse=view.findViewById(R.id.reverse);
		closeAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isFade=!isFade;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				fade.setVisibility(isFade?View.VISIBLE:View.GONE);
				reverse.setVisibility(!isFade?View.VISIBLE:View.GONE);
				fade.setOnClickListener(onClickListener);
				reverse.setOnClickListener(onClickListener);
				Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, true);
				rotateAnimation.setDuration(duration);
				rotateAnimation.setFillAfter(true);
				rotateAnimation.setInterpolator(new DecelerateInterpolator());
				view.startAnimation(rotateAnimation);
			}
		});
	}

	private void initAnim(View view){
		centerX = (view.getRight()-view.getLeft())/2;
		centerY = (view.getBottom()-view.getTop())/2;
		initCloseAnim(view);
		initOpenAnim(view);
	}
	//View滑动监听器
	private View.OnTouchListener scrollTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			initAnim(v);
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mPosX = event.getX();
					mPosY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					mCurPosX = event.getX();
					mCurPosY = event.getY();

					break;
				case MotionEvent.ACTION_UP:
					if (mCurPosY - mPosY > 0
							&& (Math.abs(mCurPosY - mPosY) > 25)) {
						//向下滑動
						Log.i("move","bottom");
					} else if (mCurPosY - mPosY < 0
							&& (Math.abs(mCurPosY - mPosY) > 25)) {
						//向上滑动
						Log.i("move","top");
					}
					if (mCurPosX-mPosX>25) {
						mContentRl.startAnimation(openAnimation);
						//向右滑动
						Log.i("move","right");
					} else if(mCurPosX-mPosX<-25){
						mContentRl.startAnimation(closeAnimation);
					  	//向左滑动
						Log.i("move","left");
					}
					break;
			}
			return true;
		}
	};

	//点击监听
	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(v.getContext(),((TextView)v).getText()+"被点击", Toast.LENGTH_SHORT).show();
		}
	};
}
