'use strict';

/* Controllers */

angular.module('PlayTravelApp.page-flights.controllers', [])
    .controller('FlightsPageCtrl',['$scope', '$state','FlightsData', function ($scope, $state, FlightsData) {
        $scope.Flights = FlightsData.Flights;
        $scope.pagination    = FlightsData.pagination;

        $scope.$watch('pagination.page', function(newValue, oldValue) {
            if (typeof newValue != 'undefined' && newValue != oldValue) {
                var params = $state.params;
                if (newValue > 1) {
                    params.page = newValue;
                } else {
                    params.page = null;
                }

                $state.transitionTo($state.current.name, params, {reload : $state.$current});
            }
        });

        $scope.changeNumberOfItems = function (itemsPerPage) {
            var params = $state.params;
            params.page = null;

            if (itemsPerPage != 20) {
                params.items_per_page = itemsPerPage;
            } else {
                params.items_per_page = null;
            }

            $state.transitionTo($state.current.name, params, {reload : $state.$current});
        };

        console.log('Flights controller 1');
        console.log(FlightsData);
    }])
    .controller('FlightPageCtrl',['$scope', '$state', 'FlightData', 'ReclassifyMessage', function ($scope, $state, FlightData, ReclassifyMessage) {
        $scope.Flight = FlightData;
        console.log('Flight controller 2');
        console.log(FlightData);

        $scope.addToClassifier = function(message) {
            ReclassifyMessage(message);
        };
    }])
;