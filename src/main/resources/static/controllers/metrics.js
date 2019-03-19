myControllerModule.controller('MetricReportListController', function(alertService, $timeout, $window, $location,
$scope, ReportFactory, $state, $uibModal, displayRestError, CommonServices, mchelper, $interval) {

  //GUI page settings
  $scope.headerStringList = "Metric Reports";
  $scope.noItemsSystemMsg = "No Report available";
  $scope.noItemsSystemIcon = "fa fa-sitemap";

  //load empty, configuration, etc.,
  $scope.mchelper = mchelper;
  $scope.filteredList=[];

  //data query details
  $scope.query = {};
  $scope.queryResponse = {};
  
  // charts
  $scope.chartList = [];
  $scope.fetching = false;


  $scope.isRunning = false;

  //Hold all the selected item ids
  $scope.itemIds = [];

  $scope.selectItem = function(item) {
    //CommonServices.selectItem($scope, item);
    $state.go("metricReport",{'reportId': item.name});
  };

  //get metric
  $scope.getMetricReportList = function() {
    if($scope.isRunning){
      return;
    }
    $scope.isRunning = true;
    $scope.query.detailed = false;
    ReportFactory.getAll($scope.query, function(response) {
      $scope.queryResponse = response;
      $scope.isRunning = false;
    },function(error){
      displayRestError.display(error);
      $scope.isRunning = false;
    });
  }

  // get report metric
  $scope.getMetricReportList();

});

myControllerModule.controller('MetricReportController', function(alertService, $timeout, $window, $location,
$scope, MetricFactory, $stateParams, $state, $uibModal, displayRestError, CommonServices, mchelper, $interval) {

  //GUI page settings
  $scope.headerStringList = "Metric for ";
  $scope.noItemsSystemMsg = "No Metric available";
  $scope.noItemsSystemIcon = "fa fa-sitemap";

  //load empty, configuration, etc.,
  $scope.mchelper = mchelper;
  $scope.filteredList=[];

  //data query details
  $scope.query = {};
  $scope.queryResponse = {};
  
  // charts
  $scope.chartList = [];
  $scope.fetching = false;


  $scope.isRunning = false;

  //Hold all the selected item ids
  $scope.itemIds = [];

  $scope.selectItem = function(item) {
    //CommonServices.selectItem($scope, item);
    $state.go("metricReportDetailed",{'reportId': $stateParams.reportId, 'suiteId': item.suiteId});
  };

  //get metric
  $scope.getReportMetric = function(){
    if($scope.isRunning){
      return;
    }
    $scope.isRunning = true;
    angular.forEach($location.search(), function(value, key) {
      $scope.query[key] = value;
    });

    MetricFactory.get($scope.query, function(response) {
      $scope.queryResponse = response;
      $scope.headerStringList = "Metric for, "+response.name;
      $scope.isRunning = false;
      // update chart data
      angular.forEach($scope.queryResponse.charts, function(chart) {
      if(chart.type === "BAR" || chart.type === "LINE") {
        var graph = CommonServices.getLineChart(chart);
        $scope.chartList.push(graph);
      } else if(chart.type === "PIE") {
        var graph = CommonServices.getPieChart(chart);
        $scope.chartList.push(graph);
      }
    });

    CommonServices.fixGraphIssue();
    },function(error){
      displayRestError.display(error);
      $scope.isRunning = false;
    });
  }


  if($stateParams.reportId){
    $scope.query.reportId = $stateParams.reportId;
    // get report metric
    $scope.getReportMetric();
  }
});

myControllerModule.controller('MetricReportDetailedController', function(alertService, $timeout, $window, $filter,
$scope, MetricFactory, SuiteFileFactory, $stateParams, $state, $uibModal, displayRestError, CommonServices, mchelper, $interval) {

  //GUI page settings
  $scope.headerStringList = "Detailed report, suite id: "+$stateParams.suiteId;
  $scope.noItemsSystemMsg = "No Metric available";
  $scope.noItemsSystemIcon = "fa fa-sitemap";

  //load empty, configuration, etc.,
  $scope.mchelper = mchelper;
  $scope.filteredList=[];

  //data query details
  $scope.query = {};
  $scope.queryResponse = {};
  
  $scope.description = [];

  // charts
  $scope.chartList = [];
  $scope.fetching = false;

  $scope.isRunning = false;

  //get all Items
  $scope.getReportMetricDetailed = function(){
    if($scope.isRunning){
      return;
    }
    $scope.isRunning = true;
    
    // load files
    SuiteFileFactory.list($scope.query, function(response) {
      $scope.filesList = response;
    },function(error){
      displayRestError.display(error);
    });
    
    MetricFactory.getDetailed($scope.query, function(response) {
      $scope.queryResponse = response;
      // update chart data
      angular.forEach($scope.queryResponse.charts, function(chart) {
        if(chart.type === "BAR" || chart.type === "LINE") {
          var graph = CommonServices.getLineChart(chart);
          $scope.chartList.push(graph);
        } else if(chart.type === "PIE") {
          var graph = CommonServices.getPieChart(chart);
          $scope.chartList.push(graph);
        }
      });
      $scope.description = $filter('orderBy') (Object.keys($scope.queryResponse.description));
      if($scope.description.length > 2) {
        var offset = 1;
        if($scope.description.length % 2 == 0){
          offset = 0;
        }
        $scope.descriptionLeft = $scope.description.splice(0, ($scope.description.length/2) + offset);
        $scope.descriptionRight = $scope.description;
      } else{
        $scope.descriptionLeft = $scope.description;
        $scope.descriptionRight = [];
      }
      
      $scope.isRunning = false;
      CommonServices.fixGraphIssue();
    },function(error){
      displayRestError.display(error);
      $scope.isRunning = false;
    });
  }

  if($stateParams.reportId){
    $scope.query.reportId = $stateParams.reportId;
    $scope.query.suiteId = $stateParams.suiteId;
    // get report metric
    $scope.getReportMetricDetailed();
  }
  
});
