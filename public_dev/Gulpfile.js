//GUlp components
var gulp        = require('gulp'),
    concat      = require('gulp-concat'),      //concats files
    uglify      = require('gulp-uglify'),      //minifies files
    ngAnnotate  = require('gulp-ng-annotate'), //annotates (angular brakes without this)
    minifyCSS   = require('gulp-cssnano'),     //minifies css files
    runSequence = require('run-sequence'),     //runs gulp tasks in sequence (tasks as array)
    sass        = require('gulp-sass'),        //sass compiler
    sourcemaps  = require('gulp-sourcemaps');  //creates sourcemaps for debugging
    //rename      = require("gulp-rename");


/**
 * IF GULP IS TYPED WITH NO OTHER ARGUMENTS THIS WILL BE EXECUTED
 */
gulp.task('default', function() {
    runSequence(
        //everything in square brackets executes in parallel
        //to force sequential execution put your task outside brackets
        [
            'vendor-js',
            'vendor-css',
            'app-js',
            'app-scss'
        ]
    );
});

/**
 * START ALL THE WATCHERS
 */
gulp.task('watch-init', function() {
    runSequence(
        //everything in square brackets executes in parallel
        //to force sequential execution put your task outside brackets
        [
            'watch-app-js',
            'watch-app-css'
        ]
    );
});

/**
 * JAVASCRIPT RELATED TASKS
 */
//Concat & compress vendor files
gulp.task('vendor-js', function () {
    gulp.src(
        [
            // Angular Core, Modules etc
            'bower_components/angular/angular.min.js',
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/angular-strap/dist/angular-strap.min.js',
            'bower_components/angular-strap/dist/angular-strap.tpl.min.js',
            'bower_components/angular-aria/angular-aria.min.js',
            'bower_components/angular-cookies/angular-cookies.min.js',
            'bower_components/angular-loader/angular-loader.min.js',
            'bower_components/angular-messages/angular-messages.min.js',
            'bower_components/angular-resource/angular-resource.min.js',
            'bower_components/angular-sanitize/angular-sanitize.min.js',
            'bower_components/angular-touch/angular-touch.min.js',
            'bower_components/angular-ui-router/release/angular-ui-router.min.js',
            'bower_components/angular-toastr/dist/angular-toastr.min.js',
            'bower_components/angular-toastr/dist/angular-toastr.tpls.min.js',
            'bower_components/angular-loading-bar/build/loading-bar.min.js',

            // Required for Auth
            'bower_components/satellizer/satellizer.min.js',

            // For date/timepickers
            'bower_components/moment/min/moment.min.js',
            'bower_components/mdPickers/dist/mdPickers.min.js',

            // Bootstrap
            'bower_components/angular-bootstrap/ui-bootstrap.min.js',
            'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
            //'bower_components/ui-select/dist/select.min.js',

            // Angular Material modules
            'bower_components/angular-material/angular-material.min.js',
            'bower_components/angular-material-data-table/dist/md-data-table.min.js',
            'bower_components/angular-material/modules/js/core/core.min.js',
            'bower_components/angular-material/modules/js/input/input.min.js',
            'bower_components/angular-material/modules/js/checkbox/checkbox.min.js',
            'bower_components/angular-material/modules/js/switch/switch.min.js'

            //'bower_components/Chart.js/src/Chart.Core.js',
            //'bower_components/Chart.js/src/Chart.Bar.js',
            //'bower_components/Chart.js/src/Chart.Doughnut.js',
            //'bower_components/Chart.js/src/Chart.Line.js',
            //'bower_components/angular-chart.js/dist/angular-chart.min.js',
        ]
        )
        .pipe(concat('vendor.min.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest('../public/js'));
});

//Concat & compress application specific files
gulp.task('app-js', function () {
    gulp.src(['js/**/*.js'])
        .pipe(concat('app.min.js'))
        //.pipe(ngAnnotate())
        //.pipe(uglify())
        .pipe(gulp.dest('../public/js'));
});

//File watcher: if an app specific js is edited run concat compressor again
gulp.task('watch-app-js', ['app-js'], function () {
    gulp.watch('js/**/*.js', ['app-js']);
});

/**
 * END OF JAVASCRIPT RELATED TASKS
 */

/**
 * CSS RELATED TASKS
 */
//compile app specific scss to css
gulp.task('app-scss', function() {
    gulp.src('scss/*.scss')
        .pipe(sourcemaps.init())
        //The onError handler prevents Gulp from crashing when you make a mistake in your SASS
        //compile & compress
        .pipe(sass({onError: function(e) { console.log(e); }, outputStyle: 'compressed'}))
        .pipe(concat('app.min.css'))
        //make source-maps but put them in a different folder & not include content
        .pipe(sourcemaps.write('maps', {includeContent: false}))
        .pipe(gulp.dest('../public/css'));
});

//watch app specific scss
gulp.task('watch-app-css', ['app-scss'], function () {
    gulp.watch('scss/*.scss', ['app-scss']);
});

//concat vendor css files MUST BE MINIFIED AT THIS STAGE
gulp.task('vendor-css', function() {

    gulp.src(
        [
            'bower_components/angular-material/angular-material.min.css',
            //'bower_components/angular-material/modules/js/core/core.min.css',
            //'bower_components/angular-material/modules/js/input/input.min.css',
            //'bower_components/angular-material/modules/js/checkbox/checkbox.min.css',
            //'bower_components/angular-material/modules/js/switch/switch.min.css',
            //'bower_components/angular-material/modules/js/sidenav/sidenav.min.css',
            'bower_components/ui-select/dist/select.min.css',
            'bower_components/angular-toastr/dist/angular-toastr.min.css',
            'bower_components/angular-loading-bar/build/loading-bar.min.css',
            'bower_components/angular-material-data-table/dist/md-data-table.min.css',
            'css/vendor/*.css'
        ]
        )
        .pipe(concat('vendor.min.css'))
        .pipe(minifyCSS({keepSpecialComments:'*'}))
        .pipe(gulp.dest('../public/css/'));
});
/**
 * END OF CSS RELATED TASKS
 */