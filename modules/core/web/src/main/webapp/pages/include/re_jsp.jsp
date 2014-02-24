<%@ page import="com.redhat.reportengine.server.gui.*"%>
<%@ page import="com.redhat.reportengine.server.gui.mapper.*"%>
<%@ page import="com.redhat.reportengine.server.cache.*"%>
<%@ page import="com.redhat.reportengine.server.dbmap.*"%>
<%@ page import="com.redhat.reportengine.server.reports.*"%>
<%@page import="com.redhat.reportengine.server.dbdata.*"%>

<%@page import="com.redhat.reportengine.restapi.urimap.*"%>
<%@page import="org.hyperic.sigar.*"%>
<%@page import="org.hyperic.sigar.SysInfo"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.*"%>
<%@page import="com.redhat.reportengine.server.restclient.agent.*"%>
<%@page import="com.redhat.reportengine.agent.rest.mapper.jvm.*"%>

<%@ page import="java.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Calendar,java.text.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.io.*"%>
<%@page import="java.net.URLEncoder"%>
<%@ page errorPage="exceptionHandler.jsp" %>
