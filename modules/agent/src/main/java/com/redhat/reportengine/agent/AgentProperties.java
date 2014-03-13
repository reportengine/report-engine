package com.redhat.reportengine.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.redhat.reportengine.scheduler.ManageFailedJobs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 18, 2013
 */
public class AgentProperties {
	private static final String AGENT_PORT = "AGENT_PORT";
	private static final String SERVER_REST_URL = "SERVER_REST_URL";
	public static final String LOG4J_PROPERTY_FILE = "LOG4J_PROPERTY_FILE";
	public static final String LOG4J_LOG_FILE = "LOG4J_LOG_FILE";
	public static final String JOB_FAIL_COUNT_MAX = "JOB_FAIL_COUNT_MAX";
	public static final String JOB_RESUME_DELAY = "JOB_RESUME_DELAY";
	private static String AGENT_HOME = null;
	
	
	private static int agentPort;
	private static String log4jPropertyFile;
	private static String log4jLogFile;
	private static String serverRestUrl;
	private static Integer jobFailCountMax;
	private static Integer jobResumeDelay;

	public static void loadProperties(String propertiesFile) throws FileNotFoundException, IOException{
		Properties properties = new Properties();
		properties.load(new FileReader(propertiesFile));

		setAgentPort(Integer.valueOf(properties.getProperty(AGENT_PORT).trim()));
		setLog4jPropertyFile(properties.getProperty(LOG4J_PROPERTY_FILE).trim());
		setLog4jLogFile(properties.getProperty(LOG4J_LOG_FILE).trim());
		setServerRestUrl(properties.getProperty(SERVER_REST_URL).trim());
		setJobFailCountMax(Integer.valueOf(properties.getProperty(JOB_FAIL_COUNT_MAX).trim()));
		setJobResumeDelay(Integer.valueOf(properties.getProperty(JOB_RESUME_DELAY).trim()));
	}
	
	
	public static String getHomeLocation(){
		if(AGENT_HOME == null){
			AGENT_HOME = new File(System.getProperty("user.dir")).getParent()+File.separator;
		}
		return AGENT_HOME;		
	}
	
	public static void setHomeLocation(String homeLocation){
		if(!homeLocation.endsWith(File.separator)){
			AGENT_HOME = homeLocation+File.separator;
		}else{
			AGENT_HOME = homeLocation;
		}
	}
	
	public static int getAgentPort() {
		return agentPort;
	}
	public static void setAgentPort(int agentPort) {
		AgentProperties.agentPort = agentPort;
	}

	public static String getLog4jPropertyFile() {
		return log4jPropertyFile;
	}

	public static void setLog4jPropertyFile(String log4jPropertyFile) {
		AgentProperties.log4jPropertyFile = getHomeLocation()+log4jPropertyFile;
		System.setProperty(LOG4J_PROPERTY_FILE, getLog4jPropertyFile());
	}

	public static String getLog4jLogFile() {
		return log4jLogFile;
	}

	public static void setLog4jLogFile(String log4jLogFile) {
		AgentProperties.log4jLogFile = getHomeLocation()+log4jLogFile;
		System.setProperty(LOG4J_LOG_FILE, getLog4jLogFile());
	}


	public static String getServerRestUrl() {
		return serverRestUrl;
	}


	public static void setServerRestUrl(String serverRestUrl) {
		AgentProperties.serverRestUrl = serverRestUrl;
	}


	public static Integer getJobFailCountMax() {
		return jobFailCountMax;
	}

	public static void setJobFailCountMax(Integer jobFailCountMax) {
		AgentProperties.jobFailCountMax = jobFailCountMax;
		ManageFailedJobs.setJobMaxFailCount(jobFailCountMax);
	}

	public static Integer getJobResumeDelay() {
		return jobResumeDelay;
	}

	public static void setJobResumeDelay(Integer jobResumeDelay) {
		AgentProperties.jobResumeDelay = jobResumeDelay;
		ManageFailedJobs.setJobResumeDelay(jobResumeDelay);
	}

}
