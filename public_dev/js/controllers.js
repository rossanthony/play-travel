'use strict';

/* Controllers */

angular.module('PlayTravelApp.controllers', [])
    //Page specific Controllers
    .controller('UiCtrl',['$rootScope', '$scope', '$state', '$stateParams', '$location', 'UiData', function ($rootScope, $scope, $state, $stateParams, $location, UiData) {
        $scope.uiData = UiData;
        console.log('user', $scope.uiData);
    }])
    .controller('AdminCtrl',['$rootScope', '$scope', '$state', '$stateParams', '$location', '$auth', 'UiData', function ($rootScope, $scope, $state, $stateParams, $location, $auth, UiData) {
        $scope.uiData = UiData;
        console.log('user', $scope.uiData);
    }])
    .controller('AuthCtrl',['$rootScope', '$scope', '$state', '$stateParams', '$location', function ($rootScope, $scope, $state, $stateParams, $location) {

    }])
    .controller('NotFoundController',['$scope', '$state', function ($scope, $state) {
        console.log('NotFound');
    }])
;
