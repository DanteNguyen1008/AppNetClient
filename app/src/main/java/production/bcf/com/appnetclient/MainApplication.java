/*
 * TLApplication
 *
 * TalkLife application
 *
 * @author quaych@nexlesoft.com
 * @date 2011/08/06
 * @lastChangedRevision:
 * @lastChangedDate: 2011/08/06
 */
package production.bcf.com.appnetclient;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MainApplication extends Application {

	private static MainApplication m_instance;

	@Override
	public void onCreate() {
		super.onCreate();
		m_instance = this;
		initImageLoader(getApplicationContext());
	}

	public static MainApplication getInstance() {
		return m_instance;
	}

	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		        .memoryCacheExtraOptions(500, 500)
		        // default = device screen dimensions
		        .diskCacheExtraOptions(480, 320, null)
		        .threadPoolSize(3)
		        // default
		        .threadPriority(Thread.NORM_PRIORITY - 1)
		        // default
		        .tasksProcessingOrder(QueueProcessingType.FIFO)
		        // default
		        .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		        .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
		        // default
		        .diskCache(new UnlimitedDiscCache(cacheDir))
		        // default
		        .diskCacheSize(20 * 1024 * 1024).diskCacheFileCount(100)
		        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
		        .imageDownloader(new BaseImageDownloader(context)) // default
		        .imageDecoder(new BaseImageDecoder(false)) // default
		        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
		        .build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
