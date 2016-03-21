'use strict';

angular.module(
    "PlayTravelApp.page-dashboard.directives",
    []
)
    .directive('charter', ['$compile', function($compile) {
        return {
            restrict : 'A',
            replace: true,
            scope: {
                chart : '=charter'
            },
            controller : ['$scope', function($scope) {

            }],
            link : function (scope, element, attrs) {
                switch (scope.chart.type) {
                    case 'pie':
                        //Type pie
                        element.html('<canvas style="height:50px;" class="chart chart-pie" chart-data="chart.data" chart-legend="true" chart-labels="chart.labels"></canvas>');
                        break;
                    case 'line':
                        //Type pie
                        element.html('<canvas style="height:50px;" class="chart chart-line" chart-data="[chart.data]" chart-series="[\'Active conversations\']" chart-labels="chart.labels"></canvas>');
                        break;
                }
                $compile(element.contents())(scope);
            }
        }
    }])

;