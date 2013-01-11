/**
 * 
 */
package com.redhat.reportengine.server.queue;

import java.util.LinkedList;

import com.redhat.reportengine.server.dbmap.TestLogs;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 2, 2012
 */
public class TestLogsQueue {

	private static LinkedList<TestLogs> testLogsQueue = new LinkedList<TestLogs>();

	public static synchronized int getTestLogSize(){
		return testLogsQueue.size();
	}
	
	public static synchronized TestLogs getFirstTestLog(){
		TestLogs testLog = testLogsQueue.getFirst();
		testLogsQueue.removeFirst();
		return testLog;
	}
	
	public static synchronized void addTestLog(TestLogs testLog){
		testLogsQueue.addLast(testLog);
	}
}
