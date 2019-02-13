angular.module('myApp')
.controller('ViewOffer', function($http, $scope,$stateParams, $mdDialog) {

    var allPhotos=[];

    $http.get('offer/'+$stateParams.id).then(function(res) {
        $scope.offer = res.data;

        allPhotos.push($scope.offer.photo1);
        allPhotos.push($scope.offer.photo2);
        allPhotos.push($scope.offer.photo3);
        allPhotos.push($scope.offer.photo4);
        allPhotos.push($scope.offer.photo5);

        $http.get('api/user/'+$scope.offer.owner).then(function(response) {

            $scope.owner = response.data;

        }),function (err) {
            $scope.owner=err;
        }
    });
    
    $scope.showPhoto1 = function(ev) {
        showPhoto(ev,allPhotos[0]);
    };
    $scope.showPhoto2 = function(ev) {
        showPhoto(ev,allPhotos[1]);
    };
    $scope.showPhoto3 = function(ev) {
        showPhoto(ev,allPhotos[2]);
    };
    $scope.showPhoto4 = function(ev) {
        showPhoto(ev,allPhotos[3]);
    };
    $scope.showPhoto5 = function(ev) {
        showPhoto(ev,allPhotos[4]);
    };

    showPhoto = function(ev,mimage) {

        $mdDialog.show({
            controller: DialogController,
            templateUrl: 'dialog1.tmpl.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: true,
            locals : {
                mimage : mimage
            }
        })
    };


    function DialogController($scope, $mdDialog,mimage) {
        $scope.mPhoto=mimage;

        $scope.nextPhoto = function() {
            var i;
            for (i = 0; i < allPhotos.length; i++) {
                if ($scope.mPhoto == allPhotos[i] && allPhotos[i+1]!=='brak'){
                        $scope.mPhoto = allPhotos[i + 1];
                    break;
                }
            }
        };

        $scope.prewPhoto = function() {
            var i;
            for (i = 0; i < allPhotos.length; i++) {
                if ($scope.mPhoto == allPhotos[i] && allPhotos[i]!=allPhotos[0]){
                        $scope.mPhoto = allPhotos[i - 1];
                        break;
                }
            }
        };

        $scope.hide = function() {
            $mdDialog.hide();
        };

        $scope.cancel = function() {
            $mdDialog.cancel();
        };

        $scope.answer = function(answer) {
            $mdDialog.hide(answer);
        };
    }

});



