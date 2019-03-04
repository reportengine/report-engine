// don't forget to declare this service module as a dependency in your main app constructor!
//http://js2.coffee/#coffee2js
//https://coderwall.com/p/r_bvhg/angular-ui-bootstrap-alert-service-for-angular-js

myControllerModule.factory('CommonServices', function($timeout, $window) {
  var commonService = {};

  //Get value nested supported
  commonService.getValue = function(item, key){
    var keys = key.split('.');
    for (var i = 0, n = keys.length; i < n; ++i) {
        var k = keys[i];
        if (k in item) {
            item = item[k];
        } else {
            return;
        }
    }
    return item;
  };

  //Select/unselect single row of table
  commonService.selectItem = function (baseScope, item, key){
    if(!key){
      key='id';
    }
    if(baseScope.itemIds.indexOf(item[key]) == -1){
      baseScope.itemIds.push(item[key]);
    }else{
      baseScope.itemIds.splice(baseScope.itemIds.indexOf(item[key]), 1);
    }
  };

  // select ALL/NONE of table row function
  commonService.selectAllItems = function (baseScope, key) {
    if(!key){
      key='id';
    }
    if(baseScope.filteredList.length > 0){
      if(baseScope.filteredList.length == baseScope.itemIds.length){
        baseScope.itemIds = [];
      }else{
        baseScope.itemIds = [];
        angular.forEach(baseScope.filteredList, function(value, keyT) {
          baseScope.itemIds.push(value[key]);
        });
      }
    }
  };

  // Update row selection of table function
  commonService.updateSelection = function (baseScope) {
    if(baseScope.itemIds.length > 0){
      tmpItemIds = baseScope.itemIds;
      baseScope.itemIds = [];
      angular.forEach(baseScope.filteredList, function(value, key) {
        if(tmpItemIds.indexOf(value.id) != -1){
          baseScope.itemIds.push(value.id);
        }
      });
    }
  };

  //Get min number
  commonService.getMin = function(item1, item2){
    return Math.min(item1, item2);
  };

  
  //Get integer value for switch
  commonService.getInteger = function(value){
    if(!value){
      return undefined;
    }else{
      return parseInt(value);
    }
  };

  //Switch settings
  commonService.mcbStyle = {
      handleWidth: "60px",
      stateHandleWidth: "35px",
      labelWidth: "3px",
      animate:true,
      size:"small",
    };

  //validation methods
  //-----------------------

  //Number validation
  commonService.isNumber = function (value) {
    if (isNaN(value)) {
      return false;
    }
    return true;
  };

  //is contains space validation
  commonService.isContainsSpace = function (value) {
    if(value !== undefined){
      return !value.match(/\s/g);
    }
    return true;
  };

  //is valid JSON
  commonService.isJsonString = function (value) {
    try {
        JSON.stringify(eval('('+value+')'));
        return true;
    } catch(err) {
        return false;
    }
  };

  //guid helper
  var s4 = function() {
    return Math.floor((1 + Math.random()) * 0x10000)
      .toString(16)
      .substring(1);
  }
  //get guid
  commonService.guid = function() {
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
  };

  //get friendly time
  commonService.getTimestampJson = function(timestamp){
    var timestampJson = {};
    if(timestamp % 31536000000  == 0){
      timestampJson.timestamp = timestamp / 31536000000;
      timestampJson.timeConstant = "31536000000";
      timestampJson.timeConstantString = "Years";
    }else if(timestamp % 86400000  == 0){
      timestampJson.timestamp = timestamp / 86400000;
      timestampJson.timeConstant = "86400000";
      timestampJson.timeConstantString = "Days";
    }else if(timestamp % 3600000  == 0){
      timestampJson.timestamp = timestamp / 3600000;
      timestampJson.timeConstant = "3600000";
      timestampJson.timeConstantString = "Hours";
    }else if(timestamp % 60000  == 0){
      timestampJson.timestamp = timestamp / 60000;
      timestampJson.timeConstant = "60000";
      timestampJson.timeConstantString = "Minutes";
    }
    return timestampJson;
  };

  //get timestamp
  commonService.getTimestamp = function(timestampJson){
    return timestampJson.timeConstant * timestampJson.timestamp;
  };

  //Update mill seconds to readable value
  commonService.updateReadable = function(milliSeconds, item){
     if(milliSeconds % 86400000  == 0){
      item.readableValue = milliSeconds / 86400000;
      item.timeConstant = "86400000";
    }else if(milliSeconds % 3600000  == 0){
      item.readableValue = milliSeconds / 3600000;
      item.timeConstant = "3600000";
    }else if(milliSeconds % 60000  == 0){
      item.readableValue = milliSeconds / 60000;
      item.timeConstant = "60000";
    }else{
      item.readableValue = milliSeconds / 1000;
      item.timeConstant = "1000";
    }
  };

  //Get readable value to milliseconds
  commonService.getMilliseconds = function(readableValue, timeConstant){
     return readableValue * timeConstant;
  };

  //Update default graph margin settings
  commonService.updateGraphMarginDefault = function(item){
    if(item.marginTop === undefined){
      item.marginTop = 5;
      item.marginRight = 20;
      item.marginBottom = 60;
      item.marginLeft = 65;
    }
  }

  // chart utils
  commonService.lineChartoptions = {
      chart: {
          type: 'lineChart', // multiBarChart, lineChart
          height: 270,
          margin : {
              top: 5,
              right: 20,
              bottom: 60,
              left: 80
          },
          x: function(d){ return d[0]; },
          y: function(d){ return d[1]; },

          color: d3.scale.category10().range(),
          
          useVoronoi: false,
          clipEdge: false,
          useInteractiveGuideline: true,

          xAxis: {
              axisLabel: 'X Axis',
              /*tickFormat: function(d) {
                  return d3.time.format('%m/%d/%y')(new Date(d))
              },*/
              showMaxMin: false,
              staggerLabels: true,
              rotateLabels: -20
          },

          yAxis: {
              axisLabel: 'Y Axis',
              //tickFormat: function(d){
              //    return d3.format(',.0f')(d);
              //},
              axisLabelDistance: -10
          },
          title: {
            enable: false,
            text: 'Title'
        }
      }
  };

  commonService.pieChartoptions = {
    chart: {
        type: 'pieChart',
        donut: true,
        donutRatio: 0.50,
        labelsOutside: false,
        height: 330,
        x: function(d){return d.key;},
        y: function(d){return d.y;},
        showLabels: true,
        showTooltipPercent: true,
        duration: 500,
        labelThreshold: 0.01,
        labelSunbeamLayout: true,
        color: d3.scale.category10().range(),
        tooltip: {
          valueFormatter: function(d){
            return d3.format(',d')(d);
          }
        },
        legend: {
            margin: {
                top: 5,
                right: 35,
                bottom: 5,
                left: 0
            }
        }
    }
  };

  commonService.getLineChart = function(item) {
    var graph = {};
    var options = angular.copy(commonService.lineChartoptions);
    options.chart.xAxis.axisLabel = item.xaxis;
    options.chart.yAxis.axisLabel = "Data";
    var data = {};
    // check is this metric or normal chart
    if(item.metric) {
      angular.forEach(item.data, function(_citem) {
        angular.forEach(_citem, function(_csub) {
          angular.forEach(_csub, function(values, key) {
            if(key !== item.xaxis) {
              if(!data[key]) {
                data[key] = {};
                data[key].key = key;
                data[key].values = [];
              }
              data[key].values.push([_csub[item.xaxis], _csub[key]]);
            }
          });
        });
      });
    } else {
      angular.forEach(item.data, function(row) {
        angular.forEach(item.yaxis, function(key) {
          if(!data[key]){
            data[key] = {};
            data[key].key = key;
            data[key].values = [];
          }
          data[key].values.push([row[item.xaxis], row[key]]);
        });
      });
    }
    
    // update format
    if(item.xaxisFormat) {
      options.chart.xAxis.tickFormat = function(d) {
        return eval(item.xaxisFormat);
      }
    }

    if(item.yaxisFormat) {
      options.chart.yAxis.tickFormat = function(d) {
        return eval(item.yaxisFormat);
      }
    }

    console.log(angular.toJson(data));
    var finalData = [];
    angular.forEach(data, function(values, key) {
      finalData.push(values);
    });
    // update chart type
    if(item.type == "BAR"){
      options.chart.type = "multiBarChart";
    }
    graph.options = options;
    graph.data = finalData;
    graph.name = item.name;
    return graph;
  };


  commonService.getPieChart = function(item) {
    var graph = {};
    var options = angular.copy(commonService.pieChartoptions);
    var data = [];
    angular.forEach(item.yaxis, function(key) {
      data.push({"key": key, "y": item.data[key]});
    });

    graph.options = options;
    graph.data = data;
    graph.name = item.name;
    return graph;
  };

  // graph resize issue, see: https://github.com/krispo/angular-nvd3/issues/40
  commonService.fixGraphIssue = function(){
    $timeout(function() {
      $window.dispatchEvent(new Event('resize'));
    }, 1500);
  };

 return commonService;

});
