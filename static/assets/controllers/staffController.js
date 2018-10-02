var app = angular.module('app', [ 'ngRoute','angucomplete', 'ngStorage' ]);

app.controller(
  'staffController',
  function($scope, $rootScope, $http, $localStorage, $sessionStorage, $location)
  {
    
    $scope.baseUrl = "http://localhost:8080/staff";
    $scope.baseApi = "http://localhost:8080"
    
    $scope.model = {};
    $scope.events = [];
    $scope.category = {};
    $scope.categories = [];
    $scope.workout = {};
    $scope.workouts = [];
    $scope.currentEvent = {};
    $scope.filter = {};
    $scope.user = {}
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - INICJALIZACA PANELU
    //
    /////////////////////////////////////////////////////////

    
    $scope.initVars = function(){
      $scope.getEventsByOwnerID();
      $scope.Identify();
    }

    $scope.Identify = function() {
      $http
      .post($scope.baseApi + "/identify")
      .then(
        function(result) {
          delete result.data.password;
          $scope.user = result.data;
          $scope.getEventsByOwnerID();
        });
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
    
    $scope.clearModel = function() {
      $scope.model = {};
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - EDYCJA ZAWODÓW
    //
    //////////////////////////////////////////////////////////
    
    $scope.imagedata = {};
    $scope.UploadLogo = function(file) {       
      var data = file.files[0];
      var fd = new FormData();
      fd.append("file", data);
      fd.append("token", $rootScope.token);
      fd.append("document", "document");
      fd.append("multimedia", "multimedia");

      $http.post($scope.baseApi + '/uploadFile', fd, {
        withCredentials: false,
        headers: {
          'Content-Type': undefined
        },
        transformRequest: angular.identity,
      })
      .then(function(result) {
        $scope.model.url_logo = result.data.fileName;
      });
    }
    
    $scope.deleteLogo = function(){
      delete $scope.model.url_logo;
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - KATEGORIE
    //
    //////////////////////////////////////////////////////////
    
    $scope.getCategoriesByEventID = function(x) {
      
      var req = {event_id: x}
      
      $http
      .post($scope.baseUrl + "/getCategoriesByEventID", req)
      .then(
        function(result) {
          $scope.categories = result.data;
          if($scope.categories.length != 0) {
            $scope.activeCategory = $scope.categories[0];
            $scope.getWorkoutsByCatID($scope.categories[0].id)
          }
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
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - WORKOUTY
    //
    //////////////////////////////////////////////////////////
    
    $scope.activeCategory = {}
    
    $scope.getWorkoutsByCatID = function(x) {
      
      var req = {cat_id: x}
      
      for(var i=0; i<$scope.categories.length; i++) {
        if($scope.categories[i].id == x){
          $scope.activeCategory = $scope.categories[i];
        }
      }
      
      $http
      .post($scope.baseUrl + "/getWorkoutsByCatID", req)
      .then(
        function(result) {
          console.log(result.data)
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
      $scope.senddata.cat_id = $scope.activeCategory.id;
      
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
    
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - ZAWODNICY
    //
    //////////////////////////////////////////////////////////
    
    $scope.page_players = {};
    $scope.players = [];
    
    $scope.getUsersByCatID = function(x) {
      
      var req = {cat_id: x}
      
      for(var i=0; i<$scope.categories.length; i++) {
        if($scope.categories[i].id == x){
          $scope.activeCategory = $scope.categories[i];
        }
      }
      
      $http
      .post($scope.baseUrl + "/getPlayersByCatID", req)
      .then(
        function(result) {
          $scope.players = result.data;
        });
    }
    
    $scope.addPlayer = function() {
      var cat_id = $scope.activeCategory.id;
      
      swal({
        title: 'Podaj adres email zawodnika',
        input: 'text',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: false,
        confirmButtonText: 'Dodaj',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
          $http
        .post($scope.baseUrl + "/addPlayer", {user_email: login, cat_id: cat_id})
        .then(
          function (result) {
              if (result.data.status == "OK") {
                $rootScope.showSuccessAlert("Wysłano zaproszenie!");
                $scope.getUsersByCatID(cat_id);
              } else {
                $rootScope.showErrorAlert("Użytkownik o podanym adresie nie istnieje!");
              }
          });
        }
      })
    }
    
    $scope.removePlayer = function(x) {
      var player_id = x.id;
      var cat_id = $scope.activeCategory.id;
      
      var req = {
        user_id: player_id,
        cat_id: cat_id
      }
      
      console.warn(req);
      
      $http
      .post($scope.baseUrl + "/removePlayer", req)
      .then(
        function(result) {
          console.log(result.data);
          if(result.data.status == "OK"){
            $rootScope.showSuccessAlert("OK!");
            $scope.getUsersByCatID($scope.activeCategory.id);
          } else {
            $rootScope.errorHandle(result.data.status);
          }
        });
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - WYNIKI
    //
    //////////////////////////////////////////////////////////
    
    $scope.scores = {};
    $scope.workout_table = [];
    $scope.players_table = [];
    
    $scope.getScoresByCatID = function(x) {      
      var req = {cat_id: x}
      
      for(var i=0; i<$scope.categories.length; i++) {
        if($scope.categories[i].id == x){
          $scope.activeCategory = $scope.categories[i];
        }
      }
      
      $http
      .post($scope.baseUrl + "/getScoresByCatID", req)
      .then(
        function(result) {
          console.log(result.data);
          
          $scope.workout_table = [];
          $scope.players_table = [];
          
          $scope.scores = result.data;
          
          for(var i=0; i<$scope.scores.length; i++){
            var tmp = $scope.scores[i];
            $scope.checkWorkout(tmp);
            $scope.checkPlayer(tmp);
          }        
          
        });
    }
    
    $scope.checkWorkout = function(workout) {
      if($scope.workout_table.length == 0){
        
        var obj = {
          name: workout[3],
          id: workout[7]
        }
        
        $scope.workout_table.push(obj);
      } else {
        
       var contain = false;
        
        for(var j=0; j<$scope.workout_table.length; j++){
          if($scope.workout_table[j].name == workout[3]){
            contain = true;
            return;
          }
        }
         if(!contain) {
           
          var obj2 = {
            name: workout[3],
            id: workout[7]
          }
           
          $scope.workout_table.push(obj2);
        }
      }
    }
    
    $scope.checkPlayer = function(item) {
      
      if(item[4] == null || item[4] == undefined) {
        item[4] = "0";
      }
      var single_score = null;
      var obj = null;
      
      if($scope.players_table.length == 0){
        
        obj = {
          id: item[0],
          name: item[1],
          surname: item[2],
          scores: []
        }
        
        single_score = {
          name: item[3],
          score_time: item[5],
            workout_id: item[7],
            user_id: item[0]
        }
        
        obj.scores.push(single_score);
        $scope.players_table.push(obj);
        
      } else {
        
       var contain = false;
        
        for(var j=0; j<$scope.players_table.length; j++){
          if($scope.players_table[j].id == item[0]){
            contain = true;
            
            single_score = {
              name: item[3],
              score_time: item[5],
                workout_id: item[7],
                user_id: item[0]
            }
            $scope.players_table[j].scores.push(single_score);
            
          }
        }
        
        if(contain == false) {
          obj = {
            id: item[0],
            name: item[1],
            surname: item[2],
            scores: []
          }
        
          single_score = {
            name: item[3],
            score_time: item[5],
              workout_id: item[7],
              user_id: item[0]
          }
        
          obj.scores.push(single_score);
          $scope.players_table.push(obj);
        }
      }
    }
    
    $scope.addScores = function() {
      $http
      .post($scope.baseUrl + "/addScores", $scope.players_table)
      .then(
        function(result) {
          if (result.data.status == "OK") {
              $rootScope.showSuccessAlert("OK!");
              $scope.getScoresByCatID();
            } else {
              $rootScope.showErrorAlert("Błąd!");
            }
        });
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - POMIAR CZASU
    //
    //////////////////////////////////////////////////////////
    $scope.event = {};
    
    $scope.initTime = function(x) {
      $scope.event = x;
    }
    
    $scope.saveTime = function() {
      
      var req = {id: $scope.event.id}
      
      $http
      .post($scope.baseUrl + "/saveTime", req)
      .then(
        function(result) {
          console.log(result);
          $rootScope.showSuccessAlert("OK!");
          $scope.getEventsByOwnerID();
        });
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - EDYCJA KONTA
    //
    //////////////////////////////////////////////////////////
    
    $scope.UploadAvatar = function(file) {     
      $rootScope.showLoading("Czekaj!","Trwa upload obrazka");
      var data = file.files[0];
      var fd = new FormData();
      fd.append("file", data);
      fd.append("token", $rootScope.token);
      fd.append("document", "document");
      fd.append("multimedia", "multimedia");

      $http.post($scope.baseApi + '/uploadFile', fd, {
        withCredentials: false,
        headers: {
          'Content-Type': undefined
        },
        transformRequest: angular.identity,
      })
      .then(function(result) {
        $rootScope.closeLoading();
        $scope.user.avatar_url = result.data.fileName;
      });
    }
    
    $scope.deleteAvatar = function(){
      delete $scope.user.url_logo;
    }
    
    $scope.update_user = function() {
      console.log($scope.user);
      $http
      .post($scope.baseApi + "/update_user", $scope.user)
      .then(
        function(result) {
          if (result.data.Status == "OK") {
            $rootScope.showSuccessAlert("OK!");
            $scope.Identify();
          } else {
            $rootScope.showErrorAlert("Błąd!");
          }
        });
    }
    
    //////////////////////////////////////////////////////////
    //
    //  BLOK - OBSŁUGA
    //
    //////////////////////////////////////////////////////////
    
    $scope.logout = function(){
      $localStorage.token = null;
      $scope.user = {};
      $rootScope.goHome();
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
		templateUrl : 'pages/add.html',
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
    .when('/time_panel', {
		templateUrl : 'pages/time_panel.html',
	})
	.otherwise({
		redirectTo : '/start'
	});
});


app.run(function($http,$localStorage) {
  if($localStorage.token){
    $http.defaults.headers.common.Authorization = "Bearer " + $localStorage.token;
  } 
});