var app = angular.module('app', [ 'angucomplete', 'ngStorage' ]);

app.controller(
  'homeController',
  function($scope, $rootScope, $http, $localStorage, $sessionStorage)
  {
    $scope.baseUrl = "http://localhost:8080";
    
    $scope.initVars = function(){
      $rootScope.baseUrl = $scope.baseUrl;
    }
    
    
    
    
  });