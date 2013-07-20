package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;

import com.redhat.reportengine.server.dbdata.JobClassesTable;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbdata.ResourceCpuTable;
import com.redhat.reportengine.server.dbdata.ResourceMemoryTable;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerJobTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.dbmap.ServerJob;
import com.redhat.reportengine.server.jobs.system.ServerAgentReachableStatus;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.scheduler.ManageJobs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class ManageServer {
	
	public static final String MANAGE_SERVER_GROUP = "Manage Server Group";
	
	public void updateStatusNow(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
		new ServerAgentReachableStatus().updateServerAgentReachableStatus(id);
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		addUpdate(request, response, true);
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		addUpdate(request, response, false);
	}	
	
	public void remove(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
		Server server = new ServerTable().getById(id);
		removeResourceJob(server);
		removeAvailableStatusJob(server);
		removeResourceTables(server);
		new ServerTable().remove(id);
	}
	
	public void removeResourceTables(Server server) throws SQLException{
		ServerCpuDetail serverCpuDetail = new ServerCpuDetailTable().getByServerId(server.getId());
		//Drop Memory/Swap Table
		new ResourceMemoryTable().dropTable(ResourceMemoryTable.getTableSubName(server.getId()));
		//Drop CPU tables
		ResourceCpuTable resourceCpuTable = new ResourceCpuTable();
		resourceCpuTable.dropTable(ResourceCpuTable.getCoreCpuSubName(server.getId()));
		//Drop CPUs table
		if(serverCpuDetail != null){
			for(int i=0; i<serverCpuDetail.getTotalCores(); i++){
				resourceCpuTable.dropTable(ResourceCpuTable.getMultiCpuSubName(server.getId(), i));
			}
		}		
	}
	
	private void addUpdate(HttpServletRequest request, HttpServletResponse response, boolean add) throws SQLException, ParseException, SchedulerException{
		String name 			= request.getParameter(Keys.SERVER_NAME);
		String hostIp 			= request.getParameter(Keys.SERVER_HOSTIP);
		Integer agentPort 		= Integer.valueOf((String)request.getParameter(Keys.SERVER_AGENT_PORT));
		Integer updateInterval 	= Integer.valueOf((String)request.getParameter(Keys.SERVER_UPDATE_INTERVAL));

		if(!(name != null) && (hostIp != null)){
			return;						
		}
		
		Server server;
		if(add){
			server = new Server();
		}else{
			Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
			server = new ServerTable().getById(id);
		}		
		server.setName(name.trim());
		server.setHostIp(hostIp.trim());
		server.setAgentPort(agentPort);
		server.setUpdateInterval(updateInterval*60);
		if(add){
			new ServerTable().add(server);
			server = new ServerTable().getByName(server.getName());
			addJob(server);
		}else{
			new ServerTable().update(server);
			modifyJob(server);
		}			
	}
	
	private void modifyJob(Server server) throws SQLException, ParseException, SchedulerException{
		JobClasses jobClasses = new JobClassesTable().getByClass(JobClassesTable.JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS);
		JobSchedulerTable jobSchedulerTable = new JobSchedulerTable();
		ManageJobs.removeAJobFromScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
		JobScheduler jobSchedule = jobSchedulerTable.get(jobClasses.getTargetClassDescription()+"."+server.getName());
		jobSchedule.setRepeatInterval(server.getUpdateInterval());
		jobSchedulerTable.remove(jobSchedule.getId());
		jobSchedulerTable.add(jobSchedule);
		ManageJobs.addAJobInScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
	}
	
	private void removeAvailableStatusJob(Server server) throws ParseException, SQLException, SchedulerException{
		JobClasses jobClasses = new JobClassesTable().getByClass(JobClassesTable.JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS);
		ManageJobs.removeAJobFromScheduler(jobClasses.getTargetClassDescription()+"."+server.getName());
		JobScheduler jobScheduler = new JobSchedulerTable().get(jobClasses.getTargetClassDescription()+"."+server.getName());
		if(jobScheduler != null){
			new JobSchedulerTable().remove(jobScheduler.getId());
		}
		
	}
	
	private void removeResourceJob(Server server) throws ParseException, SQLException, SchedulerException{
		ArrayList<ServerJob> serverJobs = new ServerJobTable().getByServerId(server.getId());
		for(ServerJob serverJob: serverJobs){
			new ManageJobSchedulerGui().deleteScheduledJob(serverJob.getJobId());
		}
	}
	
	private void addJob(Server server) throws SQLException{
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
}
