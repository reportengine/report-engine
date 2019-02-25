'use strict';

// Declare app level module which depends on views, and components
var myControllerModule = angular.module('myController',[
  'ui.router',
  'ui.bootstrap',
  'ngResource',
  'ngCookies',
  'base64',
  'ngFileSaver',
  'nvd3',
  'patternfly',
  'patternfly.charts',
  'patternfly.select',
  'patternfly.views',
  'patternfly.filters',
  'patternfly.card',
  'patternfly.toolbars',
  'frapontillo.bootstrap-switch',
  'xeditable',
]);

myControllerModule.constant("mchelper", {
    internal:{},
    cfg:{},
    languages:{},
    user:{},
    userSettings:{},
});

myControllerModule.config(function($stateProvider, $urlRouterProvider) {
  //For any unmatched url, redirect to /dashboard
  $urlRouterProvider.otherwise('/');

  $stateProvider

    /* Metric */
    .state('metricReportList', {
      url:"/metric/report",
      templateUrl: "partials/metrics/list.html",
      controller: "MetricReportListController",
       data: {
        requireLogin: false
      }
    }).state('metricReport', {
      url:"/metric/report/:reportId",
      templateUrl: "partials/metrics/report.html",
      controller: "MetricReportController",
       data: {
        requireLogin: false
      }
    }).state('metricReportDetailed', {
      url:"/metric/report/:reportId/detailed/:suiteId",
      templateUrl: "partials/metrics/report-detailed.html",
      controller: "MetricReportDetailedController",
       data: {
        requireLogin: false
      }
    }).state('suiteDetailRaw', {
      url:"/suite/raw/:suiteId",
      templateUrl: "partials/suite/raw.html",
      controller: "SuiteRawController",
       data: {
        requireLogin: false
      }
    });
});


//McNavCtrl
myControllerModule.controller('McNavBarCtrl', function($scope, $location, $state, mchelper, CommonServices) {
    $scope.isCollapsed = true;
    $scope.mchelper = mchelper;
    $scope.$state = $state;


     //Show hide main menu
    $scope.showHideMainMenu = function () {
      if(mchelper.userSettings.hideMenu){
        mchelper.userSettings.hideMenu = false;
      }else{
        mchelper.userSettings.hideMenu = true;
      }
      //Update mchelper
      CommonServices.saveMchelper(mchelper);
    };
});

myControllerModule.run(function ($rootScope, $state, $location, $http, mchelper, editableOptions, CommonServices) {
  //update xeditable theme
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'

});

myControllerModule.filter('millSecondsToTimeString', function() {
  return function(millseconds) {
    var seconds = Math.floor(millseconds / 1000);
    var tmpSeconds = seconds % 60;
    var days = Math.floor(seconds / 86400);
    var hours = Math.floor((seconds % 86400) / 3600);
    var minutes = Math.floor(((seconds % 86400) % 3600) / 60);
    var timeString = '';
    if(days > 0){
      timeString += (days > 1) ? (days + " days ") : (days + " day ");
    }
    if(hours >0){
      timeString += (hours > 1) ? (hours + " hours ") : (hours + " hour ");
    }
    if(minutes > 0){
      timeString += (minutes >1) ? (minutes + " minutes ") : (minutes + " minute ");
    }
    if(tmpSeconds >= 0){
      timeString += (tmpSeconds >1) ? (tmpSeconds + " seconds ") : (tmpSeconds + " second ");
    }
    return timeString;
  }
});

myControllerModule.filter('byteToMBsizeConvertor', function() {
  return function(sizeInByte) {
    if(sizeInByte < 0){
      return "n/a";
    }
    return Math.floor(sizeInByte /(1024 * 1024)) + " MB";
  }
});

myControllerModule.filter('byteToFriendlyConvertor', function() {
  return function(sizeInByte) {
    if(sizeInByte < 0){
      return "n/a";
    }else if((sizeInByte /(1024 * 1024)) > 1024){
      return (sizeInByte /(1024 * 1024 * 1024)).toFixed(2) + " GB";
    }else if((sizeInByte /(1024)) > 1024){
      return (sizeInByte /(1024 * 1024)).toFixed(2) + " MB";
    }else if(sizeInByte > 1024){
    return (sizeInByte /1024).toFixed(2) + " KB";
    }
    return sizeInByte + " Bytes";
  }
});

myControllerModule.filter('mcResourceRepresentation', function() {
  return function(text){
    if(text === undefined){
      return undefined;
    }
    return text.replace(/>>/g, '<i class="fa fa-chevron-right"></i>')
               .replace(/\[RG\]:/g, '<i class="pficon pficon-replicator fa-lg mc-margin-icon"></i> ')
               .replace(/\[G\]:/g, '<i class="fa fa-plug"></i> ')
               .replace(/\[N\]:/g, '<i class="fa fa-sitemap"></i> ')
               .replace(/\[S\]:/g, '<i class="fa fa-eye"></i> ')
               .replace(/\[SV\]:/g, '')
               .replace(/\[T\]:/g, '<i class="fa fa-clock-o"></i> ')
               .replace(/\[RD\]:/g, '<i class="fa fa-cogs"></i> ');
  }
});


myControllerModule.filter('mcHtml', function($sce) {
    return function(htmlText) {
       return $sce.trustAsHtml(htmlText);
       //return htmlText
    };
});

myControllerModule.filter('slice', function() {
  return function(arr, start, end) {
    return (arr || []).slice(start, end);
  };
});


//Items Delete Modal
myControllerModule.controller('ControllerDeleteModal', function ($scope, $uibModalInstance, $sce, $filter) {
  $scope.header = $filter('translate')('DELETE_ITEMS');
  $scope.deleteMsg = $filter('translate')('DELETE_MESSAGE');
  $scope.remove = function() {
    $uibModalInstance.close();
  };
  $scope.cancel = function () { $uibModalInstance.dismiss('cancel'); }
});

/*
//Global exception handler
myControllerModule.factory('$exceptionHandler', function () {
  return function errorCatcherHandler(exception, cause) {
    console.log('Exception cause:'+cause+', Exception:'+angular.toJson(exception));
    //throw exception;
  };
});
*/
