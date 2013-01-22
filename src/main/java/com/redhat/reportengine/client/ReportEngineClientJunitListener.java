package com.redhat.reportengine.client;

import java.net.InetAddress;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestSuite;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 20, 2013
 */
public class ReportEngineClientJunitListener extends RunListener{
	private static RemoteAPI reportEngineClientAPI = new RemoteAPI();
	
	public ReportEngineClientJunitListener() {
		try {
			reportEngineClientAPI.initClient(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");
		} catch (Exception e) {
			System.out.println("Report Engine Client: failed to start!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Will be called before any tests have been run. 
	 * */
	public void testRunStarted(Description description){
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			reportEngineClientAPI.runLogHandler();
			try {
				if(description.getDisplayName() != null){
					reportEngineClientAPI.updateTestSuiteName(description.getDisplayName());
				}		
				reportEngineClientAPI.insertTestGroup("Junit - No groups");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 *  Will be called when all tests have finished 
	 * */
	public void testRunFinished(Result result) {
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			try {
				reportEngineClientAPI.updateTestSuite(TestSuite.COMPLETED, getBuildVersion());
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}

	/**
	 *  Will be called when an atomic test is about to be started. 
	 * */
	public void testStarted(Description description) {
		System.out.println("Starting execution of test case : "+ description.getMethodName());
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			try {
				reportEngineClientAPI.insertTestCase(description.getMethodName(), description.getClassName()+"."+description.getMethodName(), TestCase.RUNNING);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
	}

	/**
	 *  Will be called when an atomic test has finished, whether the test succeeds or fails. 
	 * */
	public void testFinished(Description description) {
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			try {
				reportEngineClientAPI.updateTestCase(TestCase.PASSED);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 *  Will be called when an atomic test fails. 
	 * */
	public void testFailure(Failure failure) {
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			try {
				reportEngineClientAPI.takeScreenShot();
				reportEngineClientAPI.updateTestCase(TestCase.FAILED, ClientCommon.toString(failure.getException()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	
	}

	/**
	 *  Will be called when a test will not be run, generally because a test method is annotated with Ignore. 
	 * */
	public void testIgnored(Description description) {
		System.out.println("Execution of test case ignored : "+ description.getMethodName());
		if(reportEngineClientAPI.isClientConfigurationSuccess()){
			try {
				if(reportEngineClientAPI.isLastTestStateRunning()){
					reportEngineClientAPI.updateTestCase(TestCase.SKIPPED);
				}else{
					reportEngineClientAPI.insertTestCase(description.getMethodName(), description.getClassName()+"."+description.getMethodName(), TestCase.SKIPPED);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the buildVersion
	 */
	public static String getBuildVersion() {
		return System.getProperty(reportEngineClientAPI.getBuildVersionReference());
	}

}
