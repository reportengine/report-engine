package com.redhat.reportengine.server.insert;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.server.dbdata.ResourceCpuTable;
import com.redhat.reportengine.server.dbmap.ResourceCpu;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 18, 2013
 */
public class InsertCpuUsage implements Runnable {
	private static Logger _logger = Logger.getLogger(InsertCpuUsage.class);
	
	private int serverId;
	private UsageCpu usageCpu;
	
	public InsertCpuUsage(int serverId, UsageCpu usageCpu){
		this.serverId = serverId;
		this.usageCpu = usageCpu;
	}
	
	public void updateCpuUsage(int serverId, UsageCpu usageCpu){
		ResourceCpu resourceCpu = new ResourceCpu();
		resourceCpu.setLocalTime(new Date());
		resourceCpu.setTableSubName(ResourceCpuTable.getCoreCpuSubName(serverId));
		try {
			resourceCpu.setCombined((float) usageCpu.getCpu().getCombined());
			resourceCpu.setIdle((float) usageCpu.getCpu().getIdle());
			resourceCpu.setIrq((float) usageCpu.getCpu().getIrq());
			resourceCpu.setNice((float) usageCpu.getCpu().getNice());
			resourceCpu.setSoftIrq((float) usageCpu.getCpu().getSoftIrq());
			resourceCpu.setStolen((float) usageCpu.getCpu().getStolen());
			resourceCpu.setSys((float) usageCpu.getCpu().getSys());
			resourceCpu.setUser((float) usageCpu.getCpu().getUser());
			resourceCpu.setWait((float) usageCpu.getCpu().getWait());
			resourceCpu.setRemoteTime(new Date(usageCpu.getTime()));
			
			new ResourceCpuTable().add(resourceCpu);

		} catch(SQLException ex){
			if(ex.getMessage().contains("does not exist")){
				try {
					new ResourceCpuTable().createTable(resourceCpu.getTableSubName());
					new ResourceCpuTable().add(resourceCpu);
				} catch (SQLException e) {
					_logger.error("Exception, ", ex);
				}
			}else{
				_logger.error("Exception, ", ex);
			}
		}		
	}
	
	@Override
	public void run() {
		this.updateCpuUsage(this.serverId, this.usageCpu);		
	}

}
