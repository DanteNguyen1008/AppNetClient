/**
 * Methods.java
 * 
 * Purpose              : List all methods
 * 
 * Optional info        : 
 *
 * @author              : Hoang Viet 
 * 
 * @date                : Apr 9, 2012
 * 
 * @lastChangedRevision : 
 *
 * @lastChangedDate     :
 *
 */
package com.bigcatfamily.networkhandler.db;

import java.io.Serializable;

import android.content.Intent;

/**
 * Methods.java
 * 
 * <br>
 * <b>Purpose</b> :
 * 
 * <br>
 * <b>Optional info</b> :
 * 
 * <br>
 * <b>author</b> :
 * 
 * <br>
 * <b>date</b> : Jul 23, 2013
 * 
 * <br>
 * <b>lastChangedRevision</b> :
 * 
 * <br>
 * <b>lastChangedDate</b> :
 * 
 */
public class Methods implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4844806149101497221L;

	public enum eMethods implements Serializable {
		test;
		private static final String name = Methods.class.getName();

		public void attachTo(Intent intent) {
			intent.putExtra(name, ordinal());
		}

		public static eMethods detachFrom(Intent intent) {
			if (!intent.hasExtra(name))
				throw new IllegalStateException();
			return values()[intent.getIntExtra(name, -1)];
		}
	}
}
