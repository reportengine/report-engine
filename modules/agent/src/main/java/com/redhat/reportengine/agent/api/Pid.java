package com.redhat.reportengine.agent.api;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.SigarException;

import com.redhat.reportengine.agent.rest.mapper.PidDetail;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jun 27, 2013
 */
public class Pid {
	private static final Logger _logger = Logger.getLogger(Pid.class.getName());
	public static DecimalFormat df2Digit =new DecimalFormat("0.00");
	private static final String NO_SUCH_PROCESS = "No such process";

	public static LinkedList<PidDetail> getPidList(){
		LinkedList<PidDetail> pidsDetail = new LinkedList<PidDetail>();
		try {
			long[] pids = SigarUtils.getSigar().getProcList();
			PidDetail pidBasicDetail;
			for(long pid : pids){
				pidBasicDetail = new PidDetail();
				pidBasicDetail.setPid(pid);

				try {
					pidBasicDetail.setPpid(SigarUtils.getSigar().getProcState(pid).getPpid());
				}catch (SigarException ex) {
					_logger.warn("Exception on ppid,", ex);
					pidBasicDetail.setPpid(-1);
				}

				try {
					pidBasicDetail.setTty(SigarUtils.getSigar().getProcState(pid).getTty());
				}catch (SigarException ex) {
					_logger.warn("Exception on tty,", ex);
					pidBasicDetail.setTty(-1);
				}

				try {
					pidBasicDetail.setStartTime(SigarUtils.getSigar().getProcTime(pid).getStartTime());
				}catch (SigarException ex) {
					_logger.warn("Exception on startTime,", ex);
					pidBasicDetail.setStartTime(-1);
				}

				try {
					pidBasicDetail.setCmd(SigarUtils.getSigar().getProcState(pid).getName());				
				}catch (SigarException ex) {
					_logger.warn("Exception on cmd,", ex);
					pidBasicDetail.setCmd("?");
				}

				try {
					pidBasicDetail.setUid(SigarUtils.getSigar().getProcCredName(pid).getUser());
				}catch (SigarException ex) {
					_logger.warn("Exception on uid,", ex);
					pidBasicDetail.setUid("?");
				}

				try {
					pidBasicDetail.setCpu(SigarUtils.getSigar().getProcCpu(pid).getPercent());
				}catch (SigarException ex) {
					_logger.warn("Exception on cpu usage,", ex);
					pidBasicDetail.setCpu(-1);
				}

				try {
					pidBasicDetail.setMemory(SigarUtils.getSigar().getProcMem(pid).getShare());
				}catch (SigarException ex) {
					_logger.warn("Exception on memory usage,", ex);
					pidBasicDetail.setMemory(-1);
				}

				pidsDetail.addLast(pidBasicDetail);				
			}

		} catch (SigarException ex) {
			_logger.warn("Exception on PidsList,", ex);
		}
		return pidsDetail;
	}

	public static PidDetail getPidDetail(long pid){
		PidDetail pidDetail = new PidDetail();
		pidDetail.setAgentDate(new Date().getTime());
		pidDetail.setPid(pid);
		pidDetail.setAvailable(true);

		try{
			pidDetail.setProgArgs(SigarUtils.getSigar().getProcArgs(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcArgs, ", ex);
		}

		try{
			pidDetail.setProcCpu(SigarUtils.getSigar().getProcCpu(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcCpu, ", ex);
			if(ex.getMessage().contains(NO_SUCH_PROCESS)){
				pidDetail.setAvailable(false);
			}
		}

		try{
			pidDetail.setProcCred(SigarUtils.getSigar().getProcCred(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcCred, ", ex);
		}

		try{
			pidDetail.setProcCredName(SigarUtils.getSigar().getProcCredName(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcCredName, ", ex);
		}

		try{
			pidDetail.setProcEnv(SigarUtils.getSigar().getProcEnv(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcEnv, ", ex);
		}

		try{
			pidDetail.setProcExe(SigarUtils.getSigar().getProcExe(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcExe, ", ex);
		}

		try{
			pidDetail.setProcFd(SigarUtils.getSigar().getProcFd(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcFd, ", ex);
		}

		try{
			pidDetail.setProcMem(SigarUtils.getSigar().getProcMem(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcMem, ", ex);
			if(ex.getMessage().contains(NO_SUCH_PROCESS)){
				pidDetail.setAvailable(false);
			}
		}

		try{
			pidDetail.setProcModules(SigarUtils.getSigar().getProcModules(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcModules, ", ex);
		}

		try{
			pidDetail.setProcState(SigarUtils.getSigar().getProcState(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcState, ", ex);
		}

		try{
			pidDetail.setProcTime(SigarUtils.getSigar().getProcTime(pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcTime, ", ex);
		}

		try{
			pidDetail.setDescription(ProcUtil.getDescription(SigarUtils.getSigar(), pid));
		}catch(Exception ex){
			_logger.warn("Unable to get ProcUtil.getDecription, ", ex);
		}
		return pidDetail;
	}


}
