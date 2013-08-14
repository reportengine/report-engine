package com.redhat.reportengine.server.actions;

import java.sql.SQLException;
import java.text.ParseException;

import org.quartz.SchedulerException;

import com.redhat.reportengine.server.dbdata.JobClassesTable;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbdata.ServerJobTable;
import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.dbmap.ServerJob;
import com.redhat.reportengine.server.scheduler.ManageJobs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 03, 2013
 */
public class SchedulerActions {
	public void deleteScheduledServerJob(int id) throws SQLException, ParseException, SchedulerException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(id);
		if(jobScheduler.getTargetClass().startsWith(JobClassesTable.AGENT_JOBS)){
			ManageJobs.unloadJobOnAgent(jobScheduler);
			removeJobEntryOnOtherTable(id, JobClasses.TYPE.AGENT);		
		}else{
			ManageJobs.removeAJobFromScheduler(id);
			removeJobEntryOnOtherTable(id, JobClasses.TYPE.SERVER);
		}
		new JobSchedulerTable().remove(id);
	}
	

	public void addJobEntryOnOtherTable(JobScheduler jobScheduler, JobClasses.TYPE type) throws SQLException{
		switch(type){
		case SERVER: addEntryServerJobMap(jobScheduler); break;
		case AGENT: addEntryServerJobMap(jobScheduler); break;
		}
	}
	public void addEntryServerJobMap(JobScheduler jobScheduler) throws SQLException{
		ServerJob serverJob = new ServerJob();
		serverJob.setJobId(jobScheduler.getId());
		serverJob.setServerId(jobScheduler.getDataReferenceId());
		new ServerJobTable().add(serverJob);
	}
	
	public void removeJobEntryOnOtherTable(int id, JobClasses.TYPE type) throws SQLException{
		switch(type){
		case SERVER: removeEntryServerJobMap(id); break;
		case AGENT: removeEntryServerJobMap(id); break;
		}
	}
	
	public void removeEntryServerJobMap(int id) throws SQLException{
		new ServerJobTable().removeByJobId(id);
	}
	
	public void addJob(JobClasses.TYPE type, JobScheduler jobScheduler) throws SQLException{
		new JobSchedulerTable().add(jobScheduler);
		jobScheduler = new JobSchedulerTable().get(jobScheduler.getJobName());
		//Put Entry on other tables like Server-Job map, etc
		addJobEntryOnOtherTable(jobScheduler, type);
		ManageJobs.addAJobInScheduler(jobScheduler);	
	}
	
	public void removeJob(int id) throws ParseException, SQLException, SchedulerException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(id);
		switch(JobClasses.TYPE.valueOf(jobScheduler.getClassType())){
		case AGENT: ManageJobs.unloadJobOnAgent(id); break;
		case SERVER: ManageJobs.removeAJobFromScheduler(jobScheduler); break;
		case USER: ManageJobs.removeAJobFromScheduler(jobScheduler); break;
		case SYSTEM: return;
		}
		removeJobEntryOnOtherTable(id, JobClasses.TYPE.valueOf(jobScheduler.getClassType()));
		new JobSchedulerTable().remove(id);
	}
	
	public void enableJob(int id) throws SQLException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(id);
		if(jobScheduler.getClassType().equals(JobClasses.TYPE.SERVER.toString())){
			return;
		}
		new JobSchedulerTable().enable(id);		
		ManageJobs.addAJobInScheduler(id);
	}
	
	public void disableJob(int id) throws SQLException, ParseException, SchedulerException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(id);
		switch(JobClasses.TYPE.valueOf(jobScheduler.getClassType())){
		case AGENT: ManageJobs.unloadJobOnAgent(id); break;
		case SERVER: ManageJobs.removeAJobFromScheduler(id); break;
		case USER: ManageJobs.removeAJobFromScheduler(id); break;
		case SYSTEM: return;
		}
		new JobSchedulerTable().disable(id);
	}
	
}
