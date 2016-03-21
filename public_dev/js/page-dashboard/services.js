'use strict';

/* Services */

angular.module('PlayTravelApp.page-dashboard.services', [])
    .factory('Dashboard', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:3001/:instance/dashboard',{instance : '@instance'}, {'get': {method: 'GET'}, 'post': { method: 'POST', isArray: false}});
    }])
    .factory('DashBoardData', ['$q','Dashboard', function ($q,Dashboard) {
        return function ($stateParams) {
            var delay = $q.defer();
            Dashboard.get({
                instance : '1',
                fromDate : $stateParams.fromDate,
                toDate   : $stateParams.toDate
            },function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
;