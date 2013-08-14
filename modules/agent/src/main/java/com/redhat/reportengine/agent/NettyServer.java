package com.redhat.reportengine.agent;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.redhat.reportengine.agent.rest.AgentResource;
import com.redhat.reportengine.agent.rest.Jobs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 31, 2013
 */
public class NettyServer {
	private static Logger _logger = Logger.getLogger(NettyServer.class);

	//Netty Server instance
	final static NettyJaxrsServer server = new NettyJaxrsServer();
	static ResteasyDeployment deployment;

	//ResteasyDeployment for Netty server.
	public static ResteasyDeployment getResteasyDeployment(){
		if(deployment == null){
			deployment = new ResteasyDeployment();
		}
		LinkedList<String> resources = new LinkedList<String>();
		resources.addLast(AgentResource.class.getName());
		resources.addLast(Jobs.class.getName());
		//deployment.setResourceClasses(Collections.singletonList(AgentResource.class.getName()));
		deployment.setResourceClasses(resources);
		return deployment;
	}

	public static void start(){
		//Deploy RestEasy with Netty
		server.setDeployment(getResteasyDeployment());
		//Set communication port for agent
		server.setPort(AgentProperties.getAgentPort());
		// Start Netty server
		server.start();
		_logger.info("Netty Server has started successfully...[listening on the port: "+server.getPort()+"]");
	}
	
	public static void stop(){
		// Stop Netty server
		server.stop();
		_logger.info("Netty Server has stopped successfully...");

	}
}
