/**
 * 
 */
package com.redhat.reportengine.client;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestSuite;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 16, 2012
 */
public class ReportEngineClientTestNGListener implements IResultListener, ISuiteListener {
	protected static Logger _logger = Logger.getLogger(ReportEngineClientTestNGListener.class.getName());

	public ReportEngineClientTestNGListener(boolean donothing){
		//donothing

	}

	public ReportEngineClientTestNGListener(){
		try {
			RemoteAPI.getRemoteApi().initClient(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, "Report Engine Client Failed to start!!", ex);
		}
	}

	protected RemoteAPI getRemoteApi(){
		return RemoteAPI.getRemoteApi();
	}
	
	public String getParametersString(Object[] parameters) {
		String parametersString = "";
		if (parameters != null && parameters.length > 0){
			parametersString = Arrays.deepToString(parameters);
		}			
		return parametersString;
	}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish(ITestContext context) {
		if(getRemoteApi().isClientLoadedSuccess()){
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
		// Test Group
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().insertTestGroup(context.getName());
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onStart,", ex);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().takeScreenShot();
				getRemoteApi().updateTestCase(TestCase.FAILED, ClientCommon.toString(result.getThrowable()));
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onTestFailure,", ex);
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				if(getRemoteApi().isLastTestStateRunning()){
					getRemoteApi().updateTestCase(TestCase.SKIPPED);
				}else{
					getRemoteApi().insertTestCase(result.getName(), result.getName()+"("+getParametersString(result.getParameters())+")", TestCase.SKIPPED);
				}
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onTestSkipped,", ex);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult test) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().insertTestCase(test.getName(), test.getName()+"("+getParametersString(test.getParameters())+")", TestCase.RUNNING);
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onTestStart,", ex);
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().updateTestCase(TestCase.PASSED);
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onTestSuccess,", ex);
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationFailure(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		if(getRemoteApi().isClientLoadedSuccess()){
			if(testResult.getThrowable() == null){
				_logger.log(Level.WARNING, "Configuration Failed!!");
			}else{
				_logger.log(Level.WARNING, "Configuration Failed!!", testResult.getThrowable());
			}			
		}	
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationSkip(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationSkip(ITestResult testResult) {
		if(getRemoteApi().isClientLoadedSuccess()){
			if(testResult.getThrowable() == null){
				_logger.log(Level.WARNING, "Configuration Skipped!!");
			}else{
				_logger.log(Level.WARNING, "Configuration Skipped!!", testResult.getThrowable());
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationSuccess(ITestResult testResult) {
		if(getRemoteApi().isClientLoadedSuccess()){
			_logger.log(Level.FINE, "Configuration loadded Successfully!!");
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onFinish(org.testng.ISuite)
	 */
	@Override
	public void onFinish(ISuite suite) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().updateTestSuite(TestSuite.COMPLETED, System.getProperty(getRemoteApi().getBuildVersionReference()));
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onFinish,", ex);
			}	
			getRemoteApi().removeHandlers();
		}	
	}

	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onStart(org.testng.ISuite)
	 */
	@Override
	public void onStart(ISuite suite) {
		if(getRemoteApi().isClientLoadedSuccess()){
			try {
				getRemoteApi().runLogHandler();
				getRemoteApi().updateTestSuiteName(suite.getName());				
			} catch (Exception ex) {
				_logger.log(Level.SEVERE, "Error on onStart,", ex);
			}	
		}
	}
}
