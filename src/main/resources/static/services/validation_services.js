// don't forget to declare this service module as a dependency in your main app constructor!
//http://js2.coffee/#coffee2js
//https://coderwall.com/p/r_bvhg/angular-ui-bootstrap-alert-service-for-angular-js

myControllerModule.factory('validationServices', function() {
  var validationService = {};

  //Validate isNumber
  validationService.isNumber = function (value) {
    if (isNaN(value)) {
      return false;
    }
    return true;
  };

  //Validate isString
  validationService.isString = function (value) {
    if (isNaN(value)) {
      return false;
    }
    return true;
  };

  //Validate isString
  validationService.isEmpty = function (value) {
    if (!value || value === "") {
      return false;
    }
    return true;
  };

 return validationService;

});
