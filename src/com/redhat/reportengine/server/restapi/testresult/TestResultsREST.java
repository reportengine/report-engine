/**
 * 
 */
package com.redhat.reportengine.server.restapi.testresult;

import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.reportengine.server.api.TestResult;
import com.redhat.reportengine.server.api.TestResultException;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.dbmap.TestLogs;
import com.redhat.reportengine.server.dbmap.TestSuite;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 18, 2012
 */

@Path(TestResultsRestUrlMap.ROOT)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

public class TestResultsREST {	

	@GET
	@Path(TestResultsRestUrlMap.GET_SERVER_TIME)
	public String getServerTime(){
		return new SimpleDateFormat(TestResultsRestUrlMap.DATE_FORMAT).format(new TestResult().getServerTime());
	}	
	
	@POST
	@Path(TestResultsRestUrlMap.INSERT_TESTSUITE)
	public void insertTestSuite(TestSuite testSuite) throws TestResultException{
		new TestResult().insertTestSuite(testSuite);
	}	
	
	@PUT
	@Path(TestResultsRestUrlMap.UPDATE_TESTSUITE_NAME)
	public void updateTestSuiteName(TestSuite testSuite) throws TestResultException{
		new TestResult().updateTestSuiteName(testSuite);
	}
	
	@PUT
	@Path(TestResultsRestUrlMap.UPDATE_TESTSUITE)
	public void updateTestSuite(TestSuite testSuite) throws TestResultException{
		new TestResult().updateTestSuite(testSuite);
	}
	
	@GET
	@Path(TestResultsRestUrlMap.GET_TESTSUITE_NEXT_AVAILABLE_ID)
	public String getTestSuiteNextAvailableId() throws TestResultException{
		//return new TestResult().getTestSuiteNextAvailableId();
		return new Integer(321).toString();
	}
	
	@GET
	@Path(TestResultsRestUrlMap.GET_TESTSUITE_BY_ID+"/{id}")
	public TestSuite getTestSuite(@PathParam("id") int id) throws TestResultException{
		return new TestResult().getTestSuite(id);
	}

	
	@POST
	@Path(TestResultsRestUrlMap.GET_TESTSUITE_BY_TESTSUITE)
	public TestSuite getTestSuite(TestSuite testSuite) throws TestResultException{
		//System.out.println("TestSuite:id:"+testSuite.getId()+"TestSuite:REF:"+testSuite.getTestReference());
		return new TestResult().getTestSuite(testSuite);
	}
	
	@POST
	@Path(TestResultsRestUrlMap.INSERT_TESTGROUP)
	public TestGroup insertTestGroup(TestGroup testGroup) throws TestResultException{
		return new TestResult().insertTestGroup(testGroup);
	}
	
	@POST
	@Path(TestResultsRestUrlMap.INSERT_TESTCASE)
	public String insertTestCase(TestCase testCase) throws TestResultException{		
		return new Integer(new TestResult().insertTestCase(testCase)).toString();
	}
	
	@PUT
	@Path(TestResultsRestUrlMap.UPDATE_TESTCASE)
	public void updateTestCase(TestCase testCase) throws TestResultException{
		new TestResult().updateTestCase(testCase);
	}
	
	@POST
	@Path(TestResultsRestUrlMap.INSERT_TESTLOG)
	public void insertTestLog(TestLogs testLogs) throws TestResultException{
		new TestResult().insertTestLog(testLogs);
	}	
	
}
