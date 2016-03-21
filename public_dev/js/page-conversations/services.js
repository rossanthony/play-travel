'use strict';

/* Services */

angular.module('PlayTravelApp.page-conversations.services', [])
    .factory('Conversations', ['$resource', function ($resource) {
        return $resource(
            'http://127.0.0.1:3001/:instance/conversations/:conversation',
            {
                instance       : '@instance',
                conversation   : '@conversation'
            },
            {
                'get': {method: 'GET'},
                'post': { method: 'POST', isArray: false}
            });
    }])
    .factory('ConversationsData', ['$q','Conversations', function ($q,Conversations) {
        return function ($stateParams) {
            var delay = $q.defer();
            Conversations.get({
                instance       : '1',
                page           : ($stateParams.page) ? $stateParams.page : null,
                items_per_page : ($stateParams.items_per_page) ? $stateParams.items_per_page : null
            },function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
    .factory('ConversationData', ['$q','Conversations', function ($q,Conversations) {
        return function ($stateParams) {
            var delay = $q.defer();
            Conversations.get({
                instance: '1',
                conversation : $stateParams.conversation
            },function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
    .factory('Topics', ['$resource', function ($resource) {
        return $resource(
            'http://127.0.0.1:3001/:instance/topics/:topic',
            {
                instance     : '@instance',
                topic : '@topic'
            },
            {
                'get': {method: 'GET'},
                'post': { method: 'POST', isArray: false}
            });
    }])
    .factory('TopicsData', ['$q','Topics', function ($q,Topics) {
        return function () {
            var delay = $q.defer();
            Topics.get({
                instance: '1'
            },function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
    .factory('ReclassifyMessage', ['$uibModal','toastr', 'BatchResource', function ($uibModal, toastr, BatchResource) {
        return function (message) {
            var modalInstance = $uibModal.open({
                templateUrl: 'template/reclassify-message-overlay.html',
                size: 'md',
                resolve: {
                    message: function () {
                        return message;
                    },
                    TopicsData: function (TopicsData) {
                        return TopicsData()
                    }
                },
                controller: ['$scope', '$uibModalInstance', 'message', 'TopicsData', function($scope, $uibModalInstance, message, TopicsData) {

                    $scope.text            = message.text;
                    $scope.textCopy        = angular.copy($scope.text);
                    $scope.availableTopics = TopicsData.topics;
                    $scope.newTopic        = {value: null};
                    $scope.textError       = false;
                    $scope.topicError      = false;
                    $scope.disabledForm    = false;

                    $scope.ok = function () {
                        $scope.disabledForm = true;
                        $scope.textError  = false;
                        $scope.topicError = false;

                        if ($scope.textCopy == '') {
                            $scope.textError = true;
                        }

                        if ($scope.newTopic.value == null) {
                            $scope.topicError = true;
                        }

                        if ($scope.topicError || $scope.textError) {
                            $scope.disabledForm = false;
                            return false;
                        }

                        $uibModalInstance.close({
                            text: $scope.textCopy,
                            topic: $scope.newTopic.value.id
                        });
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }]
            });

            modalInstance.result.then(function (data) {
                BatchResource.post({instance:'1'}, data, function (data) {
                    toastr.success('Added to batch!');
                }, function () {
                    //@todo handle the error
                });
            }, function () {
                // Action when overlay is closed
            });
        };
    }])
;