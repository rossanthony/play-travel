'use strict';

angular.module(
    "PlayTravelApp.page-dashboard",
    [
        "PlayTravelApp.page-dashboard.controllers",
        "PlayTravelApp.page-dashboard.services",
        "PlayTravelApp.page-dashboard.directives"
    ]
)
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('admin.dashboard', { url: '', templateUrl: '/assets/tpls/dashboard.html', controller: 'DashboardPageCtrl',
                resolve: {
                    DashBoardData: function (DashBoardData, $stateParams) {
                        return DashBoardData($stateParams);
                    }
                }
            })
        ;
    }])
;