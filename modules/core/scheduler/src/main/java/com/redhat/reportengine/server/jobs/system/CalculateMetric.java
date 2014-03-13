/**
 * 
 */
package com.redhat.reportengine.server.jobs.system;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.redhat.reportengine.server.dbdata.DynamicTableNameTable;
import com.redhat.reportengine.server.dbdata.TestReferenceServerMapTable;
import com.redhat.reportengine.server.dbdata.TestSuiteResourceMetricColumnTable;
import com.redhat.reportengine.server.dbdata.TestSuiteResourceMetricTable;
import com.redhat.reportengine.server.dbdata.TestSuiteTable;
import com.redhat.reportengine.server.dbmap.DynamicTableName;
import com.redhat.reportengine.server.dbmap.TestReferenceServerMap;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.dbmap.TestSuiteResourceMetric;
import com.redhat.reportengine.server.dbmap.TestSuiteResourceMetricColumn;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 4, 2014
 */
public class CalculateMetric {
	final static Logger _logger = Logger.getLogger(CalculateMetric.class);


	public static synchronized void updateMetric(int testSuiteId) throws SQLException {
		TestSuite testSuite = new TestSuiteTable().get(testSuiteId);
		updateMetric(testSuite);
	}

	public static synchronized void updateMetric(TestSuite testSuite) throws SQLException {
		TestSuiteResourceMetricTable metricTable = new TestSuiteResourceMetricTable();
		//Remove all existing entries
		metricTable.removeByTestSuiteId(testSuite.getId());
		
		ArrayList<TestReferenceServerMap> servers = new TestReferenceServerMapTable().getDetailByTestRefId(testSuite.getTestReferenceId());
		TestSuiteResourceMetric testSuiteResourceMetric = null;
		TestSuiteResourceMetricTable testSuiteResourceMetricTable = new TestSuiteResourceMetricTable();
		for(TestReferenceServerMap server: servers){
			ArrayList<DynamicTableName> tables = DynamicTableNameTable.get(server.getServerId());
			for(DynamicTableName table : tables){
				testSuiteResourceMetric = new TestSuiteResourceMetric();
				testSuiteResourceMetric.setDtnId(table.getId());
				testSuiteResourceMetric.setFromTime(testSuite.getLocalStartTime());
				testSuiteResourceMetric.setToTime(testSuite.getLocalEndTime());
				testSuiteResourceMetric.setTestReferenceId(testSuite.getTestReferenceId());
				
				ArrayList<TestSuiteResourceMetricColumn> metricColumns = new TestSuiteResourceMetricColumnTable().getByTableType(table.getTableType());
				for(TestSuiteResourceMetricColumn metricColumn : metricColumns){
					testSuiteResourceMetric.setColumnName(metricColumn.getColumnName());
					testSuiteResourceMetric.setColumnNameId(metricColumn.getId());
					testSuiteResourceMetric.setSubQuery(metricColumn.getSubQuery());
					
					TestSuiteResourceMetric tmpMetric = null;
					
					if(metricColumn.getTableType().contains(DynamicTableName.TYPE.CPU.toString()) && metricColumn.getColumnName().equalsIgnoreCase(TestSuiteResourceMetric.COLUMNS_NOT_IN_DB.cpu_used.getName())){ //If it is CPU used run call different metric query
						tmpMetric = TestSuiteResourceMetricTable.getMetricCpuUsed(testSuiteResourceMetric);
					}else{
						tmpMetric = TestSuiteResourceMetricTable.getMetric(testSuiteResourceMetric);
					}
					
					if(tmpMetric.getMinimum() != null){
						TestSuiteResourceMetric oldTestSuiteResourceMetric = metricTable.getTopByTestReferenceId(testSuiteResourceMetric);
						if(oldTestSuiteResourceMetric != null){						
							tmpMetric.setMinimumChanges(tmpMetric.getMinimum() - oldTestSuiteResourceMetric.getMinimum());
							tmpMetric.setMaximumChanges(tmpMetric.getMaximum() - oldTestSuiteResourceMetric.getMaximum());
							tmpMetric.setAverageChanges(tmpMetric.getAverage() - oldTestSuiteResourceMetric.getAverage());					
						}else{
							tmpMetric.setMinimumChanges(0.0);
							tmpMetric.setMaximumChanges(0.0);
							tmpMetric.setAverageChanges(0.0);	
						}
						tmpMetric.setTestSuiteId(testSuite.getId());
						tmpMetric.setTestReferenceId(testSuite.getTestReferenceId());
						tmpMetric.setDtnId(table.getId());
						tmpMetric.setColumnNameId(metricColumn.getId());
						//Add new entry to Metric Table
						testSuiteResourceMetricTable.add(tmpMetric);
						_logger.debug("Metric Updated for "+testSuite.getTestSuiteName()+", for: "+metricColumn.toString());
					}else{
						_logger.debug("no data available for this time line - "+testSuite.getTestSuiteName()+", for: "+metricColumn.toString());
					}
					
				}
			}
		}
	}
}
