package com.redhat.reportengine.agent;

import com.redhat.reportengine.agent.api.AgentInfo;
import com.redhat.reportengine.agent.rest.mapper.AgentDetails;
import com.redhat.reportengine.restapi.urimap.ServerRestUriMap;
import com.redhat.reportengine.restclient.RestEasyJSONClient;
import com.redhat.reportengine.server.rest.mapper.ServerInfo;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 03, 2013
 */
public class ServerConnection {
	public RestEasyJSONClient getRestClient(){
		RestEasyJSONClient client = new RestEasyJSONClient();
		client.setServerUrl(AgentProperties.getServerRestUrl());
		client.setRootUrl(ServerRestUriMap.ROOT_URI);
		return client;
	}
	
	public void registerAgent() throws Exception{
		getRestClient().post(ServerRestUriMap.REGISTER_ME, AgentInfo.getAgentDetails(), AgentDetails.class);
	}
	
	public ServerInfo getServerInfo() throws Exception{
		return (ServerInfo) getRestClient().post(ServerRestUriMap.GET_AGENT_INFO, AgentInfo.getAgentDetails(), ServerInfo.class);
	}
	
}
