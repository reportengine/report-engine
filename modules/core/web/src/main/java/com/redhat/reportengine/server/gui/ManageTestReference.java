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

import com.redhat.reportengine.server.dbdata.TestReferenceServerMapTable;
import com.redhat.reportengine.server.dbdata.TestReferenceTable;
import com.redhat.reportengine.server.dbmap.TestReference;
import com.redhat.reportengine.server.dbmap.TestReferenceServerMap;
import com.redhat.reportengine.server.reports.Keys;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 19, 2012
 */
public class ManageTestReference {
	public ArrayList<TestReference> getAllTestReference() throws SQLException{
		return new TestReferenceTable().getAll();
	}
	
	public void modifyTestServerMap(HttpServletRequest request, HttpServletResponse response) throws SQLException{
	    int testReferenceId = Integer.valueOf(request.getParameter(Keys.TEST_REFERENCE_ID));
		String[] servers = request.getParameterValues(Keys.SERVER_ID);
		TestReferenceServerMap referenceServerMap = new TestReferenceServerMap();
		referenceServerMap.setTestSuiteReferenceId(testReferenceId);
		TestReferenceServerMapTable serverMapTable = new TestReferenceServerMapTable();
		serverMapTable.removeByTestRefId(testReferenceId);
		for(String server : servers){
			referenceServerMap.setServerId(Integer.valueOf(server.trim()));
			serverMapTable.add(referenceServerMap);
		}
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException{
	    int testReferenceId = Integer.valueOf(request.getParameter(Keys.TEST_REFERENCE_ID));
		new TestReferenceServerMapTable().removeByTestRefId(testReferenceId);
	}	
	
	public void getServersKeyValue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, SQLException {
	    int testReferenceId = Integer.valueOf(request.getParameter(Keys.TEST_REFERENCE_ID));
	    ArrayList<TestReferenceServerMap> servers = new TestReferenceServerMapTable().getByTestRefId(testReferenceId);
	    Map<String, String> options = new HashMap<String, String>();
	    for(TestReferenceServerMap server : servers){
	    	//options.put(String.valueOf(server.getServerId()), server.getServerName()+"["+server.getServerHostIp()+"]");
	    	options.put(String.valueOf(server.getServerId()), server.getServerName());
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
}
