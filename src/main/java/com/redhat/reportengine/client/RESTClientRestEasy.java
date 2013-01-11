/**
 * 
 */
package com.redhat.reportengine.client;

import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.restapi.testresult.TestResultsRestUrlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 28, 2012
 */
public class RESTClientRestEasy {
	ClientRequest request = null;
	private String rootUrl; 
	private String serverUrl;
	private int MIN_STATUS_CODE = 200;
	private int MAX_STATUS_CODE = 299;
	
	private void checkStatus(int statusCode){
		if (!((statusCode >= MIN_STATUS_CODE) && (statusCode <= MAX_STATUS_CODE))) {
			throw new RuntimeException("Failed : HTTP error code : "+ statusCode);
		}
	}
	
	public <T> Object post(String uri, Object data, Class<T> dataClass) throws Exception{
		request = new ClientRequest(this.serverUrl+this.rootUrl+uri);
		request.accept(MediaType.APPLICATION_XML);
		if(data != null){
			request.body(MediaType.APPLICATION_XML_TYPE, data, dataClass);
		}		
		ClientResponse<T> response = request.post(dataClass);
		this.checkStatus(response.getStatus());
		return response.getEntity();
	}
	
	public <T> Object put(String uri, Object data, Class<T> dataClass) throws Exception{
		request = new ClientRequest(this.serverUrl+this.rootUrl+uri);
		request.accept(MediaType.APPLICATION_XML);
		if(data != null){
			request.body(MediaType.APPLICATION_XML_TYPE, data, dataClass);
		}		
		ClientResponse<T> response = request.put(dataClass);
		this.checkStatus(response.getStatus());
		return response.getEntity();
	}
	
	public <T> Object get(String uri, Class<T> dataClass) throws Exception{
		request = new ClientRequest(this.serverUrl+this.rootUrl+uri);
		request.accept(MediaType.APPLICATION_XML);
		ClientResponse<T> response = request.get(dataClass);
		this.checkStatus(response.getStatus());
		System.out.println("ERROR: "+response.getResponseStatus());
		return response.getEntity();
	}
	
	public static void main(String[] args) throws Exception {
		
		RESTClientRestEasy restClient = new RESTClientRestEasy();
		//restClient.setServerUrl("http://localhost:8080/re/resteasy");
		//https://mercury.lab.eng.pnq.redhat.com:8080/re/resteasy/testresults/testsuite/124
		restClient.setServerUrl("https://mercury.lab.eng.pnq.redhat.com:8080/re/resteasy");
		restClient.setRootUrl("/testresults");
		
		TestSuite data = new TestSuite();
		data.setId(123);
		data.setTestReference("RHQ-4.x-GUI-EAP-OVER-NIGHT-AUTOMATION-JOB");
		data.setTestReferenceId(4);
		data.setTestSuiteName("RHQ_4.4_UI_AS7Plugin_Test");
		
		//System.out.println("******Suite Name: "+((TestSuite)restClient.post(TestResultsRestUrlMap.GET_TESTSUITE_BY_TESTSUITE, data, TestSuite.class)).getTestHost());
		//System.out.println("******File Byte: "+new String(((TestCase)restClient.get("/filebyte", TestCase.class)).getScreenShotFileByte()));
		//System.out.println("******Next ID: "+((String)restClient.get(TestResultsRestUrlMap.GET_TESTSUITE_NEXT_AVAILABLE_ID, String.class)));
		//System.out.println("Users:************:"+((Users)restClient.get("/users", Users.class)).getUsers().get(0).getName());
		System.out.println("********TestSuite Name: "+((TestSuite)restClient.get(TestResultsRestUrlMap.GET_TESTSUITE_BY_ID+"/124", TestSuite.class)).getTestBuild());
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

}
