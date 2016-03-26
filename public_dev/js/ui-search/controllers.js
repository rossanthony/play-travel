'use strict';

/* Controllers */

angular.module('PlayTravelApp.ui-search.controllers', [])
    .controller('SearchPageCtrl',['$scope', '$state','Search', function ($scope, $state, Search) {
        //$scope.Results = SearchData.Flights;
        //$scope.pagination    = SearchData.pagination;

        $scope.submit = function() {
            console.log("form submitted!");

            Search.get({
                date_from : 123,
                date_to : 321
            },function (data) {
                console.log(data);
            }, function (err) {
                console.log("Unable to perform search", err)
            });
        };

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

        console.log('Search controller 1');
    }])
;