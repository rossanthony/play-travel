'use strict';

/* Filters */

angular.module('PlayTravelApp.filters', [])
    .filter('numberFixedLen', function () {
        return function (n, len) {
            var num = parseInt(n, 10);
            len = parseInt(len, 10);
            if (isNaN(num) || isNaN(len)) {
                return n;
            }
            num = ''+num;
            while (num.length < len) {
                num = '0'+num;
            }
            return num;
        };
    })
    .filter('fourDigitTime', function () {
        /**
         * Convert 24 hour numeric time format (e.g 1445 or 905) to
         * human readable ':' separated format (e.g 14:45 or 09:05)
         */
        return function (time) {
            var time = parseInt(time);
            if (isNaN(time)) {
                return time;
            }
            time = ''+time;
            // add prefix zero if less that 4
            while (time.length < 4) {
                time = '0'+time;
            }
            var timeFormated = '';
            for (var i = 0; i < time.length; i++) {
                timeFormated += time.charAt(i);
                if (i == 1) timeFormated += ':';
            }
            return timeFormated;
        };
    })
    .filter('price', function () {
        return function (price) {
            var price = parseInt(price);
            if (isNaN(price)) {
                return price;
            }
            return price.toLocaleString();
        };
    })
;