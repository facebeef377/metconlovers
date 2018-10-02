var app = angular.module('app', [ 'angucomplete', 'ngStorage' ]);

app.controller(
  'homeController',
  function($scope, $rootScope, $http, $localStorage, $sessionStorage)
  {
    $scope.baseUrl = "http://localhost:8080";
    
    $scope.user = {};
    
    $scope.initVars = function(){
      $rootScope.baseUrl = $scope.baseUrl;
      
      if($localStorage.token != null) {
        $scope.token = $localStorage.token;
        $scope.Identify();
      }
      
      $scope.get_recent_events();
    }
    
    $scope.Identify = function() {
      
      var headers = {}
      headers['Authorization'] = 'Bearer '+ $scope.token;

      var req = {
         method: 'POST',
         url: $scope.baseUrl + "/identify",
         headers: headers
        }

        $http(req).then(function(result){
          $scope.user = result.data;
          console.log(result.data);
        });
      
    }
    
    $scope.logout = function(){
      $localStorage.token = null;
      $scope.user = {};
    }
    
    $scope.get_recent_events = function() {
      $http
      .post($scope.baseUrl + "/home/get_recent_events")
      .then(
        function(result) {
          console.log(result.data);
          $scope.home = result.data;
        });
    }
    
    
    
    
    
    
  });