angular.module('myApp')
.controller('OfferController', function($http, $scope,$mdDialog,$state, AuthService) {
  $scope.showSpinner = false;
    var userName="";
    $scope.allBrands=[];
    $scope.brandSelected=false;
    $http.get('/user').then(function (res) {
            userName=res.data.username;
            console.log(userName)
        });

    $scope.setOwner = function(){
        $scope.owner = userName;
    };

    $scope.brandSelect = function(mBrand){
        $http.get("brands/"+mBrand).then(function (response) {

            $scope.selectedBrand = response.data;
            $scope.models=$scope.selectedBrand.model;
            $scope.brandSelected=false;

        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });
    };

$scope.getBrands = function(){
    $http.get("brands").then(function (response) {

        $scope.allBrands = response.data;

    }, function error(response) {
        $scope.postResultMessage = "Error Status: " +  response.statusText;
    });
    };

    $scope.getBrands();

    $scope.submit = function() {
        var payload = new FormData();


        payload.append('title', $scope.title);
        payload.append('content', $scope.content);
        payload.append('owner',$scope.owner);
        payload.append('brand',$scope.brand);
        payload.append('model',$scope.model);
        payload.append('type',$scope.type);
        payload.append('production',$scope.production);
        payload.append('mileage',$scope.mileage);
        payload.append('capacity',$scope.capacity);
        payload.append('power',$scope.power);
        payload.append('fuel',$scope.fuel);
        payload.append('price',$scope.price);
        payload.append('gearbox',$scope.gearbox);
        payload.append('photo1', $scope.selectedUploadFile1);
        payload.append('photo2', $scope.selectedUploadFile2);
        payload.append('photo3', $scope.selectedUploadFile3);
        payload.append('photo4', $scope.selectedUploadFile4);
        payload.append('photo5', $scope.selectedUploadFile5);
  $scope.showSpinner = true;

        return $http({
            url: 'newoffer',
            method: 'POST',
            data: payload,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity

        }).then(function (response) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#ap')))
                    .clickOutsideToClose(true)
                    .title('Utworzono Ogłoszenie!')
                    .textContent(response.data.message)
                    .ariaLabel('Alert')
                    .ok('OK')
            );
            $state.go('user-panel');
        }, function (error) {
              $scope.showSpinner = false;
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(true)
                    .title('Błąd!')
                    .textContent(error.data.message)
                    .ariaLabel('Alert')
                    .ok('OK')
            );
        });
    };
});



