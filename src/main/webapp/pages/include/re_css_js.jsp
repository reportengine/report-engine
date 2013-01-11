	<!-- datatable CSS-->		
	<link rel="stylesheet" href="../media/css/dt_page.css"/>
	<link rel="stylesheet" href="../media/css/dt_table_jui.css"/>

	<link rel="stylesheet" href="../media/css/log_level.css"/>
	
	<link rel="stylesheet" href="../media/css/table_css/table.css"/>
	
	<!-- jQuery themes selection-->		
	<link rel="stylesheet" href="../css/themes/<%=Settings.getAppWidgetsName()%>/jquery-ui-1.8.6.custom.css" />
	<link rel="stylesheet" href="../css/demos.css"/>
	
	
	<!-- jQuery Main java scripts-->
	<script src="../js/jquery-1.7.1.min.js"></script>
	<script src="../js/jquery-ui-1.8.6.custom.min.js"></script>
	
	<!-- dataTables jQuery-->
	<script src="../media/js/jquery.dataTables.js"></script>	
	<!--<script src="../media/js/jquery.js"></script>-->


	

	<!-- jQuery UI java scripts Starts here-->
		<script src="../js/ui/jquery-ui-1.8.6.custom.js"></script>  
		<script src="../js/ui/jquery.effects.blind.js"></script>    
		<script src="../js/ui/jquery.effects.bounce.js"></script>   
		<script src="../js/ui/jquery.effects.clip.js"></script>     
		<script src="../js/ui/jquery.effects.core.js"></script>     
		<script src="../js/ui/jquery.effects.drop.js"></script>     
		<script src="../js/ui/jquery.effects.explode.js"></script>  
		<script src="../js/ui/jquery.effects.fade.js"></script>     
		<script src="../js/ui/jquery.effects.fold.js"></script>     
		<script src="../js/ui/jquery.effects.highlight.js"></script>
		<script src="../js/ui/jquery.effects.pulsate.js"></script>  
		<script src="../js/ui/jquery.effects.scale.js"></script>    
		<script src="../js/ui/jquery.effects.shake.js"></script>    
		<script src="../js/ui/jquery.effects.slide.js"></script>    
		<script src="../js/ui/jquery.effects.transfer.js"></script> 
		<script src="../js/ui/jquery.ui.accordion.js"></script>     
		<script src="../js/ui/jquery.ui.autocomplete.js"></script>  
		<script src="../js/ui/jquery.ui.button.js"></script>        
		<script src="../js/ui/jquery.ui.core.js"></script>          
		<script src="../js/ui/jquery.ui.datepicker.js"></script>    
		<script src="../js/ui/jquery.ui.dialog.js"></script>        
		<script src="../js/ui/jquery.ui.draggable.js"></script>     
		<script src="../js/ui/jquery.ui.droppable.js"></script>     
		<script src="../js/ui/jquery.ui.mouse.js"></script>         
		<script src="../js/ui/jquery.ui.position.js"></script>      
		<script src="../js/ui/jquery.ui.progressbar.js"></script>   
		<script src="../js/ui/jquery.ui.resizable.js"></script>     
		<script src="../js/ui/jquery.ui.selectable.js"></script>    
		<script src="../js/ui/jquery.ui.slider.js"></script>        
		<script src="../js/ui/jquery.ui.sortable.js"></script>      
		<script src="../js/ui/jquery.ui.tabs.js"></script>          
		<script src="../js/ui/jquery.ui.widget.js"></script>
	<!-- jQuery UI java scripts Ends here-->


	<!-- pirobox_extended jQuery plugin-->
	<script type='text/javascript' src='../js/colorbox/jquery.colorbox.js'></script>
	<link rel="stylesheet" href="../css/colorbox/style1/colorbox.css" type="text/css"/>
	
	
	<script type='text/javascript' src='../js/jquery.hoverIntent.minified.js'></script>
	<script type='text/javascript' src='../js/jquery.dcmegamenu.1.3.3.js'></script>
	<link href="../css/skins/<%=Settings.getMenuSkin()%>.css" rel="stylesheet" type="text/css" />

	<!--  Multi select jquery plugin -->
	<script type='text/javascript' src="../js/jquery.multiselect.js"></script>
	<link rel="stylesheet" href="../css/jquery.multiselect.css" type="text/css"/>
	<script type='text/javascript' src="../js/jquery.multiselect.filter.js"></script>
	<link rel="stylesheet" href="../css/jquery.multiselect.filter.css" type="text/css"/>
	
	<!-- highchart JS files -->
	<script type='text/javascript' src="../js/Highcharts/js/highcharts.js"></script>
	<script type='text/javascript' src="../js/Highcharts/js/modules/exporting.js"></script>
	
	<!-- highstock JS files -->
	<script type='text/javascript' src="../js/Highstock/js/highstock.js"></script>
	<script type='text/javascript' src="../js/Highstock/js/modules/exporting.js"></script>
	
	
	<!-- Report Engine JS files-->
	<script type='text/javascript' src='../js/re.js'></script>
	
	
	<!--Date Picker-->
	<script>
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
