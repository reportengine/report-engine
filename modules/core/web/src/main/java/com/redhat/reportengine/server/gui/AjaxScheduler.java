package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.redhat.reportengine.server.dbdata.JobClassesTable;
import com.redhat.reportengine.server.dbdata.ReportGroupTable;
import com.redhat.reportengine.server.dbdata.ServerTable;
import com.redhat.reportengine.server.dbmap.JobClasses;
import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.dbmap.Server;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 10, 2013
 */
public class AjaxScheduler {
	
	public void getReferencesKeyValue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, SQLException {
	    String selectedId = request.getParameter(Keys.JOB_REFERENCE);
	    JobClasses jobClasses = new JobClassesTable().getById(Integer.valueOf(selectedId));
	    Map<String, String> options = new HashMap<String, String>();
	    
	    if(jobClasses.getTargetClassDescription().contains(JobClassesTable.JOB_CLASS_DESCRIPTION_EMAIL_TEST_REPORT)){
	    	getEmailTestReportList(options);
	    }else if(jobClasses.getTargetClassDescription().contains(JobClassesTable.JOB_CLASS_DESCRIPTION_RESOURCE)){
	    	getResourcesList(options);
	    }
	    
	    Writer writer = new StringWriter();
	    JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(writer);
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(jsonGenerator, options);
	    jsonGenerator.close();
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(writer.toString());
	   }
	
	private Map<String, String> getEmailTestReportList(Map<String, String> map) throws SQLException{
		ArrayList<ReportGroup> groups = new ReportGroupTable().getAll();
		for(ReportGroup group : groups){
			map.put(String.valueOf(group.getId()), group.getGroupName());
		}
		return map;
	}
	
	private Map<String, String> getResourcesList(Map<String, String> map) throws SQLException{
		ArrayList<Server> servers = new ServerTable().get();
		for(Server server: servers){
			map.put(String.valueOf(server.getId()), server.getName()+" ["+server.getHostIp()+"]");
		}
		return map;
	}

}
