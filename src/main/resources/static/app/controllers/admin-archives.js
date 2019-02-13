angular.module('myApp')
    .controller('AdminArchivesController', function ($http, $scope) {

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

        $scope.loadPage = function () {
                $http.get('/archives/get?page=' + paginationOptions.pageNumber +
                    '&size=' + paginationOptions.pageSize).then(function (response) {

                    $scope.offers = response.data.content;
                    $scope.allPages = response.data.totalPages;
                    all = parseInt(response.data.totalPages);

                    $scope.addPageButton();

                    activeBtn = 'pageBtn' + (paginationOptions.pageNumber);

                    angular.element(document.getElementById(activeBtn))[0].disabled = true;

                    for (var i = 0; i < all; i++) {
                        btnIds = 'pageBtn' + i;
                        if (btnIds != activeBtn) {
                            angular.element(document.getElementById(btnIds))[0].disabled = false;
                        }
                    }
                });
        };
        $scope.loadPage();

        $scope.addPageButton = function () {

            if ($scope.pageButtons.length != $scope.allPages)
                for (var i = 1; i < all; i++) {
                    $scope.pageButtons.push({page: i});
                }

        };


        $scope.goPage = function (event) {
            var targetBtn = event.target.id.slice(-1);

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
    });
