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
			_logger.log(Level.SEVERE, "Failed to start!!", ex);
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
		if(reportEngine.isClientConfigurationSuccess()){
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
		// Test Group
		if(reportEngine.isClientConfigurationSuccess()){
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
		if(reportEngine.isClientConfigurationSuccess()){
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
		if(reportEngine.isClientConfigurationSuccess()){
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
		if(reportEngine.isClientConfigurationSuccess()){
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
		if(reportEngine.isClientConfigurationSuccess()){
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
	public void onConfigurationFailure(ITestResult arg0) {
		if(reportEngine.isClientConfigurationSuccess()){
			reportEngine.runLogHandler();
		}	
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationSkip(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationSkip(ITestResult arg0) {
		if(reportEngine.isClientConfigurationSuccess()){
			reportEngine.runLogHandler();
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.internal.IConfigurationListener#onConfigurationSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onConfigurationSuccess(ITestResult arg0) {
		if(reportEngine.isClientConfigurationSuccess()){
			reportEngine.runLogHandler();
		}		
	}

	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onFinish(org.testng.ISuite)
	 */
	@Override
	public void onFinish(ISuite suite) {
		if(reportEngine.isClientConfigurationSuccess()){
			try {
				reportEngine.updateTestSuite(TestSuite.COMPLETED, reportEngine.getBuildVersionReference());
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
		if(reportEngine.isClientConfigurationSuccess()){
			try {
				reportEngine.updateTestSuiteName(suite.getName());
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
}
