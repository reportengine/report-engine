<%@ include file="index-part1.jsp"%>
<base href="https://github.com/jkandasa/" />
<script>
  $(function() {
	  $("#documents").tabs({
		    active: false,
		    collapsible: true,
		    beforeActivate: function (event, ui) {
		        window.open($(ui.newTab).find('a').attr('href'), '_blank');
		        return false;
		    }
		});
  });
  </script>
<%@ include file="index-part2.jsp"%>

 
<div id="dt_page">
<div id="container">
<div id="documents">
  <ul>
    <li><a href="https://github.com/jkandasa/report-engine/wiki">Wiki</a></li>
    <li><a href="https://github.com/jkandasa/report-engine/issues">Issues</a></li>
    <li><a href="https://github.com/jkandasa/report-engine/contributors">Contributors</a></li>   
    
  </ul>
</div>
</div>
</div>

<%@ include file="index-part3.jsp"%>

