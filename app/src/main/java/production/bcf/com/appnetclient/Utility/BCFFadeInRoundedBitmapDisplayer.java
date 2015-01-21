package production.bcf.com.appnetclient.Utility;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class BCFFadeInRoundedBitmapDisplayer extends BCFRoundedBitmapDisplayer {

	private final int durationMillis;
	private final boolean animateFromNetwork;
	private final boolean animateFromDisc;
	private final boolean animateFromMemory;

	/**
	 * @param durationMillis
	 *            Duration of "fade-in" animation (in milliseconds)
	 */
	public BCFFadeInRoundedBitmapDisplayer(int durationMillis, int cornerRadiusPixels) {
		this(durationMillis, true, true, true, cornerRadiusPixels);
	}

	/**
	 * @param durationMillis
	 *            Duration of "fade-in" animation (in milliseconds)
	 * @param animateFromNetwork
	 *            Whether animation should be played if image is loaded from
	 *            network
	 * @param animateFromDisc
	 *            Whether animation should be played if image is loaded from
	 *            disc cache
	 * @param animateFromMemory
	 *            Whether animation should be played if image is loaded from
	 *            memory cache
	 */
	public BCFFadeInRoundedBitmapDisplayer(int durationMillis, boolean animateFromNetwork, boolean animateFromDisc,
	        boolean animateFromMemory, int cornerRadiusPixels) {
		super(cornerRadiusPixels);
		this.durationMillis = durationMillis;
		this.animateFromNetwork = animateFromNetwork;
		this.animateFromDisc = animateFromDisc;
		this.animateFromMemory = animateFromMemory;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		super.display(bitmap, imageAware, loadedFrom);
		// imageAware.setImageBitmap(bitmap);

		if ((animateFromNetwork && loadedFrom == LoadedFrom.NETWORK)
		        || (animateFromDisc && loadedFrom == LoadedFrom.DISC_CACHE)
		        || (animateFromMemory && loadedFrom == LoadedFrom.MEMORY_CACHE)) {
			animate(imageAware.getWrappedView(), durationMillis);
		}
	}

	/**
	 * Animates {@link android.widget.ImageView} with "fade-in" effect
	 * 
	 * @param imageView
	 *            {@link android.widget.ImageView} which display image in
	 * @param durationMillis
	 *            The length of the animation in milliseconds
	 */
	public static void animate(View imageView, int durationMillis) {
		if (imageView != null) {
			AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
			fadeImage.setDuration(durationMillis);
			fadeImage.setInterpolator(new DecelerateInterpolator());
			imageView.startAnimation(fadeImage);
		}
	}
}
