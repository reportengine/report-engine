package com.redhat.reportengine.server.scheduler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;
import com.redhat.reportengine.server.dbdata.JobClassesTable;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.reports.Keys;
import com.redhat.reportengine.server.restclient.agent.AgentsConnection;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class ManageJobs {
	final static Logger _logger = Logger.getLogger(ManageJobs.class);

	public static void addAJobInScheduler(int id) throws SQLException{
		addAJobInScheduler(new JobSchedulerTable().get(id));
	}

	public static void addAJobInScheduler(String jobName) throws SQLException{
		addAJobInScheduler(new JobSchedulerTable().get(jobName));
	}

	public static boolean removeAJobFromScheduler(int id) throws ParseException, SQLException, SchedulerException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(id);
		return removeAJobFromScheduler(jobScheduler);
	}

	public static boolean removeAJobFromScheduler(JobScheduler jobScheduler) throws ParseException, SQLException, SchedulerException{
		return removeAJobFromScheduler(new JobKey(jobScheduler.getJobName(), jobScheduler.getTargetClassDescription()));
	}

	public static boolean removeAJobFromScheduler(String jobName) throws ParseException, SQLException, SchedulerException{
		JobScheduler jobScheduler = new JobSchedulerTable().get(jobName);
		if(jobScheduler == null){
			_logger.info("Job Not available on scheduler Table: "+jobName);
			return false;
		}
		return removeAJobFromScheduler(new JobKey(jobScheduler.getJobName(), jobScheduler.getTargetClassDescription()));
	}

	public static boolean removeAJobFromScheduler(JobKey jobKey) throws ParseException, SchedulerException{
		return ManageScheduler.removeJob(jobKey);
	}

	private static void loadJobs(ArrayList<JobScheduler> jobs){
		for(JobScheduler job : jobs){
			addAJobInScheduler(job);
		}
	}

	public static void loadAllJobs() throws SQLException{
		JobSchedulerTable jobSchedulerTable = new JobSchedulerTable();
		//Load System Jobs
		loadJobs(jobSchedulerTable.getSystemJobs());
		//Load User Jobs
		loadJobs(jobSchedulerTable.getUserJobs());
		//Load Server Jobs
		loadJobs(jobSchedulerTable.getServerJobs());		
	}


	public static JobDetails getJobDetails(JobScheduler jobScheduler){
		JobDetails job = new JobDetails();
		job.setEnabled(jobScheduler.isJobEnabled());
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(Keys.DATA_REFERENCDE_ID, jobScheduler.getDataReferenceId());
		jobDataMap.put(Keys.JOB_DATA, jobScheduler.getJobData());
		job.setTriggerGroupName(jobScheduler.getTargetClassDescription());
		job.setName(jobScheduler.getJobName());
		job.setJobTargetClass(jobScheduler.getTargetClass());
		job.setJobDataMap(jobDataMap);// JOB data added here
		if(jobScheduler.getValidFromTime() != null){
			job.setJobFromTime(jobScheduler.getValidFromTime());
		}else{
			job.setJobFromTime(new Date());
		}

		if(jobScheduler.getValidToTime() != null){
			job.setJobToTime(jobScheduler.getValidToTime());
		}

		job.setGroup(jobScheduler.getTargetClassDescription());

		// This is for simple jobs
		if(jobScheduler.isSimpleJob()){
			job.setRepeatInterval(jobScheduler.getRepeatInterval()*1000L);
			if(jobScheduler.getRepeatCount() != null){
				job.setRepeatCount(jobScheduler.getRepeatCount());
			}else{
				job.setRepeatCount(-1);
			}
			return job;
		}else{ //For cron jobs			
			if(jobScheduler.getCronExpression() != null){
				job.setJobCronExpression(jobScheduler.getCronExpression());
			}else{
				String cornExpression = "";
				String cronSeconds 	= new SimpleDateFormat("ss").format(jobScheduler.getJobExecutionTime());
				String cronMinutes 	= new SimpleDateFormat("mm").format(jobScheduler.getJobExecutionTime());
				String cronHours 	= new SimpleDateFormat("HH").format(jobScheduler.getJobExecutionTime());
				String cronSHM = cronSeconds+" "+cronMinutes+" "+cronHours;
				//0/5 * * * * ?
				if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.FREQUENCY_DAILY)){
					cornExpression = cronSHM+" ? * "+jobScheduler.getJobWeekday();
				}else if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.FREQUENCY_WEEKLY)){
					cornExpression = cronSHM+" ? * "+jobScheduler.getJobWeekday();
				}else if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.FREQUENCY_MONTHLY)){
					cornExpression = cronSHM+" "+jobScheduler.getJobDayMonth()+" * ?";
				}else if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.FREQUENCY_ONE_TIME)){
					cornExpression = cronSHM+" "+new SimpleDateFormat("d").format(jobScheduler.getValidFromTime())+" "+new SimpleDateFormat("MM").format(jobScheduler.getValidFromTime())+" ? "+new SimpleDateFormat("yyyy").format(jobScheduler.getValidFromTime());
				}
				_logger.debug("Job Corn Expression: "+cornExpression);
				job.setJobCronExpression(cornExpression);
			}

			//Check the job validity
			if(jobScheduler.getValidToTime() != null){
				if(jobScheduler.getValidToTime().before(new Date())){
					_logger.info("This job got expired! --> Job Name: "+jobScheduler.getJobName());
				}
			}
			return job;
		}			
	}

	public static void addAJobInScheduler(JobScheduler jobScheduler) {
		if(!jobScheduler.isJobEnabled()){
			_logger.debug("["+jobScheduler.getJobName()+"] Job has been disabled.. Skipped to update in scheduler...");
			return;
		}

		JobDetails job = getJobDetails(jobScheduler);

		if(jobScheduler.getTargetClass().startsWith(JobClassesTable.AGENT_JOBS)){
			loadJobOnAgent(jobScheduler.getDataReferenceId(), job);		
		}else{
			ManageScheduler.addJob(job);
		}

	}

	public static void loadJobOnAgent(int serverId, JobDetails job){
		try {
			AgentsConnection.getRestJSONclient(serverId).post(AgentRestUriMap.LOAD_JOB, job, JobDetails.class);
		}catch (HttpHostConnectException connectException){
			_logger.error("Connectrion Error: "+connectException.getMessage());
		}catch (Exception ex) {
			_logger.error("Unable to load the job on agent...", ex);
		}
	}
	
	public static void unloadJobOnAgent(int jobId) throws SQLException{
		unloadJobOnAgent(new JobSchedulerTable().get(jobId));
	}
	
	public static void unloadJobOnAgent(JobScheduler jobScheduler){
		JobDetails job = getJobDetails(jobScheduler);
		try {
			AgentsConnection.getRestJSONclient(jobScheduler.getDataReferenceId()).delete(AgentRestUriMap.UNLOAD_JOB, job, JobDetails.class);
		}catch (HttpHostConnectException connectException){
			_logger.error("Connectrion Error: "+connectException.getMessage());
		}catch (Exception ex) {
			_logger.error("Unable to unload the job from agent...", ex);
		}
	}

	public static void unloadAllJobsOnAgent(int serverId){
		try {
			AgentsConnection.getRestJSONclient(serverId).delete(AgentRestUriMap.UNLOAD_ALL_JOBS, JobDetails.class);
		}catch (HttpHostConnectException connectException){
			_logger.error("Connectrion Error: "+connectException.getMessage());
		}catch (Exception ex) {
			_logger.error("Unable to unload the jobs on agent...", ex);
		}
	}
}
