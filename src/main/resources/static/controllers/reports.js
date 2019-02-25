myControllerModule.controller('ReportsController', function(alertService,
$scope, ReportsFactory, $stateParams, $state, $uibModal, displayRestError, CommonServices, mchelper, $filter, $interval) {

  //GUI page settings
  $scope.headerStringList = $filter('translate')('REPORT_DETAIL');
  $scope.noItemsSystemMsg = $filter('translate')('NO_REPORTS');
  $scope.noItemsSystemIcon = "fa fa-sitemap";

  //load empty, configuration, etc.,
  $scope.mchelper = mchelper;
  $scope.filteredList=[];

  //data query details
  $scope.currentPage = 1;
  $scope.query = CommonServices.getQuery();
  $scope.queryResponse = {};

  //Get min number
  $scope.getMin = function(item1, item2){
    return CommonServices.getMin(item1, item2);
  };

  if($stateParams.gatewayId){
    $scope.query.gatewayId = $stateParams.gatewayId;
  }

  $scope.isRunning = false;
  //get all Items
  $scope.getAllItems = function(){
    if($scope.isRunning){
      return;
    }
    $scope.isRunning = true;
    ReportsFactory.getAll($scope.query, function(response) {
      $scope.queryResponse = response;
      $scope.isRunning = false;
    },function(error){
      displayRestError.display(error);
      $scope.isRunning = false;
    });
  }

  //Hold all the selected item ids
  $scope.itemIds = [];

  $scope.selectAllItems = function(){
    CommonServices.selectAllItems($scope);
  };

  $scope.selectItem = function(item){
    CommonServices.selectItem($scope, item);
  };


  //Delete Item(s)
  $scope.delete = function (size) {
    var modalInstance = $uibModal.open({
    templateUrl: 'partials/common-html/delete-modal.html',
    controller: 'ControllerDeleteModal',
    size: size,
    resolve: {}
    });

    modalInstance.result.then(function () {
      ReportsFactory.deleteIds($scope.itemIds, function(response) {
        alertService.success($filter('translate')('ITEMS_DELETED_SUCCESSFULLY'));
        //Update display table
        $scope.getAllItems();
        $scope.itemIds = [];
      },function(error){
        displayRestError.display(error);
      });
    }),
    function () {
      //console.log('Modal dismissed at: ' + new Date());
    }
  };

  //Edit item
  $scope.edit = function () {
    if($scope.itemIds.length == 1){
      $state.go("reportsAddEdit",{'id':$scope.itemIds[0]});
    }
  };

 // global page refresh
  var promise = $interval($scope.getAllItems, mchelper.cfg.globalPageRefreshTime);

  // cancel interval on scope destroy
  $scope.$on('$destroy', function(){
    $interval.cancel(promise);
  });

});


// Reports other controllers

//Add/Edit Node
myControllerModule.controller('ReportsControllerAddEdit', function ($scope, $stateParams, ReportsFactory, mchelper, alertService, displayRestError, $filter, $state, CommonServices) {
  //Load mchelper variables to this scope
  $scope.mchelper = mchelper;
  $scope.cs = CommonServices;
  $scope.item = {};
  $scope.item.altproperties='{}';
  if($stateParams.id){
    ReportsFactory.get({"reportId":$stateParams.id},function(response) {
      $scope.item = response;
      //$scope.item.altproperties = angular.toJson(response.properties);
    },function(error){
        displayRestError.display(error);
    });
  }

  //GUI page settings
  $scope.showHeaderUpdate = $stateParams.id;
  $scope.headerStringAdd = $filter('translate')('ADD_REPORT');
  $scope.headerStringUpdate = $filter('translate')('UPDATE_REPORT');
  $scope.cancelButtonState = "reprtsList"; //Cancel button state
  $scope.saveProgress = false;
  //$scope.isSettingChange = false;


  $scope.save = function(){
    $scope.saveProgress = true;
    $scope.item.properties = angular.fromJson(JSON.stringify(eval('('+$scope.item.altproperties+')')));
    console.log(angular.toJson($scope.item.altproperties));
    if($stateParams.id){
      ReportsFactory.update($scope.node,function(response) {
        alertService.success($filter('translate')('ITEM_UPDATED_SUCCESSFULLY'));
        $state.go("reportsList");
      },function(error){
        displayRestError.display(error);
        $scope.saveProgress = false;
      });
    }else{
        ReportsFactory.create($scope.node,function(response) {
        alertService.success($filter('translate')('ITEM_CREATED_SUCCESSFULLY'));
        $state.go("reportsList");
      },function(error){
        displayRestError.display(error);
        $scope.saveProgress = false;
      });
    }
  }
});
