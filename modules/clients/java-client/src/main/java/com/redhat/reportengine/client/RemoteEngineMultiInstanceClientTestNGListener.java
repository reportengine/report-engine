package com.redhat.reportengine.client;

import java.net.InetAddress;
import java.util.logging.Level;

import org.testng.ISuite;

public class RemoteEngineMultiInstanceClientTestNGListener extends ReportEngineClientTestNGListener{

	private RemoteAPI remoteApi;
	private String currentSuiteFile;

	public RemoteEngineMultiInstanceClientTestNGListener(){
		super(false); //donothign constructor
	}

	@Override
	protected RemoteAPI getRemoteApi() {
		return remoteApi;
	}
	
	@Override
	public void onStart(ISuite suite) {
		restartRemoteApiOnSuiteChange(suite);
		super.onStart(suite);
	}
	
	
	private void restartRemoteApiOnSuiteChange(ISuite arg0){
		if (!arg0.getXmlSuite().getFileName().equals(currentSuiteFile)) {
			_logger.log(Level.WARNING, arg0.getXmlSuite().getFileName());
			startRemoteApi();
			currentSuiteFile = arg0.getXmlSuite().getFileName();
		}
	}
	
	private void startRemoteApi(){
		remoteApi = new RemoteAPI();
		try {
			_logger.log(Level.INFO, "Starting Report Engine Client");
			remoteApi.initClient(InetAddress.getLocalHost().getHostName()+" ["+InetAddress.getLocalHost().getHostAddress()+"]");
		} catch (Exception ex) {
			_logger.log(Level.SEVERE, "Report Engine Client Failed to start!!", ex);
		}
		
	}
}
