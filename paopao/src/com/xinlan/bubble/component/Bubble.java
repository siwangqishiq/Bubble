package com.xinlan.bubble.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bubble {
	public static Bitmap bitmap;
	public static float RADIUS;
	public float radius;
	public float x,y;
	public float dx,dy;
	
	private int color;
	private Paint paint;
	
	public Bubble(float x,float y,int color){
		radius = RADIUS;
		this.x=x;
		this.y=y;
		this.color=color;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
	}
	
	public Bubble(float x,float y,int color,float dx,float dy){
		radius = RADIUS;
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
		this.color=color;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
	}
	
	public int getColor(){
		return color;
	}
	
	public void draw(Canvas canvas){
		canvas.drawCircle(x, y, radius, paint);
	}
}//end class
