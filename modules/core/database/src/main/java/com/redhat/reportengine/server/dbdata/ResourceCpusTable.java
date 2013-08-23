package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ResourceCpus;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 11, 2013
 */
public class ResourceCpusTable {
	public static final String CREATE_CPUS_TABLE_SEQUENCE 	= "createCpusTableSequence";
	public static final String CREATE_CPUS_TABLE 			= "createCpusTable";
	public static final String INSERT_CPUS_USAGE 			= "insertCpusUsage";
	public static final String GET_CPUS_USAGE 				= "getCpusUsage";
	public static final String GET_CPUS_USAGE_BY_TIME_RANGE 	= "getCpusUsageByTimeRange";
	public static final String GET_CPUS_USAGE_ROW_COUNT_BY_TIME_RANGE 	= "getCpusUsageCountByTimeRange";
	
	public static final String CORE_TABLE_SUB_NAME 	= "core_cpus";
		
	
	public void createTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().insert(CREATE_CPUS_TABLE_SEQUENCE, subName);
		SqlMap.getSqlMapClient().insert(CREATE_CPUS_TABLE, subName);
	}
	
	public void add(ResourceCpus cpus) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_CPUS_USAGE, cpus);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceCpus> get() throws SQLException{
		return (ArrayList<ResourceCpus>)SqlMap.getSqlMapClient().queryForList(GET_CPUS_USAGE);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceCpus> getByTimeRange(ResourceCpus resourceCpus) throws SQLException{
		return (ArrayList<ResourceCpus>)SqlMap.getSqlMapClient().queryForList(GET_CPUS_USAGE_BY_TIME_RANGE, resourceCpus);
	}
	
	public Long getRowCount(ResourceCpus cpus) throws SQLException{
		return (Long) SqlMap.getSqlMapClient().queryForObject(GET_CPUS_USAGE_ROW_COUNT_BY_TIME_RANGE, cpus);
	}

}
