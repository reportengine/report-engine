	<%@ include file="re_jsp.jsp"%>
	<!-- datatable CSS-->		
	<link rel="stylesheet" href="../gui/base/css/dt_page.css"/>
	<link rel="stylesheet" href="../gui/jquery/plugins/dataTable/dt_table_jui.css"/>

	<link rel="stylesheet" href="../gui/base/css/log_level.css"/>
	
	<link rel="stylesheet" href="../gui/jquery/plugins/dataTable/table.css"/>
	
  	<!-- jQuery themes selection-->		
	<link rel="stylesheet" href="../gui/jquery/themes/<%=Settings.getAppWidgetsName()%>/jquery-ui-1.8.24.custom.css" />
	
	<!-- jQuery Main java scripts-->
	<script src="../gui/jquery/jquery-1.8.2.min.js"></script>
	<script src="../gui/jquery/jquery-ui-1.8.24.custom.min.js"></script>
	

	<!-- dataTables jQuery-->
	<script src="../gui/jquery/plugins/dataTable/jquery.dataTables.js"></script>	

	<!-- pirobox_extended jQuery plugin-->
	<script type='text/javascript' src='../gui/jquery/plugins/colorbox/jquery.colorbox.js'></script>
	<link rel="stylesheet" href="../gui/jquery/plugins/colorbox/style1/colorbox.css" type="text/css"/>
	
	<!-- Chosen https://github.com/harvesthq/chosen jQuery plugin-->
	<script type='text/javascript' src='../gui/jquery/plugins/chosen/chosen.jquery.js'></script>
	<link rel="stylesheet" href="../gui/jquery/plugins/chosen/chosen.css" type="text/css"/>
	
	<script type='text/javascript' src='../gui/jquery/plugins/dcmegamenu/jquery.hoverIntent.minified.js'></script>
	<script type='text/javascript' src='../gui/jquery/plugins/dcmegamenu/jquery.dcmegamenu.1.3.3.js'></script>
	<link href="../gui/jquery/plugins/dcmegamenu/skins/<%=Settings.getMenuSkin()%>.css" rel="stylesheet" type="text/css" />

	<!--  Multi select jquery plugin -->
	<script type='text/javascript' src="../gui/jquery/plugins/multiselect/jquery.multiselect.js"></script>
	<link rel="stylesheet" href="../gui/jquery/plugins/multiselect/jquery.multiselect.css" type="text/css"/>
	<script type='text/javascript' src="../gui/jquery/plugins/multiselect/jquery.multiselect.filter.js"></script>
	<link rel="stylesheet" href="../gui/jquery/plugins/multiselect/jquery.multiselect.filter.css" type="text/css"/>
	
	<!-- highchart JS files -->
<!-- 	<script type='text/javascript' src="../gui/chart/Highcharts/js/highcharts.js"></script>
	<script type='text/javascript' src="../gui/chart/Highcharts/js/modules/exporting.js"></script> -->
	
	<!-- highstock JS files -->
	<script type='text/javascript' src="../gui/chart/Highstock/js/highstock.js"></script>
	<script type='text/javascript' src="../gui/chart/Highstock/js/modules/exporting.js"></script>
	

	<!-- icheck https://github.com/damirfoy/iCheck/ jQuery plugin-->
	<!-- <script type='text/javascript' src='../gui/jquery/plugins/icheck/jquery.icheck.min.js'></script> -->
	<!-- <link rel="stylesheet" href="../gui/jquery/plugins/icheck/skins/flat/flat.css" type="text/css"/> -->

	
	<!-- Report Engine JS files-->
	<script type='text/javascript' src='../gui/base/js/re.js'></script>
	
	
	<!--Date Picker-->
	<script type='text/javascript'>
	$(function() {
		$( "#datepickerFrom" ).datepicker({
			changeMonth: true,
			changeYear: true,
			showAnim:"clip",
			dateFormat: 'dd-mm-yy' });
			
		$( "#datepickerTo" ).datepicker({ 
			changeMonth: true,
			changeYear: true,
			showAnim:"clip",
			dateFormat: 'dd-mm-yy' });
			
		$( "#datepickerOneTime" ).datepicker({ 
			changeMonth: true,
			changeYear: true,
			showAnim:"clip",
			dateFormat: 'dd-mm-yy' });
		
	});
	</script>
	
	<script type="text/javascript">
	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});
	</script>
