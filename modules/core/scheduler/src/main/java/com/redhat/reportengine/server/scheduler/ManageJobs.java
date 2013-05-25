package com.redhat.reportengine.server.scheduler;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.reports.Keys;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class ManageJobs {
	final static Logger _logger = Logger.getLogger(ManageJobs.class);
	static String jobGroupName = "reportEngineJobGroup";
	static String triggerGroupName = "reportEngineTriggerGroup";
	public static void addAllJobsInScheduler(){		
		JobDetails job = new JobDetails();
		job.setTriggerGroupName(triggerGroupName);
		job.setJobName("System Job - Test Suite Aggregation");
		job.setJobTargetClass("com.redhat.reportengine.server.jobs.TestSuiteAggregationImpl");
		job.setJobFromTime(new Date());
		//job.setJobToTime(new Date(new Date().getTime()+(1000*10)));
		job.setJobGroupName(jobGroupName);
		job.setRepeatInterval(1000*60*30L); //30 minutes once
		job.setRepeatCount(-1);
		//job.setJobCronExpression("* * * ? * *");
		ManageScheduler.addNewSimpleJob(job);		
		//ManageScheduler.addNewCronJob(job);
		
		/*
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("InactiveTime", 1000*60*60*3); //3 hours
		job.setJobDataMap(jobDataMap);
		*/		
		job.setJobName("System Job - Update Job Status");
		job.setJobTargetClass("com.redhat.reportengine.server.jobs.UpdateJobStatus");
		ManageScheduler.addNewSimpleJob(job);	
	}
	
	public static void addAJobInScheduler(int id) throws SQLException{
		addJobInscheduler(new JobSchedulerTable().get(id));
	}
	
	public static void addAJobInScheduler(String jobName) throws SQLException{
		addJobInscheduler(new JobSchedulerTable().get(jobName));
	}
	
	public static boolean removeAJobFromScheduler(int id) throws ParseException, SchedulerException, SQLException{
		return removeAJobFromScheduler((new JobSchedulerTable().get(id)).getJobName());
	}
	
	public static boolean removeAJobFromScheduler(String jobName) throws ParseException, SchedulerException{
		return ManageScheduler.removeJob(jobName, jobGroupName);
	}
	
	public static void loadAllJobs() throws SQLException{
		ArrayList<JobScheduler> jobs = new JobSchedulerTable().getAll();
		for(JobScheduler job : jobs){
			addJobInscheduler(job);
		}
	}
	
	private static void addJobInscheduler(JobScheduler jobScheduler) {
		if(!jobScheduler.isJobEnabled()){
			_logger.debug("["+jobScheduler.getJobName()+"] Job has been disabled.. Skipped to update in scheduler...");
			return;
		}
		
		JobDetails job = new JobDetails();
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(Keys.DATA_REFERENCDE_ID, jobScheduler.getDataReferenceId());
		job.setTriggerGroupName(triggerGroupName);
		job.setJobName(jobScheduler.getJobName());
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
		
		job.setJobGroupName(jobGroupName);
		
		// This is for simple jobs
		if(jobScheduler.isSimpleJob()){
			job.setRepeatInterval(jobScheduler.getRepeatInterval()*1000L);
			job.setRepeatCount(jobScheduler.getRepeatCount());
			ManageScheduler.addNewSimpleJob(job);
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
			
			ManageScheduler.addNewCronJob(job);
			
		}
			
	}
}
