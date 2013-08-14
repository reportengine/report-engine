/**
 * 
 */
package com.redhat.reportengine.server.queue.actions;

import java.util.LinkedList;

import org.apache.log4j.Logger;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 3, 2012
 */
public class ManageQueues {
	final private static Logger _logger = Logger.getLogger(ManageQueues.class);
	private static LinkedList<Thread> threads = new LinkedList<Thread>();
	public static void startAllQueueManagers(){
		threads.addLast(new Thread(new InsertUpdateTestLogs()));
		threads.addLast(new Thread(new InsertUdpPackets()));
		
		for(Thread thread : threads){
			thread.start();
		}
	}
	
	private static boolean isAnyRunningThreadAvailable(){
		for(Thread thread : threads){
			if(thread.isAlive()){
				return true;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				_logger.error("Error on thread sleep, ", ex);
			}
		}
		return false;
	}
	
	public static void stopAllQueueManagers(){
		InsertUpdateTestLogs.setStopMe(true);
		while(isAnyRunningThreadAvailable());
	}
	
	public static void stopAllQueueManagersImmediate(){
		InsertUpdateTestLogs.setStopMeImmeditate(true);
		while(isAnyRunningThreadAvailable());
	}
}
