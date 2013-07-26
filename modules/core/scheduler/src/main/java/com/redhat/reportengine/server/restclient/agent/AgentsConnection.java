package com.redhat.reportengine.server.restclient.agent;

import java.sql.SQLException;
import java.util.HashMap;

import com.redhat.reportengine.restapi.urimap.AgentRestUriMap;
import com.redhat.reportengine.restclient.RestEasyJSONClient;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.Server;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 26, 2013
 */
public class AgentsConnection {
	
	public static final String SERVER_IP_REPLACEMENT = "#SERVER_LOCATION#";
	
	public static final String AGENT_URL = "http://"+SERVER_IP_REPLACEMENT;
	
	private static HashMap<Integer, RestEasyJSONClient> restJSONclient = new HashMap<Integer, RestEasyJSONClient>();
	

	public static RestEasyJSONClient getRestJSONclient(Integer serverId) throws SQLException {
		RestEasyJSONClient client = restJSONclient.get(serverId);
		Server server = new ServerTable().getById(serverId);
		if(client != null){
			return client;
		}else{
			client = new RestEasyJSONClient();
			client.setServerUrl(AGENT_URL.replaceAll(SERVER_IP_REPLACEMENT, server.getHostIp()+":"+server.getAgentPort()));
			client.setRootUrl(AgentRestUriMap.AGENT_ROOT_URI);
			addRestJSONclient(serverId, client);
			return client;
		}
	}

	public static synchronized void addRestJSONclient(Integer serverId, RestEasyJSONClient jsonClient) {
		restJSONclient.put(serverId, jsonClient);
	}
	
	public static synchronized void removeRestJSONClient(Integer serverId){
		restJSONclient.remove(serverId);
	}
}
