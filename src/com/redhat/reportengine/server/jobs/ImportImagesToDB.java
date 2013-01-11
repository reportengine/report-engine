package com.redhat.reportengine.server.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.ServerSettings;
import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

public class ImportImagesToDB {
	private static String screenShotLocation = "/opt/jeeva/temp";
	private static Logger _logger = Logger.getLogger(ImportImagesToDB.class);
	public static void importImagesToDatabase() throws SQLException, IOException{
		ArrayList<TestCase> testCases = (ArrayList<TestCase>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_SCREEN_SHOT_FILES);
		FileStorage fileStorage = new FileStorage();
		File screenShot;
		for(TestCase testCase : testCases){
			fileStorage.setCreationTime(new Date());
			fileStorage.setFileName(testCase.getTestGuiFiles());
			fileStorage.setTestCaseId(testCase.getId());
			fileStorage.setScreenShot(true);
			//screenShot = new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+testCase.getTestGuiFiles());
			screenShot = new File(screenShotLocation+"/"+testCase.getTestGuiFiles());
			if(!screenShot.exists()){
				_logger.warn("File Not Found: "+screenShotLocation+"/"+testCase.getTestGuiFiles());
			}else{
				
				byte[] screenShotByte = new byte[(int) screenShot.length()];
				FileInputStream in = new FileInputStream(screenShot);
				in.read(screenShotByte );
				in.close();
				
				//byte[] screenShotByte = new byte[(int) screenShot.length()];
				fileStorage.setFileByte(screenShotByte);
				SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_FILE_STORAGE, fileStorage);		
			}
		}
	}
}
