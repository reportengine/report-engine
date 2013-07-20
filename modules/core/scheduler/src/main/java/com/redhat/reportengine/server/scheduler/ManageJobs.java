package com.redhat.reportengine.server.scheduler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;


import com.redhat.reportengine.scheduler.JobDetails;
import com.redhat.reportengine.scheduler.ManageScheduler;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.reports.Keys;
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
	
	public static void loadAllJobs() throws SQLException{
		ArrayList<JobScheduler> jobs = new JobSchedulerTable().getAll();
		for(JobScheduler job : jobs){
			addAJobInScheduler(job);
		}
	}
	
	public static void addAJobInScheduler(JobScheduler jobScheduler) {
		if(!jobScheduler.isJobEnabled()){
			_logger.debug("["+jobScheduler.getJobName()+"] Job has been disabled.. Skipped to update in scheduler...");
			return;
		}
		
		JobDetails job = new JobDetails();
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(Keys.DATA_REFERENCDE_ID, jobScheduler.getDataReferenceId());
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
			ManageScheduler.addJob(job);
			return;
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
					return;
				}
			}
			ManageScheduler.addJob(job);			
		}
			
	}
}
