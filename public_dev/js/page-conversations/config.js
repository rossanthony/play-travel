'use strict';

angular.module(
    "PlayTravelApp.page-conversations",
    [
        "PlayTravelApp.page-conversations.controllers",
        "PlayTravelApp.page-conversations.services",
        "PlayTravelApp.page-conversations.tpls"
    ]
)
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('admin.conversations', { url: '/conversations?{items_per_page}&{page}', templateUrl: '/assets/tpls/conversations.html', controller: 'ConversationsPageCtrl',
                resolve: {
                    ConversationsData: function (ConversationsData, $stateParams) {
                        return ConversationsData($stateParams);
                    }
                }
            })
            .state('admin.conversations.item', { url: '/:conversation', templateUrl: '/assets/tpls/conversation.html', controller: 'ConversationPageCtrl',
                resolve: {
                    ConversationData: function (ConversationData, $stateParams) {
                        return ConversationData($stateParams);
                    }
                }
            })
        ;
    }])
;