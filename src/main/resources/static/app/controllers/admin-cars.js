angular.module('myApp')
.controller('AdminCarsController', function($http, $scope,$mdDialog,$state) {

    $scope.allBrands=[];
    $scope.brandSelected=false;
    $scope.modelSelected=false;

    $scope.brandSelect = function(mBrand){
        $http.get("brands/"+mBrand).then(function (response) {

            $scope.selectedBrand = response.data;
            $scope.models=$scope.selectedBrand.model;
            $scope.brandSelected=true;

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

$scope.addBrand = function(){
        if ($scope.nbrand === 'undefined'||$scope.nbrand===''){
            $http.get("brands/save/"+$scope.nbrand).then(function (response) {
                $scope.getBrands();
        }, function error(response) {
            console.log("------------Error Status: " +  response.statusText);
        });
}
};


$scope.addModel = function(){
      if ($scope.nmodel === 'undefined'||$scope.nmodel===''){
        $http.get("brands/save/model/"+$scope.brand+"/"+$scope.nmodel).then(function (response) {
            $scope.brandSelect($scope.brand);
                $scope.nmodel='';
        }, function error(response) {
            console.log("------------Error Status: " +  response.statusText);
        });
     }
};

$scope.delBrand = function(){
        $http.get("brands/del/"+$scope.brand).then(function (response) {
    $scope.getBrands();
        }, function error(response) {
            console.log("------------Error Status: " +  response.statusText);
        });
};


$scope.delModel = function(){
        $http.get("brands/del/model/"+$scope.brand+"/"+$scope.model).then(function (response) {
    $scope.brandSelect($scope.brand);
    $scope.nmodel='';
        }, function error(response) {
            console.log("------------Error Status: " +  response.statusText);
        });
};


});



