package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import com.redhat.reportengine.server.actions.SchedulerActions;
import com.redhat.reportengine.server.dbdata.JobSchedulerTable;
import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class ManageJobSchedulerGui {
	final static Logger _logger = Logger.getLogger(ManageJobSchedulerGui.class);
	private SchedulerActions schedulerActions = new SchedulerActions();

	public ArrayList<JobScheduler> getAllJobs() throws SQLException{
		return new JobSchedulerTable().getAll();
	}

	public ArrayList<JobScheduler> getAllUserJobs() throws SQLException{
		return new JobSchedulerTable().getUserJobs();
	}

	private boolean isCheckBoxEnabled(String status){
		if(status == null){
			return false;
		}else{
			return true;
		}
	}

	public void addNewUserJob(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException{
		JobClasses.TYPE type;
		if(request.getParameter(Keys.TYPE) != null){
			 type = JobClasses.TYPE.valueOf(request.getParameter(Keys.TYPE));
		}else{
			_logger.info("Job type not selected...");
			return;
		}
		JobScheduler jobScheduler = new JobScheduler();
		jobScheduler.setJobName(request.getParameter(Keys.JOB_NAME).trim());
		jobScheduler.setJobEnabled(this.isCheckBoxEnabled(request.getParameter(Keys.JOB_ENABLED)));
		jobScheduler.setSystemJob(false);
		jobScheduler.setTargetClassId(Integer.parseInt(request.getParameter(Keys.JOB_TYPE)));
		jobScheduler.setDataReferenceId(Integer.parseInt(request.getParameter(Keys.JOB_REFERENCE)));
		jobScheduler.setJobName(jobScheduler.getJobName()+"["+type.toString()+"-"+jobScheduler.getDataReferenceId()+"]");//Changing Job Name
		jobScheduler.setSimpleJob(this.isCheckBoxEnabled(request.getParameter(Keys.JOB_SIMPLE_JOB_ENABLE)));
		if(new JobSchedulerTable().get(jobScheduler.getJobName()) != null){
			_logger.info("Job already available: "+jobScheduler.getJobName());
		}else{
			if(!this.isCheckBoxEnabled(request.getParameter(Keys.JOB_END_LESS_ENABLED))){
				jobScheduler.setValidFromTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").parse(request.getParameter(Keys.JOB_DATE_FROM)+" "+request.getParameter(Keys.JOB_DATE_FROM_HOUR)+":"+request.getParameter(Keys.JOB_DATE_FROM_MINUTE)+":00.000"));
				jobScheduler.setValidToTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").parse(request.getParameter(Keys.JOB_DATE_TO)+" "+request.getParameter(Keys.JOB_DATE_TO_HOUR)+":"+request.getParameter(Keys.JOB_DATE_TO_MINUTE)+":59.999"));
			}

			if(this.isCheckBoxEnabled(request.getParameter(Keys.JOB_SIMPLE_JOB_ENABLE))){
				jobScheduler.setRepeatInterval(Integer.parseInt(request.getParameter(Keys.JOB_REPEAT_INTERVAL)));
				jobScheduler.setRepeatCount(Integer.parseInt(request.getParameter(Keys.JOB_REPEAT_COUNT)));
			}else if(this.isCheckBoxEnabled(request.getParameter(Keys.JOB_CRON_EXPRESSION_ENABLED))){
				jobScheduler.setCronExpression(request.getParameter(Keys.JOB_CRON_EXPRESSION).trim());
			}else{
				jobScheduler.setJobFrequency(request.getParameter(Keys.JOB_FREQUENCY));
				jobScheduler.setJobExecutionTime(new SimpleDateFormat("HH:mm:ss").parse(request.getParameter(Keys.JOB_TRIGGER_HOUR)+":"+request.getParameter(Keys.JOB_TRIGGER_MINUTE)+":"+request.getParameter(Keys.JOB_TRIGGER_SECOND)));
				if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.JOB_FREQUENCY_DAILY)){
					String[] days = request.getParameterValues(Keys.JOB_WEEKDAYS);
					String weekDays = null;
					for(String day : days){
						if(weekDays != null){
							weekDays += ","+day;
						}else{
							weekDays = day;
						}
					}
					jobScheduler.setJobWeekday(weekDays);
				}else if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.JOB_FREQUENCY_WEEKLY)){
					jobScheduler.setJobWeekday(request.getParameter(Keys.JOB_WEEKDAY));
				}else if(jobScheduler.getJobFrequency().equalsIgnoreCase(Keys.JOB_FREQUENCY_MONTHLY)){
					jobScheduler.setJobDayMonth(Integer.parseInt(request.getParameter(Keys.JOB_DAY_OF_MONTH)));
				}else{
					jobScheduler.setValidFromTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").parse(request.getParameter(Keys.JOB_ONE_TIME_DATE)+" 00:00:00.000"));
					jobScheduler.setValidToTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").parse(request.getParameter(Keys.JOB_ONE_TIME_DATE)+" 23:59:59.999"));
				}
			}
			jobScheduler.setCreationTime(new Date());
			schedulerActions.addJob(type, jobScheduler);
		}
	}
	
	public void deleteScheduledJob(HttpServletRequest request, HttpSession session) throws SQLException, ParseException, SchedulerException{
		int id = Integer.valueOf(request.getParameter(Keys.JOB_ID));
		schedulerActions.removeJob(id);
	}
	
	public void enableScheduledJob(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		int id = Integer.valueOf(request.getParameter(Keys.JOB_ID));
		schedulerActions.enableJob(id);		
	}
	
	public void disableScheduledJob(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		int id = Integer.valueOf(request.getParameter(Keys.JOB_ID));
		schedulerActions.disableJob(id);
	}
}
