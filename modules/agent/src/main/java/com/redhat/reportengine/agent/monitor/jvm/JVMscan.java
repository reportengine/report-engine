package com.redhat.reportengine.agent.monitor.jvm;


import java.util.Timer;
import org.apache.log4j.Logger;
import com.redhat.reportengine.agent.JvmProperties;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jan 22, 2014
 */
public class JVMscan{
	private static final Logger _logger = Logger.getLogger(JVMscan.class);
	private static Timer timer;
	private static boolean enabled = false;

	public static void startScan(){
		if(JvmProperties.getMonitorJVMs() != null){
			timer = new Timer();
			// initialise JVM scanner thread
			timer.schedule(new ScanVM(), 500, JvmProperties.getScanPeriod());
			setEnabled(true);
			_logger.info("JVMscan timer scheduled...[JVMs:"+JvmProperties.getMonitorJVMs()+"], Update Every: "+(JvmProperties.getScanPeriod()/1000)+" Sec(s)");
		}else{
			_logger.error("Failed to start JVMscan timer...There is no JVM specified to scan...");
		}		
	}

	public static void stopScan(){
		if(timer != null){
			timer.cancel();
			timer = null;
			setEnabled(false);
			_logger.info("JVMscan timer cancelled...[JVMs:"+JvmProperties.getMonitorJVMs()+"]");
		}else{
			_logger.info("JVMscan timer is not running...");
		}		
	}

	public static boolean isEnabled() {
		return enabled;
	}

	private static void setEnabled(boolean enabled) {
		JVMscan.enabled = enabled;
	}
}