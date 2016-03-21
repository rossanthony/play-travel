'use strict';

/* Services */

angular.module('PlayTravelApp.page-current-classifier.services', [])
    .factory('CurrentClassifierData', ['$q','BatchResource', function ($q,BatchResource) {
        return function ($stateParams) {
            var delay = $q.defer();
            BatchResource.get({
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
    .factory('AddBatchItem', ['$uibModal','toastr', 'BatchResource', function ($uibModal, toastr, BatchResource) {
        return function () {
            var modalInstance = $uibModal.open({
                templateUrl: 'template/new-batch-item.html',
                size: 'md',
                resolve: {
                    TopicsData: function (TopicsData) {
                        return TopicsData()
                    }
                },
                controller: ['$scope', '$uibModalInstance', 'TopicsData', function($scope, $uibModalInstance, TopicsData) {

                    $scope.text            = '';
                    $scope.availableTopics = TopicsData.topics;
                    $scope.topic           = {value: null};
                    $scope.textError       = false;
                    $scope.topicError      = false;
                    $scope.disabledForm    = false;

                    $scope.ok = function () {
                        $scope.disabledForm = true;
                        $scope.textError  = false;
                        $scope.topicError = false;

                        if ($scope.text == '') {
                            $scope.textError = true;
                        }

                        if ($scope.topic.value == null) {
                            $scope.topicError = true;
                        }

                        if ($scope.topicError || $scope.textError) {
                            $scope.disabledForm = false;
                            return false;
                        }

                        $uibModalInstance.close({
                            text: $scope.text,
                            topic: $scope.topic.value.id
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