/**
 * 
 */
package com.redhat.reportengine.client;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.redhat.reportengine.server.dbmap.TestLogs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 28, 2012
 */
public class JulReportEngineLogHandler extends Handler {
	
	private RemoteAPI remoteApi;
	
	public JulReportEngineLogHandler(RemoteAPI remoteApi){
		this.remoteApi = remoteApi;
	}
	
	/* (non-Javadoc)
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(LogRecord record) {
		TestLogs testLogs = new TestLogs();		
		testLogs.setLogLevel(record.getLevel().getName());
		testLogs.setLogTime(new Date(record.getMillis()));
		testLogs.setMessage(record.getMessage());
		testLogs.setSequenceNumber(record.getSequenceNumber());
		testLogs.setClassName(record.getSourceClassName());
		testLogs.setMethodName(record.getSourceMethodName());
		testLogs.setThrowable(ClientCommon.toString(record.getThrown()));
		try {
			remoteApi.insertLogMessage(testLogs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush() {
		System.err.println("Report Engine Client: called JUL Flush...");
		
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() throws SecurityException {
		System.err.println("Report Engine Client: called JUL Close...");
		
	}
}
