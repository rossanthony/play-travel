'use strict';

angular.module(
    "PlayTravelApp.page-current-classifier.directives",
    []
)
    .directive('rulerow', [ function() {
        return {
            restrict : 'A',
            replace: true,
            templateUrl: 'template/reclassify-rule-row.html',
            scope: {
                item       : '=rulerow',
                topics     : '=topics',
                intraining : '=intraining'
            },
            controller : ['$scope','$rootScope','toastr', 'BatchResource', 'ReloadState', 'DeleteConfirmation', function($scope, $rootScope, toastr, BatchResource, ReloadState,DeleteConfirmation) {
                $scope.textCopy           = angular.copy($scope.item.input);
                $scope.newTopic           = {value: null};
                $scope.trainingInProgress = $rootScope.trainingInProgress;

                angular.forEach($scope.topics, function(value, index) {
                    if ($scope.item.topic.id == value.id) {
                        $scope.newTopic.value = value;
                    }
                });

                $scope.textError       = false;
                $scope.topicError      = false;
                $scope.disabledForm    = false;

                $scope.deleteItem = function() {
                    DeleteConfirmation('', function () {
                        BatchResource.delete(
                            {
                                instance:'1', item: $scope.item.id
                            },
                            function (data) {
                                toastr.success('Deleted batch item!');
                                ReloadState();
                            }, function () {
                                //@todo handle the error
                                toastr.error('Ooops!');
                            });
                    })

                };

                $scope.submitForm = function() {
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

                    BatchResource.put(
                        {
                            instance:'1', item: $scope.item.id
                        },
                        {
                            text: $scope.textCopy,
                            topic: $scope.newTopic.value.id
                        }, function (data) {
                            toastr.success('Saved batch item!');
                            ReloadState();
                        }, function () {
                            //@todo handle the error
                            toastr.error('Ooops!');
                        });
                };

            }],
            link : function (scope, element, attrs) {

            }
        }
    }])

;