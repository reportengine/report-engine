package com.redhat.reportengine.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 18, 2013
 */
public class AgentProperties {
	private static final String AGENT_PORT = "AGENT_PORT";
	private static final String LOG4J_FILE = "LOG4J_FILE";
	private static String AGENT_HOME = null;
	
	
	private static int agentPort;
	private static String log4jFile;
	
	public static void loadProperties(String propertiesFile) throws FileNotFoundException, IOException{
		Properties properties = new Properties();
		properties.load(new FileReader(propertiesFile));
		setAgentPort(Integer.valueOf(properties.getProperty(AGENT_PORT).trim()));
		setLog4jFile(properties.getProperty(LOG4J_FILE).trim());
	}
	
	public static String getHomeLocation(){
		if(AGENT_HOME == null){
			AGENT_HOME = new File(System.getProperty("user.dir")).getParent()+"/";
		}
		return AGENT_HOME;		
	}
	
	public static int getAgentPort() {
		return agentPort;
	}
	public static void setAgentPort(int agentPort) {
		AgentProperties.agentPort = agentPort;
	}

	public static String getLog4jFile() {
		return log4jFile;
	}

	public static void setLog4jFile(String log4jFile) {
		AgentProperties.log4jFile = log4jFile;
	}

}
