package com.redhat.reportengine.server.dbdata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 16, 2013
 */
public class FileStorageTable {
	private final static String INSERT_FILE_STORAGE								= "insertFileStorage";
	private final static String GET_FILE_STORAGE_BY_TEST_CASE_ID_SCREEN_SHOT	= "getFileStorageByTestCaseIdScreenShot";
	private final static String GET_SCREEN_SHOT_FILES							= "getScreenShotFiles";
	private final static String GET_FILE_STORAGE_BY_ID							= "getFileStorageById";
	private final static String GET_FILE_STORAGE_BY_TEST_CASE_ID				= "getFileStorageByTestCaseId";
	
	
	public void add(FileStorage fileStorage) throws SQLException{
		SqlMap.getSqlMapClient().insert(INSERT_FILE_STORAGE, fileStorage);
	}
	
	public FileStorage get(Integer id) throws SQLException{
		return (FileStorage)SqlMap.getSqlMapClient().queryForObject(GET_FILE_STORAGE_BY_ID, id);
	}
	
	public FileStorage getByTestCaseId(Integer testCaseId) throws SQLException{
		return (FileStorage)SqlMap.getSqlMapClient().queryForObject(GET_FILE_STORAGE_BY_TEST_CASE_ID, testCaseId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<FileStorage> getScreenShotFile(Integer testCaseId) throws SQLException{
		return (ArrayList<FileStorage>)SqlMap.getSqlMapClient().queryForList(GET_FILE_STORAGE_BY_TEST_CASE_ID_SCREEN_SHOT, testCaseId);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestCase> getScreenShotFilesAll() throws SQLException{
		return (ArrayList<TestCase>)SqlMap.getSqlMapClient().queryForList(GET_SCREEN_SHOT_FILES);
	}
}
