package com.redhat.reportengine.server.queue.actions;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.redhat.reportengine.agent.rest.mapper.UsageCpu;
import com.redhat.reportengine.agent.rest.mapper.UsageCpuMemory;
import com.redhat.reportengine.agent.rest.mapper.UsageCpus;
import com.redhat.reportengine.agent.rest.mapper.UsageMemory;
import com.redhat.reportengine.agent.rest.mapper.jvm.JvmMXBeanStore;
import com.redhat.reportengine.server.insert.InsertCpuUsage;
import com.redhat.reportengine.server.insert.InsertCpusUsage;
import com.redhat.reportengine.server.insert.InsertJvmMemoryUsage;
import com.redhat.reportengine.server.insert.InsertMemoryUsage;
import com.redhat.reportengine.syslog.data.UdpPacket;
import com.redhat.reportengine.syslog.data.UdpPacketQueue;
import com.redhat.reportengine.udppacket.Formatter;
import com.redhat.reportengine.udppacket.REUdpPacket;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 31, 2013
 */
public class InsertUdpPackets extends InsertParent implements Runnable {
	private Logger _logger = Logger.getLogger(InsertUdpPackets.class);
	private UdpPacket packet;
	private REUdpPacket rePacket;
	
	public REUdpPacket getREUdpPacket(UdpPacket packet) throws JsonParseException, JsonMappingException, IOException{
		rePacket = Formatter.getJavaObject(new String(packet.getPacket().getData()), REUdpPacket.class);
		rePacket.setTime(packet.getTime());
		return rePacket;
	}

	public void insertUdpPacket(){
		try {
			packet = UdpPacketQueue.getFirst();
			_logger.debug("Packet From: "+packet.getPacket().getAddress().getHostName());
			rePacket = getREUdpPacket(packet);
			switch(rePacket.getMsgType()){
				case USAGE_CPUS: new Thread(new InsertCpusUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), UsageCpus.class))).start(); break;
				case USAGE_CPU: new Thread(new InsertCpuUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), UsageCpu.class))).start(); break;
				case USAGE_MEMORY: new Thread(new InsertMemoryUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), UsageMemory.class))).start(); break;//UsageCpuMemory
				case USAGE_CPU_MEMORY: 	new Thread(new InsertCpusUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), UsageCpuMemory.class).getUsageCpus())).start();
										new Thread(new InsertMemoryUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), UsageCpuMemory.class).getUsageMemory())).start();
										break;
				case USAGE_JVM_MEMORY:
					new Thread(new InsertJvmMemoryUsage(rePacket.getServerId(), Formatter.getJavaObject(rePacket.getObjectString(), JvmMXBeanStore.class))).start();
					break;
			}

		} catch (Exception ex) {
			_logger.error("Error on UDP Packet insertion,", ex);
		}
	}
	

	@Override
	public void run() {		
		_logger.info("Started UDP Packet Insertion Manager...");
		while(!isStopMeImmeditate()){
			if(UdpPacketQueue.geQueueSize() > 0){
				insertUdpPacket();
			}else{
				try {
					Thread.sleep(10);
				} catch (InterruptedException ex) {
					_logger.error("Thread sleep exception,", ex);
				}
			}
			if(isStopMe() && UdpPacketQueue.geQueueSize() == 0){
				_logger.info("Stopped UDP Packet Insertion Manager...");
				break;
			}
		}
		_logger.info("Stopped UDP Packet Insertion Manager...");
	}
	

}
