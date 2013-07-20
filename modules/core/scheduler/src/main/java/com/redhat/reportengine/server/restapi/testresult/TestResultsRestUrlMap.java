/**
 * 
 */
package com.redhat.reportengine.server.restapi.testresult;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 28, 2012
 */
public class TestResultsRestUrlMap {
	public static final String DATE_FORMAT	= "dd-MMM-yyyy, HH:mm:ss.S, z";
	
	public static final String ROOT 							= "/testresults";
	
	public static final String GET_SERVER_TIME 					= "/servertime";
	public static final String INSERT_TESTSUITE 				= "/testsuite";
	public static final String UPDATE_TESTSUITE_NAME 			= "/testsuitename";
	public static final String UPDATE_TESTSUITE 				= "/testsuite";
	public static final String GET_TESTSUITE_NEXT_AVAILABLE_ID 	= "/testsuiteid";
	public static final String GET_TESTSUITE_BY_ID 				= "/testsuite";
	public static final String GET_TESTSUITE_BY_TESTSUITE 		= "/testsuitebysuite";
	public static final String INSERT_TESTGROUP 				= "/testgroup";
	public static final String INSERT_TESTCASE 					= "/testcase";
	public static final String UPDATE_TESTCASE 					= "/testcase";
	public static final String INSERT_TESTLOG 					= "/testlog";
	
}
