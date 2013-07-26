package com.redhat.reportengine.udppacket;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.redhat.reportengine.syslog.data.UdpPacket;
import com.redhat.reportengine.syslog.data.UdpPacketQueue;

public class CollectorTest {
	public static void main(String[] args) throws InterruptedException, JsonParseException, JsonMappingException, IOException{
		Collector collector = new Collector(20500, 5120);
		
		new Thread(collector).start();
		System.out.println("Collector Started");
		while(true){
			if(UdpPacketQueue.geQueueSize() > 0){
				UdpPacket packet = UdpPacketQueue.getFirst();
				System.out.println("Raw: "+ new String(packet.getPacket().getData()));
				System.out.println("Stage 1: "+ new String(packet.getPacket().getData()));
				
				//REUdpPacket packet2 = Formatter.getJavaObject(new String(packet.getPacket().getData()), REUdpPacket.class);
				REUdpPacket packet2 = Formatter.getJavaObject(new String(packet.getPacket().getData()), REUdpPacket.class);
				System.out.println("Stage 2: "+packet2.getObject());

			}else{
				Thread.sleep(1000);
			}
		}
	}
}
