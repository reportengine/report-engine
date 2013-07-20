package com.redhat.reportengine.server.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.reportengine.server.dbdata.ReportGroupReferenceTable;
import com.redhat.reportengine.server.dbdata.ReportGroupTable;
import com.redhat.reportengine.server.dbmap.ReportGroup;
import com.redhat.reportengine.server.dbmap.ReportGroupReference;
import com.redhat.reportengine.server.jobs.user.EmailReportGroupJob;
import com.redhat.reportengine.server.reports.Keys;

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

			reportGroup.setId(new ReportGroupTable().getNextSeqId());
			new ReportGroupTable().add(reportGroup);
		}else{
			reportGroup.setId(Integer.parseInt(request.getParameter(Keys.REPORT_EMAIL_GROUP_ID)));
			new ReportGroupTable().modify(reportGroup);
			new ReportGroupReferenceTable().remove(reportGroup.getId());
		}

		ReportGroupReference reportGroupReference = new ReportGroupReference();
		reportGroupReference.setReportGroupId(reportGroup.getId());
		for(String reference: references){
			reportGroupReference.setTestReferenceId(Integer.valueOf(reference.trim()));
			new ReportGroupReferenceTable().add(reportGroupReference);
		}
	}

	public ReportGroup getReportGroupById(int groupId) throws SQLException{
		ReportGroup reportGroup = new ReportGroupTable().get(groupId);
		reportGroup.setReportGroupReference(new ReportGroupReferenceTable().getByReportGroupId(groupId));
		return reportGroup;
	}

	public void deleteReportGroupById(int groupId) throws SQLException{
		new ReportGroupReferenceTable().remove(groupId);
		new ReportGroupTable().remove(groupId);
	}

	public ArrayList<ReportGroup> getAllReportGroup() throws SQLException{
		return new ReportGroupTable().getAll();
	}

	public void runReportNow(int groupId) throws Exception{
		new EmailReportGroupJob().executeEmailGroupReport(groupId);
	}
}
