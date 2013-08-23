package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.reportengine.server.dbdata.ResourceCpuTable;
import com.redhat.reportengine.server.dbdata.ResourceMemoryTable;
import com.redhat.reportengine.server.dbmap.ResourceCpu;
import com.redhat.reportengine.server.dbmap.ResourceMemory;
import com.redhat.reportengine.server.gui.mapper.ChartData;
import com.redhat.reportengine.server.gui.mapper.RESOURCE_TYPE;
import com.redhat.reportengine.server.reports.General;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Sep 12, 2012
 */
public class ChartCpuMemory {

	private enum MEMORY {
	    MEMORY, SWAP, MEMORY_AU
	}
	
	private void setReportForCalendar(Calendar fromDate, Calendar toDate, HttpServletRequest request) throws ParseException{
		String reportFor = (String) request.getParameter(Keys.REPORT_FOR);
		if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_30_MINS)){
			fromDate.add(Calendar.MINUTE, -30);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_60_MINS)){
			fromDate.add(Calendar.MINUTE, -60);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_3_HOURS)){
			fromDate.add(Calendar.HOUR_OF_DAY, -3);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_6_HOURS)){
			fromDate.add(Calendar.HOUR_OF_DAY, -6);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_12_HOURS)){
			fromDate.add(Calendar.HOUR_OF_DAY, -12);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_LAST_24_HOURS)){
			fromDate.add(Calendar.HOUR_OF_DAY, -24);
		}else if(reportFor.equalsIgnoreCase(Keys.REPORT_FOR_CUSTOM)){
			String fromStrDate = request.getParameter(Keys.REPORT_DATE_FROM);
			String toStrDate = request.getParameter(Keys.REPORT_DATE_TO);
			fromDate.setTime(new SimpleDateFormat(General.guiInputDateTimeFormat).parse(fromStrDate));
			toDate.setTime(new SimpleDateFormat(General.guiInputDateTimeFormat).parse(toStrDate));
		}
	}

	public String getCpuMemoryChartJson(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException{
		
		int serverId = Integer.parseInt((String) request.getParameter(Keys.SERVER_ID));		
		
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = (Calendar) fromDate.clone();
		
		setReportForCalendar(fromDate, toDate, request);
		
		String resourceType = (String)request.getParameter(Keys.RESOURCE_TYPE);
		
		StringBuilder chartJson = new StringBuilder("[\n");
		
		if(resourceType.equalsIgnoreCase(Keys.RESOURCE_CPU)){
			ResourceCpu resourceCpu = new ResourceCpu();
			resourceCpu.setTableSubName(ResourceCpuTable.getCoreCpuSubName(serverId));
			resourceCpu.setFromTime(fromDate.getTime());
			resourceCpu.setToTime(toDate.getTime());
			setChartJsonCPU(resourceCpu, chartJson);
		}else if(resourceType.startsWith(Keys.RESOURCE_CPU+Keys.RESOURCE_CPUS_SPLITER)){
			int cpuNo = Integer.valueOf(resourceType.split(Keys.RESOURCE_CPUS_SPLITER)[1]);
			ResourceCpu resourceCpu = new ResourceCpu();
			resourceCpu.setTableSubName(ResourceCpuTable.getMultiCpuSubName(serverId, cpuNo));
			resourceCpu.setFromTime(fromDate.getTime());
			resourceCpu.setToTime(toDate.getTime());
			setChartJsonCPU(resourceCpu, chartJson);
		}else if(resourceType.equalsIgnoreCase(Keys.RESOURCE_MEMORY)){
			ResourceMemory resourceMemory = new ResourceMemory();
			resourceMemory.setTableSubName(ResourceMemoryTable.getTableSubName(serverId));
			resourceMemory.setFromTime(fromDate.getTime());
			resourceMemory.setToTime(toDate.getTime());
			setMemorySwapString(resourceMemory, chartJson, MEMORY.MEMORY);
		}else if(resourceType.equalsIgnoreCase(Keys.RESOURCE_SWAP)){
			ResourceMemory resourceMemory = new ResourceMemory();
			resourceMemory.setTableSubName(ResourceMemoryTable.getTableSubName(serverId));
			resourceMemory.setFromTime(fromDate.getTime());
			resourceMemory.setToTime(toDate.getTime());
			setMemorySwapString(resourceMemory, chartJson, MEMORY.SWAP);
		}else if(resourceType.equalsIgnoreCase(Keys.RESOURCE_MEMORY_AU)){
			ResourceMemory resourceMemory = new ResourceMemory();
			resourceMemory.setTableSubName(ResourceMemoryTable.getTableSubName(serverId));
			resourceMemory.setFromTime(fromDate.getTime());
			resourceMemory.setToTime(toDate.getTime());
			setMemorySwapString(resourceMemory, chartJson, MEMORY.MEMORY_AU);
		}
		chartJson.setLength(chartJson.length()-2);
		chartJson.append("\n]");
		if(chartJson.length() < 3){
			response.sendError(500, "Data not available for this range");
		}
		return chartJson.toString();
		
	}
	
	private void setMemorySwapString(ResourceMemory resourceMemory, StringBuilder chartJson, MEMORY memory) throws SQLException{
		ArrayList<ResourceMemory> resourceMemories = new ResourceMemoryTable().getByTimeRange(resourceMemory);
		for(ResourceMemory tmpMemory : resourceMemories){
			switch(memory){
			case SWAP: chartJson.append("[").append((tmpMemory.getLocalTime().getTime()/1000)*1000).append(",").append(General.decimalDigit2.format(((double)tmpMemory.getSwapUsed()/tmpMemory.getSwapTotal())*100.0)).append("],\n"); break;
			case MEMORY: chartJson.append("[").append((tmpMemory.getLocalTime().getTime()/1000)*1000).append(",").append(General.decimalDigit2.format(((double)tmpMemory.getUsed()/tmpMemory.getTotal())*100.0)).append("],\n"); break;
			case MEMORY_AU: chartJson.append("[").append((tmpMemory.getLocalTime().getTime()/1000)*1000).append(",").append(General.decimalDigit2.format(((double)tmpMemory.getActualUsed()/tmpMemory.getTotal())*100.0)).append("],\n"); break;				
			}
		}
	}
	
	private void setChartJsonCPU(ResourceCpu resourceCpu, StringBuilder chartJson) throws SQLException{
		ArrayList<ResourceCpu> resourceCpus = new ResourceCpuTable().getByTimeRange(resourceCpu);
		for(ResourceCpu tmpCpu : resourceCpus){
			chartJson.append("[").append((tmpCpu.getLocalTime().getTime()/1000)*1000).append(",").append(General.decimalDigit2.format(100.0 - (tmpCpu.getIdle()*100.0))).append("],\n");
		}
	}
	
	public boolean isDataAvailable(ChartData data, RESOURCE_TYPE resourceType) throws ParseException, SQLException{
		try{
			switch(resourceType){
			case CPU: 
				ResourceCpu resourceCpu = new ResourceCpu();
				resourceCpu.setTableSubName(ResourceCpuTable.getCoreCpuSubName(data.getServerId()));
				resourceCpu.setFromTime(data.getReportDateFrom());
				resourceCpu.setToTime(data.getReportDateTo());
				if(new ResourceCpuTable().getRowCount(resourceCpu) > 0){
					return true;
				}
				break;
			case MEMORY: 
				ResourceMemory resourceMemory = new ResourceMemory();
				resourceMemory.setTableSubName(ResourceMemoryTable.getTableSubName(data.getServerId()));
				resourceMemory.setFromTime(data.getReportDateFrom());
				resourceMemory.setToTime(data.getReportDateTo());
				if(new ResourceMemoryTable().getRowCount(resourceMemory) > 0){
					return true;
				}
				break;
			}
		}catch (SQLException sex){
			if(sex.getMessage().contains("does not exist")){
				return false;
			}else{
				throw sex;
			}
		}
		return false;
	}
	
}
