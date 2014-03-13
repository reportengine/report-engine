/**
 * 
 */
package com.redhat.reportengine.agent.rest.mapper.jvm;

import java.io.Serializable;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 9, 2014
 */
public class JVMnotAvailableException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8269929191764477592L;
	
	public JVMnotAvailableException(String message) {
		super(message);
	}

	public JVMnotAvailableException(String message, Throwable th) {
		super(message, th);
	}

}
