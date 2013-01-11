/**
 * 
 */
package com.redhat.reportengine.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.redhat.reportengine.server.api.RMIService;
import com.redhat.reportengine.server.queue.actions.ManageQueues;
import com.redhat.reportengine.server.scheduler.ManageScheduler;
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
			System.setProperty("log4file.location", ServerSettings.getBaseLocation()+ServerSettings.getLog4jloglocation());			
			PropertyConfigurator.configureAndWatch(ServerSettings.getBaseLocation()+ServerSettings.getLog4jlocation());
			
			//ServerSettings.setServerRmiPort(21013);
			
			//Make database connection
			SqlMap.setUpSqlClient();
			
			//Load Engine Settings
			ServerSettings.updateSystemSettingsFromDB();
			_logger.info("Engine Settings are loaded successfully!!");
			
			//Run RMI service to listen clients...
			RMIService.startRMIservice();
			
			//Start Quartz Scheduler..
			new Thread(new ManageScheduler()).start();
			Thread.sleep(1000*5);
			
			//Add system jobs in scheduler
			ManageJobs.loadAllJobs();
			

			//Add user jobs in job scheduler
			//TODO: add user jobs in job scheduler
			
			/*
			//TODO: add this job on scheduler..
			_logger.info("Starting Aggregation................");
			new TestSuiteAggregationImpl().doAggregateTestSuite();
			_logger.info("Completed Aggregation...............");
			
			//TODO: add this job on scheduler
			//Update Job Status
			_logger.info("Starting Update Job Status...............");
			new UpdateJobStatus().execute(null);
			_logger.info("Completed Update Job Status...............");
			
			*/
			//Starting all Test Queue Managers (implemented log queue)
			ManageQueues.startAllQueueManagers();
			
					
			//Updated image files to DB..This is used to import old screen shot images from disk...
			//ImportImagesToDB.importImagesToDatabase();
			
		}catch (Exception ex) {
			_logger.fatal("error, ", ex);
		}

	}
	
	 public void destroy(){
		 _logger.info("Shutdown application command has been issued! Shutting down services (Threads)...");
		 
		 //Stop Quartz Scheduler...
		 ManageScheduler.shutdownScheduler();
		 
		 //Stopping RMI services
		 try {
			RMIService.shutdownRMIservice();
			_logger.info("RMI service Stopped!");
		} catch (InterruptedException ex) {
			_logger.error("Exception while stopping RMI service, ", ex);
		}
		 //Stopping Queue Managers
		 ManageQueues.stopAllQueueManagers();
		 //Closing SQL Map active session
		 SqlMap.stopSqlMapClient();		 
		 _logger.info("Shutdown all services (Threads) has been completed!!");
	 }
}
