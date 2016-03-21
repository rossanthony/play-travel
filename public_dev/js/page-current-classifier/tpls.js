'use strict';

angular.module(
    "PlayTravelApp.page-current-classifier.tpls",
    [
        "template/reclassify-rule-row.html",
        "template/new-batch-item.html"
    ]
);


angular.module("template/reclassify-rule-row.html", []).run(["$templateCache", function($templateCache) {
    $templateCache.put("template/reclassify-rule-row.html",
        '<tbody>' +
        '<tr>' +
        '   <td class="text-center" ng-class="{ \'bg-info\':edit}">' +
        '       <md-checkbox ng-model="item.delete" ng-init="item.delete = false" aria-label="Delete item" ng-disabled="intraining"></md-checkbox>' +
        '   </td>' +
        '   <td ng-click="edit = !edit">{{item.input}}</td>' +
        '   <td ng-click="edit = !edit" class="text-center">' +
        '       <i class="fa text-lg" ng-class="{\'fa-close text-danger\': !item.included_in_last, \'fa-check text-success\': item.included_in_last}"></i>' +
        '   </td>' +
        '   <td ng-click="edit = !edit">{{item.topic.label}}</td>' +
        '   <td>' +
        '       <md-switch ng-model="item.included_in_next" aria-label="Staged" ng-disabled="intraining"></md-switch>' +
        '   </td>' +
        '   <td class="text-center">' +
        '       <a ng-disabled="intraining" ng-click=" (!intraining) ? deleteItem(): null" class="btn btn-xs btn-warning">' +
        '           <i class="fa fa-times"></i>' +
        '       </a>' +
        '   </td>' +
        '</tr>' +
        '<tr ng-show="edit && !intraining">' +
        '   <td class="bg-info">&nbsp;</td>' +
        '   <td colspan="5">' +
        '       <md-input-container class="md-block no-bottom-margin">' +
        '           <label>Edit text</label>' +
        '           <textarea ng-model="textCopy" columns="1" rows="5"></textarea>' +
        '           <span class="label label-warning" ng-show="textError">This is a required field</span>' +
        '       </md-input-container>' +
        '       <md-input-container class="md-block no-bottom-margin">' +
        '           <label>Should be classified as:</label>' +
        '           <div ui-select ng-model="newTopic.value">' +
        '               <div ui-select-match placeholder="Select...">{{$select.selected.label}}</div>' +
        '               <div ui-select-choices repeat="option in topics">' +
        '                   <div ng-bind-html="option.label | highlight: $select.search"></div>' +
        '               </div>' +
        '           </div>' +
        '           <span class="label label-warning" ng-show="topicError">This is a required field</span>' +
        '       </md-input-container>' +
        '       <button class="btn btn-default btn-sm" ng-click="submitForm()"><i class="fa fa-floppy-o"></i> Save</button>' +
        '   </td>' +
        '</tr>' +
        '</tbody>' +
        '');
}]);

angular.module("template/new-batch-item.html", []).run(["$templateCache", function($templateCache) {
    $templateCache.put("template/new-batch-item.html",
        '   <div class="modal-header">' +
        '       <h4 class="modal-title">Add batch item</h4>' +
        '   </div>' +
        '   <div class="modal-body">' +
        '       <md-input-container class="md-block">' +
        '           <label>Add text</label>' +
        '           <textarea ng-model="text" columns="1" rows="5"></textarea>' +
        '           <span class="label label-warning" ng-show="textError">This is a required field</span>' +
        '       </md-input-container>' +
        '       <md-input-container class="md-block">' +
        '           <label>Should be classified as:</label>' +
        '           <div ui-select ng-model="topic.value">' +
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