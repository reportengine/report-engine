package com.redhat.reportengine.server.insert;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMXBeanStore;
import com.redhat.reportengine.server.collection.DynamicTable;
import com.redhat.reportengine.server.dbdata.JvmMemoryTable;
import com.redhat.reportengine.server.dbmap.DynamicTableName.TYPE;
import com.redhat.reportengine.server.dbmap.JvmMemory;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 03, 2014
 */
public class InsertJvmMemoryUsage implements Runnable{
	private static Logger _logger = Logger.getLogger(InsertJvmMemoryUsage.class);
	private int serverId;
	private JvmMXBeanStore mxBeanStore;

	public InsertJvmMemoryUsage(){
		super();
	}

	public InsertJvmMemoryUsage(int serverId, JvmMXBeanStore mxBeanStore){
		this.serverId = serverId;
		this.mxBeanStore = mxBeanStore;
	}

	public void updateJvmMemoryUsage(int serverId, JvmMXBeanStore mxBeanStore){
		JvmMemory jvmMemory = new JvmMemory();
		jvmMemory.setLocalTime(new Date());

		jvmMemory.setRemoteTime(new Date(mxBeanStore.getTime()));
		jvmMemory.setHeapMemory(true);
		jvmMemory.setCommitted(mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getCommitted());
		jvmMemory.setMax(mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getMax());
		jvmMemory.setInit(mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getInit());
		jvmMemory.setUsed(mxBeanStore.getMemoryMXBean().getHeapMemoryUsage().getUsed());

		//Insert Heap memory in to DB table
		insertIntoTable(serverId, jvmMemory);

		jvmMemory.setHeapMemory(false);
		jvmMemory.setCommitted(mxBeanStore.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted());
		jvmMemory.setMax(mxBeanStore.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
		jvmMemory.setInit(mxBeanStore.getMemoryMXBean().getNonHeapMemoryUsage().getInit());
		jvmMemory.setUsed(mxBeanStore.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
		
		//Insert Non-Heap memory in to DB table
		insertIntoTable(serverId, jvmMemory);
	}

	private void insertIntoTable(int serverId, JvmMemory jvmMemory){
		JvmMemoryTable jvmMemoryTable = new JvmMemoryTable();
		try{
			//setTable Name
			jvmMemory.setTableSubName(String.valueOf(DynamicTable.get(JvmMemoryTable.getTableSubName(mxBeanStore.getVirtualMachineDescriptor().getDisplayName(), serverId), serverId, TYPE.JVM_MEMORY).getId()));
			//Insert
			jvmMemoryTable.add(jvmMemory);
			_logger.debug("JVM Memory usage details inserted on the table, "+jvmMemory.getTableSubName());
		} catch(SQLException ex){
			if(ex.getMessage().contains("does not exist")){
				try {
					jvmMemoryTable.createTable(jvmMemory.getTableSubName());
					jvmMemoryTable.add(jvmMemory);
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
		this.updateJvmMemoryUsage(this.serverId, this.mxBeanStore);		
	}

}
