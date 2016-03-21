'use strict';

/* Controllers */

angular.module('PlayTravelApp.page-current-classifier.controllers', [])
    .controller('CurrentClassifierPageCtrl',['$scope', '$state', 'CurrentClassifierData', 'TopicsData', 'AddBatchItem', 'toastr', 'BatchResource', 'DeleteConfirmation', function ($scope, $state, CurrentClassifierData, TopicsData, AddBatchItem, toastr, BatchResource, DeleteConfirmation) {
        //$scope.currentClassifier = CurrentClassifierData;
        $scope.rules   = CurrentClassifierData.rules;
        $scope.topics  = TopicsData.topics;
        $scope.addItem = AddBatchItem;

        $scope.pagination  = CurrentClassifierData.pagination;

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

        $scope.deleteSelected = function() {
            var toDelete = [];

            angular.forEach( $scope.rules, function(rule) {
                if (rule.delete == true) {
                    toDelete.push(rule.id);
                }
            });

            if (toDelete.length == 0) {
                toastr.error('Select batch items to delete!');
            } else {
                DeleteConfirmation('', function(){
                    BatchResource.delete({instance:'1'}, {todelete: toDelete}, function(){
                        toastr.success('Deleted batch items!');
                    }, function() {

                    });
                });
            }
        };

        // we have to register all watchers so we can clear them on item refresh
        // before reapplying them
        var itemWatchers = [];
        $scope.$watchCollection('rules', function(newItems, oldItems) {
            angular.forEach( $scope.rules, function(rule, index) {
                // If a watcher is already running on this item clear it
                if (typeof itemWatchers[rule.id] == 'function') itemWatchers[rule.id]();
                // Apply watcher for pagination
                itemWatchers[rule.id] = $scope.$watch('rules['+index+'].included_in_next', function(newValue, oldValue) {
                    if (typeof newValue != 'undefined' && newValue != oldValue) {
                        BatchResource.put(
                            {
                                instance:'1', item: rule.id
                            },
                            {
                                included_in_next: newValue
                            }, function (data) {
                                toastr.success(((newValue) ? 'Staged': 'Unstaged') + ' batch item!');
                            }, function () {
                                //@todo handle the error
                                toastr.error('Ooops!');
                            });

                    }
                });
            });
        });

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

        $scope.retrain = function() {
            $scope.inTraining = true;

            BatchResource.put(
                {
                    instance:'1',
                    item: 'train'
                },
                {}, function (data) {
                    toastr.success('Trained classifier!');
                    $scope.inTraining = false;
                }, function () {
                    //@todo handle the error
                    toastr.error('Ooops!');
                    $scope.inTraining = false;
                });
        };

        $scope.$watch('trainingInProgress', function(newValue, oldValue) {
            if (typeof newValue != 'undefined' && newValue != oldValue && (!newValue && oldValue)) {
                var params = $state.params;
                params.page = null;

                $state.transitionTo($state.current.name, params, {reload : $state.$current});
            }
        });
    }])
;