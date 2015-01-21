/*
 * CLog
 *
 * This class is used for print out log base on it's tag. Depend on flag ENABLE_LOG is true/false to print out log. Switch off ENABLE_LOG in release build
 *
 * @author: 
 * @date:
 * @lastChangedRevision: 1.0
 * @lastChangedDate: 2011/08/12
 */
package com.bigcatfamility.application.contranst;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.os.Debug;
import android.util.Log;


@SuppressWarnings("unused")
public class CLog implements Defines {
	public static void db(String msg) {
		if (ENABLE_LOG_DB) {
			// Log.i( DbManager.tag, msg );
		}
	}

	public static void d(String tag, String msg) {
		if (ENABLE_LOG) {
			Log.d(tag, msg);
		}
	}

	public static int d(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.d(tag, msg, tr);
		}

		return 0;
	}

	public static int e(String tag, String msg) {
		if (ENABLE_LOG) {
			return Log.e(tag, msg);
		}

		return 0;
	}

	public static int e(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.e(tag, msg, tr);
		}

		return 0;
	}

	public static String getStackTraceString(Throwable tr) {
		if (ENABLE_LOG) {
			return Log.getStackTraceString(tr);
		}

		return new String("");
	}

	public static int i(String tag, String msg) {
		if (ENABLE_LOG) {
			return Log.i(tag, msg);
		}

		return 0;
	}

	public static int i(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.i(tag, msg, tr);
		}

		return 0;
	}

	public static boolean isLoggable(String tag, int level) {
		if (ENABLE_LOG) {
			return Log.isLoggable(tag, level);
		}

		return false;
	}

	public static int println(int priority, String tag, String msg) {
		if (ENABLE_LOG) {
			return Log.println(priority, tag, msg);
		}

		return 0;
	}

	public static int v(String tag, String msg) {
		if (ENABLE_LOG) {
			return Log.v(tag, msg);
		}

		return 0;
	}

	public static int v(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.v(tag, msg, tr);
		}

		return 0;
	}

	public static int w(String tag, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.w(tag, tr);
		}

		return 0;
	}

	public static int w(String tag, String msg, Throwable tr) {
		if (ENABLE_LOG) {
			return Log.w(tag, msg, tr);
		}

		return 0;
	}

	public static int w(String tag, String msg) {
		if (ENABLE_LOG) {
			return Log.w(tag, msg);
		}

		return 0;
	}

	@SuppressLint("UseValueOf")
	public static void logHeap(String prefix, Class<?> clazz) {
		if (ENABLE_LOG && ENABLE_MEMORY_LOG) {

			Double allocated = new Double(Debug.getNativeHeapAllocatedSize()) / new Double((1048576));
			Double available = new Double(Debug.getNativeHeapSize() / 1048576.0);
			Double free = new Double(Debug.getNativeHeapFreeSize() / 1048576.0);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);

			Log.w("logHeap",
					prefix + " allocated " + df.format(allocated) + "MB in ["
							+ clazz.getName().replaceAll("fr.playsoft.assurland.", "") + "]");
			/*
			 * Log.w("logHeap", prefix + " debug.heap native: allocated " +
			 * df.format(allocated) + "MB of " + df.format(available) + "MB (" +
			 * df.format(free) + "MB free) in [" +
			 * clazz.getName().replaceAll("fr.playsoft.assurland.","") + "]");
			 */
		}
	}

	public static void printStackTraces(Exception e) {
		if (Defines.ENABLE_PRINT_STACK_TRACES) {
			e.printStackTrace();
		}
	}
}
