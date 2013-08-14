package com.redhat.reportengine.server.actions;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.quartz.SchedulerException;

import com.redhat.reportengine.server.dbdata.JobClassesTable;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbdata.ServerJobTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.dbmap.ServerJob;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;
import com.redhat.reportengine.server.scheduler.ManageJobs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 03, 2013
 */
public class ServerActions {
	public void addJob(Server server) throws SQLException{
		JobClasses jobClasses = new JobClassesTable().getByClass(JobClassesTable.JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS);
		JobScheduler jobSchedule = new JobScheduler();
		jobSchedule.setTargetClassId(jobClasses.getId());
		jobSchedule.setDataReferenceId(server.getId());
		jobSchedule.setJobEnabled(true);
		jobSchedule.setSystemJob(true);
		jobSchedule.setSimpleJob(true);
		jobSchedule.setJobName(jobClasses.getTargetClassDescription()+"."+server.getName());
		jobSchedule.setRepeatInterval(server.getUpdateInterval());
		jobSchedule.setRepeatCount(-1);
		jobSchedule.setCreationTime(new Date());
		new JobSchedulerTable().add(jobSchedule);
		ManageJobs.addAJobInScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
	}
	
	public void add(Server server) throws SQLException{
		new ServerTable().add(server);
		server = new ServerTable().getByName(server.getName());
		addJob(server);
	}
	
	public void update(Server server) throws SQLException, ParseException, SchedulerException{
		AgentsConnection.removeRestJSONClient(server.getId());
		new ServerTable().update(server);
		modifyJob(server);
	}
	
	public void modifyJob(Server server) throws SQLException, ParseException, SchedulerException{
		JobClasses jobClasses = new JobClassesTable().getByClass(JobClassesTable.JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS);
		JobSchedulerTable jobSchedulerTable = new JobSchedulerTable();
		ManageJobs.removeAJobFromScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
		JobScheduler jobSchedule = jobSchedulerTable.get(jobClasses.getTargetClassDescription()+"."+server.getName());
		jobSchedule.setRepeatInterval(server.getUpdateInterval());
		jobSchedulerTable.remove(jobSchedule.getId());
		jobSchedulerTable.add(jobSchedule);
		ManageJobs.addAJobInScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
	}
	
	public void removeAvailableStatusJob(Server server) throws ParseException, SQLException, SchedulerException{
		JobClasses jobClasses = new JobClassesTable().getByClass(JobClassesTable.JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS);
		ManageJobs.removeAJobFromScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
		JobScheduler jobScheduler = new JobSchedulerTable().get(jobClasses.getTargetClassDescription()+"."+server.getName());
		if(jobScheduler != null){
			new JobSchedulerTable().remove(jobScheduler.getId());
		}
		
	}
	
	public void removeResourceJob(Server server) throws ParseException, SQLException, SchedulerException{
		ArrayList<ServerJob> serverJobs = new ServerJobTable().getByServerId(server.getId());
		for(ServerJob serverJob: serverJobs){
			new SchedulerActions().deleteScheduledServerJob(serverJob.getJobId());
		}
	}
}
