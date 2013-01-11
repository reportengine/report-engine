/**
 * 
 */
package com.redhat.reportengine.server.scheduler;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import java.text.ParseException;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 19, 2012
 */
public class ManageScheduler implements Runnable{

	private static Logger _logger = Logger.getLogger(ManageScheduler.class);
	private static Scheduler scheduler=null;
	private static SchedulerFactory sfactory = null;

	private static void initScheduler() throws SchedulerException {
		if(scheduler == null){
			sfactory = new StdSchedulerFactory();
			scheduler = sfactory.getScheduler();
			scheduler.start();
			_logger.info("Job Scheduler started successfully!");
		}
		else{
			_logger.info("Job Schedular is running already, nothing to do!");
		}
	}

	public static boolean isJobAvailable(String jobName) throws SchedulerException{
		return isJobAvailable(jobName, Scheduler.DEFAULT_GROUP);
	}
	public static boolean isJobAvailable(String jobName, String groupName) throws SchedulerException{
		JobDetail job = scheduler.getJobDetail(jobName, groupName);
		if(job != null){
			return true;
		}else{
			return false;
		}
	}

	public static void startScheduler(){
		try{
			initScheduler();
		}
		catch(Exception ex){
			_logger.error("Unable to start the Job Scheduler!", ex);
			shutdownScheduler();
		}		
	}

	public static void shutdownScheduler(){
		try{
			scheduler.shutdown(true);
			_logger.debug("Quartz Scheduler service has been stopped!");
		}
		catch(Exception exSh){
			_logger.error("Unable to shutdown the Job Scheduler!", exSh);
		}	
	}

	public static boolean removeJob(String jobName, String jobGroup) throws ParseException, SchedulerException{
		if(scheduler.deleteJob(jobName, jobGroup)){
			_logger.info("Job deleted successfully! [Job Name: "+jobName+", Job Group: "+jobGroup+"]");
			return true;
		}
		else{
			_logger.info("Unable to delete the job!, Selected Job might not be available! [Job Name: "+jobName+", Job Group: "+jobGroup+"]");
			return false;
		}			
	}

	public static String[] getJobNames(String jobGroupName) throws SchedulerException{
		return scheduler.getJobNames(jobGroupName);		
	}

	public static String[] getJobGroupNames() throws SchedulerException{
		return scheduler.getJobGroupNames();		
	}
	
	public static boolean addNewCronJob(JobDetails jobDetails){
		try{
			JobDetail job = new JobDetail();
			job.setName(jobDetails.getJobName());
			job.setGroup(jobDetails.getJobGroupName());
			job.setJobClass(Class.forName(jobDetails.getJobTargetClass()));
			if(jobDetails.getJobDataMap() != null){
				job.setJobDataMap(jobDetails.getJobDataMap());
			}		
			
			CronTrigger trigger = new CronTrigger();
			trigger.setName("Cron"+jobDetails.getJobName());
			trigger.setJobName(jobDetails.getJobName());
			trigger.setJobGroup(jobDetails.getJobGroupName());
			trigger.setCronExpression(jobDetails.getJobCronExpression());
			if(jobDetails.getTriggerGroupName() != null){
				trigger.setGroup(jobDetails.getTriggerGroupName());
			}else{
				trigger.setGroup(Scheduler.DEFAULT_GROUP);
			}			
			if(jobDetails.getJobFromTime() != null){
				trigger.setStartTime(jobDetails.getJobFromTime());
			}
			if(jobDetails.getJobToTime() != null){
				trigger.setEndTime(jobDetails.getJobToTime());
			}
			
			scheduler.scheduleJob(job, trigger);			
			_logger.info("New Job added Successfully!\nJob Name: "+job.getName()+"\nTarget Class: "+job.getJobClass().toString()+"\nCron Expression: "+trigger.getCronExpression()+"\nFrom Date: "+trigger.getStartTime()+"\nTo Date: "+trigger.getEndTime());
			return true;
		}
		catch(Exception ex){
			_logger.error("Unable to add the job!", ex);
		}
		return false;
	}
	
	public static boolean addNewSimpleJob(JobDetails jobDetails){
		try{
			JobDetail job = new JobDetail();
			job.setName(jobDetails.getJobName());
			job.setGroup(jobDetails.getJobGroupName());
			job.setJobClass(Class.forName(jobDetails.getJobTargetClass()));
			if(jobDetails.getJobDataMap() != null){
				job.setJobDataMap(jobDetails.getJobDataMap());
			}		
			
			SimpleTrigger trigger = new SimpleTrigger();
			trigger.setName("Simple"+jobDetails.getJobName());
			trigger.setJobName(jobDetails.getJobName());
			trigger.setJobGroup(jobDetails.getJobGroupName());
			trigger.setRepeatInterval(jobDetails.getRepeatInterval());
			if(jobDetails.getRepeatCount() != null){
				trigger.setRepeatCount(jobDetails.getRepeatCount());
			}			
			if(jobDetails.getTriggerGroupName() != null){
				trigger.setGroup(jobDetails.getTriggerGroupName());
			}else{
				trigger.setGroup(Scheduler.DEFAULT_GROUP);
			}			
			if(jobDetails.getJobFromTime() != null){
				trigger.setStartTime(jobDetails.getJobFromTime());
			}
			if(jobDetails.getJobToTime() != null){
				trigger.setEndTime(jobDetails.getJobToTime());
			}
			
			scheduler.scheduleJob(job, trigger);
			
			_logger.info("New Job added Successfully!\nJob Name: "+job.getName()+"\nTarget Class: "+job.getJobClass().toString()+"\nRepeat Interval: "+trigger.getRepeatInterval()+"\nRepeat Count: "+trigger.getRepeatCount()+"\nFrom Date: "+trigger.getStartTime()+"\nTo Date: "+trigger.getEndTime());
			
			return true;
		}
		catch(Exception ex){
			_logger.error("Unable to add the job!", ex);
		}
		return false;
	}
	
	public static void phaseJob(String jobName, String groupName) throws SchedulerException{
		scheduler.pauseJob(jobName, groupName);
		_logger.info("Job Phased --> "+jobName);
	}
	
	public static void resumeJob(String jobName, String groupName) throws SchedulerException{
		scheduler.resumeJob(jobName, groupName);
		_logger.info("Job Resumed --> "+jobName);
	}

	public void run() {
		startScheduler();
		_logger.info("Quartz Schedular started successfully...");
	}
}
