package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;

import com.redhat.reportengine.server.actions.ServerActions;
import com.redhat.reportengine.server.dbdata.DynamicTableNameTable;
import com.redhat.reportengine.server.dbdata.ResourceCpuTable;
import com.redhat.reportengine.server.dbdata.ResourceMemoryTable;
import com.redhat.reportengine.server.dbdata.ServerCpuDetailTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.DynamicTableName;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.dbmap.ServerCpuDetail;
import com.redhat.reportengine.server.jobs.server.ServerAgentReachableStatus;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 08, 2013
 */
public class ManageServer {
	
	public static final String MANAGE_SERVER_GROUP = "Manage Server Group";
	private ServerActions serverActions = new ServerActions();

	public void updateStatusNow(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
		new ServerAgentReachableStatus().updateServerAgentReachableStatus(id);
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		addUpdate(request, response, true);
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		addUpdate(request, response, false);
	}	
	
	public void remove(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, SchedulerException{
		Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
		Server server = new ServerTable().getById(id);
		serverActions.removeResourceJob(server);
		serverActions.removeAvailableStatusJob(server);
		removeResourceTables(server);
		new ServerTable().remove(id);
	}
	
	public void removeResourceTables(Server server) throws SQLException{
		/*
		ServerCpuDetail serverCpuDetail = new ServerCpuDetailTable().getByServerId(server.getId());
		//Drop Memory/Swap Table
		new ResourceMemoryTable().dropTable(ResourceMemoryTable.getTableSubName(server.getId()));
		//Drop CPU tables
		ResourceCpuTable resourceCpuTable = new ResourceCpuTable();
		resourceCpuTable.dropTable(ResourceCpuTable.getCoreCpuSubName(server.getId()));
		//Drop CPUs table
		if(serverCpuDetail != null){
			for(int i=0; i<serverCpuDetail.getTotalCores(); i++){
				resourceCpuTable.dropTable(ResourceCpuTable.getMultiCpuSubName(server.getId(), i));
			}
		}	
		*/
		
		//Adding this line after moved all the dynamic tables to similar format.
		ArrayList<DynamicTableName> tableNames = DynamicTableNameTable.get(server.getId());
		for(DynamicTableName tableName : tableNames){
			DynamicTableNameTable.dropDynamicTableAndSequence(String.valueOf(tableName.getId()));
		}
	}
	
	private void addUpdate(HttpServletRequest request, HttpServletResponse response, boolean add) throws SQLException, ParseException, SchedulerException{
		String name 			= request.getParameter(Keys.SERVER_NAME);
		String hostIp 			= request.getParameter(Keys.SERVER_HOSTIP);
		String macAddr 			= request.getParameter(Keys.SERVER_MAC_ADDR);
		Integer agentPort 		= Integer.valueOf((String)request.getParameter(Keys.SERVER_AGENT_PORT));
		Integer updateInterval 	= Integer.valueOf((String)request.getParameter(Keys.SERVER_UPDATE_INTERVAL));

		if(!(name != null) && (hostIp != null) && (macAddr != null)){
			return;						
		}
		
		Server server;
		if(add){
			server = new Server();
		}else{
			Integer id = Integer.valueOf((String)request.getParameter(Keys.SERVER_ID));
			server = new ServerTable().getById(id);
		}		
		server.setName(name.trim());
		server.setHostIp(hostIp.trim());
		server.setMacAddr(macAddr.trim());
		server.setAgentPort(agentPort);
		server.setUpdateInterval(updateInterval*60);
		if(add){
			serverActions.add(server);
		}else{
			serverActions.update(server);
		}			
	}
	
	
	
	
}
