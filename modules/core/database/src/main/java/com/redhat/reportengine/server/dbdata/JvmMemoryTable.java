/**
 * 
 */
package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.JvmMemory;
import com.redhat.reportengine.server.dbmap.ResourceCpu;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 3, 2014
 */
public class JvmMemoryTable {
	public static final String CREATE_JVM_MEMORY_TABLE_SEQUENCE 	= "createJvmMemoryTableSequence";
	public static final String CREATE_JVM_MEMORY_TABLE 				= "createJvmMemoryTable";
	public static final String INSERT_JVM_MEMORY_USAGE 				= "insertJvmMemoryUsage";
	public static final String GET_JVM_MEMORY_USAGE 				= "getJvmMemoryUsage";
	public static final String GET_JVM_MEMORY_HEAP_USAGE_BY_TIME_RANGE 			= "getJvmMemoryHeapUsageByTimeRange";
	public static final String GET_JVM_MEMORY_NON_HEAP_USAGE_BY_TIME_RANGE 		= "getJvmMemoryNonHeapUsageByTimeRange";
	public static final String GET_JVM_MEMORY_USAGE_ROW_COUNT_BY_TIME_RANGE 	= "getJvmMemoryUsageCountByTimeRange";

	public static final String DROP_JVM_MEMORY_TABLE_SEQUENCE 		= "dropJvmMemoryTableSequence";
	public static final String DROP_JVM_MEMORY_TABLE 				= "dropJvmMemoryTable";
	
	//public static final String CORE_JVM_MEMORY_TABLE_SUB_NAME 	= "jvm_memory";
	

	public static String getTableSubName(String jvmName, int serverId){
		return jvmName+"_"+serverId;
	}
	
	public void createTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().insert(CREATE_JVM_MEMORY_TABLE_SEQUENCE, subName);
		SqlMap.getSqlMapClient().insert(CREATE_JVM_MEMORY_TABLE, subName);
	}
	
	public void add(JvmMemory jvmMemory) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_JVM_MEMORY_USAGE, jvmMemory);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceCpu> get() throws SQLException{
		return (ArrayList<ResourceCpu>)SqlMap.getSqlMapClient().queryForList(GET_JVM_MEMORY_USAGE);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JvmMemory> getHeapByTimeRange(JvmMemory jvmMemory) throws SQLException{
		return (ArrayList<JvmMemory>)SqlMap.getSqlMapClient().queryForList(GET_JVM_MEMORY_HEAP_USAGE_BY_TIME_RANGE, jvmMemory);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<JvmMemory> getNonHeapByTimeRange(JvmMemory jvmMemory) throws SQLException{
		return (ArrayList<JvmMemory>)SqlMap.getSqlMapClient().queryForList(GET_JVM_MEMORY_NON_HEAP_USAGE_BY_TIME_RANGE, jvmMemory);
	}
	
	public void dropTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().delete(DROP_JVM_MEMORY_TABLE, subName);
		SqlMap.getSqlMapClient().delete(DROP_JVM_MEMORY_TABLE_SEQUENCE, subName);
	}
	
	public Long getRowCount(JvmMemory jvmMemory) throws SQLException{
		return (Long) SqlMap.getSqlMapClient().queryForObject(GET_JVM_MEMORY_USAGE_ROW_COUNT_BY_TIME_RANGE, jvmMemory);
	}
	
}
