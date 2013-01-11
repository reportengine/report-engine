/**
 * 
 */
package com.redhat.reportengine.server.gui;

import com.redhat.reportengine.server.ServerSettings;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 21, 2012
 */
public class Settings {
	private static String appTitle = "Report Engine";
	private static String appVersion;
	private static String appBuildId;
	public static final String GUI_DATE_TIME = "MMM dd HH:mm:ss z yyyy";
	public static final String GUI_LOG_DATE_TIME = "MMM dd, HH:mm:ss.S z yyyy";
	public static final String GUI_TIME = "HH:mm:ss";
	
	/**
	 * @return the appTitle
	 */
	public static String getAppTitle() {
		return appTitle;
	}
	/**
	 * @param appTitle the appTitle to set
	 */
	public static void setAppTitle(String appTitle) {
		Settings.appTitle = appTitle;
	}
	/**
	 * @return the appVersion
	 */
	public static String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public static void setAppVersion(String appVersion) {
		Settings.appVersion = appVersion;
	}
	/**
	 * @return the appBuildId
	 */
	public static String getAppBuildId() {
		return appBuildId;
	}
	/**
	 * @param appBuildId the appBuildId to set
	 */
	public static void setAppBuildId(String appBuildId) {
		Settings.appBuildId = appBuildId;
	}
	/**
	 * @return the appWedgetName
	 */
	public static String getAppWidgetsName() {
		return ServerSettings.getCurrentWidget();
	}
	/**
	 * @return the menuTheme
	 */
	public static String getMenuSkin() {
		return ServerSettings.getCurrentMenuStyle();
	}

}