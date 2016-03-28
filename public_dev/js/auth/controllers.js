'use strict';

/* Controllers */

angular.module('PlayTravelApp.auth.controllers', [])
    .controller('SignUpCtrl',['$scope', '$state', '$auth', '$alert', function ($scope, $auth, $alert) {
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
    .controller('SignInCtrl',['$scope', '$auth', '$alert', '$state', function ($scope, $auth, $alert, $state) {
        /**
         * Submits the login form.
         */
        $scope.submit = function() {
            $auth.setStorageType($scope.rememberMe ? 'localStorage' : 'sessionStorage');
            $auth.login({ email: $scope.email, password: $scope.password, rememberMe: $scope.rememberMe })
                .then(function() {
                    console.log("logged in!!");
                    $state.go('admin.flights');
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
    .controller('SignOutCtrl',['$scope', '$auth', '$alert', '$state', function ($scope, $auth, $alert, $state) {
        if (!$auth.isAuthenticated()) {
            $state.go('auth.signIn');
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
                $state.go('auth.signIn');
            });
    }])
;