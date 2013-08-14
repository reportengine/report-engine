package com.redhat.reportengine.agent.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.quartz.SchedulerException;

import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;

@Path(AgentRestUriMap.AGENT_ROOT_URI)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class Jobs {

	@DELETE
	@Path(AgentRestUriMap.UNLOAD_JOB)
	public void unloadJob(JobDetails jobDetails) throws SchedulerException {
		ManageScheduler.removeJob(jobDetails);
	}
	
	@DELETE
	@Path(AgentRestUriMap.UNLOAD_ALL_JOBS)
	public void unloadAllJobs() {
		ManageScheduler.clear();
	}
	
	@POST
	@Path(AgentRestUriMap.LOAD_JOB)
	public void loadJob(JobDetails jobDetails) {
		ManageScheduler.addJob(jobDetails);
	}
	
	@POST
	@Path(AgentRestUriMap.LOAD_JOBS)
	public void loadJobs(List<JobDetails> jobsDetails) {
		for(JobDetails job : jobsDetails){
			loadJob(job);
		}
	}
}
