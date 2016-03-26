'use strict';

/* Services */

angular.module('PlayTravelApp.ui-search.services', [])
    .factory('Search', ['$resource', function ($resource) {
        return $resource(
            'http://127.0.0.1:9000/search',
            {
                'get': { method: 'GET' },
                'post': { method: 'POST', isArray: false}
            });
    }])
    .factory('SearchData', ['$q','Search', function ($q, Search) {
        return function ($stateParams) {
            var delay = $q.defer();
            Search.get({
                page           : ($stateParams.page) ? $stateParams.page : null,
                items_per_page : ($stateParams.items_per_page) ? $stateParams.items_per_page : null
            },function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
;