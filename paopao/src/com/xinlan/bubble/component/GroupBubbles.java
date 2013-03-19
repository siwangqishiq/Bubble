package com.xinlan.bubble.component;

import java.util.LinkedList;

import com.xinlan.utils.Common;
import com.xinlan.view.MainView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * 
 * @author Administrator
 * 
 */
@SuppressLint({ "FloatMath", "FloatMath" })
public class GroupBubbles {
	private MainView context;
	private float center_x, center_y;
	private LinkedList<Bubble> root;
	private Bubble tempBubble;// 临时泡泡
	private Paint paint;
	private GenBubble genBubble;
	private double dRotate = -0.01;
	Matrix rotateMatrix = new Matrix();

	public GroupBubbles(MainView context) {
		this.context = context;
		genBubble = context.getGenBubble();
		root = new LinkedList<Bubble>();
		rotateMatrix.reset();
		paint = new Paint();
		paint.setAntiAlias(true);
		center_x = MainView.screenW / 2;
		center_y = MainView.screenH / 2;
	}

	public void init() {
		Bubble topBubble = new Bubble(center_x, center_y, GenBubble.genColor());
		root.add(topBubble);
	}

	private void genSurroundBubble(Bubble bubble) {

	}

	public void setTempBubble(Bubble bubble) {
		this.tempBubble = bubble;
	}

	public void draw(Canvas canvas) {
		if (tempBubble != null) {
			tempBubble.draw(canvas);
		}// end if
		for (Bubble bubble : root) {
			bubble.draw(canvas);
		}// end for
	}

	public void logic() {
		if (tempBubble != null) {
			System.out.println(tempBubble.dy);
			tempBubble.x += tempBubble.dx;
			tempBubble.y += tempBubble.dy;
			if (tempBubble.x <= -tempBubble.radius// 越过边界
					|| tempBubble.x > MainView.screenW + tempBubble.radius
					|| tempBubble.y <= -tempBubble.radius
					|| tempBubble.y > MainView.screenH + tempBubble.radius) {
				context.getGenBubble().status = GenBubble.STATUS_CANLOAD;
				tempBubble = null;
				System.gc();
			} else {
				// 在界面内 做碰撞检测
				for (Bubble bubble : root) {
					if (isBubbleHit(tempBubble, bubble)) {
						tempBubble.dx = 0;
						tempBubble.dy = 0;
						root.add(tempBubble);
						tempBubble = null;
						context.getGenBubble().status = GenBubble.STATUS_CANLOAD;
						System.gc();
						break;
					}
				}// end for
			}
		}// end if
		
		// group do something
		for (Bubble bubble : root) {
			rotateItem(bubble);
		}// end for
	}

	/**
	 * p2.x = p0.x + (p1.x-p0.x) * cos (a) - (p1.y-p0.y) * sin(a) 
	 * p2.y = p0.y + (p1.y-p0.y) * cos(a) + (p1.x-p0.x) * sin(a);
	 * 
	 * @param bubble
	 * @param centerX
	 * @param centerY
	 */
	private void rotateItem(Bubble bubble) {
		float x = bubble.x;
		float y = bubble.y;
		float sinA = (float) Math.sin(dRotate);
		float cosA = (float) Math.cos(dRotate);
		float newX = center_x + (x - center_x) * cosA - (y - center_y) * sinA;
		float newY = center_y + (y - center_y) * cosA + (x - center_x) * sinA;
		bubble.x = newX;
		bubble.y = newY;
	}

	private boolean isBubbleHit(final Bubble moveBubble, final Bubble bubble) {	
		return Common.isCircleHit(moveBubble.x, moveBubble.y,
				moveBubble.radius, bubble.x, bubble.y, bubble.radius);
	}

	public void addBubble(Bubble bubble) {
		root.add(bubble);
	}
}// end class
