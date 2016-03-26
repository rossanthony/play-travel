'use strict';

// IE8 fix - be careful not to use console.warn() - it will cause IE8 to silently not run angular.
var console = console || {};

angular.module('PlayTravelApp', [
    'ui.router',
    'ngAnimate',
    'ngResource',
    'ngMessages',
    'ngSanitize',
    'ngCookies',
    'ui.bootstrap',
    'ui.select',
    'angular-loading-bar',
    'toastr',
    'chart.js',
    'satellizer',
    'mgcrea.ngStrap',

    //'btford.socket-io',
    'material.components.input',
    'material.components.switch',
    'material.components.checkbox',

    //App
    'PlayTravelApp.controllers',
    'PlayTravelApp.services',
    'PlayTravelApp.tpls',

    //App Pages
    'PlayTravelApp.page-dashboard',
    'PlayTravelApp.page-flights',
    'PlayTravelApp.ui-search'
])
    .config(['$stateProvider','$urlRouterProvider', '$httpProvider', '$authProvider', '$alertProvider', function ($stateProvider, $urlRouterProvider, $httpProvider, $authProvider, $alertProvider) {

        //$urlRouterProvider.when("", "/");
        //$urlRouterProvider.when("/", "/dashboard");

        //$stateProvider
        //    // All app states loaded in index.html
        //    .state('login', { url: '/login', templateUrl: '/assets/tpls/login.html', controller: 'LoginCtrl'})
        //    .state('admin', { url: '/admin', abstract: true, templateUrl: '/assets/tpls/admin-layout.html', controller: 'AdminCtrl',
        //        resolve: {
        //            UiData: function (UiData) {
        //                return UiData();
        //            }
        //        }
        //    })
        //    .state('404', { url: '/404', templateUrl: '/assets/tpls/404.html', controller: 'NotFoundCtrl'})
        //;

        $urlRouterProvider.otherwise('/404');

        $stateProvider
            .state('admin', { url: '/admin', abstract: true, templateUrl: '/assets/tpls/admin-layout.html', controller: 'AdminCtrl', resolve: {
                authenticated: function($q, $location, $auth) {
                    var deferred = $q.defer();

                    if (!$auth.isAuthenticated()) {
                        $location.path('/signIn');
                    } else {
                        deferred.resolve();
                    }

                    return deferred.promise;
                }
            }})
            .state('signUp', {
                url: '/signUp',
                templateUrl: '/views/signUp.html',
                controller: function($scope, $alert, $auth) {
                    /**
                     * The submit method.
                     */
                    $scope.submit = function(event) {
                        event.preventDefault();

                        $auth.signup({
                            firstName: $scope.firstName,
                            lastName: $scope.lastName,
                            email: $scope.email,
                            password: $scope.password
                        }).then(function() {
                            $alert({
                                content: 'You have successfully signed up',
                                animation: 'fadeZoomFadeDown',
                                type: 'material',
                                duration: 3
                            });
                        }).catch(function(response) {
                            $alert({
                                content: response.data.message,
                                animation: 'fadeZoomFadeDown',
                                type: 'material',
                                duration: 3
                            });
                        });
                    };
                }
            })
            .state('signIn', { url: '/signIn', templateUrl: '/views/signIn.html', controller: 'SignInCtrl' })
            .state('signOut', { url: '/signOut', template: null,  controller: 'SignOutCtrl' })
            .state('404', { url: '/404', templateUrl: '/assets/tpls/404.html', controller: 'NotFoundCtrl'});
    }])
    .config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(true);
    }])
    .config(function (toastrConfig) {
        angular.extend(toastrConfig, {
            allowHtml: true,
            positionClass: 'toast-bottom-right'
        });
    })
    .config(['$httpProvider', '$authProvider', function($httpProvider, $authProvider) {
        $httpProvider.interceptors.push(function($q, $injector) {
            return {
                request: function(request) {
                    // Add auth token for Silhouette if user is authenticated
                    var $auth = $injector.get('$auth');
                    if ($auth.isAuthenticated()) {
                        request.headers['X-Auth-Token'] = $auth.getToken();
                    }

                    // Add CSRF token for the Play CSRF filter
                    var cookies = $injector.get('$cookies');
                    var token = cookies.get('PLAY_CSRF_TOKEN');
                    if (token) {
                        // Play looks for a token with the name Csrf-Token
                        // https://www.playframework.com/documentation/2.4.x/ScalaCsrf
                        request.headers['Csrf-Token'] = token;
                    }

                    return request;
                },

                responseError: function(rejection) {
                    if (rejection.status === 401) {
                        var $auth = $injector.get('$auth');
                        $auth.logout();
                        $injector.get('$state').go('signIn');
                    }
                    return $q.reject(rejection);
                }
            };
        });

        // Auth config
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
        $authProvider.loginOnSignup = true;
        $authProvider.loginRedirect = '/admin/flights';
        $authProvider.logoutRedirect = '/signIn';
        $authProvider.signupRedirect = '/admin';
        $authProvider.loginUrl = '/signIn';
        $authProvider.signupUrl = '/signUp';
        $authProvider.loginRoute = '/signIn';
        $authProvider.signupRoute = '/signUp';
        $authProvider.tokenName = 'token';
        $authProvider.tokenPrefix = 'satellizer'; // Local Storage name prefix
        $authProvider.authHeader = 'X-Auth-Token';
        $authProvider.platform = 'browser';
        $authProvider.storage = 'localStorage';
    }])
    //.config(['$httpProvider', function($httpProvider) {
    //    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    //    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    //
    //    /**
    //     * The workhorse; converts an object to x-www-form-urlencoded serialization.
    //     * @param {Object} obj
    //     * @return {String}
    //     */
    //    var param = function(obj) {
    //        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
    //
    //        for(name in obj) {
    //            value = obj[name];
    //
    //            if(value instanceof Array) {
    //                for(i=0; i<value.length; ++i) {
    //                    subValue = value[i];
    //                    fullSubName = name + '[' + i + ']';
    //                    innerObj = {};
    //                    innerObj[fullSubName] = subValue;
    //                    query += param(innerObj) + '&';
    //                }
    //
    //                if (value.length == 0) {
    //                    query += encodeURIComponent(name) + '=' + '&';
    //                }
    //            }
    //            else if(value instanceof Object) {
    //                for(subName in value) {
    //                    subValue = value[subName];
    //                    fullSubName = name + '[' + subName + ']';
    //                    innerObj = {};
    //                    innerObj[fullSubName] = subValue;
    //                    query += param(innerObj) + '&';
    //                }
    //            }
    //            else if(value !== undefined && value !== null)
    //                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
    //        }
    //
    //        return query.length ? query.substr(0, query.length - 1) : query;
    //    };
    //
    //    // Override $http service's default transformRequest
    //    $httpProvider.defaults.transformRequest = [function(data) {
    //        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
    //    }];
    //}])
    //.config(['$httpProvider', function($httpProvider) {
    //    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    //}])
    .run(['$rootScope', '$state', function ($rootScope, $state) {

        //IE8 fix for non-existent indexOf extension
        if (!Array.prototype.indexOf) {
            Array.prototype.indexOf = function (obj, start) {
                for (var i = (start || 0), j = this.length; i < j; i++) {
                    if (this[i] === obj) {
                        return i;
                    }
                }
                return -1;
            }
        }
    }]);