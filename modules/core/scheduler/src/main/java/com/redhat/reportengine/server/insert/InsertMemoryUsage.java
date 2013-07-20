package com.redhat.reportengine.server.insert;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.server.dbdata.ResourceMemoryTable;
import com.redhat.reportengine.server.dbmap.ResourceMemory;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 18, 2013
 */
public class InsertMemoryUsage implements Runnable{
	private static Logger _logger = Logger.getLogger(InsertMemoryUsage.class);
	private int serverId;
	private UsageMemory usageMemory;
	
	public InsertMemoryUsage(){
		super();
	}
	
	public InsertMemoryUsage(int serverId, UsageMemory usageMemory){
		this.serverId = serverId;
		this.usageMemory = usageMemory;
	}
	
	public void updateMemorySwapUsage(int serverId, UsageMemory usageMemory){
		ResourceMemory resourceMemory = new ResourceMemory();
		resourceMemory.setLocalTime(new Date());
		resourceMemory.setTableSubName(ResourceMemoryTable.getTableSubName(serverId));
		try {
			resourceMemory.setRemoteTime(new Date(usageMemory.getTime()));
			resourceMemory.setTotal(usageMemory.getMemory().getTotal());
			resourceMemory.setUsed(usageMemory.getMemory().getUsed());
			resourceMemory.setActualUsed(usageMemory.getMemory().getActualUsed());
			resourceMemory.setSwapTotal(usageMemory.getSwap().getTotal());
			resourceMemory.setSwapUsed(usageMemory.getSwap().getUsed());
			resourceMemory.setSwapPageIn(usageMemory.getSwap().getPageIn());
			resourceMemory.setSwapPageOut(usageMemory.getSwap().getPageOut());			
			
			new ResourceMemoryTable().add(resourceMemory);

		} catch(SQLException ex){
			if(ex.getMessage().contains("does not exist")){
				try {
					new ResourceMemoryTable().createTable(resourceMemory.getTableSubName());
					new ResourceMemoryTable().add(resourceMemory);
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
		this.updateMemorySwapUsage(this.serverId, this.usageMemory);		
	}

}
