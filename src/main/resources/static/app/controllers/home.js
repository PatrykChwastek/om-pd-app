angular.module('myApp')
.controller('HomeController',function($http,AuthService, $scope,$mdSidenav, $mdDialog) {

    var paginationOptions = {
         pageNumber: 0,
         pageSize: 9
     };

$scope.pageButtons=[{page:0}];

    $scope.allPages;
    var all;
    var activeBtn;
    var targetBtn;
    var btnIds;
    var isFiltered=false;
    var titleString='';
    $scope.allBrands=[];
    $scope.brandSelected=true;


    $scope.toggleLeft = buildToggler('left');

    function buildToggler(componentId) {
        return function() {
            $mdSidenav(componentId).toggle();
        };
    }

    $scope.loadPage= function() {
        $scope.offers=null;
        titleString=$scope.title;
        if (!$scope.title||$scope.title.length === 0 || typeof $scope.title === 'undefined'){
            titleString=undefined;
        }
        if (isFiltered){

            $http.get('/filter/'+titleString+'/'+ $scope.brand+'/'+ $scope.model +'/'+ $scope.type +'/'+ $scope.fuel +'/'+
                parseInt(paginationOptions.pageNumber)+'/'+parseInt(paginationOptions.pageSize)).then(function (res) {

                    if (res.data.content.length >0)
                    {

                        $scope.offers = res.data.content;
                        $scope.allPages = res.data.totalPages;
                        all = parseInt(res.data.totalPages);

                        if (all > 1) {
                            $scope.addPageButton();

                        }
                    }else {
                        $mdDialog.show(
                            $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#offerBox')))
                                .clickOutsideToClose(true)
                                .title('Błąd!')
                                .textContent('Nie znaleziono ogłoszeń dla podanych parametrów, zmień dane wyszukiwania.')
                                .ariaLabel('Alert')
                                .ok('OK')
                        );
                        isFiltered=false;
                        $scope.loadPage();
                    }
            });

        }else {
            $http.get('/offers/get?page=' + parseInt(paginationOptions.pageNumber) +
                '&size=' + parseInt(paginationOptions.pageSize)).then(function (response) {
            $scope.offers = response.data.content;
            $scope.allPages = response.data.totalPages;
            all = parseInt(response.data.totalPages);
            $scope.addPageButton();
            isFiltered=false;
        });
        };
    };

    $scope.addPageButton = function() {

        if ($scope.pageButtons.length !=$scope.allPages)
        for (var i=1; i< all; i++) {
            $scope.pageButtons.push({page:i});
        }

        activeBtn = 'pageBtn' + (paginationOptions.pageNumber);
        angular.element(document.getElementById(activeBtn))[0].disabled = true;

        for (var i = 0; i < all; i++) {
            btnIds = 'pageBtn' + i;
            if (btnIds != activeBtn) {
                angular.element(document.getElementById(btnIds))[0].disabled = false;
            }
        }
    };


    $scope.goPage = function(event) {
        targetBtn=event.target.id.slice(-1);
        paginationOptions.pageNumber=parseInt(targetBtn);

        $scope.loadPage();
    };

    $scope.nextPage = function() {
        if (paginationOptions.pageNumber < $scope.allPages-1) {
            paginationOptions.pageNumber++;
            $scope.loadPage();
        }
    };

    $scope.previousPage = function() {
        if (paginationOptions.pageNumber > 0) {
            paginationOptions.pageNumber--;
            $scope.loadPage();
        }
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

    $scope.submit = function(){

        isFiltered=true;
        paginationOptions.pageNumber=0;
        $scope.pageButtons=[{page:0}];
        $scope.loadPage();
    };

    $scope.getBrands();

    $scope.loadPage();
});


