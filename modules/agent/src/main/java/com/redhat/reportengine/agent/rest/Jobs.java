package com.redhat.reportengine.agent.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.scheduler.JobDetails;

@Path(AgentRestUriMap.AGENT_ROOT_URI+AgentRestUriMap.AGENT_JOB_ROOT_URI)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class Jobs {

	@POST
	@Path(AgentRestUriMap.DELETE_JOB)
	public void deleteJob(JobDetails jobDetails) {
		//
	}
	
	@POST
	@Path(AgentRestUriMap.ADD_JOB)
	public void addJob(JobDetails jobDetails) {
		//
	}
}
