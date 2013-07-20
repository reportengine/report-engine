--------------------------------------------------
-- Sequence for Test Reference ID
--------------------------------------------------
CREATE SEQUENCE re_test_reference_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for Test case Suite ID
--------------------------------------------------
CREATE SEQUENCE re_test_suite_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for Test case Suite Aggregation ID
--------------------------------------------------
CREATE SEQUENCE re_test_suite_aggr_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for Test case Group ID
--------------------------------------------------
CREATE SEQUENCE re_test_group_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for Test case result ID
--------------------------------------------------
CREATE SEQUENCE re_test_case_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for Test logs ID
--------------------------------------------------
CREATE SEQUENCE re_test_logs_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
--------------------------------------------------
-- Sequence for File Storage
--------------------------------------------------
CREATE SEQUENCE re_file_storage_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


--------------------------------------------------
-- Table for test Reference
--------------------------------------------------
CREATE TABLE re_test_reference
(
  id integer NOT NULL default nextval('re_test_reference_id_seq'),
  test_reference character varying(100) NOT NULL,
  creation_time timestamp NOT NULL,
  unique(id),
  unique(test_reference)
);

--------------------------------------------------
-- Table for test Suite
--------------------------------------------------
CREATE TABLE re_test_suite
(
  id integer NOT NULL,
  test_reference_id integer NOT NULL,
  test_host character varying(100) NULL,
  test_suite_name character varying(100) NOT NULL,
  test_status character varying(50) NOT NULL,
  test_comments character varying(1000) NULL,
  test_build character varying(100) NULL,
  remote_start_time timestamp NOT NULL,
  remote_end_time timestamp NULL,
  local_start_time timestamp NOT NULL,
  local_end_time timestamp NULL,
  aggregation_status boolean default false,
  unique(id),
  FOREIGN KEY (test_reference_id) REFERENCES re_test_reference (id) ON DELETE CASCADE
);

--------------------------------------------------
-- Table for test Suite Aggregation report
--------------------------------------------------
CREATE TABLE re_test_suite_aggregation
(
  id integer NOT NULL default nextval('re_test_suite_aggr_id_seq'),
  test_suite_id integer NOT NULL,
  total_cases integer NOT NULL,
  passed_cases integer NOT NULL,
  failed_cases integer NOT NULL,
  skipped_cases integer NOT NULL,
  total_changes integer NOT NULL,
  passed_changes integer NOT NULL,
  failed_changes integer NOT NULL,
  skipped_changes integer NOT NULL,
  test_duration bigint NULL,
  test_reference_id integer NOT NULL,
  unique(id),
  unique(test_suite_id),
  FOREIGN KEY (test_suite_id) REFERENCES re_test_suite (id) ON DELETE CASCADE,
  FOREIGN KEY (test_reference_id) REFERENCES re_test_reference (id) ON DELETE CASCADE
);
--------------------------------------------------
-- Table for test groups
--------------------------------------------------
CREATE TABLE re_test_group
(
  id integer NOT NULL default nextval('re_test_group_id_seq'),
  test_suite_id integer NOT NULL,
  test_group character varying(500) NOT NULL,
  test_comments character varying(5000) NULL,
  local_time timestamp NOT NULL,
  remote_time timestamp,
  unique(id),
  unique(test_suite_id, test_group),
  FOREIGN KEY (test_suite_id) REFERENCES re_test_suite(id) ON DELETE CASCADE
);
--------------------------------------------------
-- Table for test results
--------------------------------------------------
CREATE TABLE re_test_case
(
  id integer NOT NULL default nextval('re_test_case_id_seq'),
  test_suite_id integer NOT NULL,
  test_group_id integer NOT NULL,
  test_name character varying(1000) NOT NULL,
  test_arguments character varying(10000) NULL,
  test_result character varying(20) NULL,
  test_comments character varying(10000) NULL,
  test_files character varying(10000) NULL,
  test_gui_files character varying(10000) NULL,
  remote_start_time timestamp NOT NULL,
  remote_end_time timestamp NULL,
  local_start_time timestamp NOT NULL,
  local_end_time timestamp NULL,
  unique(id),
  FOREIGN KEY (test_suite_id) REFERENCES re_test_suite(id) ON DELETE CASCADE,
  FOREIGN KEY (test_group_id) REFERENCES re_test_group(id) ON DELETE CASCADE
);
--------------------------------------------------
-- Table for test logs
--------------------------------------------------
CREATE TABLE re_test_logs
(
  id integer NOT NULL default nextval('re_test_logs_id_seq'),
  test_suite_id integer NOT NULL,
  test_group_id integer NULL,
  test_case_id integer NULL,
  sequence_number integer NOT NULL,
  log_level character varying(50) NOT NULL,
  log_time timestamp NOT NULL,
  local_time timestamp NOT NULL,
  class_name character varying(1000) NOT NULL,
  method_name  character varying(1000) NOT NULL,
  message character varying(10000) NOT NULL,
  throwable character varying(10000) NULL,
  unique(id),
  FOREIGN KEY (test_case_id) REFERENCES re_test_case(id) ON DELETE CASCADE,
  FOREIGN KEY (test_suite_id) REFERENCES re_test_suite(id) ON DELETE CASCADE,
  FOREIGN KEY (test_group_id) REFERENCES re_test_group(id) ON DELETE CASCADE
);

----------------------------------------------------------
-- This table is used to store images and files
----------------------------------------------------------
CREATE TABLE re_file_storage
(
  id integer NOT NULL default nextval('re_file_storage_id_seq'),
  test_case_id integer NOT NULL,
  file_name character varying(200) NOT NULL,
  file_byte bytea NOT NULL,
  creation_time timestamp NOT NULL,
  screen_shot boolean default false,
  unique(id),
  FOREIGN KEY (test_case_id) references re_test_case (id) ON DELETE CASCADE
);

--------------------------------------------------
-- Sequence for Email Report Group seq id
--------------------------------------------------
CREATE SEQUENCE re_report_group_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

----------------------------------------------------------
-- This table is used to email report groups
----------------------------------------------------------
CREATE TABLE re_report_group
(
  id integer NOT NULL default nextval('re_report_group_id_seq'),
  group_name character varying(200) NOT NULL,
  email_to character varying (1000) NOT NULL,
  email_cc character varying (1000) NULL,
  test_suite_group_enabled boolean default false,
  creation_time timestamp NOT NULL,
  unique(id)
);

----------------------------------------------------------
-- This table is used to store 'test references' and 'report group'
----------------------------------------------------------
CREATE TABLE re_report_group_reference
(
  report_group_id integer NOT NULL,
  test_reference_id integer NOT NULL,
  unique(report_group_id, test_reference_id),
  FOREIGN KEY (report_group_id) references re_report_group (id) ON DELETE CASCADE,
  FOREIGN KEY (test_reference_id) references re_test_reference (id) ON DELETE CASCADE
);

--------------------------------------------------
-- Sequence for Job classes 
--------------------------------------------------
CREATE SEQUENCE re_job_classes_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

----------------------------------------------------------
-- This table is used to store job classes
----------------------------------------------------------
CREATE TABLE re_job_classes
(
  id integer NOT NULL default nextval('re_job_classes_id_seq'),
  target_class character varying(1000) NOT NULL,
  target_class_description character varying(200) NULL,
  unique(id),
  unique(target_class)
);

--------------------------------------------------
-- Insert JOB classes
--------------------------------------------------
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.system.TestSuiteAggregationImpl', 'Test Suite Aggregation');
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.system.UpdateJobStatus', 'Update Incomplete Jobs Status');
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.system.ServerAgentReachableStatus', 'Update Server Reachable Status');

INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.user.EmailReportGroupJob', 'Email: Test Reports');
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.user.MeasureCpuUsage', 'Resource: Measure CPU usage');
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.user.MeasureMemoryUsage', 'Resource: Measure Memory/Swap usage');
INSERT INTO re_job_classes (target_class, target_class_description) VALUES ('com.redhat.reportengine.server.jobs.user.MeasureCpuMemoryCpusSwap', 'Resource: Measure CPU/CPUs/Memory/Swap usage');


--------------------------------------------------
-- Sequence for Job Scheduler
--------------------------------------------------
CREATE SEQUENCE re_job_scheduler_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

--------------------------------------------------
-- This table is used to store jobs.
--------------------------------------------------
CREATE TABLE re_job_scheduler
(
  id integer NOT NULL default nextval('re_job_scheduler_id_seq'),
  job_enabled boolean default true,
  system_job boolean default false,
  cron_expression character varying(50) NULL,
  job_name character varying(100) NOT NULL,
  target_class_id integer NOT NULL,
  data_reference_id integer NOT NULL,
  simple_job boolean NOT NULL,
  repeat_interval integer NULL,
  repeat_count    integer NULL,
  valid_from_time timestamp NULL,
  valid_to_time timestamp NULL,
  job_frequency character varying(50) NULL,
  job_weekday character varying(50) NULL,  
  job_day_month character varying(50) NULL,
  job_execution_time time NULL,
  job_description character varying(200) NULL,
  creation_time timestamp NOT NULL,
  last_edit_time timestamp NULL,
  unique(id),
  unique(job_name, target_class_id),
  FOREIGN KEY (target_class_id) REFERENCES re_job_classes(id) ON DELETE CASCADE
);

--------------------------------------------------
-- Insert System jobs and default jobs
--------------------------------------------------
INSERT INTO re_job_scheduler (id, system_job, job_name, target_class_id, data_reference_id, simple_job, repeat_interval, repeat_count, creation_time, job_description) 
VALUES ((SELECT id FROM nextval('re_job_scheduler_id_seq') AS id), true, 'Test Suite Aggregation Job', (SELECT id FROM re_job_classes WHERE target_class LIKE 'com.redhat.reportengine.server.jobs.TestSuiteAggregationImpl'), 0, true, 1000*60*30, -1, now(), '-');

INSERT INTO re_job_scheduler (id, system_job, job_name, target_class_id, data_reference_id, simple_job, repeat_interval, repeat_count, creation_time, job_description) 
VALUES ((SELECT id FROM nextval('re_job_scheduler_id_seq') AS id), true, 'Test Suite Status Update Job', (SELECT id FROM re_job_classes WHERE target_class LIKE 'com.redhat.reportengine.server.jobs.UpdateJobStatus'), 0, true, 1000*60*30, -1, now(), '-');


-------------------------------------------------------
-- View - Get Test Suites - Detailed
-------------------------------------------------------
CREATE VIEW viewgettestsuitesdetailed as select ts.id, ts.test_suite_name, ts.test_status, COALESCE(ts.test_comments,'-') as test_comments, ts.test_build, ts.local_start_time, ts.local_end_time, ts.remote_start_time, ts.remote_end_time, ts.test_host, tr.test_reference, ts.test_reference_id, COALESCE(tsa.total_cases,0) as total_cases, COALESCE(tsa.passed_cases,0) as passed_cases, COALESCE(tsa.failed_cases,0) as failed_cases, COALESCE(tsa.skipped_cases,0) as skipped_cases, COALESCE(tsa.total_changes,0) as total_changes, COALESCE(tsa.passed_changes,0) as passed_changes, COALESCE(tsa.failed_changes,0) as failed_changes, COALESCE(tsa.skipped_changes,0) as skipped_changes, COALESCE(tsa.test_duration,0) as test_duration from re_test_suite as ts JOIN re_test_reference as tr ON ts.test_reference_id=tr.id LEFT JOIN re_test_suite_aggregation as tsa ON ts.id=tsa.test_suite_id;


-----------------------------------------------------------------
-- View - Get Test Suites 
-----------------------------------------------------------------
CREATE VIEW viewgettestsuites AS SELECT ts.id, ts.test_suite_name, ts.test_status, ts.test_comments, ts.test_build, ts.local_start_time, ts.local_end_time, ts.remote_start_time, ts.remote_end_time, ts.test_host, ts.aggregation_status, tr.test_reference, ts.test_reference_id FROM re_test_suite ts, re_test_reference tr WHERE tr.id = ts.test_reference_id;

-----------------------------------------------------------------
-- View - Get Job Scheduler All details
-----------------------------------------------------------------
CREATE VIEW viewgetjobschedulerall AS SELECT js.id, js.job_enabled, js.system_job,  js.cron_expression,  js.job_name,  js.target_class_id,  js.data_reference_id,  js.simple_job,  js.repeat_interval,  js.repeat_count,  js.valid_from_time,  js.valid_to_time,  js.job_frequency,  js.job_weekday,  js.job_day_month,  js.job_execution_time,  js.job_description,  js.creation_time,  js.last_edit_time, jc.target_class, jc.target_class_description FROM re_job_scheduler AS js JOIN re_job_classes AS jc ON js.target_class_id=jc.id;

-----------------------------------------------------------------
-- View - Get Test Details
-----------------------------------------------------------------
CREATE VIEW re_view_gettestcasedetailreport AS SELECT id, test_suite_id, test_group_id, test_name, test_arguments, test_result, local_start_time, local_end_time, test_gui_files, COALESCE(((EXTRACT(EPOCH FROM local_end_time)*1000) - EXTRACT(EPOCH FROM local_start_time)*1000),0) as test_duration from re_test_case;


----------------------------------------------------------
-- This table is used to store Server Settings
----------------------------------------------------------
CREATE TABLE re_settings
(
  key character varying(200) NOT NULL,
  key_value character varying(1000) NOT NULL,
  unique(key)
);
--------------------------------------------------
-- Insert System Settings default key/values
--------------------------------------------------
INSERT INTO re_settings (key, key_value) values ('EmailServer','');
INSERT INTO re_settings (key, key_value) values ('SenderEmail','');
INSERT INTO re_settings (key, key_value) values ('EmailServerPort','25');
INSERT INTO re_settings (key, key_value) values ('TestSuiteInactiveTime','7200000');
INSERT INTO re_settings (key, key_value) values ('EngineRMIPort','9011');
INSERT INTO re_settings (key, key_value) values ('EngineURL','');
INSERT INTO re_settings (key, key_value) values ('EngineCurrentWidGet','start');
INSERT INTO re_settings (key, key_value) values ('EngineAvailableWidGets','black-tie,blitzer,cupertino,dark-hive,dot-luv,eggplant,excite-bike,flick,hot-sneaks,humanity,le-frog,mint-choc,overcast,pepper-grinder,redmond,smoothness,south-street,start,sunny,swanky-purse,trontastic,ui-darkness,ui-lightness,vader');
INSERT INTO re_settings (key, key_value) values ('EngineCurrentMenuStyle','blue');
INSERT INTO re_settings (key, key_value) values ('EngineAvailableMenuStyles','black,blue,green,grey,light_blue,orange,red,white');


------------------------------------------------------------------
-- Indexing 
------------------------------------------------------------------
CREATE INDEX re_index_test_suite_test_reference_id ON re_test_suite (test_reference_id);
CREATE INDEX re_index_test_suite_aggregation_test_reference_id ON re_test_suite_aggregation (test_reference_id);
CREATE INDEX re_index_test_suite_aggregation_test_suite_id ON re_test_suite_aggregation (test_suite_id);
CREATE INDEX re_index_test_group_test_suite_id ON re_test_group (test_suite_id);

CREATE INDEX re_index_test_case_test_suite_id ON re_test_case (test_suite_id);
CREATE INDEX re_index_test_case_test_group_id ON re_test_case (test_group_id);

CREATE INDEX re_index_test_logs_test_suite_id ON re_test_logs (test_suite_id);
CREATE INDEX re_index_test_logs_test_group_id ON re_test_logs (test_group_id);
CREATE INDEX re_index_test_logs_test_case_id ON re_test_logs (test_case_id);

CREATE INDEX re_index_file_storage_test_case_id ON re_file_storage (test_case_id);



--------------------------------------------------
-- Sequence for LDAP Details
--------------------------------------------------
CREATE SEQUENCE re_auth_ldap_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


--------------------------------------------------
-- This table is used to store LDAP Details.
--------------------------------------------------
CREATE TABLE re_auth_ldap
(
  id integer NOT NULL default nextval('re_auth_ldap_id_seq'),
  enabled boolean default true,
  name character varying(100) NOT NULL,
  url character varying(1000) NOT NULL,
  basedn character varying(1000) NOT NULL,
  unique(id),
  unique(name),
  unique(url)
);

  
--------------------------------------------------
-- Sequence for Users
--------------------------------------------------
CREATE SEQUENCE re_auth_user_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

--------------------------------------------------
-- This table is used to store User Details.
--------------------------------------------------
CREATE TABLE re_auth_user
(
  id integer NOT NULL default nextval('re_auth_user_id_seq'),
  enabled boolean default true,
  internal boolean default true,
  ldap_id integer NULL,
  user_name character varying(100) NOT NULL,
  first_name character varying(100) NOT NULL,
  last_name character varying(100) NULL,
  email character varying(100) NOT NULL,
  creation_time timestamp NOT NULL,
  last_edit_time timestamp NULL,
  unique(id),
  unique(user_name),
  unique(email),
  FOREIGN KEY (ldap_id) REFERENCES re_auth_ldap(id) ON DELETE CASCADE
);


--------------------------------------------------
-- This table is used to store User Details.
--------------------------------------------------
CREATE TABLE re_auth_user_password
(
  userid integer NOT NULL,
  password character varying(500) NOT NULL,
  password_salt character varying(500) NOT NULL,
  unique(userid),
  FOREIGN KEY (userid) REFERENCES re_auth_user(id) ON DELETE CASCADE
);


--------------------------------------------------
-- Sequence for Forget Password
--------------------------------------------------
CREATE SEQUENCE re_auth_user_forget_password_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
  ------------------------------------------------------
-- This table is used to store Forget Password details.
--------------------------------------------------------
CREATE TABLE re_auth_user_forget_password
(
  id integer NOT NULL default nextval('re_auth_user_forget_password_id_seq'),
  userid integer NOT NULL,
  reference character varying(1500) NOT NULL,
  creation_time timestamp NOT NULL default statement_timestamp(),
  unique(userid),
  unique(reference),
  FOREIGN KEY (userid) REFERENCES re_auth_user(id) ON DELETE CASCADE
);

-----------------------------------------------------------------
-- View - Get Test Details
-----------------------------------------------------------------
CREATE VIEW re_view_getuserlist AS SELECT * FROM re_auth_user AS au LEFT OUTER JOIN re_auth_user_password AS ap ON au.id=ap.userid;


--------------------------------------------------
-- Sequence for Roles
--------------------------------------------------
CREATE SEQUENCE re_auth_role_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

--------------------------------------------------
-- This table is used to store Roles Names.
--------------------------------------------------
CREATE TABLE re_auth_role
(
  id integer NOT NULL default nextval('re_auth_role_id_seq'),
  name character varying(500) NOT NULL,
  description character varying(5000) NULL,
  unique(id),
  unique(name)
);

--------------------------------------------------
-- Sequence for Auth Permissions
--------------------------------------------------
CREATE SEQUENCE re_auth_permission_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
--------------------------------------------------
-- This table is used to store Auth Permissions.
--------------------------------------------------
CREATE TABLE re_auth_permission
(
  id integer NOT NULL default nextval('re_auth_permission_id_seq'),
  system_level boolean default false,
  name character varying(500) NOT NULL,
  description character varying(5000) NULL,
  unique(id),
  unique(name)
);

--------------------------------------------------
-- Insert Permissions default data
--------------------------------------------------
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Security', 'Manage Users Enable/Disable, Add new permissions, etc.,');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Settings', 'System Level Settings');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Backup', 'System Backup');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Restore', 'System Restore');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Purge', 'System Purge');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Configuration', 'System Level Configurations');
INSERT INTO re_auth_permission (system_level, name, description) values (true, 'Manage Resources', 'Users Resource');

--------------------------------------------------
-- Insert Roles default data
--------------------------------------------------
INSERT INTO re_auth_role (name, description) values ('Super Admin', 'Super Admin has all access');

--------------------------------------------------
-- This table is used to store Roles Details.
--------------------------------------------------
CREATE TABLE re_auth_role_permission_map
(
  roleid integer NOT NULL,
  permissionid integer NOT NULL,
  unique(roleid, permissionid),
  FOREIGN KEY (roleid) REFERENCES re_auth_role(id) ON DELETE CASCADE,
  FOREIGN KEY (permissionid) REFERENCES re_auth_permission(id) ON DELETE CASCADE
);

--------------------------------------------------
-- Insert Role-Permissions default map data
--------------------------------------------------
INSERT INTO re_auth_role_permission_map (roleid, permissionid) SELECT role.id, permission.id FROM re_auth_role role, re_auth_permission permission WHERE role.name='Super Admin' AND permission.system_level=true;

--------------------------------------------------
-- This table is used to store Users-Roles Map.
--------------------------------------------------
CREATE TABLE re_auth_user_role_map
(
  userid integer NOT NULL,
  roleid integer NOT NULL,
  unique(userid, roleid),
  FOREIGN KEY (userid) REFERENCES re_auth_user(id) ON DELETE CASCADE,
  FOREIGN KEY (roleid) REFERENCES re_auth_role(id) ON DELETE CASCADE
);


--------------------------------------------------
-- Tables to record mesurments from remote
--------------------------------------------------

--------------------------------------------------
-- Sequence for Server
--------------------------------------------------
CREATE SEQUENCE re_server_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
--------------------------------------------------
-- This table is used to store Server Details
--------------------------------------------------
CREATE TABLE re_server
(
  id integer NOT NULL default nextval('re_server_id_seq'),
  name character varying(500) NOT NULL,
  host_ip character varying(100) NOT NULL,
  platform character varying(100) NULL,
  agent_port integer NOT NULL,
  reference_key character varying(100) NULL,
  discovery_status character varying(100) NULL,
  update_interval integer NOT NULL DEFAULT 300,  
  creation_time timestamp NOT NULL DEFAULT statement_timestamp(),
  unique(id),
  unique(name),
  unique(reference_key)
);


--------------------------------------------------
-- Sequence for Server-job table
--------------------------------------------------
CREATE SEQUENCE re_server_job_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
--------------------------------------------------
-- This table is used to store Server-Job Map
--------------------------------------------------
CREATE TABLE re_server_job
(
  id integer NOT NULL default nextval('re_server_job_id_seq'),
  server_id integer NOT NULL,
  job_id integer NOT NULL,
  unique(id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE,
  FOREIGN KEY (job_id) REFERENCES re_job_scheduler(id) ON DELETE CASCADE
);

--------------------------------------------------
-- Sequence for Server Status
--------------------------------------------------
CREATE SEQUENCE re_server_status_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
----------------------------------------------------
-- This table is used to store Server Status Details
----------------------------------------------------
CREATE TABLE re_server_status
(
  id integer NOT NULL default nextval('re_server_status_id_seq'),
  server_id integer NOT NULL,
  reachable boolean NOT NULL DEFAULT false,
  agent_status boolean NOT NULL DEFAULT false,
  local_time timestamp NOT NULL DEFAULT statement_timestamp(),
  unique(id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE
);


-----------------------------------------------------------------
-- View - Get Server Details with status
-----------------------------------------------------------------
CREATE VIEW re_view_getserverdetailwithstatus AS SELECT sr.id, sr.name, sr.host_ip, sr.platform, sr.agent_port, sr.reference_key, sr.discovery_status, sr.update_interval, sr.creation_time, COALESCE(srs1.reachable, false) AS reachable,  COALESCE(srs1.agent_status,false) AS agent_status, srs1.local_time FROM re_server AS sr LEFT JOIN re_server_status AS srs1 ON (sr.id=srs1.server_id) LEFT OUTER JOIN re_server_status AS srs2 ON (sr.id = srs2.server_id AND srs1.id < srs2.id) WHERE srs2.id IS NULL;



--------------------------------------------------
-- Sequence for Server CPU Details
--------------------------------------------------
CREATE SEQUENCE re_server_cpu_detail_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
----------------------------------------------------
-- This table is used to store Server Status Details
----------------------------------------------------
CREATE TABLE re_server_cpu_detail
(
  id integer NOT NULL default nextval('re_server_cpu_detail_id_seq'),
  server_id integer NOT NULL,
  remote_time timestamp NOT NULL,
  local_time timestamp NOT NULL DEFAULT statement_timestamp(),
  cache_size bigint NOT NULL,
  cores_per_socket bigint NOT NULL,
  mhz integer NOT NULL,
  model varchar(100) NOT NULL,
  total_cores integer NOT NULL,
  total_sockets integer NOT NULL,
  vendor varchar(100) NOT NULL,
  unique(id),
  unique(server_id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE
);

--------------------------------------------------
-- Sequence for Server Network Details
--------------------------------------------------
CREATE SEQUENCE re_server_network_detail_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
----------------------------------------------------
-- This table is used to store Server Network Details
----------------------------------------------------
CREATE TABLE re_server_network_detail
(
  id integer NOT NULL default nextval('re_server_network_detail_id_seq'),
  server_id integer NOT NULL,
  remote_time timestamp NOT NULL,
  local_time timestamp NOT NULL DEFAULT statement_timestamp(),
  hostname varchar(100) NOT NULL,
  default_gateway varchar(100) NULL,
  primary_dns varchar(100) NULL,
  secondary_dns varchar(100) NULL,
  domain_name varchar(100) NULL,
  iface_name varchar(50) NOT NULL,
  iface_description varchar(500) NULL,
  iface_type varchar(100) NOT NULL,
  ip_address varchar(100) NOT NULL,
  subnetmask varchar(100) NOT NULL,
  broadcast varchar(100) NOT NULL,
  destination varchar(100) NULL,
  mac varchar(100) NOT NULL,
  flags bigint NULL,
  metric bigint NULL,
  mtu bigint NULL,
  unique(id),
  unique(server_id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE
);


--------------------------------------------------
-- Sequence for Server OS Details
--------------------------------------------------
CREATE SEQUENCE re_server_os_detail_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
----------------------------------------------------
-- This table is used to store Server Network Details
----------------------------------------------------
CREATE TABLE re_server_os_detail
(
  id integer NOT NULL default nextval('re_server_os_detail_id_seq'),
  server_id integer NOT NULL,
  remote_time timestamp NOT NULL,
  local_time timestamp NOT NULL DEFAULT statement_timestamp(),
  name varchar(100) NOT NULL,
  description varchar(300) NULL,
  arch varchar(100) NOT NULL,
  machine varchar(100) NULL,
  kernel_version varchar(300) NULL,
  patch_level varchar(500) NULL,
  vendor varchar(300) NOT NULL,
  vendor_version varchar(500) NULL,
  vendor_code_name varchar(100) NULL,
  data_model varchar(100) NULL,
  cpu_endian varchar(100) NULL,
  vendor_name varchar(100) NULL,
  unique(id),
  unique(server_id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE
);

--------------------------------------------------
-- Sequence for Server MEMORY Details
--------------------------------------------------
CREATE SEQUENCE re_server_memory_detail_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
----------------------------------------------------
-- This table is used to store Server Network Details
----------------------------------------------------
CREATE TABLE re_server_memory_detail
(
  id integer NOT NULL default nextval('re_server_memory_detail_id_seq'),
  server_id integer NOT NULL,
  remote_time timestamp NOT NULL,
  local_time timestamp NOT NULL DEFAULT statement_timestamp(),
  physical bigint NOT NULL,
  swap bigint NOT NULL,
  unique(id),
  unique(server_id),
  FOREIGN KEY (server_id) REFERENCES re_server(id) ON DELETE CASCADE
);
