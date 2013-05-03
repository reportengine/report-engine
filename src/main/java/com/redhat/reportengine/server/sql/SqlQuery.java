/**
 * 
 */
package com.redhat.reportengine.server.sql;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
public class SqlQuery {
	public static String GET_TEST_SUITES 			= "getTestSuites";
	public static String GET_TOP_N_TEST_SUITES 		= "getTopNTestSuites";
	public static String GET_TOP_N_TEST_SUITES_EXCLUDE_RUNNING 		= "getTopNTestSuitesExcludeRunning";
	public static String GET_TREND_REPORT 			= "getTrendReportExcludeRunning";
	
	public static String INSERT_TEST_SUITE 			= "insertTestSuite";
	public static String UPDATE_TEST_SUITE 			= "updateTestSuite";
	public static String UPDATE_TEST_SUITE_NAME 	= "updateTestSuiteName";
	public static String GET_TEST_SUITE_NEXT_ID 	= "getTestSuiteNextId";
	public static String GET_TEST_SUITE_BY_ID 		= "getTestSuiteById";
	public static String GET_TEST_SUITE_BY_REFF		= "getTestSuiteByNameRef";
	public static String ENABLE_TEST_SUITE_AGGREGATION_STATUS		= "enableTestSuiteAggregationStatus";
	
	
	public static String GET_TEST_GROUP_BY_SUITE_ID_NAME	= "getTestGroupWithTestSuiteIdTestGroup";
	public static String INSERT_TEST_GROUP					= "insertTestGroup";
	public static String GET_TEST_GROUP_SUITE_BY_ID 		= "getTestGroupSuiteById";
	public static String GET_TEST_GROUPS_BY_ID_DETAILED_COUNT	= "getTestGroupsByIdDetailedCount";
	
	public static String GET_TOP_TEST_SUITE_BY_TEST_REFERENCE_IDS	= "getTopTestSuitesByTestReferenceIds";
	
	
	public static String INSERT_TEST_CASE			= "insertTestCase";
	public static String UPDATE_TEST_CASE			= "updateTestCase";
	public static String GET_TEST_CASE_ID			= "getTestCaseID";
	public static String GET_TEST_CASES				= "getTestCases";
	public static String GET_TEST_CASES_REPORT		= "getTestCasesReport";
	public static String GET_TEST_CASES_REPORT_BY_SUITE_ID_ALL			= "getTestCasesReportBySuiteIdAll";
	public static String GET_TEST_CASES_REPORT_BY_SUITE_ID_AND_STATUS	= "getTestCasesReportBySuiteIdAndStatus";
	public static String GET_TEST_CASES_REPORT_BY_GROUP_ID				= "getTestCasesReportByGroupId";
	public static String GET_TEST_CASES_REPORT_BY_GROUP_ID_AND_STATUS	= "getTestCasesReportByGroupIdAndStatus";
	
	public static String GET_TEST_CASE_DETAIL		= "getTestCaseDetailById";
	
	public static String INSERT_TEST_LOGS				= "insertTestLogs";
	public static String GET_TEST_LOGS_BY_TEST_SUITE_ID = "getTestLogsByTestSuiteId";
	public static String GET_TEST_LOGS_BY_TEST_GROUP_ID = "getTestLogsByTestGroupId";
	public static String GET_TEST_LOGS_BY_TEST_CASE_ID 	= "getTestLogsByTestCaseId";
	
	public static String GET_TEST_LOGS_BY_TEST_SUITE_ID_AJAX 	= "getTestLogsByTestSuiteIdAjax";
	public static String GET_TEST_LOGS_BY_TEST_GROUP_ID_AJAX 	= "getTestLogsByTestGroupIdAjax";
	public static String GET_TEST_LOGS_BY_TEST_CASE_ID_AJAX 	= "getTestLogsByTestCaseIdAjax";
	
	public static String GET_TEST_LOGS_BY_ID 			= "getTestLogsById";
	
	
	public static String GET_TEST_CASES_SCREEN_SHOT_BY_ID 			= "getTestCasesScreenShot";
		
	//Aggregation
	
	public static String GET_TEST_SUITES_NON_AGGREGATE 							= "getTestSuitesNonAggregate";
	public static String GET_TEST_SUITE_PRE_AGGREGATION_BY_TEST_SUITE_ID 		= "getTestSuitePreAggregationByTestSuiteId";
	public static String GET_TEST_SUITE_AGGREGATION_BY_TEST_REFERENCE_ID	 	= "getTestSuiteAggregationByReferenceId";
	public static String INSERT_TEST_SUITE_AGGREGATION				 			= "insertTestSuiteAggregation";
	public static String GET_TOP_N_TEST_REFERENCE_AGGREGATION_EXCLUDE_RUNNING	="getTopNTestReferenceAggregationExcludeRunning";
	
	//Delete Statement
	
	public static String DELETE_TEST_LOGS_BY_TEST_SUITE_ID				= "deleteTestLogsByTestSuiteId";
	public static String DELETE_TEST_CASE_BY_TEST_SUITE_ID				= "deleteTestCaseByTestSuiteId";
	public static String DELETE_TEST_GROUP_BY_TEST_SUITE_ID				= "deleteTestGroupByTestSuiteId";
	public static String DELETE_TEST_SUITE_AGGREGSTION_BY_TEST_SUITE_ID	= "deleteTestSuiteAggregationByTestSuiteId";
	public static String DELETE_TEST_SUITE_BY_ID						= "deleteTestSuiteById";
	
	
	// Test case
	
	public static String GET_TEST_SUITES_RUNNING						= "getTestSuitesRunning";
	public static String GET_TEST_CASE_LATEST_BY_SUITE_ID				= "getLatestTestCaseBySuiteID";
	public static String UPDATE_TEST_CASE_RESULT_BY_SUITE_ID			= "updateTestCaseResult";
	public static String UPDATE_TEST_SUITE_STATUS_ID					= "updateTestSuiteStatusById";
	
	//Test References
	
	public static String GET_TEST_REFERENCE_BY_NAME						= "getTestReferenceByName";
	public static String GET_TEST_REFERENCE_ALL							= "getTestReferenceAll";
	public static String GET_TEST_REFERENCE_NEXT_ID						= "getTestReferenceNextId";
	public static String INSERT_TEST_REFERENCE							= "insertTestReference";
	
	//File Storage
	
	public static String INSERT_FILE_STORAGE							= "insertFileStorage";
	public static String GET_FILE_STORAGE_BY_TEST_CASE_ID_SCREEN_SHOT	= "getFileStorageByTestCaseIdScreenShot";
	public static String GET_SCREEN_SHOT_FILES							= "getScreenShotFiles";
	public static String GET_FILE_STORAGE_BY_ID							= "getFileStorageById";
	
	//Report Group
	public static String GET_REPORT_GROUP_SEQ_ID						= "getReportGroupNextId";
	public static String GET_REPORT_GROUP_ALL							= "getReportGroupAll";
	public static String GET_REPORT_GROUP_BY_ID							= "getReportGroupById";
	public static String INSERT_REPORT_GROUP							= "insertReportGroup";
	public static String UPDATE_REPORT_GROUP							= "updateReportGroup";
	public static String DELETE_REPORT_GROUP							= "deleteReportGroup";

	//Report Group Reference
	public static String GET_REPORT_GROUP_REFERENCE_ALL					= "getReportGroupReferenceAll";
	public static String GET_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID	= "getReportGroupReferenceByReportGroupId";
	public static String INSERT_REPORT_REFERENCE_GROUP					= "insertReportGroupReference";
	public static String DELETE_REPORT_GROUP_REFERENCE_BY_REPORT_GROUP_ID  = "deleteReportGroupReferenceByReportGroupId";
	
	public static String GET_TOP_TEST_SUITES_BY_TEST_REFERENCE_IDS		= "getTopTestSuitesByTestReferenceIds";
	
	//Job Schedules
	public static String GET_JOB_SCHEDULER_NEXT_SEQ_ID					= "getJobSchedulerNextId";
	public static String GET_JOB_SCHEDULER_ALL							= "getJobSchedulerAll";
	public static String GET_JOB_USER_SCHEDULER_ALL						= "getUserJobSchedulerAll";
	public static String GET_JOB_SCHEDULER_BY_ID						= "getJobSchedulerById";
	public static String GET_JOB_SCHEDULER_BY_NAME						= "getJobSchedulerByJobName";
	public static String INSERT_JOB_SCHEDULER							= "insertJobScheduler";
	public static String DELETE_SCHEDULED_JOB							= "deleteAscehduledJob";
	public static String ENABLE_SCHEDULED_JOB							= "enableAscehduledJob";
	public static String DISABLE_SCHEDULED_JOB							= "disableAscehduledJob";
	
	
	
	//Engine Settings
	public static String GET_ENGINE_SETTINGS_ALL			= "getEngineSettingsAll";
	public static String GET_ENGINE_SETTINGS_BY_KEY			= "getEngineSettingsByKey";
	public static String INSERT_ENGINE_SETTINGS				= "insertEngineSettings";
	public static String UPDATE_ENGINE_SETTINGS				= "updateEngineSettings";
	public static String DELETE_ENGINE_SETTINGS				= "deleteEngineSettings";
	
	//User 
	
	public static String GET_USER_DETAILS_BY_USER_NAME		= "getUserByUserName";
	

	
	}
