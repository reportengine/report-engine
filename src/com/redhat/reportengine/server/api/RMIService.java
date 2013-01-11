/**
 * 
 */
package com.redhat.reportengine.server.api;

import java.io.IOException;
import java.net.InetAddress;

import gnu.cajo.invoke.Remote;
import gnu.cajo.utils.ItemServer;

import org.apache.log4j.Logger;

import com.redhat.reportengine.common.ClientRMI;
import com.redhat.reportengine.server.ServerSettings;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 15, 2012
 */
public class RMIService {
	private static Logger _logger = Logger.getLogger(RMIService.class);

	public static void startRMIservice() {		
		try {
			Remote.config(null, ServerSettings.getServerRmiPort(), null, 0);			
			//ItemServer.bind(new RmiPack(), Common.SERVER_RMI_PACK);
			//Object secret = new CryptObject(new RmiPack());			
			ItemServer.bind(new TestResult(), ClientRMI.CLIENT_RMI_PACK);
			_logger.info("Server RMI service is running on the port: "+ServerSettings.getServerRmiPort());
			_logger.info("Host IP: "+InetAddress.getLocalHost().getHostAddress());
		} 
		catch (IOException ex) 	{
			_logger.error("Error on server RMI service, ", ex);
		}
	}
	
	public static void shutdownRMIservice() throws InterruptedException{
		Remote.shutdown();
		Thread.sleep(1000*1);
		_logger.info("RMI service stopped!");
	}
}
