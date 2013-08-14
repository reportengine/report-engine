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
	
	public static void sendToServer(String objectString, MSG_TYPE msgType) throws JsonGenerationException, JsonMappingException, IOException{
		REUdpPacket udpPacket = new REUdpPacket();
		udpPacket.setMsgType(msgType);
		udpPacket.setObjectString(objectString);
		udpPacket.setServerId(ServerProperties.getServerId());
		Sender.sendMessage(Formatter.getJsonString(udpPacket), ServerProperties.getServerAddress(), ServerProperties.getServerUdpPort());		
	}		   
}
