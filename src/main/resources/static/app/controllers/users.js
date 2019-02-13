angular.module('myApp')
.controller('UsersController', function($http, $scope, AuthService, $mdDialog) {
	var edit = false;
	$scope.buttonText = 'Stwórz';
	var init = function() {
		$http.get('api/users').then(function(res) {
			$scope.users = res.data;
			
			$scope.userForm.$setPristine();
			$scope.message='';
			$scope.appUser = null;
			$scope.buttonText = 'Stwórz';
			
		},function(error) {
			$scope.message = error.message;
		});
	};
	$scope.initEdit = function(appUser) {
		edit = true;
		$scope.appUser = appUser;
		console.log($scope.appUser);
		$scope.message='';
		$scope.buttonText = 'Zmień';
	};
	$scope.initAddUser = function() {
		edit = false;
		$scope.appUser = null;
		$scope.userForm.$setPristine();
		$scope.message='';
		$scope.buttonText = 'Stwórz';
	};
	$scope.deleteUser = function(appUser) {

            var confirm = $mdDialog.confirm()
                .title('Czy napewno usunąć użytkownika:'+ appUser.username+'?')

                .ariaLabel('usuń')
                .ok('Tak')
                .cancel('Nie');

            $mdDialog.show(confirm).then(function() {
                $http.delete('api/users/'+parseInt(appUser.id)).then(function(res) {
                    $scope.userForm.$setPristine();
                    init();
                },function(error) {
                    $scope.deleteMessage = error.message;
                });
            }, function() {

            });
	};
	var editUser = function(){
		$http.put('api/users', $scope.appUser).then(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.userForm.$setPristine();
			init();
		},function(error) {
			$scope.message =error.message;
		});
	};
	var addUser = function(){
		$http.post('api/users', $scope.appUser).then(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.userForm.$setPristine();
			init();
		},function(error) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#ap')))
                    .clickOutsideToClose(true)
                    .title('Błąd!')
                    .textContent(error.data.message)
                    .ariaLabel('Alert')
                    .ok('OK')
            );
		});
	};
	$scope.submit = function() {
		if(edit){
			editUser();
		}else{
			addUser();	
		}
	};
	init();

});