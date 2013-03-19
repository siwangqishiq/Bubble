package com.xinlan.bubble.component;

import java.util.LinkedList;

import com.xinlan.utils.Common;
import com.xinlan.view.MainView;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 
 * @author Administrator
 * 
 */
public class GroupBubbles {
	private MainView context;
	private float center_x, center_y;
	private LinkedList<Bubble> root;
	private Bubble tempBubble;// 临时泡泡
	private Paint paint;
	private GenBubble genBubble;

	public GroupBubbles(MainView context) {
		this.context = context;
		genBubble = context.getGenBubble();
		root = new LinkedList<Bubble>();
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
	}

	private boolean isBubbleHit(final Bubble moveBubble, final Bubble bubble) {
		return Common.isCircleHit(moveBubble.x, moveBubble.y,
				moveBubble.radius, bubble.x, bubble.y, bubble.radius);
	}

	public void addBubble(Bubble bubble) {
		root.add(bubble);
	}
}// end class
