/**
 * 
 */
package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbmap.DynamicTableName;
import com.redhat.reportengine.server.dbmap.DynamicTableName.TYPE;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 3, 2014
 */
public class DynamicTableNameTable {
	private static Logger _logger = Logger.getLogger(DynamicTableNameTable.class);
	
	public static final String INSERT_DYNAMIC_TABLE_NAME 				= "insertDynamicTableName";
	public static final String GET_DYNAMIC_TABLE_NAME 					= "getDynamicTableName";
	public static final String GET_DYNAMIC_TABLE_NAME_BY_SERVER_ID		= "getDynamicTableNameByServerId";
	public static final String GET_DYNAMIC_TABLE_NAME_BY_ID				= "getDynamicTableNameById";
	public static final String GET_DYNAMIC_TABLE_NAME_BY_SERVER_ID_AND_TYPE		= "getDynamicTableNameByServerIdAndType";
	public static final String DELETE_DYNAMIC_TABLE_NAME_BY_ID			= "deleteDynamicTableNameById";
	public static final String DELETE_DYNAMIC_TABLE_NAME_BY_SERVER_ID	= "deleteDynamicTableNameByServerId";
	public static final String DELETE_DYNAMIC_TABLE_NAME_BY_NAME		= "deleteDynamicTableNameByName";
	public static final String DROP_DYNAMIC_TABLE			= "dropDynamicTable";
	public static final String DROP_DYNAMIC_TABLE_SEQUENCE	= "dropDynamicTableSequence";

	
	public static void add(String name, int serverId, TYPE type) throws SQLException{
		DynamicTableName tableName = new DynamicTableName();
		tableName.setServerId(serverId);
		tableName.setName(name);
		tableName.setTableType(type.toString());
		add(tableName);
	}
	
	public static void add(DynamicTableName tableName)throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_DYNAMIC_TABLE_NAME, tableName);
		_logger.debug("Added a row, "+tableName.getName());
	}
	
	public static DynamicTableName get(String name) throws SQLException{
		return (DynamicTableName) SqlMap.getSqlMapClient().queryForObject(GET_DYNAMIC_TABLE_NAME, name);

	}
	
	public static DynamicTableName getById(int id) throws SQLException{
		return (DynamicTableName) SqlMap.getSqlMapClient().queryForObject(GET_DYNAMIC_TABLE_NAME_BY_ID, id);

	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<DynamicTableName> get(int serverId) throws SQLException{
		return (ArrayList<DynamicTableName>) SqlMap.getSqlMapClient().queryForList(GET_DYNAMIC_TABLE_NAME_BY_SERVER_ID, serverId);

	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<DynamicTableName> get(DynamicTableName tableName) throws SQLException{
		return (ArrayList<DynamicTableName>) SqlMap.getSqlMapClient().queryForList(GET_DYNAMIC_TABLE_NAME_BY_SERVER_ID_AND_TYPE, tableName);
	}
	
	public static ArrayList<DynamicTableName> get(int serverId, TYPE type) throws SQLException{
		DynamicTableName tableName = new DynamicTableName();
		tableName.setServerId(serverId);
		tableName.setTableType(type.toString());
		return get(tableName);
	}
	
	public static void removeById(int id) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_DYNAMIC_TABLE_NAME_BY_ID, id);
	}
	
	public static void removeByName(String name) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_DYNAMIC_TABLE_NAME_BY_NAME, name);
	}
	
	public static void removeByServerId(int serverId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_DYNAMIC_TABLE_NAME_BY_SERVER_ID, serverId);
	}
	
	public static void dropDynamicTable(String tableId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DROP_DYNAMIC_TABLE, tableId);
	}
	
	public static void dropDynamicTableSequence(String tableId) throws SQLException{
		SqlMap.getSqlMapClient().delete(DROP_DYNAMIC_TABLE_SEQUENCE, tableId);
	}
	
	public static void dropDynamicTableAndSequence(String tableId) throws SQLException{
		dropDynamicTable(tableId);
		dropDynamicTableSequence(tableId);
	}
	
	
	
}
