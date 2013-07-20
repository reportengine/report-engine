/**
 * 
 */
package com.redhat.reportengine.client;

import java.util.Date;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.redhat.reportengine.server.dbmap.TestLogs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 20, 2013
 */
public class Log4jReportEngineLogHandler extends AppenderSkeleton {

	private static long sequenceNumber; 

	private RemoteAPI remoteApi;

	public Log4jReportEngineLogHandler(RemoteAPI remoteApi){
		this.remoteApi = remoteApi;
	}


	@Override
	public void close() throws SecurityException {
		System.err.println("Report Engine Client: called LOG4J Close...");

	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent record) {
		TestLogs testLogs = new TestLogs();		
		testLogs.setLogLevel(record.getLevel().toString());
		testLogs.setLogTime(new Date(record.getTimeStamp()));
		testLogs.setMessage(record.getMessage().toString());
		testLogs.setSequenceNumber(getSequenceNumber());
		testLogs.setClassName(record.getLocationInformation().getClassName());
		testLogs.setMethodName(record.getLocationInformation().getMethodName()+":"+record.getLocationInformation().getLineNumber());
		testLogs.setThrowable(getThrowable(record.getThrowableInformation()));
		try {
			remoteApi.insertLogMessage(testLogs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}

	private static String getThrowable(ThrowableInformation throwableInformation){
		if(throwableInformation != null){
			return ClientCommon.toString(throwableInformation.getThrowable());
		}
		return null;		
	}
	
	private static long getSequenceNumber(){
		return sequenceNumber++;
	}
}
