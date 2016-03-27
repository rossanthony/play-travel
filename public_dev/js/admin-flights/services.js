'use strict';

/* Services */

angular.module('PlayTravelApp.admin-flights.services', [])
    .factory('$flights', ['$resource', function ($resource) {
        return {
            flights: $resource('flight/:id')
        };
    }]);

//angular.module('PlayTravelApp.admin-flights.services', [])
//    .factory('Flights', ['$resource', function ($resource) {
//        return $resource(
//            'http://127.0.0.1:9000/flights/:flight',
//            {
//                flight   : '@flight'
//            },
//            {
//                'get': {method: 'GET'},
//                'post': { method: 'POST', isArray: false}
//            });
//    }])
//    .factory('FlightsData', ['$q','Flights', function ($q, Flights) {
//        return function ($stateParams) {
//            var delay = $q.defer();
//            Flights.get({
//                page           : ($stateParams.page) ? $stateParams.page : null,
//                items_per_page : ($stateParams.items_per_page) ? $stateParams.items_per_page : null
//            },function (data) {
//                delay.resolve(data);
//            }, function () {
//                delay.reject('Unable to fetch data');
//            });
//            return delay.promise;
//        };
//    }])
//    .factory('FlightData', ['$q','Flights', function ($q, Flights) {
//        return function ($stateParams) {
//            var delay = $q.defer();
//            Flights.get({
//                flight : $stateParams.flight
//            },function (data) {
//                delay.resolve(data);
//            }, function () {
//                delay.reject('Unable to fetch data');
//            });
//            return delay.promise;
//        };
//    }])
//;