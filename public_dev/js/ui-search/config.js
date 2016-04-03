'use strict';

angular.module(
    "PlayTravelApp.ui-search",
    [
        "PlayTravelApp.ui-search.controllers",
        "PlayTravelApp.ui-search.services"
    ])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('ui.search', { url: '/search?{items_per_page}&{page}', templateUrl: '/assets/tpls/ui/search.html', controller: 'SearchPageCtrl'})
        ;
    }])
;