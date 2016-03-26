'use strict';

/* Controllers */

angular.module('PlayTravelApp.controllers', [])
    //Page specific Controllers
    .controller('AdminCtrl',['$rootScope', '$scope', '$state', '$stateParams', '$location', 'UiData', '$interval', function ($rootScope, $scope, $state, $stateParams, $location, UiData, $interval) {
        $scope.uiData             = UiData;
        $rootScope.batchAvailable     = false;
        $rootScope.trainingInProgress = false;

        console.log(UiData);

        //$interval(function() {
        //    StatusResource.get(
        //        {instance: '1'},
        //        function(data){
        //            $rootScope.batchAvailable     = data.batch_edited;
        //            $rootScope.trainingInProgress = data.training_in_progress;
        //        },function(){
        //            $rootScope.batchAvailable     = false;
        //            $rootScope.trainingInProgress = false;
        //        });
        //}, 3000);
    }])
    .controller('NotFoundController',['$scope', '$state', function ($scope, $state) {
        console.log('NotFound');
    }])
    .controller('LoginCtrl',['$scope', '$state', '$http', function ($scope, $state, $http) {

        $scope.submit = function (isValid) {
            if (isValid) {
                $scope.formErrors = [];

                var request = {
                    method: 'POST',
                    url: '/api/auth',
                    data: {
                        username   : $scope.user.username,
                        password   : $scope.user.password
                    }
                };

                $http(request).success(function(data) {

                    console.log('login form:', data);

                    $scope.formErrors = data.errors;
                    if (data.errors.length == 0) {
                        console.log('go to dashboard!');
                        $state.go('admin.dashboard');
                    }
                })
                .error(function(data) {
                    $scope.formErrors = data.errors;
                });
            }
        };
    }])
    .controller('SignUpCtrl', ['$scope', '$alert', '$auth', function($scope, $alert, $auth) {
        /**
         * The submit method.
         */
        $scope.submit = function(event) {
            event.preventDefault();

            $auth.signup({
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                email: $scope.email,
                password: $scope.password
            }).then(function() {
                $alert({
                    content: 'You have successfully signed up',
                    animation: 'fadeZoomFadeDown',
                    type: 'material',
                    duration: 3
                });
            }).catch(function(response) {
                $alert({
                    content: response.data.message,
                    animation: 'fadeZoomFadeDown',
                    type: 'material',
                    duration: 3
                });
            });
        };
    }])
    .controller('SignInCtrl', ['$scope', '$alert', '$auth', '$state', function($scope, $alert, $auth, $state) {
        /**
         * Submits the login form.
         */
        $scope.submit = function() {
            $auth.setStorageType($scope.rememberMe ? 'localStorage' : 'sessionStorage');
            $auth.login({ email: $scope.email, password: $scope.password, rememberMe: $scope.rememberMe })
                .then(function() {
                    console.log("logged in!!");
                    //$state.go('admin.flights');
                    $alert({
                        content: 'You have successfully signed in',
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                })
                .catch(function(response) {
                    console.log(response);
                    $alert({
                        content: response.data.message,
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                });
        };

        /**
         * Authenticate with a social provider.
         *
         * @param provider The name of the provider to authenticate.
         */
        $scope.authenticate = function(provider) {
            $auth.authenticate(provider)
                .then(function() {
                    $alert({
                        content: 'You have successfully signed in',
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                })
                .catch(function(response) {
                    $alert({
                        content: response.data.message,
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                });
        };
    }])
    .controller('SignOutCtrl', ['$auth', '$alert', function($auth, $alert) {
        if (!$auth.isAuthenticated()) {
            return;
        }
        $auth.logout()
            .then(function() {
                $alert({
                    content: 'You have been logged out',
                    animation: 'fadeZoomFadeDown',
                    type: 'material',
                    duration: 3
                });
            });
    }])
;
