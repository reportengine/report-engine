package com.redhat.reportengine.agent.api;

import org.hyperic.sigar.Sigar;

import com.redhat.reportengine.agent.rest.mapper.SigarDetail;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * May 14, 2013
 */
public class SigarUtils {

	private static Sigar sigar = null;
	
	public static Sigar getSigar(){
		if(sigar == null){
			sigar = new Sigar();
		}
		return sigar;
	}
	
	public static String getStringFromArray(long[] ls){
		StringBuffer strBuff = new StringBuffer();
		for(Object arg : ls){
			strBuff.append(arg).append("\n");
		}
		return strBuff.toString();
	}
	
	public static SigarDetail getSigarDetail(){
		SigarDetail sigar = new SigarDetail();		
		sigar.setBuildDate(Sigar.BUILD_DATE);
		sigar.setNativeBuildDate(Sigar.NATIVE_BUILD_DATE);
		sigar.setScmRevision(Sigar.SCM_REVISION);
		sigar.setNativeScmRevision(Sigar.NATIVE_SCM_REVISION);
		sigar.setFieldNotimpl(Sigar.FIELD_NOTIMPL);
		sigar.setVersionString(Sigar.VERSION_STRING);
		sigar.setNativeVersionString(Sigar.NATIVE_VERSION_STRING);
		return sigar;
	}
}
