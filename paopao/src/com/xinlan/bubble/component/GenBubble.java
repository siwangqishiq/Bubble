package com.xinlan.bubble.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.xinlan.utils.Common;
import com.xinlan.view.MainView;

public class GenBubble {
	public static final int STATUS_WAIT=0;
	public static final int STATUS_RELOAD=1;
	public static final int STATUS_READY=2;
	public static final int STATUS_FIRING=3;
	public static final int STATUS_CANLOAD=4;
	
	private float x,y;
	//private float bubble_x,bubble_y;
	private float bubbleGenSpeed=0.4f;
	private int status;
	
	private Bubble mBubble;
	private Paint mPaint;
	public static int[] colors= {Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,Color.RED,Color.YELLOW};
	private int waitDelay=40;
	
	public GenBubble(){
		status=STATUS_WAIT;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		x=MainView.screenW/2;
		y=-Bubble.RADIUS;
	}
	
	public void genOneBubble(){
		
	}
	
	public void draw(Canvas canvas){
		mPaint.setColor(mBubble.getColor());
		canvas.drawCircle(mBubble.x, mBubble.y, mBubble.radius, mPaint);
	}
	
	public void logic(){
		switch(status){
		case STATUS_WAIT:
			waitDelay--;
			if(waitDelay==0){
				status=STATUS_CANLOAD;
			}
			break;	
		case STATUS_CANLOAD:
			mBubble = new Bubble(x,y,genColor());
			status=STATUS_RELOAD;
			break;	
		case STATUS_RELOAD:
			mBubble.y+=bubbleGenSpeed;
			if(mBubble.y>=mBubble.radius){
				mBubble.y=mBubble.radius;
				status=STATUS_READY;
			}
			break;
		case STATUS_READY:
			break;
		}//end switch
	}
	
	private int genColor(){
		return colors[Common.genRand(0, colors.length)];
	}
}//end class
