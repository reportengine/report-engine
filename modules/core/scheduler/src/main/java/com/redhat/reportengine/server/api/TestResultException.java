/**
 * 
 */
package com.redhat.reportengine.server.api;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 16, 2012
 */
public class TestResultException extends Exception{
	private static final long serialVersionUID = 2799588582720718560L;
	
	public TestResultException(String message) {
		super(message);
	}

	public TestResultException(String message, Throwable th) {
		super(message, th);
	}
}
