package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;



/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Aug 07, 2013
 */
@XmlRootElement
public class DiskUsage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4837447117009270941L;
	
	private FileSystemUsage fileSystemUsage;
	private FileSystem fileSystem;
	
	public FileSystemUsage getFileSystemUsage() {
		return fileSystemUsage;
	}
	public void setFileSystemUsage(FileSystemUsage fileSystemUsage) {
		this.fileSystemUsage = fileSystemUsage;
	}
	public FileSystem getFileSystem() {
		return fileSystem;
	}
	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}
		
}
