'use strict';

/* Controllers */

angular.module('PlayTravelApp.ui-search.controllers', [])
    .controller('SearchPageCtrl',['$scope', '$state','$flights', '$mdDialog', function ($scope, $state, $flights, $mdDialog) {

        console.log('SearchPageCtrl');

        var bookmark;

        $scope.selected = [];

        $scope.options = {
            autoSelect: true,
            boundaryLinks: true,
            largeEditDialog: true,
            pageSelector: true,
            rowSelection: true
        };

        $scope.filter = {
            options: {
                debounce: 500
            }
        };

        $scope.query = {
            order: '',
            limit: 5,
            page: 1,
            departureDate: ''
        };

        $scope.datepickers = {
            departureDate: '',
            returnDate: ''
        };

        $scope.dateOptions = {
            dateFormat: "yy-mm-dd"
        };

        function getFlights(query) {
            console.log('query', query);

            // Format the dates correctly before passing to the service (otherwise Json.stringify will add timezone)
            if (typeof $scope.datepickers.departureDate === 'object') {
                $scope.query.departureDate = $scope.datepickers.departureDate.getFullYear().toString()
                    + '-' + ($scope.datepickers.departureDate.getMonth() + 1).toString()
                    + '-' + $scope.datepickers.departureDate.getDate().toString();
            }
            if (typeof $scope.datepickers.returnDate === 'object') {
                $scope.query.returnDate = $scope.datepickers.returnDate.getFullYear().toString()
                    + '-' + ($scope.datepickers.returnDate.getMonth() + 1).toString()
                    + '-' + $scope.datepickers.returnDate.getDate().toString();
            }
            console.log('$scope.query', $scope.query);
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

        $scope.submit = function () {
            getFlights();
        };

        $scope.$watch('datepickers.departureDate', function (newValue, oldValue) {
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
    }])
;