'use strict';

/* Services */

angular.module('PlayTravelApp.services', [])
    .factory('Ui', ['$resource', function ($resource) {
        return $resource('/api/auth/user');
    }])
    .factory('UiData', ['$q','Ui', function ($q, Ui) {
        return function () {
            //return {data:[1,2,3]};
            var delay = $q.defer();
            Ui.get(function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
    .factory('ReloadState', ['$state', function ($state) {
        return function () {
            $state.transitionTo($state.current.name, $state.params, {reload : $state.$current});
        }
    }])
;