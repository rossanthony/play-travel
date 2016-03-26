'use strict';

angular.module(
    "PlayTravelApp.ui-search",
    [
        "PlayTravelApp.ui-search.controllers",
        "PlayTravelApp.ui-search.services",
        "PlayTravelApp.ui-search.tpls"
    ]
)
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('search', { url: '/search?{items_per_page}&{page}', templateUrl: '/assets/tpls/ui/search.html', controller: 'SearchPageCtrl',
                resolve: {
                    SearchData: function (SearchData, $stateParams) {
                        return SearchData($stateParams);
                    }
                }
            })
        ;
    }])
;