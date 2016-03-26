'use strict';

angular.module(
    "PlayTravelApp.page-flights.tpls",
    [
        "template/reclassify-message-overlay.html"
    ]
);

angular.module("template/reclassify-message-overlay.html", []).run(["$templateCache", function($templateCache) {
    $templateCache.put("template/reclassify-message-overlay.html",
        '   <div class="modal-header">' +
        '       <h4 class="modal-title">Reclassify message</h4>' +
        '   </div>' +
        '   <div class="modal-body">' +
        '       <md-input-container class="md-block">' +
        '           <label>Original message</label>' +
        '           <textarea ng-model="text" columns="1" rows="5" ng-disabled="true"></textarea>' +
        '       </md-input-container>' +
        '       <md-input-container class="md-block">' +
        '           <label>Edit message</label>' +
        '           <textarea ng-model="textCopy" columns="1" rows="5"></textarea>' +
        '           <span class="label label-warning" ng-show="textError">This is a required field</span>' +
        '       </md-input-container>' +
        '       <md-input-container class="md-block">' +
        '           <label>Should be classified as:</label>' +
        '           <div ui-select ng-model="newTopic.value">' +
        '               <div ui-select-match placeholder="Select...">{{$select.selected.label}}</div>' +
        '               <div ui-select-choices repeat="option in availableTopics">' +
        '                   <div ng-bind-html="option.label | highlight: $select.search"></div>' +
        '               </div>' +
        '           </div>' +
        '           <span class="label label-warning" ng-show="topicError">This is a required field</span>' +
        '       </md-input-container>' +
        '       <div class="text-right">' +
        '           <div class="btn-group" role="group" aria-label="...">' +
        '               <button type="button" ng-click="ok()" class="btn btn-sm btn-default" ng-disabled="disabledForm"><span class="fa fa-paper-plane-o" ng-class="{ \'fa-paper-plane-o\': !disabledForm, \'fa-spin fa-spinner\': disabledForm }"></span> Add to classifier</button>' +
        '               <span class="btn btn-default btn-sm" ng-click="cancel()"><span class="fa fa-close"></span>&nbsp;&nbsp;Close (Esc)</span>' +
        '           </div>' +
        '       </div>' +
        '   </div>' +
        '');
}]);