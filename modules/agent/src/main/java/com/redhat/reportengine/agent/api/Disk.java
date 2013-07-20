package com.redhat.reportengine.agent.api;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hyperic.sigar.SigarException;

import com.redhat.reportengine.agent.rest.mapper.NetworkInfo;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class Disk {
	private static final Logger _logger = Logger.getLogger(Disk.class.getName());
	
	public static NetworkInfo getNetworkInfo(String ifName){
		NetworkInfo networkInfo = new NetworkInfo();
		try {
			networkInfo.setNetInfo(SigarUtils.getSigar().getNetInfo());
			networkInfo.setNetInterfaceConfig(SigarUtils.getSigar().getNetInterfaceConfig(ifName));
			networkInfo.setNetInterfaceStat(SigarUtils.getSigar().getNetInterfaceStat(networkInfo.getNetInterfaceConfig().getName()));
		} catch (SigarException ex) {
			_logger.warn("Exception, ", ex);
		}
		networkInfo.setTime(new Date().getTime());
		return networkInfo;
	}
	
	public static NetworkInfo getNetworkInfo(){
		return getNetworkInfo(null);
	}
	
}
