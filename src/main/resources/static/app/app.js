var myApp = angular.module('myApp', ['ui.router','ngMaterial','ngMessages']);

myApp.run(function(AuthService, $rootScope, $state) {
	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
		if (!AuthService.user) {
			if (toState.name != 'login'
				&& toState.name != 'register'
				&& toState.name !='page-not-found'
				&& toState.name !='home'
				&& toState.name !='view-offer'
				&& toState.name !='user-panel') {
				event.preventDefault();
				$state.go('login');
			}
		} else {
			if (toState.data && toState.data.role) {
				var hasAccess = false;
				for (var i = 0; i < AuthService.user.roles.length; i++) {
					var role = AuthService.user.roles[i];
					if (toState.data.role == role) {
						hasAccess = true;
						break;
					}
				}
				if (!hasAccess) {
					event.preventDefault();
					$state.go('access-denied');
				}

			}
		}
	});
});

myApp.directive('fileModel', function ($parse) {
    return{
        restric: 'A',
        link: function (scope, element, attrs) {
            var model =$parse(attrs.fileModel);
            var modelSetter=model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });

        }
    };

});
