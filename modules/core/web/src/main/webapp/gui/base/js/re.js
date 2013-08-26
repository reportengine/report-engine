<!-- Menu Function-->
$(document).ready(function($){
	$('#mega-menu').dcMegaMenu({
		rowItems: '3',
		speed: 'fast',
		effect: 'slide',
		event: 'click',
		fullWidth: false
	});
});
	
<!-- Button Function-->
$(function() {
	$( "button, input:submit", ".jbutton" ).button();
});

<!-- Radio Button -->
$(function() {
	$( ".radio" ).buttonset();
});

<!--Date Picker-->
$(function() {
	$( "#datepickerFrom" ).datepicker({
		changeMonth: true,
		changeYear: true,
		showAnim:"clip",
		dateFormat: 'dd-mm-yy' 
	});
			
	$( "#datepickerTo" ).datepicker({ 
		changeMonth: true,
		changeYear: true,
		showAnim:"clip",
		dateFormat: 'dd-mm-yy' 
	});
			
	$( "#datepickerOneTime" ).datepicker({ 
		changeMonth: true,
		changeYear: true,
		showAnim:"clip",
		dateFormat: 'dd-mm-yy' 
	});
		
});
	
	
function trim(str) {
	str = str.replace(/^\s+/, '');
	for (var i = str.length - 1; i >= 0; i--) {
		if (/\S/.test(str.charAt(i))) {
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;
}

//dd/mm/YYY hh:ii:ss
//Mon Nov 29 00:04:00 IST 2010
//var Days = {"sunday" : 0, "monday" : 1, "tuesday" : 3, "wednesday" : 4, "thursday" : 5, "friday" : 6, "saturday" : 7}; 
//var month = 'Jan';
var months = new Array();
months['Jan'] = 10;
months['Feb'] = 11;
months['Mar'] = 12;
months['Apr'] = 13;
months['May'] = 14;
months['Jun'] = 15;
months['Jul'] = 16;
months['Aug'] = 17;
months['Sep'] = 18;
months['Oct'] = 19;
months['Nov'] = 20;
months['Dec'] = 21;
//alert(months[month]);


//Mon Nov 29 00:04:00 IST 2010
function getDate(a){
	if ((trim(a) != '') && (trim(a) != '-')) {
		var frDatea = trim(a).split(' ');
		var montha = frDatea[0];
		var daya =  frDatea[1];
		var frTimea = frDatea[2].split(':');
		var yeara =  frDatea[4];
		//yyyyMMddhhMMss
		var x = (yeara + months[montha] + daya + frTimea[0] + frTimea[1] + frTimea[2]) * 1;
		return x;
		} else {
		var x = 10000000000000; // = l'an 1000 ...
		return x;
	}
}

jQuery.fn.dataTableExt.oSort['date-gui-asc'] = function(a, b) {
	var x = getDate(a);
        var y = getDate(b);
	var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
	return z;
};

//Mar 29 22:10:50 IST 2012
jQuery.fn.dataTableExt.oSort['date-gui-desc'] = function(a, b) {
	var x = getDate(a);
        var y = getDate(b);
	var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));		            
	return z;
}; 


//21:04:23
function getDuration(a){
	if ((trim(a) != '') && (trim(a) != '-')) {
		var frTimea = trim(a).split(':');
		//hhMMss
		var x = (frTimea[0] + frTimea[1] + frTimea[2]) * 1;
		return x;
		} else {
		var x = 10000000000000; // = l'an 1000 ...
		return x;
	}
}

jQuery.fn.dataTableExt.oSort['duration-gui-asc'] = function(a, b) {
	var x = getDuration(a);
        var y = getDuration(b);
	var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
	return z;
};

jQuery.fn.dataTableExt.oSort['duration-gui-desc'] = function(a, b) {
	var x = getDuration(a);
        var y = getDuration(b);
	var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));		            
	return z;
}; 


//Number in HTML			
jQuery.fn.dataTableExt.oSort['num-html-asc']  = function(a,b) {
	var x = a.replace( /<.*?>/g, "" );
	var y = b.replace( /<.*?>/g, "" );
	x = parseFloat( x );
	y = parseFloat( y );
	return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

jQuery.fn.dataTableExt.oSort['num-html-desc'] = function(a,b) {
	var x = a.replace( /<.*?>/g, "" );
	var y = b.replace( /<.*?>/g, "" );
	x = parseFloat( x );
	y = parseFloat( y );
	return ((x < y) ?  1 : ((x > y) ? -1 : 0));
};			
			
			
//CPU Usage 

jQuery.fn.dataTableExt.oSort['cpu-usage-asc'] = function(x,y){
 x = x.replace('%','');
 y = y.replace('%','');
 if(x.indexOf('/')>=0)x = eval(x);
 if(y.indexOf('/')>=0)y = eval(y);
 return x/1 - y/1;
};
jQuery.fn.dataTableExt.oSort['cpu-usage-desc'] = function(x,y){
 x = x.replace('%','');
 y = y.replace('%','');
 if(x.indexOf('/')>=0)x = eval(x);
 if(y.indexOf('/')>=0)y = eval(y);
 return y/1 - x/1;
};

/*
//Memory Usage
jQuery.fn.dataTableExt.oSort['file-size-asc']  = function(a,b) {
    var x = a.substring(0,a.length - 2);
    var y = b.substring(0,b.length - 2);
       
    var x_unit = (a.substring(a.length - 2, a.length) == "MB" ? 1000 : (a.substring(a.length - 2, a.length) == "GB" ? 1000000 : 1));
    var y_unit = (b.substring(b.length - 2, b.length) == "MB" ? 1000 : (b.substring(b.length - 2, b.length) == "GB" ? 1000000 : 1));
    
    x = parseInt( x * x_unit );
    y = parseInt( y * y_unit );
    
    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

jQuery.fn.dataTableExt.oSort['file-size-desc'] = function(a,b) {
    var x = a.substring(0,a.length - 2);
    var y = b.substring(0,b.length - 2);

    var x_unit = (a.substring(a.length - 2, a.length) == "MB" ? 1000 : (a.substring(a.length - 2, a.length) == "GB" ? 1000000 : 1));
    var y_unit = (b.substring(b.length - 2, b.length) == "MB" ? 1000 : (b.substring(b.length - 2, b.length) == "GB" ? 1000000 : 1));

    x = parseInt( x * x_unit);
    y = parseInt( y * y_unit);

    return ((x < y) ?  1 : ((x > y) ? -1 : 0));
};
*/
jQuery.fn.dataTableExt.oSort['file-size-asc']  = function(a,b) {

    // grab number at the front of the string.
    var x = parseFloat(a); if (isNaN(x)) { x = -1; }
    var y = parseFloat(b); if (isNaN(y)) { y = -1; }

    // trim any "quota"
    a = a.replace(/\s+?\/.*/,'')
    b = b.replace(/\s+?\/.*/,'')

    var x_unit = a.match(/TB/i) ? 1024 * 1024 * 1024 * 1024
    		   : a.match(/GB/i) ? 1024 * 1024 * 1024
               : a.match(/MB/i) ? 1024 * 1024
               : a.match(/KB/i) ? 1024
               : 1;
    var y_unit = b.match(/TB/i) ? 1024 * 1024 * 1024 * 1024
    		   : b.match(/GB/i) ? 1024 * 1024 * 1024
               : b.match(/MB/i) ? 1024 * 1024
               : b.match(/KB/i) ? 1024
               : 1;

    x = parseInt( parseFloat(x) * x_unit ) || 0;
    y = parseInt( parseFloat(y) * y_unit ) || 0;

    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

jQuery.fn.dataTableExt.oSort['file-size-desc'] = function(a,b) {
    return jQuery.fn.dataTableExt.oSort['file-size-asc'](b,a);
};

jQuery.fn.dataTableExt.oSort['test-total-asc'] = function(x,y){
 x = x.replace( /<.*?>/g, "" );
 y = y.replace( /<.*?>/g, "" );
 x = trim(x).split(' ')[0];
 y = trim(y).split(' ')[0];
 x = parseFloat( x );
 y = parseFloat( y );
 return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

jQuery.fn.dataTableExt.oSort['test-total-desc'] = function(x,y){
 x = x.replace( /<.*?>/g, "" );
 y = y.replace( /<.*?>/g, "" );
 x = trim(x).split(' ')[0];
 y = trim(y).split(' ')[0];
 x = parseFloat( x );
 y = parseFloat( y );
 return ((x < y) ?  1 : ((x > y) ? -1 : 0));
};

<!-- Alt alert box -->
jQuery.extend({ alert: function (message, title) {
		$("<div></div>").dialog( {
	buttons: { "Ok": function () { $(this).dialog("close"); } },
		close: function (event, ui) { $(this).remove(); },
		resizable: false,
		title: title,
		modal: true
		}).text(message);
}
});
