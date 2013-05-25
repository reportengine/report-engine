/**
 * 
 */
package com.redhat.reportengine.client;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 28, 2012
 */
public class LogHandler {
	
	protected static RemoteAPI remoteApi;
	protected static boolean isLoadedHandler = false;
	protected static JulReportEngineLogHandler reportEngineHandler = null;
	private static final Logger _logger = Logger.getLogger(LogHandler.class.getName());
	
	public static void julLoggerSetup() {
		// Create Logger
		Logger logger = Logger.getLogger("");
		//Logger logger = LogManager.getLogManager().getLogger("");
		
		if(!remoteApi.getLogLevel().equalsIgnoreCase("DEFAULT")){
			logger.setLevel(Level.parse(remoteApi.getLogLevel()));
		}
		
		if(isLoadedHandler){
			logger.removeHandler(reportEngineHandler);
			//reportEngineHandler = new JulReportEngineLogHandler(getRemoteApi());
			logger.addHandler(reportEngineHandler);	
			remoteApi.setLoggers(RemoteAPI.loggersToWarn, Level.WARNING);
			_logger.log(Level.INFO, "Report Engine Client: JUL Handler reloaded...");
		}else{
			reportEngineHandler = new JulReportEngineLogHandler(getRemoteApi());
			logger.addHandler(reportEngineHandler);
			remoteApi.setLoggers(RemoteAPI.loggersToWarn, Level.WARNING);
			_logger.log(Level.INFO, "Report Engine Client: JUL Handler added...");
			isLoadedHandler = true;
		}
		_logger.log(Level.INFO, "Report Engine Client: Log Level: "+logger.getLevel());
	}

	public static RemoteAPI getRemoteApi() {
		return remoteApi;
	}

	public static void setRemoteApi(RemoteAPI remoteApi) {
		LogHandler.remoteApi = remoteApi;
	}
	
	public static void initLogger(){
		if(remoteApi.getLoggerType().equalsIgnoreCase("JUL")){
			julLoggerSetup();
		}else{
			_logger.log(Level.WARNING, "Report Engine Client: Un-supported Logger Type: "+remoteApi.getLoggerType());
		}
	}
}
