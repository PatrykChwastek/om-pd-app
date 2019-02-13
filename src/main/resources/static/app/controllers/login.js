angular.module('myApp')
.controller('LoginController', function($http, $scope, $state, AuthService, $rootScope) {
	$scope.login = function() {
		$http({
			url : 'authenticate',
			method : "POST",
			params : {
				username : $scope.username,
				password : $scope.password
			}
		}).then(function(res) {
			$scope.password = null;
			if (res.data.token) {
				$scope.message = '';
				$http.defaults.headers.common['Authorization'] = 'Bearer ' + res.data.token;

				AuthService.user = res.data.user;
				$rootScope.$broadcast('LoginSuccessful');
				$state.go('home');
			} else {
				$scope.message = 'Błąd logowania';
			}
		},function(res) {
			$scope.message = 'Błąd logowania';
		});
	};
});
