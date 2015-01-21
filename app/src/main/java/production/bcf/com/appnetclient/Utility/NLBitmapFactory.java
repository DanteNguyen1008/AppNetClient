package production.bcf.com.appnetclient.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.Log;

import com.bigcatfamily.networkhandler.constant.IConstant;

import java.util.Arrays;

public class NLBitmapFactory {
	private static NLBitmapFactory m_instance;

	public NLBitmapFactory() {
	}

	public static NLBitmapFactory GetInstance() {
		if (m_instance == null) {
			m_instance = new NLBitmapFactory();
		}
		return m_instance;
	}

	public Bitmap GetCroppedBitmap(Bitmap bitmap, int radius) {
		return GetCroppedBitmap(bitmap, radius, 0, 0, IConstant.LOGIN_SCREEN_BACKGROUND_COLOR);
	}

	public Bitmap GetFillBackGroundBitmap(Bitmap bitmap, int dx, int dy, int color) {
		if (bitmap == null || bitmap.getConfig() == null)
			return bitmap;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
		Canvas cs = new Canvas(newBitmap);
		cs.drawColor(Color.BLACK);
		cs.drawBitmap(bitmap, 0, 0, null);

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		paint.setColor(color);
		final Rect src_rect = new Rect(0, 0, width, height);
		int left = dx;
		int top = dy;
		int right = left + width;
		int bottom = dy + height;
		final Rect dest_rect = new Rect(left, top, right, bottom);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		canvas.drawBitmap(newBitmap, src_rect, dest_rect, paint);
		return output;
	}

	public Bitmap GetCroppedBitmap(Bitmap bitmap, int radius, int dx, int dy, int color) {
		if (bitmap == null)
			return bitmap;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
		Canvas cs = new Canvas(newBitmap);
		cs.drawColor(Color.BLACK);
		cs.drawBitmap(bitmap, 0, 0, null);

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		paint.setColor(color);
		final Rect src_rect = new Rect(0, 0, width, height);
		int left = dx;
		int top = dy;
		int right = left + width;
		int bottom = dy + height;
		final Rect dest_rect = new Rect(left, top, right, bottom);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		canvas.drawCircle(width >> 1, height >> 1, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(newBitmap, src_rect, dest_rect, paint);
		return output;
	}

	public Drawable GetCroppedDrawable(Context context, Drawable drawable) {
		if (drawable == null)
			return null;
		Bitmap resultBitmap = GetCroppedBitmap(((BitmapDrawable) drawable).getBitmap(),
		        IConstant.LOGIN_SCREEN_BACKGROUND_COLOR);
		return new BitmapDrawable(context.getResources(), resultBitmap);
	}

	public Bitmap GetCircleBitmap(int rec_width, int rec_height, int color) {
		int dradius = rec_width < rec_height ? rec_width : rec_height;
		Bitmap bitmap = Bitmap.createBitmap(rec_width, rec_height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0x00000000);
		canvas.drawRect(new Rect(0, 0, rec_width, rec_height), paint);
		paint.setColor(color);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawCircle(rec_width >> 1, rec_height >> 1, dradius >> 1, paint);
		return bitmap;
	}

	@SuppressLint("NewApi")
	public Bitmap SwapBitmapColor(Bitmap bitmap, int oldColor, int newColor) {
		if (bitmap == null)
			return null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int[] pixels_temp = Arrays.copyOf(pixels, pixels.length);
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] == oldColor) {
				pixels[i] = newColor;
			}
		}
		for (int i = 0; i < pixels_temp.length; i++) {
			if (pixels_temp[i] == newColor) {
				pixels[i] = oldColor;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	public Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels, int color) {
		Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(result);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return result;
	}

	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	public Bitmap drawShadow(Bitmap bitmap, int leftRightThk, int bottomThk, int padTop) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int newW = w - (leftRightThk * 2);
		int newH = h - (bottomThk + padTop);

		Config conf = Config.ARGB_8888;
		Bitmap bmp = Bitmap.createBitmap(w, h, conf);
		Bitmap sbmp = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Canvas c = new Canvas(bmp);

		// Left
		int leftMargin = (leftRightThk + 7) / 2;
		Shader lshader = new LinearGradient(0, 0, leftMargin, 0, Color.TRANSPARENT, Color.BLACK, TileMode.CLAMP);
		paint.setShader(lshader);
		c.drawRect(0, padTop, leftMargin, newH, paint);

		// Right
		Shader rshader = new LinearGradient(w - leftMargin, 0, w, 0, Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
		paint.setShader(rshader);
		c.drawRect(newW, padTop, w, newH, paint);

		// Bottom
		Shader bshader = new LinearGradient(0, newH, 0, bitmap.getHeight(), Color.BLACK, Color.TRANSPARENT,
		        TileMode.CLAMP);
		paint.setShader(bshader);
		c.drawRect(leftMargin - 3, newH, newW + leftMargin + 3, bitmap.getHeight(), paint);
		c.drawBitmap(sbmp, leftRightThk, 0, null);

		return bmp;
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	public Drawable getResizeDrawable(Drawable drawable, float scaleDimen) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		int height = (int) (bd.getBitmap().getHeight() * scaleDimen);
		int width = (int) (bd.getBitmap().getWidth() * scaleDimen);

		Log.d("NLBitmap", "Scaled gift dimen " + width + "-" + height);

		ScaleDrawable scaledDrawable = new ScaleDrawable(drawable, 0, width, height);
		return scaledDrawable;
	}
}
