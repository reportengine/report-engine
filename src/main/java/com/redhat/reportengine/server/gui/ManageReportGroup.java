package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.dbmap.ReportGroupReference;
import com.redhat.reportengine.server.jobs.EmailReportGroupJob;
import com.redhat.reportengine.server.sql.SqlMap;
import com.redhat.reportengine.server.sql.SqlQuery;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 19, 2012
 */
public class ManageReportGroup {

	public void addUpdateGroup(HttpServletRequest request, HttpServletResponse response, boolean isNewGroup) throws SQLException{
		String[] references = request.getParameterValues(Keys.REPORT_EMAIL_GROUP_REFERENCE);
		String emailTo = request.getParameter(Keys.REPORT_EMAIL_GROUP_EMAIL_TO);
		String emailCc = request.getParameter(Keys.REPORT_EMAIL_GROUP_EMAIL_CC);
		String groupName = request.getParameter(Keys.REPORT_EMAIL_GROUP_NAME);
		boolean isSuiteGroupEnabled = false;
		if(request.getParameter(Keys.REPORT_EMAIL_GROUP_GROUP_ENABLED) != null){
			isSuiteGroupEnabled = true;
		}
		
		if(!(references != null) && (emailTo != null) && (groupName != null)){
			return;						
		}
		
		emailTo		= emailTo.replaceAll("\n", "");
		if(emailCc != null){
			emailCc = emailCc.replaceAll("\n", "");
		}
		
		ReportGroup reportGroup = new ReportGroup();
		reportGroup.setCreationTime(new Date());
		reportGroup.setGroupName(groupName.trim());
		reportGroup.setEmailTo(emailTo);
		reportGroup.setEmailCc(emailCc);	
		
		reportGroup.setTestSuiteGroupEnabled(isSuiteGroupEnabled);
		
		if(isNewGroup){
			reportGroup.setId((Integer) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_REPORT_GROUP_SEQ_ID));
			
			SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_REPORT_GROUP, reportGroup);
		}else{
			reportGroup.setId(Integer.parseInt(request.getParameter(Keys.REPORT_EMAIL_GROUP_ID)));
			SqlMap.getSqlMapClient().update(SqlQuery.UPDATE_REPORT_GROUP, reportGroup);
			SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, reportGroup.getId());
		}
		
		ReportGroupReference reportGroupReference = new ReportGroupReference();
		reportGroupReference.setReportGroupId(reportGroup.getId());
		for(String reference: references){
			reportGroupReference.setTestReferenceId(Integer.valueOf(reference.trim()));
			SqlMap.getSqlMapClient().insert(SqlQuery.INSERT_REPORT_REFERENCE_GROUP, reportGroupReference);
		}
	}
	
	public ReportGroup getReportGroupById(int groupId) throws SQLException{
		ReportGroup reportGroup = (ReportGroup) SqlMap.getSqlMapClient().queryForObject(SqlQuery.GET_REPORT_GROUP_BY_ID, groupId);
		reportGroup.setReportGroupReference((ArrayList<ReportGroupReference>)SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, groupId));
		return reportGroup;
	}
	
	public void deleteReportGroupById(int groupId) throws SQLException{
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID, groupId);
		SqlMap.getSqlMapClient().delete(SqlQuery.DELETE_REPORT_GROUP, groupId);
	}
	
	public ArrayList<ReportGroup> getAllReportGroup() throws SQLException{
		return (ArrayList<ReportGroup>) SqlMap.getSqlMapClient().queryForList(SqlQuery.GET_REPORT_GROUP_ALL);
	}
	
	public void runReportNow(int groupId) throws Exception{
		new EmailReportGroupJob().executeEmailGroupReport(groupId);
	}
}
