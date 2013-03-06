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
	private static RemoteAPI reportEngine = new RemoteAPI();

	public ReportEngineClientTestNGListener(){
		try {
			reportEngine.initClient(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, "Report Engine Client Failed to start!!", ex);
		}
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
		if(RemoteAPI.isClientLoadedSuccess()){
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
		// Test Group
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.insertTestGroup(context.getName());
			} catch (Exception ex) {
				ex.printStackTrace();
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
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.takeScreenShot();
				reportEngine.updateTestCase(TestCase.FAILED, ClientCommon.toString(result.getThrowable()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				if(reportEngine.isLastTestStateRunning()){
					reportEngine.updateTestCase(TestCase.SKIPPED);
				}else{
					reportEngine.insertTestCase(result.getName(), result.getName()+"("+getParametersString(result.getParameters())+")", TestCase.SKIPPED);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult test) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.insertTestCase(test.getName(), test.getName()+"("+getParametersString(test.getParameters())+")", TestCase.RUNNING);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.updateTestCase(TestCase.PASSED);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationFailure(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		if(RemoteAPI.isClientLoadedSuccess()){
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
		if(RemoteAPI.isClientLoadedSuccess()){
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
		if(RemoteAPI.isClientLoadedSuccess()){
			_logger.log(Level.FINE, "Configuration loadded Successfully!!");
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onFinish(org.testng.ISuite)
	 */
	@Override
	public void onFinish(ISuite suite) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.updateTestSuite(TestSuite.COMPLETED, System.getProperty(reportEngine.getBuildVersionReference()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}	
	}

	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onStart(org.testng.ISuite)
	 */
	@Override
	public void onStart(ISuite suite) {
		if(RemoteAPI.isClientLoadedSuccess()){
			try {
				reportEngine.runLogHandler();
				reportEngine.updateTestSuiteName(suite.getName());				
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
}
