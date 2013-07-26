package com.redhat.reportengine.udppacket;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Jul 23, 2013
 */
public class Formatter {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String getJsonString(Object object) throws JsonGenerationException, JsonMappingException, IOException{
		return mapper.writeValueAsString(object);
	}
	
	public static <T> T getJavaObject(String jsonString, Class<T> type) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(jsonString, type);
	}
	
	public static String encodeBase64String(String normalString){
		return new String(Base64.encodeBase64(normalString.getBytes()));
	}
	
	public static String decodeBase64String(String base64String){
		return new String(Base64.decodeBase64(base64String));
	}
	
	public static String decodeBase64String(byte[] base64Byte){
		return new String(Base64.decodeBase64(base64Byte));
	}

}
