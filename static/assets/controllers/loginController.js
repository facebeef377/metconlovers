var app = angular.module('app', ['ngStorage' ]);

app.controller(
  'loginController',
  function($scope, $rootScope, $http, $localStorage, $sessionStorage)
  {
    $scope.baseUrl = "http://localhost:8080";
    
     
            $scope.initVars = function() {
                console.log("BasketApp ver. 0.05 Init!");
            }
            
            $scope.model = {};
            
            $scope.settings_loginpage = {
                login: true,
                accept: false
            }
            
            $scope.register = function() {
                
                if(($scope.model.password != $scope.settings_loginpage.password) || $scope.model.password == undefined) {
                    $rootScope.showErrorAlert("Hasła nie są identyczne!");
                } else if(($scope.model.email != $scope.settings_loginpage.email) || $scope.model.email == undefined) {
                    $rootScope.showErrorAlert("Adresy email nie są identyczne!");
                } else if(!$scope.settings_loginpage.accept) {
                    $rootScope.showErrorAlert("Musisz akceptować regulamin!");
                } else if($scope.model.login == undefined) {
                    $rootScope.showErrorAlert("Podaj login");   
                } else {
                    $http
                    .post($rootScope.apiUrl + "register", $scope.model)
                    .then(
                        function(result) 
                        {
                            if(result.data.Status == "OK"){
                                $rootScope.showSuccessAlert("Zarejestrowano!");
                            } else if(result.data.Status == "USER_EXISTS"){
                                $rootScope.showErrorAlert("Nazwa użytkownika jest zajęta!");
                            } else {
                                $rootScope.showErrorAlert("Błąd!");
                            }
                        });
                }
            }
            
            $scope.login = function() {
                
                $http
                .post($rootScope.apiUrl + "login", $scope.model)
                .then(
                    function(result) 
                    {
                        console.log(result);
                        if(result.data.id){
                            $rootScope.showSuccessAlert("Zalogowano!");
                        } else {
                            $rootScope.showErrorAlert("Błąd!");
                        }
                    });
            }
            
            $scope.gotoRegistration = function() {
                if($scope.settings_loginpage.login){
                    $scope.settings_loginpage.login = false;
                } else {
                    $scope.settings_loginpage.login = true;
                }
            }

    
    
  });