'use strict';

/* Controllers */

angular.module('PlayTravelApp.admin-flights.controllers', [])
    .controller('FlightsPageCtrl',['$scope', '$state','$flights', '$mdDialog', function ($scope, $state, $flights, $mdDialog) {

        var bookmark;

        $scope.selected = [];

        $scope.options = {
            autoSelect: true,
            boundaryLinks: true,
            largeEditDialog: true,
            pageSelector: true,
            rowSelection: true
        };

        $scope.query = {
            order: 'departureLocation',
            limit: 5,
            page: 1
        };

        function getFlights(query) {
            console.log('query', query);
            $scope.promise = $flights.flights.get(query || $scope.query, success).$promise;
        }

        function success(flights) {
            $scope.flights = flights.data;
        }

        $scope.onPaginate = function (page, limit) {
            getFlights(angular.extend({}, $scope.query, {page: page, limit: limit}));
        };

        $scope.onReorder = function (order) {
            getFlights(angular.extend({}, $scope.query, {order: order}));
        };

        $scope.removeFilter = function () {
            $scope.filter.show = false;
            $scope.query.filter = '';

            if($scope.filter.form.$dirty) {
                $scope.filter.form.$setPristine();
            }
        };

        $scope.$watch('query.filter', function (newValue, oldValue) {
            if(!oldValue) {
                bookmark = $scope.query.page;
            }

            if(newValue !== oldValue) {
                $scope.query.page = 1;
            }

            if(!newValue) {
                $scope.query.page = bookmark;
            }

            getFlights();
        });

        $scope.addItem = function (event) {
            $mdDialog.show({
                clickOutsideToClose: true,
                controller: 'addFlightController',
                controllerAs: 'ctrl',
                focusOnOpen: false,
                targetEvent: event,
                templateUrl: 'assets/tpls/admin/add-flight-dialog.html',
            }).then(getFlights);
        };

        $scope.delete = function (event) {
            $mdDialog.show({
                clickOutsideToClose: true,
                controller: 'deleteFlightController',
                controllerAs: 'ctrl',
                focusOnOpen: false,
                targetEvent: event,
                locals: { flights: $scope.selected },
                templateUrl: 'assets/tpls/admin/delete-flight-dialog.html',
            }).then(getFlights);
        };

    }])
    .controller('addFlightController',['$scope', '$state', '$flights', '$mdDialog', function ($scope, $state, $flights, $mdDialog) {

        this.cancel = $mdDialog.cancel;

        function success(flight) {
            $mdDialog.hide(flight);
        }

        this.addItem = function () {
            $scope.item.form.$setSubmitted();

            if($scope.item.form.$valid) {
                $flights.flights.save({flight: $scope.flight}, success);
            }
        };

    }])
    .controller('deleteFlightController',['$scope', '$state', 'flights', '$flights', '$mdDialog', '$q', function ($scope, $state, flights, $flights, $mdDialog, $q) {

        this.cancel = $mdDialog.cancel;

        function deleteDessert(flight, index) {
            var deferred = $flights.flights.remove({id: flight.id});

            deferred.$promise.then(function () {
                flights.splice(index, 1);
            });

            return deferred.$promise;
        }

        function onComplete() {
            $mdDialog.hide();
        }

        this.confirm = function () {
            console.log('flights',flights);
            $q.all(flights.forEach(deleteDessert)).then(onComplete);
        }
    }])
;