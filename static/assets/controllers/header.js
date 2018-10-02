app.run(function($rootScope) {
  
  //Alerty
    $rootScope.showSuccessAlert = function(text)
    {
        swal({
            type: 'success',
            title: text,
            showConfirmButton: false,
            timer: 1500
        })
    }
            
    $rootScope.showErrorAlert = function(text)
    {
        swal({
            type: 'error',
            title: text,
            showConfirmButton: false,
            timer: 2000
        })
    }
    
    $rootScope.showLoading = function(text,text2)
    {
        swal({
            title: text,
            text: text2,
            onOpen: () => {
                swal.showLoading()
            }
        })         
    }
    
    $rootScope.showInfoAlert = function(text)
    {
        swal({
            type: 'info',
            title: text,
            showConfirmButton: false,
            timer: 2000
        })
    }
    
    $rootScope.showInfoAlert2 = function(text,time)
    {
        swal({
            type: 'info',
            title: text,
            showConfirmButton: false,
            timer: time
        })
    }
    
    $rootScope.showLoadingText = function(text)
    {
        swal({
            title: 'Czekaj!',
            text: text,
            onOpen: () => {
                swal.showLoading()
            }
        })         
    }
            
    $rootScope.closeLoading = function()
    {
        swal.close();    
    }
  
  $rootScope.errorHandle = function(x) {
        if(x == 'INVALID_REGON'){
            $rootScope.showErrorAlert("Nieprawidłowy REGON");
        } else if (x == 'INVALID_NIP') {
            $rootScope.showErrorAlert("Nieprawidłowy NIP");
        } else if (x == "INVALID_EMAIL") {
          $rootScope.showErrorAlert("Nieprawidłowy EMAIL");
        } else if (x == "EXCEPTION") {
          $rootScope.showErrorAlert("Wystąpił błąd!");
        } else if (x == "SOME_VALUES_WERE_NOT_UNIQUE") {
          $rootScope.showErrorAlert("Istnieje już konto z takim adresem email lub nip'em!");
        } else if(x == "BASKET_ALREADY_CLOSED") {
          $rootScope.showInfoAlert("Nie możesz dodawać produktu!");
        } else if(x == "BASKET_ALREADY_ACCEPTED") {
          $rootScope.showInfoAlert("Nie możesz wysłać zamówienia kolejny raz!");
        } else {
            $rootScope.showErrorAlert(x);
        }
    }
  
  $rootScope.nextLogin = function(type)
  {
    if(type == "owner"){
      document.location = $rootScope.baseUrl + "/staff/index.html";
    }
    if(type == "admin"){
      document.location = $rootScope.baseUrl + "/admin/index.html";
    }
    if(type == "judge"){
      document.location = $rootScope.baseUrl + "/judge/index.html";
    }
    if(type == "player"){
      document.location = $rootScope.baseUrl + "/index.html";
    }
  }
  
  $rootScope.gotoLogin = function() {
    document.location = $rootScope.baseUrl + "/login.html";
  }
  
  $rootScope.goHome = function() {
    document.location = $rootScope.baseUrl + "/index.html";
  }
  
  $rootScope.gotoPanel = function()
  {
    document.location = $rootScope.baseUrl + "/staff/index.html";
  }
  
});