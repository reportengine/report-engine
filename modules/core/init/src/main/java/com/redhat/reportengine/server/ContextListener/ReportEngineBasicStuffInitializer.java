package com.redhat.reportengine.server.ContextListener;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.redhat.reportengine.server.cache.ServerSettings;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 29, 2013
 */
public class ReportEngineBasicStuffInitializer implements ServletContextListener{

	/**
	 * Default name for home dir.
	 */
	public static final String DEFAULT_CONFIG = "${user.home}/.java/reportengine";
	public static final String REPORTENGINE_CONF_LOCATION = "conf/reportengine.properties";

	private static Properties reportEngineProperies;

	/**
	 * Name of context-param in the web.xml which contains the reportengine Home
	 * directory. 
	 */
	public static final String REPORTENGINE_HOME_DIR = "reportengine.home.dir";

	/**
	 * Logger object
	 */
	static Logger _logger =  Logger.getLogger(ReportEngineBasicStuffInitializer.class);	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// exit if ReportEngine home system property is defined.
		if(System.getProperty(REPORTENGINE_HOME_DIR) == null){
			// get value from web.xml
			String descriptorValue = sce.getServletContext().getInitParameter(REPORTENGINE_HOME_DIR);

			// use default value if undefined 
			if( null == descriptorValue ) {    	   
				descriptorValue = DEFAULT_CONFIG;
				// do variable substitution of system properties 
				String[] matches = StringUtils.substringsBetween(descriptorValue, "${", "}");
				System.out.println("Value: "+descriptorValue);

				// iterate over matches
				for (String match: matches) {

					// get system property
					String propertyValue = System.getProperty(match); 

					// replace value 
					String searchString = "${" + match + "}";
					descriptorValue = StringUtils.replace(descriptorValue, searchString, propertyValue);
				}
			}
			if(!descriptorValue.endsWith("/")){
				descriptorValue = descriptorValue+"/";
			}
			// set ReportEngine home system property
			System.setProperty(REPORTENGINE_HOME_DIR, descriptorValue); 	
		}
		reportEngineProperies = new Properties();
		try {
			checkDefaultConfigOnDisk(System.getProperty(REPORTENGINE_HOME_DIR));
			reportEngineProperies.load(new FileInputStream(System.getProperty(REPORTENGINE_HOME_DIR)+REPORTENGINE_CONF_LOCATION));
			Enumeration<Object> em = reportEngineProperies.keys();
			while(em.hasMoreElements()){
				String key = (String)em.nextElement();
				System.setProperty(key.trim(), reportEngineProperies.getProperty(key.trim()).trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// Set log4j conf file location
		System.setProperty(ServerSettings.LOG4J_CONF_LOCATION, System.getProperty(REPORTENGINE_HOME_DIR)+System.getProperty(ServerSettings.LOG4J_CONF_LOCATION));
		System.setProperty(ServerSettings.LOG4J_LOG_LOCATION, System.getProperty(REPORTENGINE_HOME_DIR)+System.getProperty(ServerSettings.LOG4J_LOG_LOCATION));
		initializeLog4j();
	}

	private static void checkDefaultConfigOnDisk(String confLocation){
		System.out.println("Checking Conf File....");
		File destDir = new File(confLocation);
		System.out.println("***  Dir Size: "+FileUtils.sizeOfDirectory(destDir) );
		if(destDir.exists()){
			if(FileUtils.sizeOfDirectory(destDir) > 0){
				System.out.println("Already available! no need to create, Location:"+confLocation);
				return;
			}
		}
		//Copy files from war package...
		File resource;
		try {
			resource = new File(new URI(ReportEngineBasicStuffInitializer.class.getResource("/").toString()));

			FileFilter confFileFilter = new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getAbsolutePath().contains("/conf");
				}
			};
			//Clean all the files on the target dir
			FileUtils.cleanDirectory(destDir);
			//Copy all the files to the target dir
			FileUtils.copyDirectory(resource, destDir, confFileFilter);
			System.out.println("Configurations files copied to: "+destDir.getAbsolutePath());
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("Check conf file Done....");
	}
	
	
	public static void initializeLog4j(){
		//Create parent DIRs for log4j file
		new File(System.getProperty(ServerSettings.LOG4J_LOG_LOCATION)).getParentFile().mkdirs();;
		System.out.println("log4j Configuration File:"+System.getProperty(ServerSettings.LOG4J_CONF_LOCATION));
		System.out.println("Log4j Log fine: "+System.getProperty(ServerSettings.LOG4J_LOG_LOCATION));
		PropertyConfigurator.configureAndWatch(System.getProperty(ServerSettings.LOG4J_CONF_LOCATION));
		_logger.debug("log4j:"+System.getProperty(ServerSettings.LOG4J_CONF_LOCATION));
		_logger.debug("LOG: "+System.getProperty(ServerSettings.LOG4J_LOG_LOCATION));
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	/**
	 * @return the reportEngineProperies
	 */
	public static Properties getReportEngineProperies() {
		return reportEngineProperies;
	}

}
