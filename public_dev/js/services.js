'use strict';

/* Services */

angular.module('PlayTravelApp.services', [])
    .factory('Ui', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:9000/user');
    }])
    .factory('UiData', ['$q','Ui', function ($q, Ui) {
        return function () {
            //return {data:[1,2,3]};
            var delay = $q.defer();
            Ui.get(function (data) {
                delay.resolve(data);
            }, function () {
                delay.reject('Unable to fetch data');
            });
            return delay.promise;
        };
    }])
    .factory('ReloadState', ['$state', function ($state) {
        return function () {
            $state.transitionTo($state.current.name, $state.params, {reload : $state.$current});
        }
    }])
    .factory('DeleteConfirmation', ['$uibModal', function ($uibModal) {
        return function (message, callback) {
            var modalInstance = $uibModal.open({
                templateUrl: 'template/confirm-delete.html',
                size: 'sm',
                resolve: {
                    message: function () {
                        return message;
                    }
                },
                controller: ['$scope', '$uibModalInstance', 'message', function($scope, $uibModalInstance, message) {

                    $scope.message = message;

                    $scope.ok = function () {
                        $uibModalInstance.close();
                    };

                    $scope.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    };
                }]
            });

            modalInstance.result.then(function () {
                callback();
            }, function () {
                // Action when overlay is closed
            });
        };
    }])
;