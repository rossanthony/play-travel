'use strict';

angular.module(
    "PlayTravelApp.tpls",
    [
        "template/confirm-delete.html"
    ]
);


angular.module("template/confirm-delete.html", []).run(["$templateCache", function($templateCache) {
    $templateCache.put("template/confirm-delete.html",
        '   <div class="modal-header">' +
        '       <h5 class="modal-title">{{(message != \'\' ? message : \'Please confirm you wish to delete\' )}}</h5>' +
        '   </div>' +
        '   <div class="modal-body">' +
        '       <div class="text-right">' +
        '           <div class="btn-group" role="group" aria-label="...">' +
        '               <button type="button" ng-click="ok()" class="btn btn-sm btn-default"><span class="fa fa-paper-plane-o"></span> Proceed</button>' +
        '               <span class="btn btn-default btn-sm" ng-click="cancel()"><span class="fa fa-close"></span>&nbsp;&nbsp;Close (Esc)</span>' +
        '           </div>' +
        '       </div>' +
        '   </div>' +
        '');
}]);