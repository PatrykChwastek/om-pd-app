angular.module('myApp')
    .controller('UserArchivesController', function ($http, $scope,AuthService ,$mdDialog) {

        var appUser;

        var paginationOptions = {
            pageNumber: 0,
            pageSize: 8
        };

        $scope.pageButtons = [{page: 0}];

        $scope.allPages;
        var all;
        var activeBtn;
        var targetBtn;
        var btnIds;

        $scope.loadPage = function () {
            $http.get('/user').then(function (res) {
                appUser = res.data;
                $scope.appUser = appUser;

                $http.get('/user/archives?page=' + paginationOptions.pageNumber +
                    '&size=' + paginationOptions.pageSize + '&owner=' + appUser.username).then(function (response) {

                    $scope.offers = response.data.content;
                    $scope.allPages = response.data.totalPages;
                    all = parseInt(response.data.totalPages);

                    $scope.addPageButton();

                    activeBtn = 'pageBtn' + (paginationOptions.pageNumber);


                    angular.element(document.getElementById(activeBtn))[0].disabled = true;

                    for (var i = 0; i < all; i++) {
                        btnIds = 'pageBtn'+i;
                        if (btnIds != activeBtn) {
                            angular.element(document.getElementById(btnIds))[0].disabled = false;
                        }
                    }
                });
            });
        };

        $scope.addPageButton = function () {

            if ($scope.pageButtons.length != $scope.allPages)
                for (var i = 1; i < all; i++) {
                    $scope.pageButtons.push({page: i});
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

        $scope.delUser = function () {
                  var confirm = $mdDialog.confirm()
                        .title('Czy napewno usunąć swoje konto?')
                        .ariaLabel('usuń')
                        .ok('Tak')
                        .cancel('Nie');

                    $mdDialog.show(confirm).then(function() {
                    $http.delete('api/users/del/'+ appUser.id).then(function (response) {
                    window.location.reload();
                        },function(error) {
                          console.log(error);
                         });
                    });
        };

        $scope.loadPage();
    });
