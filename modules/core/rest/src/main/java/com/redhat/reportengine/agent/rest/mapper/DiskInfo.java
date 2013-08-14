package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 07, 2013
 */
@XmlRootElement
public class DiskInfo extends AgentBaseMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2500183523599999216L;
	
	private FileSystem[] fileSystem;
	private List<DiskUsage> diskUsages;
	private FileSystemUsage fileSystemUsage;
	
	public FileSystem[] getFileSystem() {
		return fileSystem;
	}
	public void setFileSystem(FileSystem[] fileSystem) {
		this.fileSystem = fileSystem;
	}
	public FileSystemUsage getFileSystemUsage() {
		return fileSystemUsage;
	}
	public void setFileSystemUsage(FileSystemUsage fileSystemUsage) {
		this.fileSystemUsage = fileSystemUsage;
	}
	public List<DiskUsage> getDiskUsages() {
		return diskUsages;
	}
	public void setDiskUsages(List<DiskUsage> diskUsages) {
		this.diskUsages = diskUsages;
	}
	
}
