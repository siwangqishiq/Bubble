package com.xinlan.bubble.component;

public class Bubble {
	public static float RADIUS;
	public float radius;
	public float x,y;
	private int color;
	
	public Bubble(float x,float y,int color){
		radius = RADIUS;
		this.x=x;
		this.y=y;
		this.color=color;
	}
	
	public int getColor(){
		return color;
	}
}//end class
