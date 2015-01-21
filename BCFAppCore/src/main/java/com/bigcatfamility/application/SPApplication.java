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
package com.bigcatfamility.application;

import android.app.Application;

public class SPApplication extends Application {

	private static SPApplication m_instance;

	@Override
	public void onCreate() {
		super.onCreate();
		m_instance = this;
	}

	public static SPApplication getInstance() {
		return m_instance;
	}

}
