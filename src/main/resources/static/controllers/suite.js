myControllerModule.controller('SuiteRawController', function(alertService, $timeout, $window, 
$scope, SuiteFactory, $stateParams, $state, $uibModal, displayRestError, CommonServices, mchelper, $interval) {

  //GUI page settings
  $scope.headerStringList = "Suite raw data";
  $scope.noItemsSystemMsg = "No data available";
  $scope.noItemsSystemIcon = "fa fa-sitemap";

  //load empty, configuration, etc.,
  $scope.mchelper = mchelper;

  //data query details
  $scope.query = {};
  $scope.queryResponse = {};
  
  $scope.isRunning = false;
  
  //get metric
  $scope.updateSuiteRaw = function(){
      if($scope.isRunning){
        return;
      }
      $scope.isRunning = true;
      SuiteFactory.get($scope.query, function(response) {
      $scope.queryResponse = response;
      $scope.headerStringList = "Suite raw data: "+response.id;
      $scope.jsonData = angular.toJson(response, true);
      $scope.isRunning = false;
    },function(error){
      displayRestError.display(error);
      $scope.isRunning = false;
    });
  }

  if($stateParams.suiteId){
    $scope.query.suiteId = $stateParams.suiteId;
    // get report metric
    $scope.updateSuiteRaw();
  }

});
