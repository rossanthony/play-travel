'use strict';

/* Controllers */

angular.module('PlayTravelApp.page-conversations.controllers', [])
    .controller('ConversationsPageCtrl',['$scope', '$state','ConversationsData', function ($scope, $state, ConversationsData) {
        $scope.conversations = ConversationsData.conversations;
        $scope.pagination    = ConversationsData.pagination;

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

        console.log('conversations controller 1');
        console.log(ConversationsData);
    }])
    .controller('ConversationPageCtrl',['$scope', '$state', 'ConversationData', 'ReclassifyMessage', function ($scope, $state, ConversationData, ReclassifyMessage) {
        $scope.conversation = ConversationData;
        console.log('conversation controller 2');
        console.log(ConversationData);

        $scope.addToClassifier = function(message) {
            ReclassifyMessage(message);
        };
    }])
;