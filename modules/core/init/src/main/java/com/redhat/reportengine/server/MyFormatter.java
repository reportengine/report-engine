/**
 * 
 */
package com.redhat.reportengine.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Mar 27, 2012
 */
public class MyFormatter extends Formatter {
	
	public static String toString(Throwable th) {
		if(th != null){
			 StringWriter sw = new StringWriter();
			    PrintWriter pw = new PrintWriter(sw);
			    th.printStackTrace(pw);
			    return sw.toString();
		}else{
			return null;
		}
	  }

	/* (non-Javadoc)
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("<tr>");
		buf.append("<td>");

		if (rec.getLevel().intValue() >= Level.WARNING.intValue())
		{
			buf.append("<b>");
			buf.append(rec.getLevel().getName());
			buf.append("</b>");
		} else
		{
			buf.append(rec.getLevel().getName());
		}
		buf.append("</td>");
		buf.append("<td>");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>");
		buf.append("<td>");
		buf.append(formatMessage(rec));
		buf.append(", [Seq:"+rec.getSequenceNumber()+"]<BR>[Class:"+rec.getSourceClassName()+"]<BR>[Method:"+rec.getSourceMethodName()+"]<BR>[Error: "+toString(rec.getThrown()));
		buf.append('\n');
		buf.append("<td>");
		buf.append("</tr>\n");
		return buf.toString();
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.S");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h)
	{
		return "<HTML>\n<HEAD>\n" + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table border>\n  "
				+ "<tr><th>Level</th><th>Time</th><th>Log Message</th></tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}
