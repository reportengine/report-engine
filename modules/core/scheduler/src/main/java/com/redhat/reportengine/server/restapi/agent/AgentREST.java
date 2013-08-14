package com.redhat.reportengine.server.restapi.agent;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.quartz.SchedulerException;

import com.redhat.reportengine.agent.rest.mapper.AgentDetails;
import com.redhat.reportengine.restapi.urimap.ServerRestUriMap;
import com.redhat.reportengine.server.api.AgentApi;
import com.redhat.reportengine.server.rest.mapper.ServerInfo;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 03, 2013
 */
@Path(ServerRestUriMap.ROOT_URI)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AgentREST {

	@POST
	@Path(ServerRestUriMap.GET_AGENT_INFO)
	public ServerInfo getServerInfo(AgentDetails agentDetails) throws SQLException, UnknownHostException {
		return new AgentApi().getServerInfo(agentDetails);
	}
	
	@POST
	@Path(ServerRestUriMap.REGISTER_ME)
	public void register(AgentDetails agentDetails) throws SQLException, ParseException, SchedulerException {
		new AgentApi().register(agentDetails);
	}
	
	@POST
	@Path(ServerRestUriMap.GET_AGENT_JOBS)
	public void getAgentJobs(AgentDetails agentDetails) throws SQLException {
		new AgentApi().getAgentJobs(agentDetails);
	}
}
