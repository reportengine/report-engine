<%@ include file="index-part1.jsp"%>
<%
ArrayList<TestSuite> testSuites = new TestSuiteReport().getTopNTestSuites(20, Keys.ORDER_BY_LOCAL_START_TIME, false);
String categories 	= null;
String passedCases 	= null;
String failedCases 	= null;
String skippedCases	= null;

for(TestSuite tmpSuite : testSuites){
	if(categories != null){
		categories 		+= ",'<b>"+tmpSuite.getTestSuiteName()+"</b><br>"+General.getGuiDateTime(tmpSuite.getRemoteEndTime())+"'";
		passedCases 	+= ","+tmpSuite.getPassedCases();
		failedCases 	+= ","+tmpSuite.getFailedCases();
		skippedCases	+= ","+tmpSuite.getSkippedCases();
	}else{
		categories 		= "'<b>"+tmpSuite.getTestSuiteName()+"</b><br>"+General.getGuiDateTime(tmpSuite.getRemoteEndTime())+"'";
		passedCases 	= ""+tmpSuite.getPassedCases();
		failedCases 	= ""+tmpSuite.getFailedCases();
		skippedCases	= ""+tmpSuite.getSkippedCases();
	}
}

%>
<script type="text/javascript">
$(function () {
    Highcharts.setOptions({
        colors: ['#008000', '#FF0000', '#800000', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
        chart: {
            backgroundColor: {
               linearGradient: [0, 0, 500, 500],
               stops: [
                  [0, 'rgb(255, 255, 255)'],
                  [1, 'rgb(240, 240, 255)']
               ]
            },
            borderWidth: 2,
            plotBackgroundColor: 'rgba(255, 255, 255, .9)',
            plotShadow: true,
            plotBorderWidth: 1
         }
    });
$(function () {
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'chartContainer',
                type: 'column'
            },
            credits: {
            	enabled: false
            	},
            title: {
                text: 'Latest Test Suite Status'
            },
            
            
            xAxis: {
            	categories: [<%=categories%>],
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '10px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
              
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Test Cases'
                },
                stackLabels: {
                    enabled: false,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -100,
                verticalAlign: 'top',
                y: 0,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
            	formatter: function() {
                    return ''+
                        this.series.name +': '+ this.y +' ('+ Math.round(this.percentage * 100)/100 +'%)'+'<br>Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: false,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: [{
                name: 'Passed',
                data: [<%=passedCases%>]
            }, {
                name: 'Failed',
                data: [<%=failedCases%>]
            }, {
                name: 'Skipped',
                data: [<%=skippedCases%>]
            }]
        });
    });
    
});

});

		</script>
<%@ include file="index-part2.jsp"%>

 
<div id="dt_page">
<div id="container">
<h1>Dashboard:</h1>
<div id="chartContainer" style="min-width: 90%;"></div>
</div>
</div>

<%@ include file="index-part3.jsp"%>