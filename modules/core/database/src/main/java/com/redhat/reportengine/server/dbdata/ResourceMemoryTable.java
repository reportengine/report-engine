package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ResourceMemory;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 05, 2013
 */
public class ResourceMemoryTable {
	public static final String CREATE_MEMORY_TABLE_SEQUENCE 	= "createMemoryTableSequence";
	public static final String CREATE_MEMORY_TABLE 				= "createMemoryTable";
	public static final String INSERT_MEMORY_USAGE 			= "insertMemoryUsage";
	public static final String GET_MEMORY_USAGE 				= "getMemoryUsage";
	public static final String GET_MEMORY_USAGE_BY_TIME_RANGE 	= "getMemoryUsageByTimeRange";
	public static final String GET_MEMORY_USAGE_ROW_COUNT_BY_TIME_RANGE 	= "getMemoryUsageCountByTimeRange";

	
	public static final String DROP_MEMORY_TABLE_SEQUENCE 		= "dropMemoryTableSequence";
	public static final String DROP_MEMORY_TABLE 				= "dropMemoryTable";
		
	public static final String MEMORY_TABLE_SUB_NAME 	= "memory_swap";
	
	public static String getTableSubName(int serverId){
		return MEMORY_TABLE_SUB_NAME+"_"+serverId;
	}
	
	public void createTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().insert(CREATE_MEMORY_TABLE_SEQUENCE, subName);
		SqlMap.getSqlMapClient().insert(CREATE_MEMORY_TABLE, subName);
	}
	
	public void add(ResourceMemory memory) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_MEMORY_USAGE, memory);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceMemory> get() throws SQLException{
		return (ArrayList<ResourceMemory>)SqlMap.getSqlMapClient().queryForList(GET_MEMORY_USAGE);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceMemory> getByTimeRange(ResourceMemory resourceMemory) throws SQLException{
		return (ArrayList<ResourceMemory>)SqlMap.getSqlMapClient().queryForList(GET_MEMORY_USAGE_BY_TIME_RANGE, resourceMemory);
	}
	
	public void dropTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().delete(DROP_MEMORY_TABLE_SEQUENCE, subName);
		SqlMap.getSqlMapClient().delete(DROP_MEMORY_TABLE, subName);
		
	}
	
	public Long getRowCount(ResourceMemory memory) throws SQLException{
		return (Long) SqlMap.getSqlMapClient().queryForObject(GET_MEMORY_USAGE_ROW_COUNT_BY_TIME_RANGE, memory);
	}

}
