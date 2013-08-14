package com.redhat.reportengine.agent.api;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarPermissionDeniedException;

import com.redhat.reportengine.agent.rest.mapper.DiskInfo;
import com.redhat.reportengine.agent.rest.mapper.DiskUsage;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 19, 2013
 */
public class Disk {
	private static Logger _logger = Logger.getLogger(Disk.class);

	public static DiskInfo getFileSystemUsage(String name) throws SigarException{
		DiskInfo diskInfo = getDiskObj();
		diskInfo.setFileSystemUsage(getUsage(name));
		return diskInfo;
	}
	
	private static FileSystemUsage getUsage(String name) throws SigarException{
		return SigarUtils.getSigar().getFileSystemUsage(name);
	}
	
	public static DiskInfo getFileSystemUsage() throws SigarException{
		DiskInfo diskInfo = getFileSystem();
		List<DiskUsage> usages = new LinkedList<DiskUsage>();
		for(FileSystem fileSystem : diskInfo.getFileSystem()){
			try{
				DiskUsage usage =  new DiskUsage();
				usage.setFileSystem(fileSystem);
				usage.setFileSystemUsage(getUsage(fileSystem.getDirName()));
				usages.add(usage);
			}catch (SigarPermissionDeniedException pd){
				_logger.error("Exception on reading, ["+fileSystem.getDevName()+"],", pd);
			}
		}
		diskInfo.setDiskUsages(usages);
		diskInfo.setFileSystem(null);
		return diskInfo;
	}
	
	public static DiskInfo getFileSystem() throws SigarException{
		DiskInfo diskInfo = getDiskObj();
		diskInfo.setFileSystem(SigarUtils.getSigar().getFileSystemList());		
		return diskInfo;
	}
	
	private static DiskInfo getDiskObj(){
		DiskInfo diskInfo = new DiskInfo();
		diskInfo.setTime(new Date().getTime());
		return diskInfo;
	}
}
