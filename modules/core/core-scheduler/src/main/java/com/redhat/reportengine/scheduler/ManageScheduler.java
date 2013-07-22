/**
 * 
 */
package com.redhat.reportengine.scheduler;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import java.util.List;
import java.util.Set;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 19, 2012
 */
public class ManageScheduler implements Runnable{

	private static Logger _logger = Logger.getLogger(ManageScheduler.class);
	private static Scheduler scheduler=null;

	public static boolean isJobAvailable(String jobName) throws SchedulerException{
		return isJobAvailable(jobName, Scheduler.DEFAULT_GROUP);
	}
	public static boolean isJobAvailable(JobKey jobKey) throws SchedulerException{
		JobDetail job = scheduler.getJobDetail(jobKey);
		if(job != null){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isJobAvailable(String jobName, String groupName) throws SchedulerException{
		return isJobAvailable(new JobKey(jobName, groupName));
	}

	public static boolean removeJob(String jobName, String jobGroup) throws SchedulerException{
		return removeJob(new JobKey(jobName, jobGroup));
	}

	public static boolean removeJob(JobKey jobKey) throws SchedulerException{
		if(scheduler.deleteJob(jobKey)){
			_logger.info("Job deleted successfully! [Job Name: "+jobKey.getName()+", Job Group: "+jobKey.getGroup()+"]");
			return true;
		}
		else{
			_logger.info("Unable to delete the job!, Selected Job might not be available! [Job Name: "+jobKey.getName()+", Job Group: "+jobKey.getGroup()+"]");
			return false;
		}		
	}

	public static Set<JobKey> getJobKeys(String jobGroupName) throws SchedulerException {
		return scheduler.getJobKeys(GroupMatcher.jobGroupEndsWith(jobGroupName));
	}

	public static List<String> getJobGroupNames() throws SchedulerException{
		return scheduler.getJobGroupNames();		
	}

	public static boolean addJob(JobDetail job, Trigger trigger){
		try {
			scheduler.scheduleJob(job, trigger);
			return true;
		} catch (SchedulerException ex) {
			_logger.error("Unable to add the job["+job+"]", ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean addJob(JobDetails jobDetails){
		try{
			JobDetail job;
			
			if(jobDetails.getJobDataMap() != null){
				job = JobBuilder.newJob((Class<? extends Job>)Class.forName(jobDetails.getJobTargetClass()))
						.withIdentity(jobDetails.getName(), jobDetails.getGroup())
						.usingJobData(jobDetails.getJobDataMap())
						.build();
			}else{
				job = JobBuilder.newJob((Class<? extends Job>)Class.forName(jobDetails.getJobTargetClass()))
						.withIdentity(jobDetails.getName(), jobDetails.getGroup())
						.build();
			}

			Trigger trigger;
			if(jobDetails.getJobCronExpression() != null){
				trigger = TriggerBuilder.newTrigger()			
						.withSchedule(CronScheduleBuilder.cronSchedule(jobDetails.getJobCronExpression()))
						.build();
			}else{
				SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
				ssb.withIntervalInMilliseconds(jobDetails.getRepeatInterval());
				if(jobDetails.getRepeatCount() != null){
					ssb.withRepeatCount(jobDetails.getRepeatCount());
				}			
				trigger = TriggerBuilder.newTrigger()			
						.withSchedule(ssb)
						.build();
			}

			if(jobDetails.getTriggerGroupName() != null){
				trigger.getTriggerBuilder().withIdentity(jobDetails.getName(), jobDetails.getGroup());
			}else{
				trigger.getTriggerBuilder().withIdentity(jobDetails.getName(), Scheduler.DEFAULT_GROUP);
			}			
			if(jobDetails.getJobFromTime() != null){
				trigger.getTriggerBuilder().startAt(jobDetails.getJobFromTime());
			}
			if(jobDetails.getJobToTime() != null){
				trigger.getTriggerBuilder().endAt(jobDetails.getJobToTime());
			}

			scheduler.scheduleJob(job, trigger);
			StringBuilder sb = new StringBuilder("New Job added Successfully!\n");
			sb.append("JobKey: ").append(job.getKey()).append("\n");
			sb.append("TargetClass: ").append(job.getJobClass().toString()).append("\n");
			
			if(jobDetails.getJobCronExpression() != null){
				sb.append("Cron Expression: ").append(jobDetails.getJobCronExpression()).append("\n");
			}else{
				sb.append("Repeat Interval(milli sec): ").append(jobDetails.getRepeatInterval()).append("\n");
				if(jobDetails.getRepeatCount() != null){
					sb.append("Repeat Count: ").append(jobDetails.getRepeatCount()).append("\n");
				}
			}
						
			if(trigger.getStartTime() != null){
				sb.append("Start Time: ").append(trigger.getStartTime()).append("\n");
			}

			if(trigger.getEndTime() != null){
				sb.append("End Time: ").append(trigger.getEndTime()).append("\n");
			}else{
				sb.append("End Time: ").append("Never\n");
			}
			
			
			sb.append("Next Fire Time: ").append(trigger.getNextFireTime());
			_logger.info(sb.toString());
						
			return true;
		} catch(SchedulerException se){
			_logger.error("Unable to add the job!", se);
		} catch (ClassNotFoundException se) {
			_logger.error("Unable to add the job!", se);
		}
		return false;
	}

	public static void phaseJob(String jobName, String groupName) throws SchedulerException{
		phaseJob(new JobKey(jobName, groupName));
	}

	public static void phaseJob(JobKey jobKey) throws SchedulerException{
		scheduler.pauseJob(jobKey);
		_logger.info("Job Phased --> "+jobKey);
	}

	public static void resumeJob(String jobName, String groupName) throws SchedulerException{
		resumeJob(new JobKey(jobName, groupName));
	}

	public static void resumeJob(JobKey jobKey) throws SchedulerException{
		scheduler.resumeJob(jobKey);
		_logger.info("Job Resumed --> "+jobKey);
	}
	
	public static synchronized void shutdown(){
		try {
			if(scheduler != null){
				scheduler.shutdown();
				if(scheduler.isShutdown()){
					_logger.info("Quartz Scheduler stopped...");
				}else{
					_logger.warn("unable to stop Quartz Scheduler...");
				}
			}
		} catch (SchedulerException ex) {
			_logger.error("unable to stop Quartz Scheduler!", ex);
		}		
	}
	
	public static synchronized void start(){
		try {
			if(scheduler == null){
				scheduler = new StdSchedulerFactory().getScheduler();
			}
			scheduler.start();
			if(scheduler.isStarted()){
				_logger.info("Quartz Scheduler started...");
			}else{
				_logger.warn("Unable to start Quartz Scheduler...");
			}
		} catch (SchedulerException ex) {
			_logger.error("unable to start Quartz Scheduler!", ex);
		}
	}

	public void run() {
		start();
	}
}
