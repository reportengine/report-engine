package com.redhat.reportengine.agent.rest.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 28, 2013
 */
@XmlRootElement
public class SigarDetail extends AgentBaseMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4685352377732461203L;
	
	private String buildDate;
	private String nativeBuildDate;
	private String scmRevision;
	private String nativeScmRevision;
	private long fieldNotimpl;
	private String versionString;
	private String nativeVersionString;
	
	/**
	 * @return the buildDate
	 */
	public String getBuildDate() {
		return buildDate;
	}

	/**
	 * @param buildDate the buildDate to set
	 */
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * @return the nativeBuildDate
	 */
	public String getNativeBuildDate() {
		return nativeBuildDate;
	}

	/**
	 * @param nativeBuildDate the nativeBuildDate to set
	 */
	public void setNativeBuildDate(String nativeBuildDate) {
		this.nativeBuildDate = nativeBuildDate;
	}

	/**
	 * @return the scmRevision
	 */
	public String getScmRevision() {
		return scmRevision;
	}

	/**
	 * @param scmRevision the scmRevision to set
	 */
	public void setScmRevision(String scmRevision) {
		this.scmRevision = scmRevision;
	}

	/**
	 * @return the nativeScmRevision
	 */
	public String getNativeScmRevision() {
		return nativeScmRevision;
	}

	/**
	 * @param nativeScmRevision the nativeScmRevision to set
	 */
	public void setNativeScmRevision(String nativeScmRevision) {
		this.nativeScmRevision = nativeScmRevision;
	}

	/**
	 * @return the fieldNotimpl
	 */
	public long getFieldNotimpl() {
		return fieldNotimpl;
	}

	/**
	 * @param fieldNotimpl the fieldNotimpl to set
	 */
	public void setFieldNotimpl(long fieldNotimpl) {
		this.fieldNotimpl = fieldNotimpl;
	}

	/**
	 * @return the versionString
	 */
	public String getVersionString() {
		return versionString;
	}

	/**
	 * @param versionString the versionString to set
	 */
	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}

	/**
	 * @return the nativeVersionString
	 */
	public String getNativeVersionString() {
		return nativeVersionString;
	}

	/**
	 * @param nativeVersionString the nativeVersionString to set
	 */
	public void setNativeVersionString(String nativeVersionString) {
		this.nativeVersionString = nativeVersionString;
	}
	
	
	
}
