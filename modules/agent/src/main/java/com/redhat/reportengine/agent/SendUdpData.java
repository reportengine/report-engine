package com.redhat.reportengine.agent;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.redhat.reportengine.udppacket.Formatter;
import com.redhat.reportengine.udppacket.MSG_TYPE;
import com.redhat.reportengine.udppacket.REUdpPacket;
import com.redhat.reportengine.udppacket.Sender;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */
public class SendUdpData {
	
	private static REUdpPacket udpPacket;
	
	public static void sendToServer(Object object, MSG_TYPE msgType) throws JsonGenerationException, JsonMappingException, IOException{
		REUdpPacket udpPacket = new REUdpPacket();
		udpPacket.setMsgType(msgType);
		udpPacket.setObject(object);
		Sender.sendMessage(Formatter.getJsonString(udpPacket), ServerProperties.getServerAddress(), ServerProperties.getServerUdpPort());		
	}
	
	public static REUdpPacket getREUdpPacket(){
		if(udpPacket == null){
			udpPacket = new REUdpPacket();
			udpPacket.setServerId(ServerProperties.getServerId());
		}
		return udpPacket;
	}
	
 
		   
}
