package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCred;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcFd;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;


/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 27, 2013
 */
@XmlRootElement
public class PidDetail extends AgentBaseMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8811723690736495628L;
		
	private long agentDate;
	
	private long pid;
	private boolean available;
	private String[] progArgs;
	private ProcCpu procCpu;
	private ProcCred procCred;
	private ProcCredName procCredName;
	private java.util.Map procEnv;
	private ProcExe procExe;
	private ProcFd procFd;
	private ProcMem procMem;
	private java.util.List procModules;
	private  ProcState  procState;
	private  ProcTime procTime;
	//Try to determine classname for java programs
	private String description;
	
	//Basic Details
	private long ppid;
	private String uid;
	private String cmd;
	private long startTime;
	private int tty;
	private double cpu;
	private long memory;
	
	
	public PidDetail(){
		super();
	}

	/**
	 * @return the agentDate
	 */
	public long getAgentDate() {
		return agentDate;
	}

	/**
	 * @param agentDate the agentDate to set
	 */
	public void setAgentDate(long agentDate) {
		this.agentDate = agentDate;
	}

	/**
	 * @return the pid
	 */
	public long getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}

	/**
	 * @return the progArgs
	 */
	public String[] getProgArgs() {
		return progArgs;
	}

	/**
	 * @param progArgs the progArgs to set
	 */
	public void setProgArgs(String[] progArgs) {
		this.progArgs = progArgs;
	}

	/**
	 * @return the procCpu
	 */
	public ProcCpu getProcCpu() {
		return procCpu;
	}

	/**
	 * @param procCpu the procCpu to set
	 */
	public void setProcCpu(ProcCpu procCpu) {
		this.procCpu = procCpu;
	}

	/**
	 * @return the procCred
	 */
	public ProcCred getProcCred() {
		return procCred;
	}

	/**
	 * @param procCred the procCred to set
	 */
	public void setProcCred(ProcCred procCred) {
		this.procCred = procCred;
	}

	/**
	 * @return the procCredName
	 */
	public ProcCredName getProcCredName() {
		return procCredName;
	}

	/**
	 * @param procCredName the procCredName to set
	 */
	public void setProcCredName(ProcCredName procCredName) {
		this.procCredName = procCredName;
	}

	/**
	 * @return the procEnv
	 */
	public java.util.Map getProcEnv() {
		return procEnv;
	}

	/**
	 * @param procEnv the procEnv to set
	 */
	public void setProcEnv(java.util.Map procEnv) {
		this.procEnv = procEnv;
	}

	/**
	 * @return the procExe
	 */
	public ProcExe getProcExe() {
		return procExe;
	}

	/**
	 * @param procExe the procExe to set
	 */
	public void setProcExe(ProcExe procExe) {
		this.procExe = procExe;
	}

	/**
	 * @return the procFd
	 */
	public ProcFd getProcFd() {
		return procFd;
	}

	/**
	 * @param procFd the procFd to set
	 */
	public void setProcFd(ProcFd procFd) {
		this.procFd = procFd;
	}

	/**
	 * @return the procMem
	 */
	public ProcMem getProcMem() {
		return procMem;
	}

	/**
	 * @param procMem the procMem to set
	 */
	public void setProcMem(ProcMem procMem) {
		this.procMem = procMem;
	}

	/**
	 * @return the procModules
	 */
	public java.util.List getProcModules() {
		return procModules;
	}

	/**
	 * @param procModules the procModules to set
	 */
	public void setProcModules(java.util.List procModules) {
		this.procModules = procModules;
	}

	/**
	 * @return the procState
	 */
	public ProcState getProcState() {
		return procState;
	}

	/**
	 * @param procState the procState to set
	 */
	public void setProcState(ProcState procState) {
		this.procState = procState;
	}

	/**
	 * @return the procTime
	 */
	public ProcTime getProcTime() {
		return procTime;
	}

	/**
	 * @param procTime the procTime to set
	 */
	public void setProcTime(ProcTime procTime) {
		this.procTime = procTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public long getPpid() {
		return ppid;
	}

	public void setPpid(long ppid) {
		this.ppid = ppid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getTty() {
		return tty;
	}

	public void setTty(int tty) {
		this.tty = tty;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}
	
}
