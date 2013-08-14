/**
 * 
 */
package com.redhat.reportengine.server.cache;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbdata.EngineSettingsTable;
import com.redhat.reportengine.server.dbmap.EngineSettings;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
public class ServerSettings {

	final private static Logger _logger = Logger.getLogger(ServerSettings.class);
	public static final String LOG4J_CONF_LOCATION = "log4j.conf.file";
	public static final String LOG4J_LOG_LOCATION = "log4j.log.file";
	
	public static final String KEY_TEST_SUITE_INACTIVE_TIME		= "TestSuiteInactiveTime";
	public static final String KEY_EMAIL_SERVER					= "EmailServer";
	public static final String KEY_SENDER_EMAIL					= "SenderEmail";
	public static final String KEY_EMAIL_SERVER_PORT			= "EmailServerPort";	
	public static final String KEY_ENGINE_UDP_PORT				= "EngineUDPPort";
	public static final String KEY_ENGINE_URL					= "EngineURL";
	public static final String KEY_ENGINE_CURRENT_WIDGET		= "EngineCurrentWidGet";
	public static final String KEY_ENGINE_AVAILABLE_WIDGETS		= "EngineAvailableWidGets";
	public static final String KEY_ENGINE_CURRENT_MENU_STYLE	= "EngineCurrentMenuStyle";
	public static final String KEY_ENGINE_AVAILABLE_MENU_STYLES	= "EngineAvailableMenuStyles";

	private static final String log4jLocation = "WEB-INF/classes/conf/log4j.properties";
	private static final String rePropertyLocation = "WEB-INF/classes/conf/report-engine.properties";
	private static final String log4jLogLocation = "WEB-INF/logs/ReportEngine.log";
	private static final String serverTempLocation = "WEB-INF/temp";
	private static String baseLocation = "";
	private static int serverUdpPort = 9011;
	
	private static long testSuiteInactiveTime = 60*60*2; //2 hours
	
	private static String screenShotFileLocation = "WEB-INF/temp";
	
	private static String emailServer = null;
	private static String emailFrom	  = null;
	private static String emailServerPort = null;
	
	private static String engineURL = null;
	private static String currentWidget = null;
	private static String availableWidgets = null;
	private static String currentMenuStyle = null;
	private static String availableMenuStyles = null;
	
			/**
			 * @return the Logger
			 */
			public static Logger getLogger() {
				return _logger;
			}
			/**
			 * @return the log4jlocation
			 */
			public static String getLog4jlocation() {
				return log4jLocation;
			}
			/**
			 * @return the repropertylocation
			 */
			public static String getRepropertylocation() {
				return rePropertyLocation;
			}
			/**
			 * @return the log4jloglocation
			 */
			public static String getLog4jloglocation() {
				return log4jLogLocation;
			}
			/**
			 * @return the servertemplocation
			 */
			public static String getServertemplocation() {
				return serverTempLocation;
			}
			public static String getBaseLocation() {
				return baseLocation;
			}
			public static void setBaseLocation(String baseLocation) {
				ServerSettings.baseLocation = baseLocation;
			}
			/**
			 * @return the serverUdpPort
			 */
			public static int getServerUdpPort() {
				return serverUdpPort;
			}
			/**
			 * @param serverRmiPort the serverUdpPort to set
			 */
			public static void setServerUdpPort(int serverUdpPort) {
				ServerSettings.serverUdpPort = serverUdpPort;
			}
			/**
			 * @return the screenShotFileLocation
			 */
			public static String getScreenShotFileLocation() {
				return screenShotFileLocation;
			}
			/**
			 * @param screenShotFileLocation the screenShotFileLocation to set
			 */
			public static void setScreenShotFileLocation(String screenShotFileLocation) {
				ServerSettings.screenShotFileLocation = screenShotFileLocation;
			}
			public static long getTestSuiteInactiveTime() {
				return testSuiteInactiveTime;
			}
			public static void setTestSuiteInactiveTime(long jobInactiveTime) {
				ServerSettings.testSuiteInactiveTime = jobInactiveTime;
			}
			/**
			 * @return the emailServer
			 */
			public static String getEmailServer() {
				return emailServer;
			}
			/**
			 * @param emailServer the emailServer to set
			 */
			public static void setEmailServer(String emailServer) {
				ServerSettings.emailServer = emailServer;
			}
			/**
			 * @return the emailFrom
			 */
			public static String getEmailFrom() {
				return emailFrom;
			}
			/**
			 * @param emailFrom the emailFrom to set
			 */
			public static void setEmailFrom(String emailFrom) {
				ServerSettings.emailFrom = emailFrom;
			}
			/**
			 * @return the emailServerPort
			 */
			public static String getEmailServerPort() {
				return emailServerPort;
			}
			/**
			 * @param emailServerPort the emailServerPort to set
			 */
			public static void setEmailServerPort(String emailServerPort) {
				ServerSettings.emailServerPort = emailServerPort;
			}
			
			private static String getDBValue(String key) throws SQLException{
				return new EngineSettingsTable().get(key).getValue();
			}
			
			public static void updateSystemSettingsFromDB() throws NumberFormatException, SQLException{
				setTestSuiteInactiveTime(Long.parseLong(getDBValue(KEY_TEST_SUITE_INACTIVE_TIME)));
				setEmailServer(getDBValue(KEY_EMAIL_SERVER));
				setEmailFrom(getDBValue(KEY_SENDER_EMAIL));
				setEmailServerPort(getDBValue(KEY_EMAIL_SERVER_PORT));
				setServerUdpPort(Integer.parseInt(getDBValue(KEY_ENGINE_UDP_PORT)));
				setEngineURL(getDBValue(KEY_ENGINE_URL));
				updateWidgetsSettingsFromDB();
			}
			
			public static void updateWidgetsSettingsFromDB() throws SQLException{
				setCurrentWidget(getDBValue(KEY_ENGINE_CURRENT_WIDGET));
				setAvailableWidgets(getDBValue(KEY_ENGINE_AVAILABLE_WIDGETS));
				setCurrentMenuStyle(getDBValue(KEY_ENGINE_CURRENT_MENU_STYLE));
				setAvailableMenuStyles(getDBValue(KEY_ENGINE_AVAILABLE_MENU_STYLES));
			}
			
			private static void updateSystemSettingsToDB(HttpServletRequest request, HttpSession session, String[] keys) throws SQLException{
				EngineSettings engineSettings = new EngineSettings();
				for(String key : keys){
					engineSettings.setKey(key);
					engineSettings.setValue((String)request.getParameter(key));
					new EngineSettingsTable().modify(engineSettings);
				}
			}
			
			public static void updateSystemSettings(HttpServletRequest request, HttpSession session) throws SQLException{
				String[] keys = {KEY_EMAIL_SERVER, KEY_EMAIL_SERVER_PORT, KEY_SENDER_EMAIL, KEY_TEST_SUITE_INACTIVE_TIME, KEY_ENGINE_UDP_PORT, KEY_ENGINE_URL};
				updateSystemSettingsToDB(request, session, keys);
				updateSystemSettingsFromDB();
			}
			
			
			public static void updateWidgetsSettings(HttpServletRequest request, HttpSession session) throws SQLException{
				String[] keys = {KEY_ENGINE_CURRENT_WIDGET, KEY_ENGINE_CURRENT_MENU_STYLE};
				updateSystemSettingsToDB(request, session, keys);
				updateWidgetsSettingsFromDB();
			}
			
			/**
			 * @return the engineURL
			 */
			public static String getEngineURL() {
				return engineURL;
			}
			/**
			 * @param engineURL the engineURL to set
			 */
			public static void setEngineURL(String engineURL) {
				ServerSettings.engineURL = engineURL;
			}
			/**
			 * @return the currentWidget
			 */
			public static String getCurrentWidget() {
				return currentWidget;
			}
			/**
			 * @param currentWidget the currentWidget to set
			 */
			public static void setCurrentWidget(String currentWidget) {
				ServerSettings.currentWidget = currentWidget;
			}
			/**
			 * @return the availableWidgets
			 */
			public static String getAvailableWidgets() {
				return availableWidgets;
			}
			/**
			 * @param availableWidgets the availableWidgets to set
			 */
			public static void setAvailableWidgets(String availableWidgets) {
				ServerSettings.availableWidgets = availableWidgets;
			}
			/**
			 * @return the currentMenuStyle
			 */
			public static String getCurrentMenuStyle() {
				return currentMenuStyle;
			}
			/**
			 * @param currentMenuStyle the currentMenuStyle to set
			 */
			public static void setCurrentMenuStyle(String currentMenuStyle) {
				ServerSettings.currentMenuStyle = currentMenuStyle;
			}
			/**
			 * @return the availableMenuStyles
			 */
			public static String getAvailableMenuStyles() {
				return availableMenuStyles;
			}
			/**
			 * @param availableMenuStyles the availableMenuStyles to set
			 */
			public static void setAvailableMenuStyles(String availableMenuStyles) {
				ServerSettings.availableMenuStyles = availableMenuStyles;
			}
}
