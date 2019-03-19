'use strict';

// report services
myControllerModule.factory('ReportFactory', function ($resource) {
  return $resource('/reports/:reportId', {reportId: '@reportId'}, {
    getAll:  { method: 'GET', isArray: true, params: {reportId: null}},
    get:   { method: 'GET', isArray: false, params: {reportId: '@reportId'}},
    create: { method: 'POST', params: {reportId: null}},
    update: { method: 'PUT', params: {reportId: null}},
    delete: { method: 'DELETE', params: {reportId: '@reportId'} },
    deleteIds: { method: 'POST', params: {reportId: 'delete'} },
  })
});


// metrics services
myControllerModule.factory('MetricFactory', function ($resource) {
  return $resource('/metrics/report/:reportId/:detailed/:suiteId', {reportId: '@reportId'}, {
    get:   { method: 'GET', isArray: false, params: {reportId: '@reportId', detailed: null, suiteid: null}},
    getDetailed:   { method: 'GET', isArray: false, params: {reportId: '@reportId', detailed: 'detailed', suiteid: '@suiteId'}},
  })
});


// suite services
myControllerModule.factory('SuiteFactory', function ($resource) {
  return $resource('/suites/:suiteId', {suiteId: '@suiteId'}, {
    get:   { method: 'GET', isArray: false, params: {suiteId: '@suiteId'}},
  })
});

// suite files services
myControllerModule.factory('SuiteFileFactory', function ($resource) {
  return $resource('/suites/files/list/:suiteId', {suiteId: '@suiteId'}, {
    list:   { method: 'GET', isArray: true, params: {suiteId: '@suiteId'}},
  })
});
