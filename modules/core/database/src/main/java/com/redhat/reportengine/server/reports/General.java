/**
 * 
 */
package com.redhat.reportengine.server.reports;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 22, 2012
 */
public class General {
	public static final String guiInputDateFormat 	= "dd-MM-yyyy";
	public static final String guiInputDateTimeFormat 	= "dd-MM-yyyy HH:mm:ss";
	public static final String dbDateFormat 		= "yyyy-MM-dd";
	public static final String dbDateTimeFormat		= "yyyy-MM-dd HH:mm:ss.S";
	public static final String GUI_DATE_TIME = "MMM dd HH:mm:ss z yyyy";
	public static final String GUI_LOG_DATE_TIME = "MMM dd, HH:mm:ss.S z yyyy";
	public static final String GUI_TIME = "HH:mm:ss";
	public static final DecimalFormat decimalDigit2 = new DecimalFormat("#.##");

	public static final String HTML_ICONS_LOCATION 		= "../gui/base/icons/";
	public static final String HTML_IMAGES_LOCATION 	= "../gui/base/images/";

	//Note: These values will be calculated from KB not from Bytes !!!
	public static final double teraSize = 1024*1024*1024.0;
	public static final double gigaSize = 1024*1024.0;
	public static final double megaSize = 1024.0;

	public static String getColor(int value, boolean positive, String append){
		StringBuffer strValue = new StringBuffer("");
		String color = "red";
		if(value == 0 ){
			return strValue.append("(0").append(append).append(")").toString();
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
		strValue.append(value).append(append).append(")</font>");
		return strValue.toString();
	}

	public static String getColor(int value, boolean positive){
		return getColor(value, positive, "");
	}


	public static String getColorCpu(double value, boolean positive){
		String append = "";
		StringBuffer strValue = new StringBuffer("");
		String color = "red";
		if(value == 0 ){
			return strValue.append("(0").append(append).append(")").toString();
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
		strValue.append(value).append(append).append(")</font>");
		return strValue.toString();
	
	}

	public static String getColorFileSizeFromBytes(long value, boolean positive){
		StringBuffer strValue = new StringBuffer("");
		String color = "red";
		if(value == 0 ){
			return strValue.append("(0)").toString();
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
		if(value > 0){
			strValue.append("+");
		}else{
			strValue.append("-");	
		}
		if(value < 0){
			getFileSizeFromBytes(value * - 1);
			strValue.append(getFileSizeFromBytes(value * - 1)).append(")</font>");
		}else{
			strValue.append(getFileSizeFromBytes(value)).append(")</font>");
		}

		return strValue.toString();
	}



	public static String getGuiDateTime(Date date){
		if(date == null){
			return "-";
		}else{
			return new SimpleDateFormat(GUI_DATE_TIME).format(date);
		}		
	}

	public static String getGuiLogDateTime(Date date){
		if(date == null){
			return "-";
		}else{
			return new SimpleDateFormat(GUI_LOG_DATE_TIME).format(date);
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
		}else if(buildInfo.length() != 0){
			return buildInfo.split("\n")[0].trim();
		}else{
			return buildInfo;
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

	public static boolean isCheckBoxEnabled(String status){
		if(status == null){
			return false;
		}else{
			return true;
		}
	}

	public static String getString(String[] args, String nextLineStr){
		StringBuilder builder = new StringBuilder();
		if(args != null){
			for(String arg: args){
				builder.append(arg).append(nextLineStr);
			}
			return builder.toString();
		}else{
			return "-";
		}

	}

	public static String getString(Map map, String nextLineStr){
		StringBuilder builder = new StringBuilder();
		if(map != null){
			Set<String> keys = map.keySet();
			for(String key: keys){
				builder.append(key).append("=").append(map.get(key)).append(nextLineStr);
			}
			return builder.toString();
		}else{
			return "-";
		}		
	}

	public static String getString(List<Object> lists, String nextLineStr){
		StringBuilder builder = new StringBuilder();
		if(lists != null){
			for(Object list: lists){
				builder.append(list).append(nextLineStr);
			}
			return builder.toString();
		}else{
			return "-";
		}		
	}

	public static String getFileSize(long fileSize){
		StringBuilder builder = new StringBuilder();
		if(fileSize >= 1){
			if(fileSize > teraSize){
				builder.append(Math.round((fileSize/teraSize)*100)/100.0);
				builder.append(" TB");
			}else if(fileSize > gigaSize){
				builder.append(Math.round((fileSize/gigaSize)*100)/100.0);
				builder.append(" GB");
			}else if(fileSize > megaSize){
				builder.append(Math.round((fileSize/megaSize)*100)/100.0);
				builder.append(" MB");
			}else{
				builder.append(fileSize);
				builder.append(" KB");
			}
		}else if(fileSize < 0){
			builder.append("-");
		}else{
			builder.append(fileSize);
		}
		return builder.toString();
	}

	public static String getFileSizeFromBytes(long fileSize){
		StringBuilder builder = new StringBuilder();
		if(fileSize > 1024){
			fileSize = fileSize/1024;
			if(fileSize > teraSize){
				builder.append(Math.round((fileSize/teraSize)*100)/100.0);
				builder.append(" TB");
			}else if(fileSize > gigaSize){
				builder.append(Math.round((fileSize/gigaSize)*100)/100.0);
				builder.append(" GB");
			}else if(fileSize > megaSize){
				builder.append(Math.round((fileSize/megaSize)*100)/100.0);
				builder.append(" MB");
			}else{
				builder.append(fileSize);
				builder.append(" KB");
			}
		}else if(fileSize < 0){
			builder.append("-");
		}else{
			builder.append(fileSize);
		}
		return builder.toString();
	}
}
