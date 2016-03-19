// Gulp components
var gulp            = require('gulp'),
    concat          = require('gulp-concat'),      //concats files
    uglify          = require('gulp-uglify'),      //minifies js files
    cssmin          = require('gulp-cssmin'),
    ngAnnotate      = require('gulp-ng-annotate'), //annotates (angular brakes without this)
    runSequence     = require('run-sequence'),     //runs gulp tasks in sequence (tasks as array)
    sass            = require('gulp-sass'),        //sass compiler
    sourcemaps      = require('gulp-sourcemaps'),  //creates sourcemaps for debugging
    jsonminify      = require('gulp-jsonminify'),
    autoprefixer    = require('gulp-autoprefixer'),
    combineMq       = require('gulp-combine-mq'),
    stripCssCmts    = require('gulp-strip-css-comments'),
    shell           = require('gulp-shell'),
    htmlmin         = require('gulp-htmlmin'),
    rename          = require("gulp-rename");

/**
 * IF GULP IS TYPED WITH NO OTHER ARGUMENTS THIS WILL BE EXECUTED
 */
gulp.task('default', function() {
    runSequence(
        //everything in square brackets executes in parallel
        //to force sequential execution put your task outside brackets
        [
            //'vendor-js',
            //'vendor-css',
            //'app-js',
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
            //'watch-app-js',
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
            '',
            ''
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
        .pipe(ngAnnotate())
        .pipe(uglify())
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
    gulp.src('scss/**/*.scss')
        .pipe(sourcemaps.init())
        .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError)) // compile & compress
        .pipe(concat('app.min.css'))
        .pipe(combineMq({beautify: false}))
        .pipe(stripCssCmts({preserve:false}))
        .pipe(sourcemaps.write('maps', {includeContent: false}))
        .pipe(gulp.dest('../public/stylesheets'));
});

//watch app specific scss
gulp.task('watch-app-css', ['app-scss'], function () {
    gulp.watch('scss/**/*.scss', ['app-scss']);
});
