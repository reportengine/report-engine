/**
 * 
 */
package com.redhat.reportengine.server.gui;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.redhat.reportengine.server.ServerSettings;
import com.redhat.reportengine.server.dbmap.FileStorage;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 23, 2012
 */
public class GetAjaxData {
	
	public void getScreenShotImage(HttpServletResponse response, String imageId) throws IOException, SQLException{
		int id = Integer.valueOf(imageId);
		FileStorage fileStorage = (FileStorage) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_FILE_STORAGE_BY_ID, id);

		/*
		File destFile = new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+fileStorage.getFileName());
		if (!destFile.exists()) {
			new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()).mkdir();
            destFile.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(destFile);
		out.write(fileStorage.getFileByte());
		out.close();
		
		System.out.println("File Created: "+ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+fileStorage.getFileName());
		File screenShot = new File(ServerSettings.getBaseLocation()+"/"+ServerSettings.getScreenShotFileLocation()+"/"+fileStorage.getFileName());
		byte[] screenShotByte = new byte[(int) screenShot.length()];
		FileInputStream in = new FileInputStream(screenShot);
		in.read(screenShotByte);
		in.close();
		*/
		
		response.setContentType("image/jpeg");
		//response.getOutputStream().write(screenShotByte);
		response.getOutputStream().write(fileStorage.getFileByte());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return;
	}
	
}
