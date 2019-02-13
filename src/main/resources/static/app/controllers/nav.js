angular.module('myApp')
.controller('NavController', function($http, $scope, AuthService, $state, $rootScope) {

	$scope.isAdmin =false;

	$scope.$on('LoginSuccessful', function() {
		$scope.user = AuthService.user;

        for (var i = 0; i < AuthService.user.roles.length; i++) {
            var role = AuthService.user.roles[i];
            if ( role == 'ADMIN') {
                $scope.isAdmin=true;
                break;
            }
        }
	});
	$scope.$on('LogoutSuccessful', function() {
		$scope.user = null;
	});
	$scope.logout = function() {
		AuthService.user = null;
		$rootScope.$broadcast('LogoutSuccessful');
        $scope.isAdmin =false;
		$state.go('login');
	};
});
