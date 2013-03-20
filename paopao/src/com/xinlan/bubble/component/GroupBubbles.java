package com.xinlan.bubble.component;

import java.util.LinkedList;

import com.xinlan.utils.Common;
import com.xinlan.utils.VectorUtil;
import com.xinlan.view.MainView;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * 
 * @author Panyi
 * 
 */
public class GroupBubbles {
	public static final int ROW_NUM = 7;
	public static final int COL_NUM = 7;

	private MainView context;
	private float center_x, center_y;
	private LinkedList<Bubble> root;
	private LinkedList<Bubble> hitList;
	private Bubble tempBubble;// 临时泡泡
	private Paint paint;
	private GenBubble genBubble;

	private double dRotate = 0.0f;
	private float rotateSpeed = 0.0f;
	private float descdRotate = 0.0005f;

	public GroupBubbles(MainView context) {
		this.context = context;
		genBubble = context.getGenBubble();
		root = new LinkedList<Bubble>();
		hitList = new LinkedList<Bubble>();
		paint = new Paint();
		paint.setAntiAlias(true);
		center_x = MainView.screenW / 2;
		center_y = MainView.screenH / 2;
	}

	public void init() {
		float startX = center_x - ROW_NUM * Bubble.RADIUS + Bubble.RADIUS;
		float startY = center_y - COL_NUM * Bubble.RADIUS + Bubble.RADIUS;
		for (int i = 0; i < COL_NUM; i++) {
			float cursor_y = startY + i * 2 * Bubble.RADIUS;
			for (int j = 0; j < ROW_NUM; j++) {
				Bubble bubble = new Bubble(startX + j * 2 * Bubble.RADIUS,
						cursor_y, GenBubble.genColor());
				root.add(bubble);
			}// end for j
		}// end for i
		rotateAllWithAngle(-Math.PI / 4);
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
					if (isBubbleHit(tempBubble, bubble)) {// 碰撞
						hitList.add(bubble);
					}
				}// end for

				if (hitList.size() >= 1) {
					for (Bubble bubble : hitList) {
						hitRelocation(tempBubble, bubble);
					}
					hitRotate(tempBubble);
					tempBubble.dx = 0;
					tempBubble.dy = 0;
					root.add(tempBubble);
					tempBubble = null;
					context.getGenBubble().status = GenBubble.STATUS_CANLOAD;
					hitList.remove();
					hitList.clear();
					System.gc();
					System.out.println(rotateSpeed);
				}// end if

			}
		}// end if

		if (Math.abs(rotateSpeed) > 0.000001f) {
			for (Bubble bubble : root) {
				rotateItem(bubble, rotateSpeed);
			}// end for
			int flag = -1 * Common.getFlag(rotateSpeed);
			rotateSpeed += flag * descdRotate;
		} else {
			rotateSpeed = 0.0f;
		}

	}

	private void hitRotate(Bubble bubble) {
		float distance = Common
				.distance(bubble.x, bubble.y, center_x, center_y);
		float force = VectorUtil.calCosTwoVector(bubble.dx, bubble.dy,
				center_x, 0);
		rotateSpeed = distance * force / 2000;
	}

	/**
	 * 恢复形状
	 * 
	 * @param bubble
	 * @param hitBubble
	 */
	private void hitRelocation(Bubble bubble, Bubble hitBubble) {
		float back_dx = -bubble.dx;
		float back_dy = -bubble.dy;
		float deltaX = hitBubble.x - bubble.x;
		float deltaY = hitBubble.y - bubble.y;
		float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		if (bubble.radius + hitBubble.radius > distance) {
			float len = Math.abs(bubble.radius + hitBubble.radius - distance);
			float lens = (float) Math.sqrt(back_dx * back_dx + back_dy
					* back_dy);
			bubble.x = bubble.x + len * (back_dx / lens);
			bubble.y = bubble.y + len * (back_dy / lens);
		}
	}

	/**
	 * p2.x = p0.x + (p1.x-p0.x) * cos (a) - (p1.y-p0.y) * sin(a) p2.y = p0.y
	 * +(p1.y-p0.y) * cos(a) + (p1.x-p0.x) * sin(a);
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

	private void rotateItem(Bubble bubble, double angle) {
		float x = bubble.x;
		float y = bubble.y;
		float sinA = (float) Math.sin(angle);
		float cosA = (float) Math.cos(angle);
		float newX = center_x + (x - center_x) * cosA - (y - center_y) * sinA;
		float newY = center_y + (y - center_y) * cosA + (x - center_x) * sinA;
		bubble.x = newX;
		bubble.y = newY;
	}

	private void rotateAllWithAngle(double angle) {
		for (Bubble bubble : root) {
			rotateItem(bubble, angle);
		}// end for
	}

	private boolean isBubbleHit(final Bubble moveBubble, final Bubble bubble) {
		return Common.isCircleHit(moveBubble.x, moveBubble.y,
				moveBubble.radius, bubble.x, bubble.y, bubble.radius);
	}

	public void addBubble(Bubble bubble) {
		root.add(bubble);
	}
}// end class
