angular.module('myApp').config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/page-not-found');
	$stateProvider.state('nav', {
		abstract : true,
		url : '',
		views : {
			'nav@' : {
				templateUrl : 'app/views/nav.html',
				controller : 'NavController'
			}
		}
	}).state('login', {
		parent : 'nav',
		url : '/login',
		views : {
			'content@' : {
				templateUrl : 'app/views/login.html',
				controller : 'LoginController'
			}
		}
	}).state('users', {
		parent : 'nav',
		url : '/users',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/users.html',
				controller : 'UsersController'
			}
		}
    }).state('user-panel', {
        parent : 'nav',
        url : '/user-panel',
		data : {
			role : 'USER'
		},
        views : {
            'content@' : {
                templateUrl : 'app/views/user-panel.html',
                controller : 'UserPanelController'
            }
        }
    }).state('user-offers', {
        parent : 'nav',
        url : '/user-offers',
		data : {
			role : 'USER'
		},
        views : {
            'content@' : {
                templateUrl : 'app/views/user-offers.html',
                controller : 'UserOffersController'
            }
        }
	}).state('user-archives', {
		parent : 'nav',
		url : '/user-archives',
		data : {
			role : 'USER'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/user-archives.html',
				controller : 'UserArchivesController'
			}
		}
    }).state('admin-offers', {
        parent : 'nav',
        url : '/admin-offers',
		data : {
			role : 'ADMIN'
		},
        views : {
            'content@' : {
                templateUrl : 'app/views/admin-offers.html',
                controller : 'AdminOffersController'
            }
        }
	}).state('admin-archives', {
		parent : 'nav',
		url : '/admin-archives',
		data : {
			role : 'ADMIN'
		},
		views : {
			'content@' : {
				templateUrl : 'app/views/admin-archives.html',
				controller : 'AdminArchivesController'
			}
		}
		}).state('admin-cars', {
        		parent : 'nav',
        		url : '/admin-cars',
        		data : {
        			role : 'ADMIN'
        		},
        		views : {
        			'content@' : {
        				templateUrl : 'app/views/admin-cars.html',
        				controller : 'AdminCarsController'
        			}
        		}
	}).state('home', {
		parent : 'nav',
		url : '/',
		views : {
			'content@' : {
				templateUrl : 'app/views/home.html',
				controller : 'HomeController'
			}
		}
    }).state('view-offer', {
        parent : 'nav',
        url : '/view-offer/:id',
        views : {
            'content@' : {
                templateUrl : 'app/views/offer.html',
                controller : 'ViewOffer'
            }
        }
	}).state('view-archive-offer', {
		parent : 'nav',
		url : '/view-archive-offer/:id',
		views : {
			'content@' : {
				templateUrl : 'app/views/view-archive-offer.html',
				controller : 'ViewArchiveOffer'
			}
		}
    }).state('new-offer', {
        parent : 'nav',
        url : '/new-offer',
        data : {
            role : 'USER'
        },
        views : {
            'content@' : {
                templateUrl : 'app/views/new-offer.html',
                controller : 'OfferController'
            }
        }
	}).state('page-not-found', {
		parent : 'nav',
		url : '/page-not-found',
		views : {
			'content@' : {
				templateUrl : 'app/views/page-not-found.html',
				controller : 'PageNotFoundController'
			}
		}
	}).state('access-denied', {
		parent : 'nav',
		url : '/access-denied',
		views : {
			'content@' : {
				templateUrl : 'app/views/access-denied.html',
				controller : 'AccessDeniedController'
			}
		}
	}).state('register', {
		parent : 'nav',
		url : '/register',
		views : {
			'content@' : {
				templateUrl : 'app/views/register.html',
				controller : 'RegisterController'
			}
		}
	});
});
