package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.ResourceCpu;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 05, 2013
 */
public class ResourceCpuTable {
	public static final String CREATE_CPU_TABLE_SEQUENCE 	= "createCpuTableSequence";
	public static final String CREATE_CPU_TABLE 			= "createCpuTable";
	public static final String INSERT_CPU_USAGE 			= "insertCpuUsage";
	public static final String GET_CPU_USAGE 				= "getCpuUsage";
	public static final String GET_CPU_USAGE_BY_TIME_RANGE 	= "getCpuUsageByTimeRange";
	public static final String GET_CPU_USAGE_ROW_COUNT_BY_TIME_RANGE 	= "getCpuUsageCountByTimeRange";

	public static final String DROP_CPU_TABLE_SEQUENCE 	= "dropCpuTableSequence";
	public static final String DROP_CPU_TABLE 			= "dropCpuTable";
	
		
	public static final String CORE_CPU_TABLE_SUB_NAME 	= "core_cpu";
	public static final String MULTI_CPU_TABLE_SUB_NAME = "multi_cpu";
	
	
	
	public static String getMultiCpuSubName(int serverId, int cpuNo){
		return MULTI_CPU_TABLE_SUB_NAME+"_"+serverId+"_"+cpuNo;
	}
	
	public static String getCoreCpuSubName(int serverId){
		return CORE_CPU_TABLE_SUB_NAME+"_"+serverId;
	}
	
	public void createTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().insert(CREATE_CPU_TABLE_SEQUENCE, subName);
		SqlMap.getSqlMapClient().insert(CREATE_CPU_TABLE, subName);
	}
	
	public void add(ResourceCpu cpu) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_CPU_USAGE, cpu);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceCpu> get(String tableName) throws SQLException{
		return (ArrayList<ResourceCpu>)SqlMap.getSqlMapClient().queryForList(GET_CPU_USAGE, tableName);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ResourceCpu> getByTimeRange(ResourceCpu resourceCpu) throws SQLException{
		return (ArrayList<ResourceCpu>)SqlMap.getSqlMapClient().queryForList(GET_CPU_USAGE_BY_TIME_RANGE, resourceCpu);
	}
	
	public void dropTable(String subName) throws SQLException{
		SqlMap.getSqlMapClient().delete(DROP_CPU_TABLE, subName);
		SqlMap.getSqlMapClient().delete(DROP_CPU_TABLE_SEQUENCE, subName);
	}
	
	public Long getRowCount(ResourceCpu cpu) throws SQLException{
		return (Long) SqlMap.getSqlMapClient().queryForObject(GET_CPU_USAGE_ROW_COUNT_BY_TIME_RANGE, cpu);
	}

}
