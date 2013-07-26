package com.redhat.reportengine.udppacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */

public class Sender {
	private static DatagramPacket packet ;
	private static DatagramSocket socket;
	private static byte []data;
	private static Logger _logger = Logger.getLogger(Sender.class);
	
	private static DatagramSocket getSocket() throws SocketException{
		if(socket == null){
			socket 	= new DatagramSocket();
		}
		return socket;
	}
	
	
	public static void sendMessage(String message, InetAddress address, int port) throws IOException 
	{
		data 	= new byte[message.length()];
		data	= message.getBytes();	
		packet 	= new DatagramPacket(data, data.length, address, port);
		getSocket().send(packet);
		_logger.debug("Message Sent: IP: "+address.getHostAddress()+" PORT: "+port+" MessageLength: "+data.length+" Message: "+message);
	}
	
	public static void closeSocket(){
		socket.close();
	}
}
