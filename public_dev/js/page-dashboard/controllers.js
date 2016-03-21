'use strict';

/* Controllers */

angular.module('PlayTravelApp.page-dashboard.controllers', [])
    .controller('DashboardPageCtrl',['$scope', '$state', 'DashBoardData', 'Dashboard', 'toastr', '$filter', function ($scope, $state, DashBoardData, Dashboard, toastr, $filter) {
        $scope.dateFrom    = new Date(DashBoardData.filters.date_from);
        $scope.dateTo      = new Date(DashBoardData.filters.date_to);
        $scope.charts      = DashBoardData.charts;
        $scope.dataLoading = false;

        $scope.reloadData = function() {
            $scope.dataLoading = true;
            Dashboard.get({
                instance : '1',
                dateFrom : $filter('date')($scope.dateFrom, 'yyyy-MM-dd'),
                dateTo   : $filter('date')($scope.dateTo, 'yyyy-MM-dd'),
                period   : 'custom'
            },function(response){
                toastr.success('Data reloaded');
                $scope.dataLoading = false;
            }, function(){

            });
        };

        console.log(DashBoardData);
    }])
;