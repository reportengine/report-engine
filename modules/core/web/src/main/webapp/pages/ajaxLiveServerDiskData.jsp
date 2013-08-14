<%@page import="com.redhat.reportengine.agent.rest.mapper.DiskUsage"%>
<%@ include file="include/re_jsp.jsp"%>

<%
String queryType = request.getParameter(Keys.DISK_QUERY_TYPE);
if(queryType.equalsIgnoreCase(Keys.DISK_FILE_SYSTEM)){
	%>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table_list').dataTable({
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[0,'asc']],
			"aoColumns": [
			null,
			null,
			null,
			null,
			null,
			null,
			null
			],
			"iDisplayLength":15
			});
} );			
</script>

<table cellpadding="0" cellspacing="0" border="0" class="display"
	id="dt_table_list">
	<thead>
		<tr>
			<th>Name</th>
			<th>Dir Name</th>
			<th>Type Name</th>
			<th>Sys Type</th>
			<th>Options</th>
			<th>Type</th>
			<th>Flags</th>
		</tr>
	</thead>
	<tbody>
		<%
	FileSystem[] fileSystems = new AjaxServerInfo().getLiveFileSystemsList(request, response).getFileSystem();

	StringBuilder builder = new StringBuilder();
	for(FileSystem fs : fileSystems){
		builder.append("<tr>");
		
		builder.append("<td align=\"left\">").append(fs.getDevName()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getDirName()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getTypeName()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getSysTypeName()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getOptions()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getType()).append("</td>");
		builder.append("<td align=\"left\">").append(fs.getFlags()).append("</td>");
		builder.append("</tr>\n");
	}
	out.println(builder.toString());
%>
	</tbody>
</table>

<%	
}else if(queryType.equalsIgnoreCase(Keys.DISK_USAGE)){
%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#dt_table_usage').dataTable({
			"bJQueryUI": true,
			"bPaginate": true,
			"sPaginationType": "full_numbers",
			"aaSorting": [[0,'asc']],
			"aoColumns": [
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null
			],
			"iDisplayLength":15
			});
} );			
</script>

<table cellpadding="0" cellspacing="0" border="0" class="display"
	id="dt_table_usage">
	<thead>
		<tr>
			<th>Device</th>
			<th>Dir</th>
			<th>Total</th>
			<th>Free</th>
			<th>used</th>
			<th>Available</th>
			<th>Files</th>
			<th>Free Files</th>
			<th>Disk Reads</th>
			<th>Disk Writes</th>
			<th>Disk Read Bytes</th>
			<th>Disk Write Bytes</th>
			<th>Disk Queue</th>
			<th>Disk Service Time</th>
			<th>Used %</th>

		</tr>
	</thead>
	<tbody>
		<%
	List<DiskUsage> diskUsages = new AjaxServerInfo().getLiveFileSystemsUsage(request, response).getDiskUsages();

	StringBuilder builder = new StringBuilder();
	for(DiskUsage disk : diskUsages){
		if(disk.getFileSystemUsage().getTotal()> 0){
			builder.append("<tr>");			
			builder.append("<td nowrap align=\"left\">").append(disk.getFileSystem().getDevName()).append("</td>");
			builder.append("<td align=\"left\">").append(disk.getFileSystem().getDirName()).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getTotal())).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getFree())).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getUsed())).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getAvail())).append("</td>");
			builder.append("<td align=\"right\">").append(disk.getFileSystemUsage().getFiles()).append("</td>");
			builder.append("<td align=\"right\">").append(disk.getFileSystemUsage().getFreeFiles()).append("</td>");
			builder.append("<td align=\"right\">").append(disk.getFileSystemUsage().getDiskReads()).append("</td>");
			builder.append("<td align=\"right\">").append(disk.getFileSystemUsage().getDiskWrites()).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getDiskReadBytes())).append("</td>");
			builder.append("<td nowrap align=\"right\">").append(General.getFileSize(disk.getFileSystemUsage().getDiskWriteBytes())).append("</td>");
			builder.append("<td align=\"right\">").append(Math.round(disk.getFileSystemUsage().getDiskQueue()*10000.0)/10000.0).append("</td>");
			builder.append("<td align=\"right\">").append(Math.round(disk.getFileSystemUsage().getDiskServiceTime()*10000.0)/10000.0).append("</td>");
			builder.append("<td align=\"right\">").append(Math.round(disk.getFileSystemUsage().getUsePercent()*10000.0)/100.0).append("</td>");
			builder.append("</tr>\n");	
		}
	}
	out.println(builder.toString());
%>
	</tbody>
</table>

<%
}
%>