package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.JobClasses.TYPE;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class JobClassesTable {
	private final static String GET_JOB_CLASSES_ALL			= "getJobClasses";
	private final static String GET_JOB_CLASSES_BY_ID		= "getJobClassesById";
	private final static String GET_JOB_CLASSES_BY_CLASS	= "getJobClassesByClass";
	private final static String GET_JOB_CLASSES_BY_CLASS_LIKE	= "getJobClassesByClassLike";
	private final static String GET_JOB_CLASSES_BY_CLASS_TYPE	= "getJobClassesByClassType";
	
	
	
	public final static String SYSTEM_JOBS	= "com.redhat.reportengine.server.jobs.system";
	public final static String USER_JOBS	= "com.redhat.reportengine.server.jobs.user";
	public final static String SERVER_JOBS	= "com.redhat.reportengine.server.jobs.server";
	public final static String AGENT_JOBS	= "com.redhat.reportengine.agent.jobs";
	
	public final static String JOB_CLASS_SERVER_AGENT_REACHABLE_STATUS	= "com.redhat.reportengine.server.jobs.server.ServerAgentReachableStatus";
	
	public final static String JOB_CLASS_DESCRIPTION_RESOURCE	= "Resource: ";
	public final static String JOB_CLASS_DESCRIPTION_EMAIL_TEST_REPORT	= "Email: Test Report";
	public final static String JOB_CLASS_DESCRIPTION_UPPDATE_SERVER_REACHABLE_STATE	= "Update Server Reachable Status";
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobClasses> get() throws SQLException{
		return (ArrayList<JobClasses>)SqlMap.getSqlMapClient().queryForList(GET_JOB_CLASSES_ALL);
	}
	
	public JobClasses getById(Integer id) throws SQLException{
		return (JobClasses)SqlMap.getSqlMapClient().queryForObject(GET_JOB_CLASSES_BY_ID, id);
	}
	
	public JobClasses getByClass(String className) throws SQLException{
		return (JobClasses)SqlMap.getSqlMapClient().queryForObject(GET_JOB_CLASSES_BY_CLASS, className);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobClasses> getSystemJobClass() throws SQLException{
		return (ArrayList<JobClasses>)SqlMap.getSqlMapClient().queryForList(GET_JOB_CLASSES_BY_CLASS_TYPE, TYPE.SYSTEM.toString());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobClasses> getUserJobClass() throws SQLException{
		return (ArrayList<JobClasses>)SqlMap.getSqlMapClient().queryForList(GET_JOB_CLASSES_BY_CLASS_TYPE, TYPE.USER.toString());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobClasses> getServerJobClass() throws SQLException{
		return (ArrayList<JobClasses>)SqlMap.getSqlMapClient().queryForList(GET_JOB_CLASSES_BY_CLASS_TYPE, TYPE.SERVER.toString());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JobClasses> getAgentJobClass() throws SQLException{
		return (ArrayList<JobClasses>)SqlMap.getSqlMapClient().queryForList(GET_JOB_CLASSES_BY_CLASS_TYPE, TYPE.AGENT.toString());
	}
	
	

}
