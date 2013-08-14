/**
 * 
 */
package com.redhat.reportengine.server.restapi.testresult;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.reportengine.restapi.urimap.TestResultsRestUriMap;
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

@Path(TestResultsRestUriMap.ROOT)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

public class TestResultsREST {	 

	@GET
	@Path(TestResultsRestUriMap.GET_SERVER_TIME)
	public String getServerTime(){
		return new SimpleDateFormat(TestResultsRestUriMap.DATE_FORMAT).format(new TestResult().getServerTime());
	}	
	
	@POST
	@Path(TestResultsRestUriMap.INSERT_TESTSUITE)
	public void insertTestSuite(TestSuite testSuite) throws TestResultException{
		new TestResult().insertTestSuite(testSuite);
	}	
	
	@PUT
	@Path(TestResultsRestUriMap.UPDATE_TESTSUITE_NAME)
	public void updateTestSuiteName(TestSuite testSuite) throws TestResultException{
		new TestResult().updateTestSuiteName(testSuite);
	}
	
	@PUT
	@Path(TestResultsRestUriMap.UPDATE_TESTSUITE)
	public void updateTestSuite(TestSuite testSuite) throws TestResultException{
		new TestResult().updateTestSuite(testSuite);
	}
	
	@GET
	@Path(TestResultsRestUriMap.GET_TESTSUITE_NEXT_AVAILABLE_ID)
	public String getTestSuiteNextAvailableId() throws TestResultException{
		return new Integer(new TestResult().getTestSuiteNextAvailableId()).toString();
	}
	
	@GET
	@Path(TestResultsRestUriMap.GET_TESTSUITE_BY_ID+"/{id}")
	public TestSuite getTestSuite(@PathParam("id") int id) throws TestResultException{
		return new TestResult().getTestSuite(id);
	}

	
	@POST
	@Path(TestResultsRestUriMap.GET_TESTSUITE_BY_TESTSUITE)
	public TestSuite getTestSuite(TestSuite testSuite) throws TestResultException{
		//System.out.println("TestSuite:id:"+testSuite.getId()+"TestSuite:REF:"+testSuite.getTestReference());
		return new TestResult().getTestSuite(testSuite);
	}
	
	@POST
	@Path(TestResultsRestUriMap.INSERT_TESTGROUP)
	public TestGroup insertTestGroup(TestGroup testGroup) throws TestResultException{
		return new TestResult().insertTestGroup(testGroup);
	}
	
	@POST
	@Path(TestResultsRestUriMap.INSERT_TESTCASE)
	public TestCase insertTestCase(TestCase testCase) throws TestResultException{	
		testCase.setId(new TestResult().insertTestCase(testCase));
		return testCase;
	}
	
	@PUT
	@Path(TestResultsRestUriMap.UPDATE_TESTCASE)
	public void updateTestCase(TestCase testCase) throws TestResultException, IOException{
		new TestResult().updateTestCase(testCase);
	}
	
	@POST
	@Path(TestResultsRestUriMap.INSERT_TESTLOG)
	public void insertTestLog(TestLogs testLogs) throws TestResultException{
		new TestResult().insertTestLog(testLogs);
	}	
	
}
