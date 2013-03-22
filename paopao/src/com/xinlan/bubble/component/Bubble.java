package com.xinlan.bubble.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Bubble {
	public Bitmap bitmap;
	public static float RADIUS;
	private BitmapDataContent dataContent;
	public float radius;
	public float x, y;
	public float dx, dy;

	private int color;
	private Paint paint;
	private Rect srcRect;
	private RectF dstRect;
	private float width, height;

	public Bubble(BitmapDataContent data, float x, float y, int color,
			float dx, float dy) {
		radius = RADIUS;
		dataContent = data;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.color = color;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		loadBitmap();
		srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		dstRect = new RectF();
		width = 2 * radius;
		height = 2 * radius;
	}

	public Bubble(BitmapDataContent data, float x, float y, int color) {
		this(data, x, y, color, 0, 0);
	}

	public int getColor() {
		return color;
	}

	private void loadBitmap() {
		if (bitmap == null) {
			bitmap = dataContent.getBitmapByColor(color);
		}
	}

	public void draw(Canvas canvas) {
		// canvas.drawCircle(x, y, radius, paint);
		dstRect.left = x - radius;
		dstRect.top = y - radius;
		dstRect.right = dstRect.left + width;
		dstRect.bottom = dstRect.top + height;
		canvas.drawBitmap(bitmap, srcRect, dstRect, null);
		// canvas.drawBitmap(bitmap, x, y, null);
	}
}// end class
