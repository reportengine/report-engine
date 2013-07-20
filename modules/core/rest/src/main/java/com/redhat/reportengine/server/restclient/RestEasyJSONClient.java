package com.redhat.reportengine.server.restclient;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 26, 2013
 */
public class RestEasyJSONClient {
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
		request.accept(MediaType.APPLICATION_JSON);
		if(data != null){
			request.body(MediaType.APPLICATION_JSON_TYPE, data, dataClass);
		}		
		ClientResponse<T> response = request.post(dataClass);
		this.checkStatus(response.getStatus());
		return response.getEntity();
	}
	
	public <T> Object put(String uri, Object data, Class<T> dataClass) throws Exception{
		request = new ClientRequest(this.serverUrl+this.rootUrl+uri);
		request.accept(MediaType.APPLICATION_JSON);
		if(data != null){
			request.body(MediaType.APPLICATION_JSON_TYPE, data, dataClass);
		}		
		ClientResponse<T> response = request.put(dataClass);
		this.checkStatus(response.getStatus());
		return response.getEntity();
	}
	
	public <T> Object get(String uri, Class<T> dataClass) throws Exception{
		request = new ClientRequest(this.serverUrl+this.rootUrl+uri);
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<T> response = request.get(dataClass);
		this.checkStatus(response.getStatus());
		return response.getEntity();
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
