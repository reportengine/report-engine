/**
 * 
 */
package com.redhat.reportengine.server.sql;

import java.io.Reader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;

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
	final private static long CONNECTION_CLOSE_WAIT_TIME = 1000*10;
	private static SqlMapClient sqlMap;
	static{
		initSqlMap();
	}
	private static final void initSqlMap() {
		try {
			String resource = "SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader (resource);
			
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader, System.getProperties());
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
			// This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
	        Enumeration<Driver> drivers = DriverManager.getDrivers();
	        while (drivers.hasMoreElements()) {
	            Driver driver = drivers.nextElement();
	            try {
	                _logger.info(String.format("deregistering jdbc driver: %s", driver));
	            	DriverManager.deregisterDriver(driver);
	                } catch (SQLException e) {
	            	_logger.info(String.format("Error deregistering driver %s", driver), e);
	            }
	        }
			
			if(!sqlMap.getCurrentConnection().isClosed()){
				sqlMap.endTransaction();
				sqlMap.getCurrentConnection().close();
                _logger.info("Initiated Database Connection close...");
                long startTime = new Date().getTime();
				while(!sqlMap.getCurrentConnection().isClosed()){
					if(startTime >= (new Date().getTime() - CONNECTION_CLOSE_WAIT_TIME)){
						Thread.sleep(10);
					}else{
		                _logger.warn("Unable to close the DB connection on the specified time... Terminating...");
		                return;
					}					
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