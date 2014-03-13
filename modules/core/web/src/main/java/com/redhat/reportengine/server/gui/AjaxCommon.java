/**
 * 
 */
package com.redhat.reportengine.server.gui;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Feb 27, 2014
 */
public class AjaxCommon {
	final static Logger _logger = Logger.getLogger(AjaxServerInfo.class);

	public void setTableHead(StringBuilder stringBuilder){
		//stringBuilder.append("\n<font size=\"2\" face=\"Courier\">");
		stringBuilder.append("\n<table border=\"0\" cellpadding=\"3\" align=\"left\">");
	}
	
	public void setTableHead100PercentWidth(StringBuilder stringBuilder){
		//stringBuilder.append("\n<font size=\"2\" face=\"Courier\">");
		stringBuilder.append("\n<table width=\"100%\" border=\"0\" cellpadding=\"3\" align=\"left\">");
	}

	public void setTableTail(StringBuilder stringBuilder){
		stringBuilder.append("</table>\n");
		//stringBuilder.append("</font>\n");
	}

	public void setKeyValue(StringBuilder stringBuilder, String key, Object value){
		stringBuilder.append("\n<TR>");
		stringBuilder.append("\n<td align=\"left\">");
		stringBuilder.append("<I>").append(key).append("</I>").append("</td>");
		stringBuilder.append("\n<TD>:</TD>");
		stringBuilder.append("\n<td>").append(value).append("</td>");
		stringBuilder.append("\n</TR>\n");
	}

	public void setKeyValueTabsHeading(StringBuilder tabHeader, String heading){
		setKeyValueTabsHeading(tabHeader, heading, null);
	}

	public void setKeyValueTabsHeading(StringBuilder tabHeader, String heading, String url){
		tabHeader.append("\n<li><a href=\"");
		if(url == null){
			tabHeader.append("#").append(heading.toLowerCase().replaceAll("\\s+", "-").replaceAll("\\.", "-"));
		}else{
			tabHeader.append(url);
		}
		tabHeader.append("\">").append(heading).append("</a></li>");
	}


	public void setKeyValueTabs(StringBuilder tabHeader, StringBuilder tabContent, String heading, Object value){
		setKeyValueTabsHeading(tabHeader, heading);
		tabContent.append("\n<div id=\"").append(heading.toLowerCase().replaceAll("\\s+", "-").replaceAll("\\.", "-")).append("\">");
		tabContent.append(value);
		tabContent.append("</div>");
	}

	public String getHtmlTableFromMap(Map map){
		StringBuilder builder = new StringBuilder();
		if(map != null){
			setTableHead(builder);
			Set<String> keys = map.keySet();
			for(String key: keys){
				setKeyValue(builder, key, map.get(key));
			}
			setTableTail(builder);
			return builder.toString();
		}else{
			return "-";
		}		
	}

	public void writeInResponse(HttpServletResponse response, StringBuilder stringBuilder) throws IOException{
		response.setContentType(MediaType.TEXT_HTML);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(stringBuilder.toString());
	}
	
	public String getString(List<String> list, String lineSp){
		StringBuilder builder = new StringBuilder();
		for(String line : list){
			builder.append(line).append(lineSp);
		}
		return builder.toString();
	}


}
