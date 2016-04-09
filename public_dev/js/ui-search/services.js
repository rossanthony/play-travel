'use strict';

/* Services */

angular.module('PlayTravelApp.ui-search.services', [])
    .factory('Flights', ['$resource', function ($resource) {
        return {
            flights: $resource('/api/v1/flight/search')
        };
    }])
    .factory('Airports', ['$resource', function ($resource) {
        return {
            airports: $resource('/api/v1/airport/list')
        };
    }])

    //.factory('Search', ['$resource', function ($resource) {
    //    return $resource(
    //        '/api/v1/search/flight',
    //        {
    //            'get': { method: 'GET' },
    //            'post': { method: 'POST', isArray: false}
    //        });
    //}])
    //.factory('SearchData', ['$q','Search', function ($q, Search) {
    //    return function ($stateParams) {
    //        var delay = $q.defer();
    //        Search.get({
    //            page           : ($stateParams.page) ? $stateParams.page : null,
    //            items_per_page : ($stateParams.items_per_page) ? $stateParams.items_per_page : null
    //        },function (data) {
    //            delay.resolve(data);
    //        }, function () {
    //            delay.reject('Unable to fetch data');
    //        });
    //        return delay.promise;
    //    };
    //}])
;