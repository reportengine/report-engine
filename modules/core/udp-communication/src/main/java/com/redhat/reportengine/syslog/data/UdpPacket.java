package com.redhat.reportengine.syslog.data;

import java.net.DatagramPacket;

public class UdpPacket {
	private DatagramPacket packet;
	private long time;
	
	public UdpPacket(){
		super();
	}
	
	public UdpPacket(DatagramPacket packet, long time){
		this.packet = packet;
		this.time = time;
	}

	public DatagramPacket getPacket() {
		return packet;
	}

	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
