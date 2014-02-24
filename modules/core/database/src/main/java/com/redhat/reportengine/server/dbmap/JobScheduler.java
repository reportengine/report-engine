package com.redhat.reportengine.server.dbmap;

import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 20, 2012
 */
public class JobScheduler {
	private int id;
	private boolean systemJob;
	private boolean jobEnabled;
	private String cronExpression;
	private String jobName;
	private int targetClassId;
	private int dataReferenceId;
	private boolean simpleJob;
	private Integer repeatInterval;
	private Integer repeatCount;
	private Date validFromTime;
	private Date validToTime;
	private String jobFrequency;
	private String jobWeekday;
	private Integer jobDayMonth;
	private Date jobExecutionTime;
	private String jobDescription;
	private Date creationTime;
	private Date lastEditTime;
	
	private String targetClass;
	private String targetClassDescription;
	private String classType;
	private String jobData;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the systemJob
	 */
	public boolean isSystemJob() {
		return systemJob;
	}
	/**
	 * @param systemJob the systemJob to set
	 */
	public void setSystemJob(boolean systemJob) {
		this.systemJob = systemJob;
	}
	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the targetClassId
	 */
	public int getTargetClassId() {
		return targetClassId;
	}
	/**
	 * @param targetClassId the targetClassId to set
	 */
	public void setTargetClassId(int targetClassId) {
		this.targetClassId = targetClassId;
	}
	/**
	 * @return the dataReferenceId
	 */
	public int getDataReferenceId() {
		return dataReferenceId;
	}
	/**
	 * @param dataReferenceId the dataReferenceId to set
	 */
	public void setDataReferenceId(int dataReferenceId) {
		this.dataReferenceId = dataReferenceId;
	}
	/**
	 * @return the simpleJob
	 */
	public boolean isSimpleJob() {
		return simpleJob;
	}
	/**
	 * @param simpleJob the simpleJob to set
	 */
	public void setSimpleJob(boolean simpleJob) {
		this.simpleJob = simpleJob;
	}
	/**
	 * @return the repeatInterval
	 */
	public Integer getRepeatInterval() {
		return repeatInterval;
	}
	/**
	 * @param repeatInterval the repeatInterval to set
	 */
	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	/**
	 * @return the repoeatCount
	 */
	public Integer getRepeatCount() {
		return repeatCount;
	}
	/**
	 * @param repoeatCount the repoeatCount to set
	 */
	public void setRepeatCount(Integer repoeatCount) {
		this.repeatCount = repoeatCount;
	}
	/**
	 * @return the validFromTime
	 */
	public Date getValidFromTime() {
		return validFromTime;
	}
	/**
	 * @param validFromTime the validFromTime to set
	 */
	public void setValidFromTime(Date validFromTime) {
		this.validFromTime = validFromTime;
	}
	/**
	 * @return the validToTime
	 */
	public Date getValidToTime() {
		return validToTime;
	}
	/**
	 * @param validToTime the validToTime to set
	 */
	public void setValidToTime(Date validToTime) {
		this.validToTime = validToTime;
	}
	/**
	 * @return the jobFrequency
	 */
	public String getJobFrequency() {
		return jobFrequency;
	}
	/**
	 * @param jobFrequency the jobFrequency to set
	 */
	public void setJobFrequency(String jobFrequency) {
		this.jobFrequency = jobFrequency;
	}
	/**
	 * @return the jobWeekday
	 */
	public String getJobWeekday() {
		return jobWeekday;
	}
	/**
	 * @param jobWeekday the jobWeekday to set
	 */
	public void setJobWeekday(String jobWeekday) {
		this.jobWeekday = jobWeekday;
	}
	/**
	 * @return the jobDayMonth
	 */
	public Integer getJobDayMonth() {
		return jobDayMonth;
	}
	/**
	 * @param jobDayMonth the jobDayMonth to set
	 */
	public void setJobDayMonth(Integer jobDayMonth) {
		this.jobDayMonth = jobDayMonth;
	}
	/**
	 * @return the jobExecutionTime
	 */
	public Date getJobExecutionTime() {
		return jobExecutionTime;
	}
	/**
	 * @param jobExecutionTime the jobExecutionTime to set
	 */
	public void setJobExecutionTime(Date jobExecutionTime) {
		this.jobExecutionTime = jobExecutionTime;
	}
	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}
	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the lastEditTime
	 */
	public Date getLastEditTime() {
		return lastEditTime;
	}
	/**
	 * @param lastEditTime the lastEditTime to set
	 */
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	/**
	 * @return the targetClass
	 */
	public String getTargetClass() {
		return targetClass;
	}
	/**
	 * @param targetClass the targetClass to set
	 */
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	/**
	 * @return the targetClassDescription
	 */
	public String getTargetClassDescription() {
		return targetClassDescription;
	}
	/**
	 * @param targetClassDescription the targetClassDescription to set
	 */
	public void setTargetClassDescription(String targetClassDescription) {
		this.targetClassDescription = targetClassDescription;
	}
	/**
	 * @return the jobEnabled
	 */
	public boolean isJobEnabled() {
		return jobEnabled;
	}
	/**
	 * @param jobEnabled the jobEnabled to set
	 */
	public void setJobEnabled(boolean jobEnabled) {
		this.jobEnabled = jobEnabled;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getJobData() {
		return jobData;
	}
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	
}
