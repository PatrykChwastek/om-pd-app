angular.module('myApp')
    .controller('AdminOffersController', function ($http, $scope, $mdDialog) {

        var paginationOptions = {
            pageNumber: 0,
            pageSize: 10
        };

        $scope.pageButtons = [{page: 0}];

        $scope.allPages;
        var all;
        var activeBtn;
        var targetBtn;
        var btnIds;
        var isFiltered=false;
        var isId=false;
        var url='/offers/get?page=' + paginationOptions.pageNumber +
            '&size=' + paginationOptions.pageSize;

        $scope.loadPage = function () {
            if (isId)
            {
                paginationOptions.pageNumber=0;
                url='/offer/'+$scope.id;
                $scope.pageButtons = [{page: 0}];
            }


            if (isFiltered){
                paginationOptions.pageNumber=0;
                url = '/offers/get?page=' + paginationOptions.pageNumber +
                        '&size=' + paginationOptions.pageSize + '&title=' + $scope.title;
                $scope.pageButtons = [{page: 0}];
            }

            if (!isId && !isFiltered){
                url = '/offers/get?page=' + paginationOptions.pageNumber +
                    '&size=' + paginationOptions.pageSize;
            }
            console.log(url);
                $http.get(url).then(function (response) {

                    if (!isId) {
                        $scope.offers = response.data.content;
                        $scope.allPages = response.data.totalPages;
                        all = parseInt(response.data.totalPages);
                    } else {
                        $scope.offers=response;
                        all=0;
                    }
                    if (all > 1) {
                        $scope.addPageButton();
                    }
                });
        };

        $scope.findOffer = function () {
            if (!$scope.id || $scope.id.length === 0 || typeof $scope.id === 'undefined'
            ) {
               isId=false;
            }else {
                isId=true;
            }

                if (!$scope.title || $scope.title.length === 0 || typeof $scope.title === 'undefined') {
                    isFiltered = false
                } else {
                    isFiltered = true;
                }

            $scope.loadPage();

        };
        $scope.addPageButton = function () {

            if ($scope.pageButtons.length != $scope.allPages)
                for (var i = 1; i < all; i++) {
                    $scope.pageButtons.push({page: i});
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


        $scope.goPage = function (event) {
            targetBtn = event.target.id.slice(-1);

            paginationOptions.pageNumber = targetBtn;

            $scope.loadPage();
        };

        $scope.nextPage = function () {
            if (paginationOptions.pageNumber < $scope.allPages - 1) {
                paginationOptions.pageNumber++;
                $scope.loadPage();
            }
        };

        $scope.previousPage = function () {
            if (paginationOptions.pageNumber > 0) {
                paginationOptions.pageNumber--;
                $scope.loadPage();
            }
        };

        $scope.archiveOffer = function (appOffer) {

            var confirm = $mdDialog.confirm()
                .title('Czy napewno usunąć ogłoszenie, i przenieść je do archiwum?')
                .ariaLabel('usuń')
                .ok('Tak')
                .cancel('Nie');

            $mdDialog.show(confirm).then(function() {
                $http.delete('/offer/archives/'+parseInt(appOffer.id)).then(function(res) {

                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#uo')))
                            .clickOutsideToClose(true)
                            .title('')
                            .textContent('Ogłoszenie zostało przeniesione do archiwum')
                            .ariaLabel('Alert')
                            .ok('OK')
                    );
                    $scope.loadPage();
                },function(error) {
                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#uo')))
                            .clickOutsideToClose(true)
                            .title('Błąd!')
                            .textContent(error.data.message)
                            .ariaLabel('Alert')
                            .ok('OK')
                    );
                });
            }, function() {

            });
        };

        $scope.loadPage();
    });
