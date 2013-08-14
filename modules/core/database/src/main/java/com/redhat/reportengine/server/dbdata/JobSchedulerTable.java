package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobClasses.TYPE;
import com.redhat.reportengine.server.dbmap.JobScheduler;
import com.redhat.reportengine.server.sql.SqlMap;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class JobSchedulerTable {

	//Job Schedules
	private final static String GET_JOB_SCHEDULER_NEXT_SEQ_ID					= "getJobSchedulerNextId";
	private final static String GET_JOB_SCHEDULER_ALL							= "getJobSchedulerAll";
	private final static String GET_JOB_USER_SCHEDULER_ALL						= "getUserJobSchedulerAll";
	private final static String GET_JOB_SCHEDULER_BY_ID							= "getJobSchedulerById";
	private final static String GET_JOB_SCHEDULER_BY_NAME						= "getJobSchedulerByJobName";
	private final static String INSERT_JOB_SCHEDULER							= "insertJobScheduler";
	private final static String DELETE_SCHEDULED_JOB							= "deleteAscehduledJob";
	private final static String ENABLE_SCHEDULED_JOB							= "enableAscehduledJob";
	private final static String DISABLE_SCHEDULED_JOB							= "disableAscehduledJob";
	private final static String GET_JOB_BY_TARGET_CLASS_ID_AND_REFERENCE_ID		= "getJobSchedulerByTargetClassIdAndReferenceId";
	private final static String GET_JOB_SCHEDULER_BY_JOB_CLASS_LIKE				= "getJobSchedulerByClassLike";
	private final static String GET_JOB_SCHEDULER_BY_JOB_CLASS_LIKE_AND_DATA_REFERENCE_ID	= "getJobSchedulerByClassLikeAndDataReference";
	private final static String GET_JOB_SCHEDULER_BY_SERVER_ID	= "getJobSchedulerByServerId";
	private final static String GET_JOB_SCHEDULER_BY_CLASS_TYPE	= "getJobSchedulerByClassType";
	private final static String GET_JOB_SCHEDULER_BY_CLASS_TYPE_DATA_REFERENCE	= "getJobSchedulerByClassTypeAndDataReference";
	
	
	
	public Integer getNextSeqId() throws SQLException{
		return (Integer) SqlMap.getSqlMapClient().queryForObject(GET_JOB_SCHEDULER_NEXT_SEQ_ID);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobScheduler> getAll() throws SQLException{
		return (ArrayList<JobScheduler>) SqlMap.getSqlMapClient().queryForList(GET_JOB_SCHEDULER_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobScheduler> getUsersJobAll() throws SQLException{
		return (ArrayList<JobScheduler>) SqlMap.getSqlMapClient().queryForList(GET_JOB_USER_SCHEDULER_ALL);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobScheduler> getJobsByClassType(TYPE type) throws SQLException{
		return (ArrayList<JobScheduler>) SqlMap.getSqlMapClient().queryForList(GET_JOB_SCHEDULER_BY_CLASS_TYPE, type.toString());
	}
	
	public ArrayList<JobScheduler> getUserJobs() throws SQLException{
		return getJobsByClassType(JobClasses.TYPE.USER);
	}
	
	public ArrayList<JobScheduler> getSystemJobs() throws SQLException{
		return getJobsByClassType(JobClasses.TYPE.SYSTEM);
	}
	
	public ArrayList<JobScheduler> getAgentJobs() throws SQLException{
		return getJobsByClassType(JobClasses.TYPE.AGENT);
	}
	
	public ArrayList<JobScheduler> getServerJobs() throws SQLException{
		return getJobsByClassType(JobClasses.TYPE.SERVER);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobScheduler> getJobsByServerId(int serverId) throws SQLException{
		return (ArrayList<JobScheduler>) SqlMap.getSqlMapClient().queryForList(GET_JOB_SCHEDULER_BY_SERVER_ID, serverId);
	}
	
	public ArrayList<JobScheduler> getAgentJobs(int serverId) throws SQLException{
		return getJobsByTypeDataReference(serverId);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobScheduler> getJobsByTypeDataReference(int dataReferenceId) throws SQLException{
		JobScheduler jobScheduler = new JobScheduler();
		jobScheduler.setClassType(JobClasses.TYPE.AGENT.toString());
		jobScheduler.setDataReferenceId(dataReferenceId);
		return (ArrayList<JobScheduler>) SqlMap.getSqlMapClient().queryForList(GET_JOB_SCHEDULER_BY_CLASS_TYPE_DATA_REFERENCE, jobScheduler);
	}
	
	public JobScheduler get(Integer id) throws SQLException{
		return (JobScheduler) SqlMap.getSqlMapClient().queryForObject(GET_JOB_SCHEDULER_BY_ID, id);
	}
	
	public JobScheduler getByTargetClassIdAndDataReferenceId(JobScheduler jobScheduler) throws SQLException{
		return (JobScheduler) SqlMap.getSqlMapClient().queryForObject(GET_JOB_BY_TARGET_CLASS_ID_AND_REFERENCE_ID, jobScheduler);
	}
	
	public JobScheduler get(String name) throws SQLException{
		return (JobScheduler) SqlMap.getSqlMapClient().queryForObject(GET_JOB_SCHEDULER_BY_NAME, name);
	}
	
	public void add(JobScheduler jobScheduler) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_JOB_SCHEDULER, jobScheduler);
	}
	
	public void enable(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().update(ENABLE_SCHEDULED_JOB, id);
	}
	
	public void disable(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().update(DISABLE_SCHEDULED_JOB, id);
	}
	
	public void remove(Integer id) throws SQLException{
		SqlMap.getSqlMapClient().update(DELETE_SCHEDULED_JOB, id);
	}
}
