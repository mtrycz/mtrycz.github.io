'use strict';

/**
 * @ngdoc function
 * @name pkgocountersApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the pkgocountersApp
 */
angular.module('pkgocountersApp', ['datatables'])
  .controller('MainCtrl', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }).controller('WithAjaxCtrl', WithAjaxCtrl);

function WithAjaxCtrl(DTOptionsBuilder, DTColumnBuilder) {
    var vm = this;
    vm.dtOptions = DTOptionsBuilder.fromSource('json/defenders.json')
        .withPaginationType('full_numbers');
    vm.dtColumns = [
        DTColumnBuilder.newColumn('defendername').withTitle('defendername'),
        DTColumnBuilder.newColumn('defenderfastmove').withTitle('defenderfastmove'),
        DTColumnBuilder.newColumn('defenderspecialmove').withTitle('defenderspecialmove')
    ];
}