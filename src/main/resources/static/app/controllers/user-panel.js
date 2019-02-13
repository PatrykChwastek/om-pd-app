angular.module('myApp')
.controller('UserPanelController', function($http, $scope, $mdDialog, AuthService) {

    var appUser;

    $http.get('/user').then(function (res) {
        appUser=res.data;
        console.log(appUser)
        $scope.appUser = appUser;
    });

    $scope.submitOptions=function(appUser){
        $http.put('api/users', $scope.appUser).then(function (res) {

            $scope.optionsForm.$setPristine();
            $scope.message = "Ustawienia zostały zmienione";
        }, function (error) {
            $scope.message = error.message;
        });
    };

    $scope.emailFunc=function(){

        if (!appUser.show_phone) {
            appUser.show_email = true;
        }
    };

    $scope.submit=function(appUser){
    $http.put('api/users', $scope.appUser).then(function (res) {

        $scope.userForm.$setPristine();
        $scope.message = "Dane zostały zmienione";
    }, function (error) {
        $scope.message = error.message;
    });
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
});
