'use strict';

angular.module(
    "PlayTravelApp.admin-flights",
    [
        "PlayTravelApp.admin-flights.controllers",
        "PlayTravelApp.admin-flights.services"
    ])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('admin.flights', { url: '/flights?{items_per_page}&{page}', templateUrl: '/assets/tpls/admin/flights.html', controller: 'FlightsPageCtrl'})
            .state('admin.flights.item', { url: '/:flight', templateUrl: '/assets/tpls/admin/flight.html', controller: 'FlightPageCtrl'})
        ;
    }])
;