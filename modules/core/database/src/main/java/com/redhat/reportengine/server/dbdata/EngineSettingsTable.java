package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.EngineSettings;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class EngineSettingsTable {

	//Engine Settings
	public static final String GET_ENGINE_SETTINGS_ALL				= "getEngineSettingsAll";
	public static final String GET_ENGINE_SETTINGS_BY_KEY			= "getEngineSettingsByKey";
	public static final String INSERT_ENGINE_SETTINGS				= "insertEngineSettings";
	public static final String UPDATE_ENGINE_SETTINGS				= "updateEngineSettings";
	public static final String DELETE_ENGINE_SETTINGS				= "deleteEngineSettings";
	
	@SuppressWarnings("unchecked")
	public ArrayList<EngineSettings> getAll() throws SQLException{
		return (ArrayList<EngineSettings>) SqlMap.getSqlMapClient().queryForList(GET_ENGINE_SETTINGS_ALL);
	}
	
	public EngineSettings get(String key) throws SQLException{
		return (EngineSettings) SqlMap.getSqlMapClient().queryForObject(GET_ENGINE_SETTINGS_BY_KEY, key);
	}
	
	public void add(EngineSettings engineSettings) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_ENGINE_SETTINGS, engineSettings);
	}
	
	public void modify(EngineSettings engineSettings) throws SQLException{
		SqlMap.getSqlMapClient().update(UPDATE_ENGINE_SETTINGS, engineSettings);
	}
	
	public void remove(String key) throws SQLException{
		SqlMap.getSqlMapClient().delete(DELETE_ENGINE_SETTINGS, key);
	}
}
