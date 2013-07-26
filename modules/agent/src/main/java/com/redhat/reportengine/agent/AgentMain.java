package com.redhat.reportengine.agent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.hyperic.sigar.Sigar;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.redhat.reportengine.agent.rest.AgentResource;
import com.redhat.reportengine.agent.scheduler.ManageJobs;
import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 13, 2013
 */

public class AgentMain {
	
	private static Logger _logger = Logger.getLogger(AgentMain.class);
	private static String AGENT_PROPERTIES_FILE = "conf/re-agent.properties";
	//Logger Configuration
	static
	{
	    Logger rootLogger = Logger.getRootLogger();
	    rootLogger.setLevel(Level.DEBUG);
	    rootLogger.addAppender(new ConsoleAppender(
	               new PatternLayout("[%d{ISO8601}]%5p %6.6r[%t]%x [%M(%F:%L)] %n%m%n")));
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
	
	public static void main( String[] args ) {
		//RESTEasy deployment
		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setResourceClasses(Collections.singletonList(AgentResource.class.getName()));
		
		//Netty Server instance for REST API
		final NettyJaxrsServer server = new NettyJaxrsServer();

		//Deploy RestEasy with Netty
		server.setDeployment(deployment);
		
		try {
			AgentProperties.loadProperties(AgentProperties.getHomeLocation()+AGENT_PROPERTIES_FILE);
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		//Set communication port for agent
		server.setPort(AgentProperties.getAgentPort());
		// Start Netty server
		server.start();
		
		_logger.info("RE-Agent is listening on port "+AgentProperties.getAgentPort());
		_logger.info(getSigarDetails());
		
		ManageScheduler.start();
		_logger.info("RE-Agent Scheduler started successfully...");
		
		JobDetails details = new  JobDetails();
		details.setName("Agent Job");
		details.setGroup("RE- Group");
		details.setJobTargetClass("com.redhat.reportengine.agent.jobs.SendCpuMemorySwapInfo");
		details.setRepeatCount(new Integer(-1));
		details.setRepeatInterval(new Long(1000*10));
		
		ManageJobs.addJob(details);
	}

}
