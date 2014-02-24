/**
 * 
 */
package com.redhat.reportengine.server.collection;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.redhat.reportengine.server.dbdata.DynamicTableNameTable;
import com.redhat.reportengine.server.dbmap.DynamicTableName;
import com.redhat.reportengine.server.dbmap.DynamicTableName.TYPE;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 3, 2014
 */
public class DynamicTable {
	private static LinkedHashMap<String, DynamicTableName> tableNames = new LinkedHashMap<String, DynamicTableName>();
	
	public static DynamicTableName get(String name, int serverId, TYPE tableType) throws SQLException{
		if(tableNames.get(name) == null){
			DynamicTableName tableName = DynamicTableNameTable.get(name);
			if(tableName != null){
				tableNames.put(tableName.getName(), tableName);
			}else{
				tableName = new DynamicTableName();
				tableName.setName(name);
				tableName.setServerId(serverId);
				tableName.setTableType(tableType.toString());
				add(tableName);
			}
		}
		return tableNames.get(name);
	}
	
	public static void add(DynamicTableName tableName) throws SQLException{
		DynamicTableNameTable.add(tableName);
		tableName = DynamicTableNameTable.get(tableName.getName());
		tableNames.put(tableName.getName(), tableName);
	}
}
