angular.module('myApp')
.controller('RegisterController', function($http, $scope, AuthService) {
	$scope.submit = function() {
		$http.post('register', $scope.appUser).then(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.register.$setPristine();
			$scope.message = "Zarejestrowano";
		},function(error) {
			$scope.message = error.message;
		});
	};
});
