/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 22, 2012
 */
public class General {
	public static final String guiInputDateFormat 	= "dd-MM-yyyy";
	public static final String dbDateFormat 		= "yyyy-MM-dd";
	public static final String dbDateTimeFormat		= "yyyy-MM-dd HH:mm:ss.S";
	public static String getColor(int value, boolean positive){
		StringBuffer strValue = new StringBuffer("");
		String color = "red";
		if(value == 0 ){
			return " (0)";
		}else if(value > 0){
			if(positive){
				color = "green";
			}
		}else{
			if(!positive){
				color = "green";
			}
		}
		strValue.append("<font color=\"");
		strValue.append(color);
		strValue.append("\"> (");
		if(value > 0){strValue.append("+");}
		strValue.append(value).append(")</font>");
		return strValue.toString();
	}
	
	public static String getGuiDateTime(Date date){
		if(date == null){
			return "-";
		}else{
			return new SimpleDateFormat(Settings.GUI_DATE_TIME).format(date);
		}		
	}
	
	public static String getGuiLogDateTime(Date date){
		if(date == null){
			return "-";
		}else{
			return new SimpleDateFormat(Settings.GUI_LOG_DATE_TIME).format(date);
		}		
	}
	
	public static String getGuiDuration(long milliseconds){
		if(milliseconds == 0){
			return "-";
		}else{
			return formatIntoHHMMSS (milliseconds);
		}		
	}
	
	public static String formatIntoHHMMSS (long milliseconds) {
		DecimalFormat df = new DecimalFormat("00");
        int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) );
		StringBuffer duration = new StringBuffer();
		duration.append(df.format(hours)).append(":").append(df.format(minutes)).append(":").append(df.format(seconds));
		return duration.toString();		
	}
	
	public static String getBuildDetails(String buildInfo){
		if(buildInfo == null){
			return "-";
		}else{
			return buildInfo.split("\n")[0].trim();
		}
	}
	public static String getHtml(String string){
		if(string == null){
			return "-";
		}else{
			return string.replace("\n", "<br>");
		}
	}
	
	public static String getScreenShotIconLink(String testStatus, String screenShot){
		StringBuffer returnData = new StringBuffer();
		if(screenShot != null){
			String[] screen = screenShot.split("=");
			returnData.append("<a href=\"getScreenShotImage.jsp?imageId=").append(screen[1]).append("\" title=\"File Name: ").append(screen[0]+"\" class=\"ajax\">").append("<img width=\"20\" height=\"20\"  src='../images/icons/").append(testStatus).append(".png' alt='").append(testStatus).append("'>").append("</a>");
		}else{
			returnData.append("<img width=\"20\" height=\"20\"  src='../images/icons/").append(testStatus).append(".png' alt='").append(testStatus).append("'>");
		}
		return returnData.toString();
	}
	
	public static String getNotNullString(Object string){
		if(string == null){
			return "-";
		}else{
			return string.toString();
		}
	}
	
	public static String getHourMinute(Object string){
		if(string == null){
			return "-";
		}else{
			return new SimpleDateFormat("HH:mm:ss").format((Date)string);
		}
	}
	
	public static String getJobFrequency(Object frequency, Object weekDays){
		if(frequency == null){
			return "-";
		}else{
			if(((String)frequency).equalsIgnoreCase(Keys.FREQUENCY_ONE_TIME)){
				return (String)frequency;
			}
			return frequency+" ["+weekDays+"]";
		}
	}
	
	public static String getLogFileString(Object string){
		if(string == null){
			return "";
		}else{
			return string.toString();
		}
	}
	
	public static String getThrowableString(String throwable){
		if(throwable == null){
			return "";
		}else{
			return throwable.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			//return throwable.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		}
	}
	
	public static String getLogLevelStr(String level){
		if(level.equalsIgnoreCase(Keys.LEVEL_WARNING)){
			return level.substring(0, 4);
		}else{
			return level;
		}
	}
}
