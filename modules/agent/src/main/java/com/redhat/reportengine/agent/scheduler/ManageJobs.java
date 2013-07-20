package com.redhat.reportengine.agent.scheduler;

import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 25, 2013
 */

public class ManageJobs {
	public static String AGENT_JOB_GROUP = "RE_AGENT_JOBS";
	
	public static boolean addJob(JobDetails jobDetails){
		return ManageScheduler.addJob(jobDetails);
	}
}
