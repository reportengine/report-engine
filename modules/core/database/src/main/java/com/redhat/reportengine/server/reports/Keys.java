/**
 * 
 */
package com.redhat.reportengine.server.reports;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 21, 2012
 */
public class Keys {	
	public static String USER_ID 			= "userId";
	public static String TMP_USER_ID		= "tmpUserId";
	public static String USER_PASSWORD 		= "userPassword";
	public static String USER_PASSWORD_CONF	= "userPasswordConf";
	public static String SESSION_USER_ID 	= "sessionUserId";
	public static String SESSION_STATUS    	= "sessionStatus";
	public static String USER_FIRST_NAME	= "userFirstName";
	public static String USER_LAST_NAME		= "userLastName";
	public static String USER_EMAIL			= "userEmail";
	public static String USER_FORGET_PASSWORD_REFERENCE		= "userForgetPasswordReference";
	public static String SESSION_PASSWORD_RESET 	= "sessionPasswordReset";

	
	public static String TEST_LOG_AJAX_REF    	= "TestLogAjaxRef";
	
	public static String SUBMIT						    	= "SUBMIT";
	
	public static final String ORDER_BY_LOCAL_START_TIME 	= "local_start_time";
	public static final String ORDER_BY_FAILED_CASES 		= "failed_cases";
	public static final String ORDER_BY_PASSED_CASES 		= "passed_cases";
	public static final String ORDER_BY_SKIPPED_CASES 		= "skipped_cases";
	public static final String ORDER_BY_TOTAL_CASES 		= "total_cases";
	public static final String ORDER_BY_TOTAL_DURATION 		= "test_duration";
	
	public static final String LEVEL_WARNING				= "WARNING";
	
	public static final String FREQUENCY_DAILY				= "Daily";
	public static final String FREQUENCY_WEEKLY				= "Weekly";
	public static final String FREQUENCY_MONTHLY			= "Monthly";
	public static final String FREQUENCY_ONE_TIME			= "OneTime";
	
	public static final String DATA_REFERENCDE_ID			= "id";
	
	//For reports
	public static final String DELETE_REPORTS					= "deleteReports";
	public static final String DELETE_REPORT					= "deleteReport";
		
	public static final String REPORT_FOR						= "reportFor";
	public static final String REPORT_FOR_LAST_30_MINS			= "last30minutes";
	public static final String REPORT_FOR_LAST_60_MINS			= "last60minutes";
	public static final String REPORT_FOR_LAST_3_HOURS			= "last3hours";
	public static final String REPORT_FOR_LAST_6_HOURS			= "last6hours";
	public static final String REPORT_FOR_LAST_12_HOURS			= "last12hours";
	public static final String REPORT_FOR_LAST_24_HOURS			= "last24hours";
	public static final String REPORT_FOR_LAST_7_DAYS			= "last7days";
	public static final String REPORT_FOR_LAST_15_DAYS			= "last15days";
	public static final String REPORT_FOR_LAST_30_DAYS			= "last30days";
	public static final String REPORT_FOR_LAST_60_DAYS			= "last60days";
	public static final String REPORT_FOR_LAST_90_DAYS			= "last90days";
	public static final String REPORT_FOR_CUSTOM				= "custom";
	public static final String REPORT_FOR_COMPLETE				= "complete";
	
	public static final String REPORT_DATE_TR					= "reportDateTr";
	
	public static final String REPORT_DATE_FROM					= "datepickerFrom";
	public static final String REPORT_DATE_TO					= "datepickerTo";
	public static final String REPORT_HOUR_FROM					= "hourFrom";
	public static final String REPORT_HOUR_TO					= "hourTo";
	public static final String REPORT_MINUTE_FROM				= "minuteFrom";
	public static final String REPORT_MINUTE_TO					= "minuteTo";
	public static final String REPORT_ACTION					= "action";
	
	//Job Scheduler
	public static final String JOB_ID							= "jobId";
	public static final String JOB_NAME							= "jobName";
	public static final String JOB_DATA							= "jobData";
	public static final String JOB_TYPE							= "jobType";
	public static final String JOB_REFERENCE					= "jobReference";
	public static final String JOB_ENABLED						= "jobEnabled";	
	public static final String JOB_SIMPLE_JOB_ENABLE			= "simpleJobEnable";
	public static final String JOB_REPEAT_COUNT_TR				= "repeatCountTr";
	public static final String JOB_REPEAT_INTERVAL_TR			= "repeatIntervalTr";
	public static final String JOB_REPEAT_COUNT					= "repeatCount";
	public static final String JOB_REPEAT_INTERVAL				= "repeatInterval";
	public static final String JOB_FREQUENCY_TR					= "frequencyTr";
	public static final String JOB_FREQUENCY					= "jobFrequency";
	public static final String JOB_FREQUENCY_DAILY				= FREQUENCY_DAILY;
	public static final String JOB_FREQUENCY_WEEKLY				= FREQUENCY_WEEKLY;
	public static final String JOB_FREQUENCY_MONTHLY			= FREQUENCY_MONTHLY;
	public static final String JOB_FREQUENCY_ONE_TIME			= FREQUENCY_ONE_TIME;
	public static final String JOB_WEEKDAYS_TR					= "JobWeekdaysTr";
	public static final String JOB_WEEKDAYS						= "JobWeekdays";
	public static final String JOB_WEEKDAY_TR					= "JobWeekdayTr";
	public static final String JOB_WEEKDAY						= "JobWeekday";
	public static final String JOB_DAY_OF_MONTH_TR				= "JobDayOfMonthTr";
	public static final String JOB_DAY_OF_MONTH					= "JobDayOfMonth";
	public static final String JOB_ONE_TIME_DATE_TR				= "oneTimeDateTr";
	public static final String JOB_ONE_TIME_DATE				= "datepickerOneTime";
	public static final String JOB_TRIGGER_TIME_TR				= "jobTriggerTimeTr";
	public static final String JOB_TRIGGER_HOUR					= "jobTriggerHour";
	public static final String JOB_TRIGGER_MINUTE				= "jobTriggerMinute";
	public static final String JOB_TRIGGER_SECOND				= "jobTriggerSecond";
	public static final String JOB_END_LESS_TR					= "endLessTr";
	public static final String JOB_END_LESS_ENABLED				= "endLessEnabled";
	public static final String JOB_DATE_FROM_TR					= "datepickerFromTr";
	public static final String JOB_DATE_FROM					= "datepickerFrom";
	public static final String JOB_DATE_FROM_HOUR				= "dateFromHour";
	public static final String JOB_DATE_FROM_MINUTE				= "dateFromMinute";
	public static final String JOB_DATE_TO_TR					= "datepickerToTr";
	public static final String JOB_DATE_TO						= "datepickerTo";
	public static final String JOB_DATE_TO_HOUR					= "dateToHour";
	public static final String JOB_DATE_TO_MINUTE				= "dateToMinute";
	public static final String JOB_CRON_EXPRESSION_ENABLED_TR	= "cronExpressionEnabledTr";
	public static final String JOB_CRON_EXPRESSION_ENABLED		= "cronExpressionEnabled";
	public static final String JOB_CRON_EXPRESSION_TR			= "cronExpressionTr";
	public static final String JOB_CRON_EXPRESSION				= "cronExpression";
	public static final String TYPE								= "TYPE";
	
	
	//Group Report
	public static final String REPORT_EMAIL_GROUP_ID			= "groupId";
	public static final String REPORT_EMAIL_GROUP_NAME			= "groupName";
	public static final String REPORT_EMAIL_GROUP_REFERENCE		= "groupReference";
	public static final String REPORT_EMAIL_GROUP_RESOURCE_METRIC_REFERENCE		= "groupResourceMetricReference";
	public static final String REPORT_EMAIL_GROUP_EMAIL_TO		= "emailTo";
	public static final String REPORT_EMAIL_GROUP_EMAIL_CC		= "emailCc";
	public static final String REPORT_EMAIL_GROUP_GROUP_ENABLED = "isGroupEnabled";
	public static final String REPORT_EMAIL_GROUP_RESOURCE_METRIC_ENABLED = "isResourceMetricEnabled";
	
	//Login Page
	public static final String LOGIN_DB							= "loginDatabase";
	public static final String TMP_LOGIN_DB						= "tmpLoginDatabase";
	
	
	//Servers
	public static final String SERVER_ID							= "serverId";
	public static final String SERVER_NAME							= "serverName";
	public static final String SERVER_HOSTIP						= "serverHostIp";
	public static final String SERVER_MAC_ADDR						= "serverMacAddr";
	public static final String SERVER_AGENT_PORT					= "serverAgentPort";
	public static final String SERVER_UPDATE_INTERVAL				= "serverUpdateInterval";
	
	//Resource CPU/Memory
	public static final String RESOURCE_CPU							= "CPU";
	public static final String RESOURCE_CPU_NO						= "resourceCpuNo";
	public static final String RESOURCE_CPUS						= "CPUS";
	public static final String RESOURCE_CPUS_SPLITER				= "-";
	public static final String RESOURCE_MEMORY						= "Memory";
	public static final String RESOURCE_MEMORY_AU					= "Memory Actual";
	public static final String RESOURCE_SWAP						= "Swap";
	public static final String RESOURCE_TYPE						= "resourceType";
	public static final String SELECTED_RESOURCES					= "selectedResources";
	public static final String RESOURCE_NETWORK						= "Network";
	public static final String RESOURCE_OS							= "OS";
	public static final String CHART_DATA							= "chartData";
	
	public static final String CHART_JVM_MEMORY_HEAP				= "Heap";
	public static final String CHART_JVM_MEMORY_NON_HEAP			= "Non-Heap";
	
	//Test Suite Details
	public static final String SUB_MENU								= "subMenu";
	public static final String TEST_SUITE_DETAILS					= "testSuiteDetails";
	public static final String RESOURCE_UTILIZATION					= "resourceUtilization";
	public static final String TREND_REPORT							= "trendReport";
	
	
	//URL Encoding Decoding
	public static final String URL_ENCODE_UTF_8						= "UTF-8";
	
	//PID data
	public static final String PID									= "pid";
	
	//JVM data
	public static final String JVM_PID								= "jvmPid";
	public static final String JVM_NAME								= "jvmName";
	
	//Interfaces
	public static final String INTERFACE_NAME						= "interfaceName";
	public static final String NETWORK_INFO							= "networkInfo";
	
	//Disk Data
	public static final String DISK_QUERY_TYPE						= "diskQueryType";
	public static final String DISK_FILE_SYSTEM						= "diskFileSystem";
	public static final String DISK_USAGE							= "diskUsage";

	//Test Reference, Server Map
	public static final String TEST_REFERENCE_ID					= "testReferenceId";
	
	//Test Suite
	public static final String TEST_SUITE_ID						= "testSuiteId";
	public static final String TEST_GROUP_ID						= "testGroupId";
	public static final String TEST_SUITE							= "testSuite";
	public static final String TEST_CASE							= "testCase";
	public static final String TEST_CASE_ID							= "testCaseId";
	public static final String TEST_STATUS							= "testStatus";
	


}