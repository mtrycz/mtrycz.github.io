"use strict";function WithAjaxCtrl(a,b){}angular.module("pkgocountersApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl",controllerAs:"about"}).otherwise({redirectTo:"/"})}]),angular.module("pkgocountersApp",["datatables"]).controller("MainCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}).controller("WithAjaxCtrl",WithAjaxCtrl),WithAjaxCtrl.$inject=["DTOptionsBuilder","DTColumnBuilder"],angular.module("pkgocountersApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),$(document).ready(function(){var a=$("#table_defenders").DataTable({ajax:{url:"json/defenders.json",dataSrc:""},columns:[{data:"defendername"},{data:"defenderfastmove",searchable:!0},{data:"defenderspecialmove",searchable:!0},{data:"won",searchable:!1},{data:"defenderavailable",visible:!1},{data:"defenderevolutions",visible:!1}],paging:!1,ordering:!1,select:"single",processing:!0});a.column(4).search("Yes"),attackers=$("#table_attackers").DataTable({ajax:{url:"json/empty.json",dataSrc:""},columns:[{data:"attackername",orderable:!1},{data:"attackerfastmove",searchable:!1,orderable:!1},{data:"attackerspecialmove",searchable:!1,orderable:!1},{data:"hpleft",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"hpleftpercent",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"timetozerohp",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"attackeravailable",visible:!1,orderable:!1},{data:"attackerevolutions",visible:!1,orderable:!1}],paging:!1,ordering:!0,processing:!0,order:[[4,"desc"]]}).column(6).search("Yes"),a.on("select",function(b,c,d,e){console.log(b);var f=a.row(e[0]).data();a.columns([0,1,2]).data().search(f.defendername+" "+f.defenderfastmove+" "+f.defenderspecialmove).draw(),attackers.clear().draw(),console.log(a.search());var g="json/"+f.defendername+"_"+f.defenderfastmove+"_"+f.defenderspecialmove+".json";console.log(g),attackers.ajax.url(g).load(function(){attackers.columns.adjust().draw()})})}),angular.module("pkgocountersApp").run(["$templateCache",function(a){a.put("views/about.html","<p>This is the about view.</p>"),a.put("views/main.html",'<div class="jumbotron"> <h1>\'Allo, \'Allo!</h1> <p class="lead"> <img src="images/yeoman.8cb970fb.png" alt="I\'m Yeoman"><br> Always a pleasure scaffolding your apps. </p> <p><a class="btn btn-lg btn-success" ng-href="#/">Splendid!<span class="glyphicon glyphicon-ok"></span></a></p> </div> <div class="row marketing"> <h4>HTML5 Boilerplate</h4> <p> HTML5 Boilerplate is a professional front-end template for building fast, robust, and adaptable web apps or sites. </p> <h4>Angular</h4> <p> AngularJS is a toolset for building the framework most suited to your application development. </p> <h4>Karma</h4> <p>Spectacular Test Runner for JavaScript.</p> </div>')}]);