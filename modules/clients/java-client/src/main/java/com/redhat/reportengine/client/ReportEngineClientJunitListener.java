package com.redhat.reportengine.client;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	protected static Logger _logger = Logger.getLogger(ReportEngineClientJunitListener.class.getName());
	private static RemoteAPI reportEngineClientAPI = new RemoteAPI();

	public ReportEngineClientJunitListener() {
		try {
			reportEngineClientAPI.initClient(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, "Report Engine Client Failed to start!!", ex);
		}
	}

	/**
	 * Will be called before any tests have been run. 
	 * */
	public void testRunStarted(Description description){
		if(RemoteAPI.isClientLoadedSuccess()){
			reportEngineClientAPI.runLogHandler();
			try {
				if(description != null){
					if(description.getDisplayName() != null){
						reportEngineClientAPI.updateTestSuiteName(description.getDisplayName());
						return;
					}
				}
				reportEngineClientAPI.insertTestGroup("Junit - No groups");
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testRunStarted,", ex);
			}
		}
	}

	/**
	 *  Will be called when all tests have finished 
	 * */
	public void testRunFinished(Result result) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngineClientAPI.updateTestSuite(TestSuite.COMPLETED, System.getProperty(reportEngineClientAPI.getBuildVersionReference()));
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testRunFinished,", ex);
			}	
		}
	}

	/**
	 *  Will be called when an atomic test is about to be started. 
	 * */
	public void testStarted(Description description) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngineClientAPI.insertTestCase(description.getMethodName(), description.getClassName()+"."+description.getMethodName(), TestCase.RUNNING);
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testStarted,", ex);
			}
		}		
	}

	/**
	 *  Will be called when an atomic test has finished, whether the test succeeds or fails. 
	 * */
	public void testFinished(Description description) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngineClientAPI.updateTestCase(TestCase.PASSED);
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testFinished,", ex);
			}
		}
	}

	/**
	 *  Will be called when an atomic test fails. 
	 * */
	public void testFailure(Failure failure) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngineClientAPI.takeScreenShot();
				reportEngineClientAPI.updateTestCase(TestCase.FAILED, ClientCommon.toString(failure.getException()));
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testFailure,", ex);
			}
		}	
	}

	/**
	 *  Will be called when a test will not be run, generally because a test method is annotated with Ignore. 
	 * */
	public void testIgnored(Description description) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				if(reportEngineClientAPI.isLastTestStateRunning()){
					reportEngineClientAPI.updateTestCase(TestCase.SKIPPED);
				}else{
					reportEngineClientAPI.insertTestCase(description.getMethodName(), description.getClassName()+"."+description.getMethodName(), TestCase.SKIPPED);
				}
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on testIgnored,", ex);
			}
		}
	}
}
