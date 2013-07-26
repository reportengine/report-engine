package com.redhat.reportengine.udppacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

import org.apache.log4j.Logger;

import com.redhat.reportengine.syslog.data.UdpPacket;
import com.redhat.reportengine.syslog.data.UdpPacketQueue;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */
public class Collector implements Runnable{
	private static Logger _logger = Logger.getLogger(Collector.class);

	private static int serverPort;
	private static int packetSize;
	private static DatagramSocket socket;

	public Collector(){
		super();
	}

	public Collector(int serverPort, int pocketSize){
		super();
		Collector.serverPort = serverPort;
		Collector.packetSize = pocketSize;
	}

	public static void collectMessage() throws IOException{
		socket = new DatagramSocket(Collector.serverPort);
		while(true){
			DatagramPacket packet = new DatagramPacket(new byte[Collector.packetSize],Collector.packetSize);
			packet.setLength(Collector.packetSize);
			socket.receive(packet);
			UdpPacketQueue.addPacket(new UdpPacket(packet, new Date().getTime()));
		}
	}
	
	public static void shutdown(){
		socket.close();
	}

	public void run() {
		try {
			Collector.collectMessage();
		} catch (IOException ex) {
			_logger.error("Failed to start the collector,", ex);
		}
		
	}
}
