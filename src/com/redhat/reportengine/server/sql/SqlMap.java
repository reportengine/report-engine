/**
 * 
 */
package com.redhat.reportengine.server.sql;

import java.io.Reader;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
public class SqlMap {
	final private static Logger _logger = Logger.getLogger(SqlMap.class);
	private static SqlMapClient sqlMap;
	static{
		initSqlMap();
	}
	private static final void initSqlMap() {
		try {
			String resource = "com/redhat/reportengine/ibatis/conf/SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader (resource);
		
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			_logger.info("SQL Client loaded successfully!");
			
		}catch (Exception ex) {
			_logger.error("Error", ex);
		}
	}
	
	public static void setUpSqlClient(){
		if(sqlMap == null){
			initSqlMap();
		}
	}
	
	public static SqlMapClient getSqlMapClient(){
		/*
		if(sqlMap == null){
			initSqlMap();
		}*/
		return sqlMap;
	}
	
	public static void stopSqlMapClient(){
		try {
			if(!sqlMap.getCurrentConnection().isClosed()){
				sqlMap.endTransaction();
				sqlMap.getCurrentConnection().close();
				while(!sqlMap.getCurrentConnection().isClosed()){
					Thread.sleep(10);
				}
				_logger.info("SQL MAP connection is not active!");
				return;
			}
		} catch (SQLException ex) {
			_logger.info("Error on SQL Map connection close!, ", ex);
		} catch (InterruptedException ex) {
			_logger.info("Error on SQL Map connection close!, ", ex);
		}
		_logger.info("SQL MAP connection is closed!");
	}

}