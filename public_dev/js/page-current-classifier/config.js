'use strict';

angular.module(
    "PlayTravelApp.page-current-classifier",
    [
        "PlayTravelApp.page-current-classifier.controllers",
        "PlayTravelApp.page-current-classifier.services",
        "PlayTravelApp.page-current-classifier.directives",
        "PlayTravelApp.page-current-classifier.tpls"
    ]
)
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('admin.current-classifier', { url: '/current-classifier?{items_per_page}&{page}', templateUrl: '/assets/tpls/current-classifier.html', controller: 'CurrentClassifierPageCtrl',
                resolve: {
                    CurrentClassifierData: function (CurrentClassifierData,$stateParams) {
                        return CurrentClassifierData($stateParams);
                    },
                    TopicsData: function (TopicsData) {
                        return TopicsData();
                    }
                }
            })
        ;
    }])
;