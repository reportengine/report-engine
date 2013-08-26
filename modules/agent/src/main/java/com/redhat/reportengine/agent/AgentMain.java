package com.redhat.reportengine.agent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hyperic.sigar.Sigar;

import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;
import com.redhat.reportengine.server.rest.mapper.ServerInfo;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 13, 2013
 */

public class AgentMain {
	
	private static Logger _logger = Logger.getLogger(AgentMain.class);
	private static String AGENT_PROPERTIES_FILE = "conf/re-agent.properties";
	static ServerConnection connection = new ServerConnection();

	
	public static void main( String[] args ) throws FileNotFoundException, IOException {
		loadProperties();
		initializeLog4j();
		startServices();

		_logger.info(getSigarDetails());
		
		loadAgentInfoFromServer();
		//addJob();		
	}	
	
	//Logger Configuration
	public static void initializeLog4j(){
		PropertyConfigurator.configureAndWatch(System.getProperty(AgentProperties.LOG4J_PROPERTY_FILE));
		_logger.debug("log4j Property File: "+System.getProperty(AgentProperties.LOG4J_PROPERTY_FILE));
		_logger.debug("log4j Log File: "+System.getProperty(AgentProperties.LOG4J_LOG_FILE));
	}
	
	public static void loadProperties() throws FileNotFoundException, IOException{
		AgentProperties.loadProperties(AgentProperties.getHomeLocation()+AGENT_PROPERTIES_FILE);
	}
	
	public static void startServices(){
		//Start Netty Server
		NettyServer.start();
		//Start Scheduler
		ManageScheduler.start();
	}
	
	public static void stopServices(){
		//Stop Netty Server
		NettyServer.stop();
	}
	
	public static String getSigarDetails(){
		StringBuffer sigarInfo = new StringBuffer();
		sigarInfo.append("********************** SIGAR INFO **********************").append("\n");
		sigarInfo.append("Build Date            : ").append(Sigar.BUILD_DATE).append("\n");
		sigarInfo.append("Native Build Date     : ").append(Sigar.NATIVE_BUILD_DATE).append("\n");
		sigarInfo.append("SCM Revision          : ").append(Sigar.SCM_REVISION).append("\n");
		sigarInfo.append("Native SCM Revision   : ").append(Sigar.NATIVE_SCM_REVISION).append("\n");
		sigarInfo.append("Field Notimpl         : ").append(Sigar.FIELD_NOTIMPL).append("\n");
		sigarInfo.append("Version String        : ").append(Sigar.VERSION_STRING).append("\n");
		sigarInfo.append("Native Version String : ").append(Sigar.NATIVE_VERSION_STRING).append("\n");
		sigarInfo.append("********************************************************");
		
		return sigarInfo.toString();
	}
	
	//Load this agent jobs from report engine server
	private static void loadAgentJobs() throws Exception{
		try{
			for(JobDetails jobDetail : connection.getAgentJobs()){
				ManageScheduler.addJob(jobDetail);
			}
			_logger.info("Jobs are loaded successfully from server!!");	
		}catch(Exception ex){
			_logger.error("Error while loading jobs, ", ex);
		}
	}
	
	public static void loadAgentInfoFromServer(){
		boolean serverInfoNotSet = true;
		do{
			try {
				connection.registerAgent();
				ServerInfo serverInfo = connection.getServerInfo();
				ServerProperties.setServerUdpPort(serverInfo.getReServerUdpPort());
				ServerProperties.setServerAddress(InetAddress.getByName(new URI(AgentProperties.getServerRestUrl()).getHost()));
				//ServerProperties.setServerAddress(serverInfo.getServerAddress());
				ServerProperties.setServerId(serverInfo.getServerId());
				ServerProperties.setServerName(serverInfo.getServerName());
				ServerProperties.setEnabled(true);
				loadAgentJobs();
				serverInfoNotSet = false;
				_logger.info("Agent Info loaded successfully! Agent Id on server: "+ServerProperties.getServerId()+", Server Address: "+ServerProperties.getServerAddress().getHostAddress());
			} catch (Exception ex) {
				_logger.error("Exception on loading server/agent information", ex);
				try {
					long threadSleep = 1000*60;
					_logger.info("Agent info failed to load, will retry in another "+(threadSleep/1000)+" second(s)");
					Thread.sleep(threadSleep);
				} catch (InterruptedException te) {
					_logger.error("Thread interruptred...", te);
				}
			}
		}while(serverInfoNotSet);		
	}

}
