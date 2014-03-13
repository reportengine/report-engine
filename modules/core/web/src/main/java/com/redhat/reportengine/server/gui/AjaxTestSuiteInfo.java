package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.redhat.reportengine.server.charts.REJfreeChart;
import com.redhat.reportengine.server.collection.DynamicTable;
import com.redhat.reportengine.server.dbdata.JvmMemoryTable;
import com.redhat.reportengine.server.dbdata.ResourceCpuTable;
import com.redhat.reportengine.server.dbdata.ResourceMemoryTable;
import com.redhat.reportengine.server.dbdata.TestReferenceServerMapTable;
import com.redhat.reportengine.server.dbmap.JvmMemory;
import com.redhat.reportengine.server.dbmap.ResourceCpu;
import com.redhat.reportengine.server.dbmap.ResourceMemory;
import com.redhat.reportengine.server.dbmap.TestReferenceServerMap;
import com.redhat.reportengine.server.dbmap.TestSuite;
import com.redhat.reportengine.server.dbmap.DynamicTableName.TYPE;
import com.redhat.reportengine.server.reports.General;
import com.redhat.reportengine.server.reports.Keys;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 27, 2014
 */
public class AjaxTestSuiteInfo extends AjaxCommon{

	public void getTestSuiteDetails(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		int testSuiteId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));

		StringBuilder tsInfo = new StringBuilder();
		TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testSuiteId);


		setTableHead(tsInfo);

		setKeyValue(tsInfo, "Test Suite Name", testSuite.getTestSuiteName());
		setKeyValue(tsInfo, "Build", General.getHtml(testSuite.getTestBuild()));
		setKeyValue(tsInfo, "Status", "<img width=\"16\" height=\"16\"  src='"+General.HTML_ICONS_LOCATION+testSuite.getTestStatus()+".png' alt='"+testSuite.getTestStatus()+"'>");
		setKeyValue(tsInfo, "Test Reference", "<a class=\"alink\" href=\"reportsAllTestSuites.jsp?"+Keys.TEST_REFERENCE_ID+"="+testSuite.getTestReferenceId()+"\">"+testSuite.getTestReference()+"</a>");
		setKeyValue(tsInfo, "Host (Test Bed Machine)", General.getNotNullString(testSuite.getTestHost()));
		setKeyValue(tsInfo, "Start Time (Local)", General.getGuiDateTime(testSuite.getLocalStartTime()));
		setKeyValue(tsInfo, "Start Time (Remote)", General.getGuiDateTime(testSuite.getRemoteStartTime()));
		setKeyValue(tsInfo, "End Time (Local)", General.getGuiDateTime(testSuite.getLocalEndTime()));
		setKeyValue(tsInfo, "End Time (Remote)", General.getGuiDateTime(testSuite.getRemoteEndTime()));
		setKeyValue(tsInfo, "Comments", General.getHtml(testSuite.getTestComments()));
		setKeyValue(tsInfo, "Trend Report For Last", "<a class=\"alink\" href=\"testTrendReport.jsp?references="+testSuite.getTestReferenceId()+"&chartType=area&"+Keys.REPORT_FOR+"="+Keys.REPORT_FOR_LAST_7_DAYS+"&SUBMIT=Get\">7 Days,</a>"
				+ "&nbsp;<a class=\"alink\" href=\"testTrendReport.jsp?references="+testSuite.getTestReferenceId()+"&chartType=area&"+Keys.REPORT_FOR+"="+Keys.REPORT_FOR_LAST_15_DAYS+"&SUBMIT=Get\">15 Days,</a>"
				+ "&nbsp;<a class=\"alink\" href=\"testTrendReport.jsp?references="+testSuite.getTestReferenceId()+"&chartType=area&"+Keys.REPORT_FOR+"="+Keys.REPORT_FOR_LAST_30_DAYS+"&SUBMIT=Get\">30 Days</a>"
				+ "&nbsp;<a class=\"alink\" href=\"testTrendReport.jsp?references="+testSuite.getTestReferenceId()+"&chartType=area&"+Keys.REPORT_FOR+"="+Keys.REPORT_FOR_LAST_60_DAYS+"&SUBMIT=Get\">60 Days</a>"
				+ "&nbsp;<a class=\"alink\" href=\"testTrendReport.jsp?references="+testSuite.getTestReferenceId()+"&chartType=area&"+Keys.REPORT_FOR+"="+Keys.REPORT_FOR_LAST_90_DAYS+"&SUBMIT=Get\">90 Days</a>");


		if(!testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
			/*
			StringBuilder pieChart = new StringBuilder();
			pieChart.append("<script type=\"text/javascript\">"
					+ "\n $(function () {"
					+ "\n Highcharts.setOptions({"
					+ "\n colors: ['#008000', '#FF0000', '#800000', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']"
					+ "\n });"
					+ "\n $(function () {"
					+ "\n var chart;"
					+ "\n $(document).ready(function() {"
					+ "\n chart = new Highcharts.Chart({"
					+ "\n chart: {"
					+ "\n renderTo: 'pieChartContainer").append(testSuiteId)
					.append("',"
					+ "\n plotBackgroundColor: null,"
					+ "\n plotBorderWidth: null,"
					+ "\n plotShadow: false"
					+ "\n },"
					+ "\n title: {"
					+ "\n text: ''"
					+ "\n },"
					+ "\n credits: {"
					+ "\n enabled: false"
					+ "\n },"
					+ "\n exporting: {"
					+ "\n enabled: false"
					+ "\n },"
					+ "\n tooltip: {"
					+ "\n formatter: function() {"
					+ "\n  return ''+"
					+ "\n    this.point.name +': '+ this.y +' ('+ Math.round(this.percentage * 100)/100 +'%)'+'<br>Total: '+ this.point.total;"
					+ "\n }"
					+ "\n  },"
					+ "\n plotOptions: {"
					+ "\n  pie: {"
					+ "\n  allowPointSelect: true,"
					+ "\n cursor: 'pointer',"
					+ "\n dataLabels: {"
					+ "\n  enabled: true,"
					+ "\n color: '#000000',"
					+ "\n connectorColor: '#000000',"
					+ "\n formatter: function() {"
					+ "\n return '<b>'+ this.point.name +': </b>'+ this.y +' ('+ Math.round(this.percentage * 100)/100 +'%)';"
					+ "\n }"
					+ "\n }"
					+ "\n }"
					+ "\n },"
					+ "\n series: [{"
					+ "\n   type: 'pie',"
					+ "\n   data: ["
					+ "\n     ['Passed', ").append(testSuite.getPassedCases()).append("],")
					.append("\n     ['Failed', ").append(testSuite.getFailedCases()).append("],")
					.append( "\n     ['Skipped', ").append(testSuite.getSkippedCases()).append("]")
					.append( "\n    ]"
					+ "\n }]"
					+ "\n });"
					+ "\n  });"
					+ "\n });"
					+ "\n });"
					+ "\n </script>");

			pieChart.append("\n <TR><td colspan=\"3\" align=\"center\"><div id=\"pieChartContainer")
			.append(testSuiteId)
			.append("\" style=\"height: 250px; margin: 0 auto;\"></div></td></TR>");
			tsInfo.append(pieChart);
			 */
			DefaultPieDataset dataset = new DefaultPieDataset();
			dataset.setValue("Failed", testSuite.getFailedCases());
			dataset.setValue("Skipped", testSuite.getSkippedCases());
			dataset.setValue("Passed", testSuite.getPassedCases());
			setKeyValue(tsInfo, "Pie Chart", "<img alt=\"Pie Chart\" src=\"data:image/png;base64,"+
					new String(Base64.encodeBase64(EncoderUtil.encode(REJfreeChart.getPieChart("", dataset).createBufferedImage(430, 300),ImageFormat.PNG)))+"\" />");
		}

		setTableTail(tsInfo);

		writeInResponse(response, tsInfo);		
	}

	public void getResourceUtilization(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		int testSuiteId = Integer.valueOf(request.getParameter(Keys.TEST_SUITE_ID));		
		TestSuite testSuite = new TestSuiteReport().getTestSuiteDetails(testSuiteId);

		StringBuilder resUtilization = new StringBuilder();
		setTableHead100PercentWidth(resUtilization);

		ArrayList<TestReferenceServerMap> servers = new TestReferenceServerMapTable().getDetailByTestRefId(testSuite.getTestReferenceId());
		for(TestReferenceServerMap server : servers){
			resUtilization.append("\n <TR><td align=\"left\"><h1>Server: <I>").append(server.getServerName()).append("</I></h1></td></TR>");
			resUtilization.append("\n <TR><td align=\"left\">").append("<img alt=\"Pie Chart\" src=\"data:image/png;base64,")
			.append(new String(Base64.encodeBase64(EncoderUtil.encode(
					REJfreeChart.getLineChart("CPU/Memory", getXYDatasetCpuMemory(server, testSuite)).createBufferedImage(430, 300),ImageFormat.PNG))))
					.append("\" />").append("</td></TR>");
			resUtilization.append("\n <TR><td align=\"left\">").append("<img alt=\"Pie Chart\" src=\"data:image/png;base64,")
			.append(new String(Base64.encodeBase64(EncoderUtil.encode(
					REJfreeChart.getLineChart("JVM", getXYDatasetJvmMemory(server, testSuite)).createBufferedImage(430, 300),ImageFormat.PNG))))
					.append("\" />").append("</td></TR>");

		}
		setTableTail(resUtilization);
		writeInResponse(response, resUtilization);		
	}

	private XYDataset getXYDatasetCpuMemory(TestReferenceServerMap server, TestSuite testSuite) throws SQLException{
		
		//CPU
		ResourceCpu resourceCpu = new ResourceCpu();
		resourceCpu.setFromTime(testSuite.getLocalStartTime());
		if(testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
			resourceCpu.setToTime(new Date());
		 }else{
				resourceCpu.setToTime(testSuite.getLocalEndTime());
		 }
		resourceCpu.setTableSubName(String.valueOf(DynamicTable.get(ResourceCpuTable.getCoreCpuSubName(server.getServerId()), server.getServerId(), TYPE.CPU).getId()));		
		ArrayList<ResourceCpu> cpuUsages = new ResourceCpuTable().getByTimeRange(resourceCpu);
		
		
		
		TimeSeries cpu = new TimeSeries(TimeUnit.SECONDS);
		cpu.setDomainDescription("CPU");
		for(ResourceCpu cpuUsage: cpuUsages){
			cpu.add(new Second(cpuUsage.getLocalTime()), Double.valueOf(General.decimalDigit2.format(100.0 - (cpuUsage.getIdle()*100.0))));
		}
		
		//Memory
		ResourceMemory resourceMemory = new ResourceMemory(); 
		resourceMemory.setFromTime(testSuite.getLocalStartTime());
		if(testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
			resourceMemory.setToTime(new Date());
		 }else{
			 resourceMemory.setToTime(testSuite.getLocalEndTime());
		 }
		resourceMemory.setTableSubName(String.valueOf(DynamicTable.get(ResourceMemoryTable.getTableSubName(server.getServerId()), server.getServerId(), TYPE.MEMORY).getId()));
		ArrayList<ResourceMemory> memoryUsages = new ResourceMemoryTable().getByTimeRange(resourceMemory);
				
		
		TimeSeries memory = new TimeSeries(TimeUnit.SECONDS);
		memory.setDomainDescription("Memory");
		for(ResourceMemory memoryUsage: memoryUsages){
			memory.add(new Second(memoryUsage.getLocalTime()), Double.valueOf(General.decimalDigit2.format(((double)memoryUsage.getActualUsed()/memoryUsage.getTotal())*100.0)));
		}


		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(cpu);
		dataset.addSeries(memory);

		dataset.setDomainIsPointsInTime(true);

		return dataset;
	}
	private XYDataset getXYDatasetJvmMemory(TestReferenceServerMap server, TestSuite testSuite){
		//JVM Memory
				JvmMemory jvmMemory = new JvmMemory();
				jvmMemory.setFromTime(testSuite.getLocalStartTime());
				if(testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
					jvmMemory.setToTime(new Date());
				 }else{
					 jvmMemory.setToTime(testSuite.getLocalEndTime());
				 }
				/*//jvmMemory.setTableSubName(String.valueOf(DynamicTable.get(JvmMemoryTable.getTableSubName(SubName(server.getServerId()), tserver.getServerId(), TYPE.CPU).getId()));		
				ArrayList<ResourceCpu> cpuUsages = new ResourceCpuTable().getByTimeRange(resourceCpu);
				
				
				
				TimeSeries cpu = new TimeSeries(TimeUnit.SECONDS);
				cpu.setDomainDescription("CPU");
				for(ResourceCpu cpuUsage: cpuUsages){
					cpu.add(new Second(cpuUsage.getLocalTime()), Double.valueOf(General.decimalDigit2.format(100.0 - (cpuUsage.getIdle()*100.0))));
				}
				
				//Memory
				ResourceMemory resourceMemory = new ResourceMemory(); 
				resourceMemory.setFromTime(testSuite.getLocalStartTime());
				if(testSuite.getTestStatus().equalsIgnoreCase(TestSuite.RUNNING)){
					resourceMemory.setToTime(new Date());
				 }else{
					 resourceMemory.setToTime(testSuite.getLocalEndTime());
				 }
				resourceMemory.setTableSubName(String.valueOf(DynamicTable.get(ResourceMemoryTable.getTableSubName(server.getServerId()), server.getServerId(), TYPE.MEMORY).getId()));
				ArrayList<ResourceMemory> memoryUsages = new ResourceMemoryTable().getByTimeRange(resourceMemory);
						
				
				TimeSeries memory = new TimeSeries(TimeUnit.SECONDS);
				memory.setDomainDescription("Memory");
				for(ResourceMemory memoryUsage: memoryUsages){
					memory.add(new Second(memoryUsage.getLocalTime()), Double.valueOf(General.decimalDigit2.format(((double)memoryUsage.getActualUsed()/memoryUsage.getTotal())*100.0)));
				}


				TimeSeriesCollection dataset = new TimeSeriesCollection();
				dataset.addSeries(cpu);
				dataset.addSeries(memory);

				dataset.setDomainIsPointsInTime(true);
*/
				TimeSeriesCollection dataset = new TimeSeriesCollection();


		return dataset;
	}
}
