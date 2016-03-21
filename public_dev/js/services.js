'use strict';

/* Services */

angular.module('PlayTravelApp.services', [])
    .factory('Ui', ['$resource', function ($resource) {
        return $resource('/WHATEVER/ui-data',{}, { 'get': {method: 'GET'}});
    }])
    .factory('UiData', ['$q','Ui', function ($q,Ui) {
        return function () {
            return {data:[1,2,3]};


            //var delay = $q.defer();
            //Ui.get(function (data) {
            //    delay.resolve(data);
            //}, function () {
            //    delay.reject('Unable to fetch data');
            //});
            //return delay.promise;
        };
    }])
    .factory('LogoutResource', ['$resource', function ($resource) {
        return $resource('/WHATEVER/logout',{}, {'get': {method: 'GET'}});
    }])
    .factory('Logout', ['LogoutResource','$state', function (LogoutResource, $state) {
        return function () {
            LogoutResource.get({},function (data) {
                if (typeof data.errors !=  'undefined' && data.errors.length > 0) {
                    angular.forEach(data.errors, function(error) {
                        //@todo handle the error (error.message)
                    });
                } else {
                    $state.go('login');
                }
            }, function () {
                //@todo handle the error
            });
        };
    }])
    .factory('BatchResource', ['$resource', function ($resource) {
        return $resource(
            'http://127.0.0.1:3001/:instance/batch_data/:item',
            {
                instance     : '@instance',
                item         : '@item'
            },
            {
                'put'    :{method: 'PUT'},
                'get'    :{method: 'GET'},
                'post'   :{method: 'POST'},
                'delete' :{method: 'DELETE'}
            });
    }])
    //.factory('StatusResource', ['$resource', function ($resource) {
    //    return $resource(
    //        'http://127.0.0.1:3001/:instance/status',
    //        {
    //            instance : '@instance'
    //        },
    //        {
    //            'get' :{method: 'GET'}
    //        });
    //}])
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
    .factory('socket', function ($rootScope) {
        var socket = io.connect();
        return {
            on: function (eventName, callback) {
                socket.on(eventName, function () {
                    var args = arguments;
                    $rootScope.$apply(function () {
                        callback.apply(socket, args);
                    });
                });
            },
            emit: function (eventName, data, callback) {
                socket.emit(eventName, data, function () {
                    var args = arguments;
                    $rootScope.$apply(function () {
                        if (callback) {
                            callback.apply(socket, args);
                        }
                    });
                })
            }
        };
    })
;