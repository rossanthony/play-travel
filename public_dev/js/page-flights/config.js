'use strict';

angular.module(
    "PlayTravelApp.page-flights",
    [
        "PlayTravelApp.page-flights.controllers",
        "PlayTravelApp.page-flights.services",
        "PlayTravelApp.page-flights.tpls"
    ]
)
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('admin.flights', { url: '/flights?{items_per_page}&{page}', templateUrl: '/assets/tpls/flights.html', controller: 'FlightsPageCtrl',
                resolve: {
                    ConversationsData: function (ConversationsData, $stateParams) {
                        return ConversationsData($stateParams);
                    }
                }
            })
            .state('admin.flights.item', { url: '/:flight', templateUrl: '/assets/tpls/flight.html', controller: 'FlightPageCtrl',
                resolve: {
                    ConversationData: function (ConversationData, $stateParams) {
                        return ConversationData($stateParams);
                    }
                }
            })
        ;
    }])
;