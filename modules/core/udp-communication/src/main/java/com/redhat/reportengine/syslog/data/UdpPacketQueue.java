package com.redhat.reportengine.syslog.data;

import java.util.LinkedList;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */

public class UdpPacketQueue {

	private static LinkedList<UdpPacket> packetsQueue = new LinkedList<UdpPacket>();

	public static synchronized int geQueueSize(){
		return packetsQueue.size();
	}
	
	public static synchronized UdpPacket getFirst(){
		UdpPacket syslog = packetsQueue.getFirst();
		packetsQueue.removeFirst();
		return syslog;
	}
	
	public static synchronized void addPacket(UdpPacket syslog){
		packetsQueue.addLast(syslog);
	}

}
