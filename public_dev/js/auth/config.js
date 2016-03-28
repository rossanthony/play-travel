'use strict';

angular.module("PlayTravelApp.auth", ["PlayTravelApp.auth.controllers"])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('auth.signIn',  { url: '/sign-in',  controller: 'SignInCtrl',  templateUrl: '/views/signIn.html' })
            .state('auth.signUp',  { url: '/sign-up',  controller: 'SignUpCtrl',  templateUrl: '/views/signUp.html' })
            .state('auth.signOut', { url: '/sign-out', controller: 'SignOutCtrl', templateUrl: null                 })
        ;
    }])
;