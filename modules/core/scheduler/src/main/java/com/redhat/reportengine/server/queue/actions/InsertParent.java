package com.redhat.reportengine.server.queue.actions;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 31, 2013
 */
public class InsertParent {
	private static boolean stopMe = false;
	private static boolean stopMeImmeditate = false;
	
	public static boolean isStopMe() {
		return stopMe;
	}
	public static void setStopMe(boolean stopMe) {
		InsertParent.stopMe = stopMe;
	}
	public static boolean isStopMeImmeditate() {
		return stopMeImmeditate;
	}
	public static void setStopMeImmeditate(boolean stopMeImmeditate) {
		InsertParent.stopMeImmeditate = stopMeImmeditate;
	}
}
