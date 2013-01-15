/**
 * 
 */
package com.redhat.reportengine.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.InetAddress;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;


import com.redhat.reportengine.common.ClientRMI;
//import com.redhat.reportengine.common.RemoteCall;
import com.redhat.reportengine.server.dbmap.TestCase;
import com.redhat.reportengine.server.dbmap.TestGroup;
import com.redhat.reportengine.server.dbmap.TestLogs;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.restapi.testresult.TestResultsRestUrlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 16, 2012
 */
public class RemoteAPI {

	private static Logger sunLogger = Logger.getLogger("sun.rmi");  
	/*   
	 static {  
	        sunLogger.setLevel(Level.WARNING);  

	    }  
	 */
	ClientCommon test = new ClientCommon();
	protected static boolean initialized = false;
	private boolean clientConfigurationSuccess = false;
	private static final String rePropertyFile 		= "REPORT_ENGINE_PROPERTY_FILE";	
	private static final String originalFile 		= "ORIGINAL.FILE";
	private static final String remoteServer 		= "REPORT.ENGINE.SERVER";
	private static final String rmiPort 			= "REPORT.ENGINE.RMI.PORT";
	private static final String testReference 		= "REPORT.ENGINE.TEST.REFERENCE";
	private static final String clientTempLocation 	= "REPORT.ENGINE.CLIENT.TEMP";
	private static final String screenShot		 	= "REPORT.ENGINE.TAKE.SCREEN.SHOT";
	private static final String buildVersion	 	= "REPORT.ENGINE.TEST.BUILD.VERSION.REFF";
	private static final String watchLogger	 		= "REPORT.ENGINE.WATCH.LOGGER";
	private static final String loggerType	 		= "REPORT.RENGINE.LOGGER.TYPE";
	private static final String logLevel	 		= "REPORT.ENGINE.LOGGER.LEVEL";
	
	private RESTClientRestEasy restClient = null;


	public boolean isLastTestStateRunning(){
		if(test.getTestCaseId() != null){
			return true;
		}
		return false;		
	}

	public String getBuildVersionReference(){
		return test.getBuildVersionReference();
	}
	public Date getServerTime() throws Exception{
		//return (Date) RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_GET_SERVER_TIME);
		return new SimpleDateFormat(TestResultsRestUrlMap.DATE_FORMAT).parse((String)restClient.get(TestResultsRestUrlMap.GET_SERVER_TIME, String.class));
	}

	private void setAllVariables(){
		try{
			Properties propertyFile = new Properties();
			String primaryPropertyFile = null;

			System.out.println("Report Engine Client: Properties File: System.getenv: "+System.getenv(rePropertyFile));
			System.out.println("Report Engine Client: Properties File: System.getProperty: "+System.getProperty(rePropertyFile));

			//Check Property file by Env path
			if(System.getenv(rePropertyFile) != null){
				primaryPropertyFile = System.getenv(rePropertyFile).trim(); 
			}else if(System.getProperty(rePropertyFile) != null){ //Check Property file by System Property
				primaryPropertyFile = System.getProperty(rePropertyFile).trim(); 
			}else{ //Check Property file by jar location
				CodeSource codeSource = RemoteAPI.class.getProtectionDomain().getCodeSource();
				File jarFile = new File(codeSource.getLocation().toURI().getPath());
				File jarDir = jarFile.getParentFile();

				if (jarDir != null && jarDir.isDirectory()) {
					primaryPropertyFile = jarDir+"/ReportEngineClient.properties"; 

				}
			}

			System.out.println("Report Engine Client: Properties File: "+primaryPropertyFile);
			if(primaryPropertyFile != null){
				propertyFile.load(new FileReader(primaryPropertyFile));
			}else{
				this.setClientConfigurationSuccess(false);
				System.out.println("Report Engine Client: Could not find report engine properties File any where, Please update property file by atleast one of the way, Disabled Report Engine logs!!");			
			}


			if((propertyFile.getProperty(originalFile) != null) && propertyFile.getProperty(originalFile).trim().length() > 0){
				String newPropertyFile = propertyFile.getProperty(originalFile).trim();
				System.out.println("Report Engine Client: Properties File(New): "+originalFile);
				propertyFile.clear();
				propertyFile.load(new FileReader(newPropertyFile));
			}

			if(System.getProperty(remoteServer) != null){
				test.setServerIp(System.getProperty(remoteServer).trim());
			}else{
				test.setServerIp(propertyFile.getProperty(remoteServer).trim());
			}

			if(System.getProperty(rmiPort) != null){
				test.setServerRMIPort(System.getProperty(rmiPort).trim());
			}else{
				test.setServerRMIPort(propertyFile.getProperty(rmiPort).trim());
			}

			if(System.getProperty(testReference) != null){
				test.setTestReference(System.getProperty(testReference).trim());
			}else{
				test.setTestReference(propertyFile.getProperty(testReference).trim());
			}

			if(System.getProperty(clientTempLocation) != null){
				test.setClientTempLocation(System.getProperty(clientTempLocation).trim());
			}else{
				test.setClientTempLocation(propertyFile.getProperty(clientTempLocation).trim());
			}

			if(System.getProperty(screenShot) != null){
				if(System.getProperty(screenShot).trim().equalsIgnoreCase("TRUE")){
					test.setTakeScreenShot(true);
				}else{
					test.setTakeScreenShot(false);
				}

			}else{
				if(propertyFile.getProperty(screenShot).trim().equalsIgnoreCase("TRUE")){
					test.setTakeScreenShot(true);
				}else{
					test.setTakeScreenShot(false);
				}
			}

			if(System.getProperty(watchLogger) != null){
				if(System.getProperty(watchLogger).trim().equalsIgnoreCase("TRUE")){
					test.setLoggerEnabled(true);
				}else{
					test.setLoggerEnabled(false);
				}

			}else{
				if(propertyFile.getProperty(watchLogger).trim().equalsIgnoreCase("TRUE")){
					test.setLoggerEnabled(true);
				}else{
					test.setLoggerEnabled(false);
				}
			}

			if(System.getProperty(loggerType) != null){
				test.setLoggerType(System.getProperty(loggerType).trim());
			}else{
				test.setLoggerType(propertyFile.getProperty(loggerType).trim());
			}

			if(System.getProperty(logLevel) != null){
				test.setLogLevel(System.getProperty(logLevel).trim());
			}else{
				test.setLogLevel(propertyFile.getProperty(logLevel).trim());
			}		

			if(System.getProperty(buildVersion) != null){
				test.setBuildVersionReference(System.getProperty(buildVersion).trim());
			}else{
				test.setBuildVersionReference(propertyFile.getProperty(buildVersion).trim());
			}

			this.setClientConfigurationSuccess(true);
		}catch(Exception ex){
			this.setClientConfigurationSuccess(false);
			ex.printStackTrace();
		}

	}

	public void initClient(String testSuiteName) throws Exception{
		restClient = new RESTClientRestEasy();
		initClient(testSuiteName, null);
	}
	public void initClient(String testSuiteName, String comments) throws Exception{
		if(!initialized){
			doInitialize(testSuiteName, comments);
			if(this.isClientConfigurationSuccess()){
				runLogHandler();
			}
			initialized = true;
		}
	}

	public void runLogHandler(){
		if(test.isLoggerEnabled()){
			LogHandler.setRemoteApi(this);
			LogHandler.initLogger(); // Initialize Log watcher
			System.out.println("Report Engine Client: Enabled Watch Logger, Logger Type: "+test.getLoggerType());
		}	
		sunLogger.setLevel(Level.WARNING);  
		System.out.println("Report Engine Client: sun.rmi log level set to WARNING");
	}


	private void doInitialize(String testSuiteName, String comments) throws Exception{
		setAllVariables();		
		if(this.isClientConfigurationSuccess()){
			restClient.setServerUrl("https://mercury.lab.eng.pnq.redhat.com:8080/re/resteasy");
			restClient.setRootUrl("/testresults");
			System.out.println("Report Engine Client: Report Engine Server URL: "+"//"+test.getServerIp()+":"+test.getServerRMIPort()+"/"+ClientRMI.CLIENT_RMI_PACK);
			//test.setServerObject(RemoteCall.getRemoteObject("//"+test.getServerIp()+":"+test.getServerRMIPort()+"/", ClientRMI.CLIENT_RMI_PACK));
			//System.out.println("Report Engine Client: Server Time: "+RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_GET_SERVER_TIME));
			this.insertTestSuite(testSuiteName, comments);
			System.out.println("Report Engine Client: Report Engine Client Loaded successfully");
		}else{
			System.out.println("Report Engine Client: Report Engine Client Failed to Load!!");
		}		
	}

	public void insertTestSuite(String TestSuitName) throws Exception{
		insertTestSuite(TestSuitName, null);
	}

	public void insertTestSuite(String TestSuitName, String comments) throws Exception{
		//test.setTestSuiteId((Integer)RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_GET_TEST_SUITE_ID));
		test.setTestSuiteId(Integer.valueOf((String)restClient.get(TestResultsRestUrlMap.GET_TESTSUITE_NEXT_AVAILABLE_ID, String.class)));
		TestSuite testSuite = new TestSuite();
		testSuite.setId(test.getTestSuiteId());
		testSuite.setRemoteStartTime(new Date());
		testSuite.setTestComments(comments);
		testSuite.setTestStatus(TestSuite.RUNNING);
		testSuite.setTestSuiteName(TestSuitName);
		testSuite.setTestReference(test.getTestReference());
		testSuite.setTestHost(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");	
		//RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_INSERT_TEST_SUITE, testSuite);
		restClient.post(TestResultsRestUrlMap.INSERT_TESTSUITE, testSuite, TestSuite.class);
	}
	
	public void updateTestSuiteName(String testSuiteName) throws Exception{
		updateTestSuiteName(testSuiteName, null);
	}
	
	public void updateTestSuiteName(String testSuiteName, String comments) throws Exception{
		TestSuite testSuite = new TestSuite();
		testSuite.setId(test.getTestSuiteId());
		testSuite.setTestSuiteName(testSuiteName);
		testSuite.setTestComments(comments);
		//RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_UPDATE_TEST_SUITE_NAME, testSuite);
		restClient.put(TestResultsRestUrlMap.UPDATE_TESTSUITE_NAME, testSuite, TestSuite.class);
	}
	

	public void updateTestSuite(String status, String build) throws Exception{
		updateTestSuite(status, build, null);
	}
	public void updateTestSuite(String status, String build, String comments) throws Exception{
		TestSuite testSuite = new TestSuite();
		testSuite.setId(test.getTestSuiteId());
		testSuite.setTestStatus(status);
		testSuite.setTestBuild(build);
		testSuite.setTestComments(comments);
		testSuite.setRemoteEndTime(new Date());
		//RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_UPDATE_TEST_SUITE, testSuite);
		restClient.put(TestResultsRestUrlMap.UPDATE_TESTSUITE, testSuite, TestSuite.class);
	}

	public void insertTestGroup(String groupName) throws Exception{
		if(!test.getTestGroupName().equals(groupName)){
			TestGroup testGroup = new TestGroup();		
			testGroup.setTestSuiteId(test.getTestSuiteId());
			testGroup.setTestGroup(groupName);	
			testGroup.setRemoteTime(new Date());
			//testGroup = (TestGroup) RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_INSERT_TEST_GROUP, testGroup);
			testGroup = (TestGroup) restClient.post(TestResultsRestUrlMap.INSERT_TESTGROUP, testGroup, TestSuite.class);
			test.setTestGroupId(testGroup.getId());
			test.setTestGroupName(groupName);
		}		
	}

	public void insertTestCase(String testCaseName, String result) throws Exception{
		insertTestCase(testCaseName, null, result);
	}
	public void insertTestCase(String testCaseName, String arguments, String result) throws Exception{
		TestCase testCase = new TestCase();
		testCase.setTestSuiteId(test.getTestSuiteId());
		testCase.setTestGroupId(test.getTestGroupId());
		testCase.setTestName(testCaseName);
		testCase.setTestArguments(arguments);
		testCase.setTestResult(result);
		testCase.setRemoteStartTime(new Date());		
		//test.setTestCaseId((Integer) RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_INSERT_TEST_CASE, testCase));
		test.setTestCaseId((Integer) restClient.post(TestResultsRestUrlMap.INSERT_TESTCASE, testCase, TestCase.class));
		test.setScreenShotFileName(null);
		if(!result.equalsIgnoreCase(TestCase.RUNNING)){
			test.setTestCaseId(null);
		}
	}

	public void updateTestCase(String status) throws Exception{
		updateTestCase(status, null, null);
	}

	public void updateTestCase(String status, String comments) throws Exception{
		updateTestCase(status, comments, null);
	}

	public void updateTestCase(String status, String comments, String screenShotFileName) throws Exception{
		TestCase testCase = new TestCase();
		testCase.setId(test.getTestCaseId());
		testCase.setTestResult(status);
		if(comments != null){
			testCase.setTestComments(comments);
		}	
		if(screenShotFileName != null){
			test.setScreenShotFileName(screenShotFileName);
		}
		if(test.getScreenShotFileName() != null){
			File screenShot = new File(test.getScreenShotFileName());
			testCase.setScreenShotFileName(screenShot.getName());
			byte[] screenShotByte = new byte[(int) screenShot.length()];
			FileInputStream in = new FileInputStream(screenShot);
			in.read(screenShotByte );
			in.close();
			//testCase.setScreenShotFileByte(screenShotByte);
			// Convert a byte array to base64 string
			testCase.setScreenShotFileBase64(new String(Base64.encodeBase64(screenShotByte)));
			System.out.println("Report Engine Client: Screen Shot File Name (Sent): "+ testCase.getScreenShotFileName());
			if(!screenShot.delete()){
				System.out.println("Report Engine Client: Failed to delete the file: "+ testCase.getScreenShotFileName());
			}
		}
		testCase.setRemoteEndTime(new Date());		
		//RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_UPDATE_TEST_CASE, testCase);
		restClient.put(TestResultsRestUrlMap.UPDATE_TESTCASE, testCase, TestCase.class);
		if(!status.equalsIgnoreCase(TestCase.RUNNING)){
			test.setTestCaseId(null);
		}
	}

	//To take Screen shot
	public void takeScreenShot(){
		if(!test.isTakeScreenShot()){
			return;
		}
		try{
			System.out.println("Report Engine Client: Taking screen shot...");
			if(new File(test.getClientTempLocation()).mkdirs()){
				System.out.println("Report Engine Client: Directory Created... : "+test.getClientTempLocation());
			}else{
				System.out.println("Report Engine Client: Directory might be available: "+test.getClientTempLocation());
			}
			String fileName = "ScreenShot_"+new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ssaa").format(new Date())+".png";
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			System.out.println("Report Engine Client: ScreenShot File name: "+test.getClientTempLocation()+fileName);
			ImageIO.write(image, "png", new File(test.getClientTempLocation()+fileName));			
			System.out.println("Report Engine Client: Screen shot done!!");
			test.setScreenShotFileName(test.getClientTempLocation()+fileName);
		}catch(Exception ex){
			System.out.println("Report Engine Client: Unable to take screen shot,");
			ex.printStackTrace();
		}

	}

	public void insertLogMessage(TestLogs testLogs) throws Exception{
		testLogs.setTestSuiteId(test.getTestSuiteId());
		if(test.getTestGroupId() != null){
			testLogs.setTestGroupId(test.getTestGroupId());
		}
		if(test.getTestCaseId() != null){
			testLogs.setTestCaseId(test.getTestCaseId());	
		}		

		//RemoteCall.invokeRemoteMethod(test.getServerObject(), ClientRMI.METHOD_INSERT_TEST_LOGS, testLogs);
		restClient.post(TestResultsRestUrlMap.UPDATE_TESTCASE, testLogs, TestLogs.class);
	}

	public String getLoggerType(){
		return test.getLoggerType();
	}

	public String getLogLevel(){
		return test.getLogLevel();
	}

	/**
	 * @return the clientConfigurationSuccess
	 */
	public boolean isClientConfigurationSuccess() {
		return this.clientConfigurationSuccess;
	}

	/**
	 * @param clientConfigurationSuccess the clientConfigurationSuccess to set
	 */
	public void setClientConfigurationSuccess(boolean clientConfigurationSuccess) {
		this.clientConfigurationSuccess = clientConfigurationSuccess;
	}
}
