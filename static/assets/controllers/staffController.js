var app = angular.module('app', [ 'ngRoute','angucomplete', 'ngStorage' ]);

app.controller(
  'staffController',
  function($scope, $rootScope, $http, $localStorage, $sessionStorage, $location)
  {
    
    $scope.baseUrl = "http://localhost:8080/staff";
    $scope.model = {};
    $scope.events = [];
    $scope.category = {};
    $scope.categories = [];
    $scope.workout = {};
    $scope.workouts = [];
    $scope.currentEvent = {};
    $scope.filter = {};
    $scope.user = {
      id: 1
    }
    
    $scope.initVars = function(){
      $scope.getEventsByOwnerID();
    }
    
    $scope.addEvent = function() {
    
      $scope.senddata = angular.copy($scope.model);
      $scope.senddata.owner_id = $scope.user.id;
      
      $http
      .post($scope.baseUrl + "/addEvent", $scope.senddata)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.getEventsByOwnerID();
            $scope.model = {};
            $location.path( "/start" );
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    $scope.getEventsByOwnerID = function() {
      
      var req = {owner_id: $scope.user.id}
      
      $http
      .post($scope.baseUrl + "/getEventsByOwnerID", req)
      .then(
        function(result) {
          $scope.events = result.data;
        });
    }
    
    $scope.setEvent = function(x) {
      $scope.model = x;
    }
    
    $scope.removeEvent = function() {
      var req = {id: $scope.model.id}
      
      $http
      .post($scope.baseUrl + "/removeEvent", req)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.getEventsByOwnerID();
            $scope.model = {};
            $location.path( "/start" );
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    $scope.addCategory = function() {
      $scope.senddata = angular.copy($scope.category);
      $scope.senddata.event_id = $scope.model.id;
      
      $http
      .post($scope.baseUrl + "/addCategory", $scope.senddata)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.category = {};
            $scope.getCategoriesByEventID($scope.model.id);
            $location.path( "/categories" );
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    $scope.getCategoriesByEventID = function(x) {
      
      var req = {event_id: x}
      
      $http
      .post($scope.baseUrl + "/getCategoriesByEventID", req)
      .then(
        function(result) {
          console.log(result.data);
          $scope.categories = result.data;
        });
    }
    
    $scope.gotoEdit_cat = function(x){
      $scope.category = x;
      $location.path( "/edit_category" );
    }
    
    $scope.removeCategory = function(x) {
      var req = {id: x.id};
      $http
      .post($scope.baseUrl + "/removeCategory", req)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.getCategoriesByEventID($scope.model.id);
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    $scope.clearModel = function() {
      $scope.model = {};
    }
    
    $scope.getWorkoutsByCatID = function() {
      
      var x = parseInt($scope.filter.category);
      
      var req = {cat_id: x}
      
      $http
      .post($scope.baseUrl + "/getWorkoutsByCatID", req)
      .then(
        function(result) {
          $scope.workouts = result.data;
        });
    }
    
    $scope.gotoEdit_workout = function(x){
      $scope.workout = x;
      $location.path( "/edit_workout" );
    }
    
    $scope.removeWorkout = function(x) {
      var req = {id: x.id};
      $http
      .post($scope.baseUrl + "/removeWorkout", req)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.getWorkoutsByCatID();
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    $scope.addWorkout = function() {
      $scope.senddata = angular.copy($scope.workout);
      $scope.senddata.cat_id = parseInt($scope.filter.category);
      
      $http
      .post($scope.baseUrl + "/addWorkout", $scope.senddata)
      .then(
        function(result) {
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.workout = {};
            $scope.getWorkoutsByCatID();
            $location.path( "/workouts" );
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    
    
  });


app.config(function($routeProvider,$locationProvider) {
	$locationProvider.hashPrefix('');
	$routeProvider	
    .when('/start', {
		templateUrl : 'pages/start.html',
	})
    .when('/add', {
		templateUrl : 'pages/add.html',
	})
    .when('/results', {
		templateUrl : 'pages/results.html',
	})
    .when('/players', {
		templateUrl : 'pages/players.html',
	})
    .when('/categories', {
		templateUrl : 'pages/categories.html',
	})
    .when('/workouts', {
		templateUrl : 'pages/workouts.html',
	})
    .when('/edit_event', {
		templateUrl : 'pages/edit_event.html',
	})
    .when('/edit_profile', {
		templateUrl : 'pages/edit_profile.html',
	})
    .when('/pay', {
		templateUrl : 'pages/pay.html',
	})
    .when('/add_category', {
		templateUrl : 'pages/add_category.html',
	})
    .when('/edit_category', {
		templateUrl : 'pages/edit_category.html',
	})
    .when('/add_workout', {
		templateUrl : 'pages/add_workout.html',
	})
    .when('/edit_workout', {
		templateUrl : 'pages/edit_workout.html',
	})
	.otherwise({
		redirectTo : '/start'
	});
});