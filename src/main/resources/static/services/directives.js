myControllerModule.directive('mcDynamic', function ($compile) {
    return {
      restrict: 'E',
      replace: true,
      link: function (scope, ele, attrs) {
        scope.$watch(attrs.ngBindHtml, function() {
          ele.html(scope.ngBindHtml);
          $compile(ele.contents())(scope);
        });
      }
    };
  });


myControllerModule.directive('convertToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return val != null ? parseInt(val, 10) : null;
      });
      ngModel.$formatters.push(function(val) {
        return val != null ? '' + val : null;
      });
    }
  };
});
