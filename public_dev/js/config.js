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
    //'ui.select',
    'angular-loading-bar',
    'toastr',
    'satellizer',
    'mgcrea.ngStrap',
    'ngMaterial',
    'md.data.table',
    'mdPickers',

    //App
    'PlayTravelApp.controllers',
    'PlayTravelApp.services',
    'PlayTravelApp.filters',

    //App Pages
    'PlayTravelApp.admin-flights',
    'PlayTravelApp.ui-search',
    'PlayTravelApp.auth'
])
    .config(['$stateProvider','$urlRouterProvider', '$httpProvider', '$authProvider', '$alertProvider', function ($stateProvider, $urlRouterProvider, $httpProvider, $authProvider, $alertProvider) {

        $urlRouterProvider
            .when("",  "/search")
            .when("/",  "/search")
            .when("/search/",  "/search")
            .when("/admin",  "/admin/flights")
            .when("/admin/", "/admin/flights")
            .otherwise(function($injector, $location){
                var state = $injector.get('$state');
                state.go('404.not-found');
                return $location.path();
            });

        $stateProvider
            .state('ui', { url: '', abstract: true, templateUrl: '/assets/tpls/ui-layout.html', controller: 'UiCtrl'})
            .state('auth', { url: '/auth', abstract: true, templateUrl: '/assets/tpls/ui-layout.html', controller: 'AuthCtrl'})
            .state('admin', { url: '/admin', abstract: true, templateUrl: '/assets/tpls/admin-layout.html', controller: 'AdminCtrl', resolve: {
                authenticated: function($q, $location, $auth) {
                    var deferred = $q.defer();
                    if (!$auth.isAuthenticated())
                        $location.path('/signIn');
                    else
                        deferred.resolve();
                    return deferred.promise;
                },
                UiData: function (UiData) {
                    return UiData();
                }
            }})
            .state('404', {  abstract: true, templateUrl: '/assets/tpls/ui-layout.html'})
            .state('404.not-found', { templateUrl: '/assets/tpls/404.html'})
        ;
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
                    if(rejection.status === 404) {
                        $injector.get('$state').go('404.not-found');
                    }
                    if (rejection.status === 401) {
                        var $auth = $injector.get('$auth');
                        $auth.logout();
                        $injector.get('$state').go('auth.signIn');
                    }
                    return $q.reject(rejection);
                }
            };
        });
        // Auth config
        $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
        $authProvider.loginOnSignup = true;
        $authProvider.loginRedirect = '/admin/flights';
        $authProvider.logoutRedirect = '/auth/sign-in';
        $authProvider.signupRedirect = '/user/account';
        $authProvider.loginUrl = '/api/v1/auth/sign-in';
        $authProvider.signupUrl = '/api/v1/auth/sign-up';
        $authProvider.loginRoute = '/auth/sign-in';
        $authProvider.signupRoute = '/auth/sign-up';
        $authProvider.tokenName = 'token';
        $authProvider.tokenPrefix = 'satellizer'; // Local Storage name prefix
        $authProvider.authHeader = 'X-Auth-Token';
        $authProvider.platform = 'browser';
        $authProvider.storage = 'localStorage';
    }]);