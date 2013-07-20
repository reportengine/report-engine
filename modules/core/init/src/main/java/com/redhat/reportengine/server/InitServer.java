/**
 * 
 */
package com.redhat.reportengine.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

import com.redhat.reportengine.scheduler.ManageScheduler;
import com.redhat.reportengine.server.cache.ServerSettings;
import com.redhat.reportengine.server.jobs.system.TestSuiteAggregationImpl;
import com.redhat.reportengine.server.jobs.system.UpdateJobStatus;
import com.redhat.reportengine.server.queue.actions.ManageQueues;
import com.redhat.reportengine.server.scheduler.ManageJobs;
import com.redhat.reportengine.server.sql.SqlMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
public class InitServer extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger _logger = Logger.getLogger(InitServer.class);


	public void init() throws ServletException 	{
		try {
			ServerSettings.setBaseLocation(getServletContext().getRealPath("/"));
			System.out.println("Report Engine: Base Location: "+ServerSettings.getBaseLocation());
			
			//Make database connection
			SqlMap.setUpSqlClient();
			
			//Load Engine Settings
			ServerSettings.updateSystemSettingsFromDB();
			_logger.info("Engine Settings are loaded successfully!!");
			
			//Start Quartz Scheduler..
			new Thread(new ManageScheduler()).start();
			Thread.sleep(1000*3);
			
			//Add system jobs in scheduler
			ManageJobs.loadAllJobs();

			
			_logger.debug("Starting Aggregation................");
			TestSuiteAggregationImpl.doAggregateTestSuite();
			_logger.debug("Completed Aggregation...............");
			
			//Update Job Status
			_logger.debug("Starting Update Job Status...............");
			new UpdateJobStatus().execute(null);
			_logger.debug("Completed Update Job Status...............");
			
			
			//Starting all Test Queue Managers (implemented log queue)
			ManageQueues.startAllQueueManagers();
					
		}catch (Exception ex) {
			_logger.fatal("error, ", ex);
		}

	}
	
	 public void destroy(){
		 _logger.info("Shutdown application command has been issued! Shutting down services (Threads)...");
		 
		 //Stop Quartz Scheduler...
		 ManageScheduler.shutdown();
		 //Stopping Queue Managers
		 ManageQueues.stopAllQueueManagers();
		 //Closing SQL Map active session
		 SqlMap.stopSqlMapClient();		 
		 _logger.info("Shutdown all services (Threads) has been completed!!");
	 }
}
