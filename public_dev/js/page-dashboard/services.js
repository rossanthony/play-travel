'use strict';

/* Services */

angular.module('PlayTravelApp.page-dashboard.services', [])
    .factory('Dashboard', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:9000/user');
    }])
    .factory('DashBoardData', ['$q','Dashboard', function ($q,Dashboard) {
        return function ($stateParams) {
            var delay = $q.defer();
            Dashboard.get({},function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
;