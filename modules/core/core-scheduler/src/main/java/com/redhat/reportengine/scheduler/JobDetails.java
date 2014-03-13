/**
 * 
 */
package com.redhat.reportengine.scheduler;

import java.io.Serializable;
import java.util.Date;

import org.quartz.JobDataMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 19, 2012
 */
public class JobDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1045470215747095305L;
	
	private String name;
	private String group;
	private String triggerGroupName;
	private String jobCronExpression;
	private Integer repeatCount;
	private Long repeatInterval;
	private String jobTargetClass;
	private Date jobFromTime;
	private Date jobToTime;
	private JobDataMap jobDataMap;
	private boolean enabled;
	/**
	 * @return the jobName
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setName(String jobName) {
		this.name = jobName;
	}
	/**
	 * @return the jobType
	 */
	public String getGroup() {
		return this.group;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setGroup(String jobType) {
		this.group = jobType;
	}
	/**
	 * @return the jobFrequency
	 */
	public String getJobCronExpression() {
		return this.jobCronExpression;
	}
	/**
	 * @param jobFrequency the jobFrequency to set
	 */
	public void setJobCronExpression(String jobFrequency) {
		this.jobCronExpression = jobFrequency;
	}
	/**
	 * @return the jobTargetClass
	 */
	public String getJobTargetClass() {
		return this.jobTargetClass;
	}
	/**
	 * @param jobTargetClass the jobTargetClass to set
	 */
	public void setJobTargetClass(String jobTargetClass) {
		this.jobTargetClass = jobTargetClass;
	}
	/**
	 * @return the jobFromTime
	 */
	public Date getJobFromTime() {
		return this.jobFromTime;
	}
	/**
	 * @param jobFromTime the jobFromTime to set
	 */
	public void setJobFromTime(Date jobFromTime) {
		this.jobFromTime = jobFromTime;
	}
	/**
	 * @return the jobToTime
	 */
	public Date getJobToTime() {
		return this.jobToTime;
	}
	/**
	 * @param jobToTime the jobToTime to set
	 */
	public void setJobToTime(Date jobToTime) {
		this.jobToTime = jobToTime;
	}
	/**
	 * @return the jobDetails
	 */
	public JobDataMap getJobDataMap() {
		return this.jobDataMap;
	}
	/**
	 * @param jobDetails the jobDetails to set
	 */
	public void setJobDataMap(JobDataMap jobDetails) {
		this.jobDataMap = jobDetails;
	}
	/**
	 * @return the group
	 */
	public String getTriggerGroupName() {
		return this.triggerGroupName;
	}
	/**
	 * @param group the group to set
	 */
	public void setTriggerGroupName(String group) {
		this.triggerGroupName = group;
	}
	/**
	 * @return the repeatCount
	 */
	public Integer getRepeatCount() {
		return this.repeatCount;
	}
	/**
	 * @param repeatCount the repeatCount to set
	 */
	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}
	/**
	 * @return the repeatInterval
	 */
	public Long getRepeatInterval() {
		return this.repeatInterval;
	}
	/**
	 * @param repeatInterval the repeatInterval to set
	 */
	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	

}
