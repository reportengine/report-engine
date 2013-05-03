package com.redhat.reportengine.server.ContextListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.redhat.reportengine.server.ReportEngineProperty;

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
	Logger _logger =  Logger.getLogger(this.getClass().getName());	

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
		initializeLog4j();
	}
	
	public void initializeLog4j(){
		System.setProperty(ReportEngineProperty.LOG4J_CONF_LOCATION, System.getProperty(REPORTENGINE_HOME_DIR)+System.getProperty(ReportEngineProperty.LOG4J_CONF_LOCATION));
		System.setProperty(ReportEngineProperty.LOG4J_LOG_LOCATION, System.getProperty(REPORTENGINE_HOME_DIR)+System.getProperty(ReportEngineProperty.LOG4J_LOG_LOCATION));
		System.out.println("log4j:"+System.getProperty(ReportEngineProperty.LOG4J_CONF_LOCATION));
		System.out.println("LOG: "+System.getProperty(ReportEngineProperty.LOG4J_LOG_LOCATION));
		PropertyConfigurator.configureAndWatch(System.getProperty(ReportEngineProperty.LOG4J_CONF_LOCATION));
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
