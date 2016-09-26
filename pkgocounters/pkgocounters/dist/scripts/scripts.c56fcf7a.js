"use strict";function WithAjaxCtrl(a,b){}angular.module("pkgocountersApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl",controllerAs:"about"}).otherwise({redirectTo:"/"})}]),angular.module("pkgocountersApp",["datatables"]).controller("MainCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}).controller("WithAjaxCtrl",WithAjaxCtrl),WithAjaxCtrl.$inject=["DTOptionsBuilder","DTColumnBuilder"],angular.module("pkgocountersApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),$(document).ready(function(){var a=window.matchMedia("handheld").matches;console.log(a);var b=$("#table_defenders").DataTable({ajax:{url:"json/defenders.json",dataSrc:""},columns:[{data:"defendername"},{data:"defenderfastmove",searchable:!0,orderable:!1},{data:"defenderspecialmove",searchable:!0,orderable:!1},{data:"won",searchable:!1,orderSequence:["desc"]},{data:"defenderavailable",visible:!1},{data:"defenderevolutions",visible:!1}],paging:!1,ordering:!0,order:[[3,"desc"]],select:"single",processing:!0}),c=$("#table_attackers").DataTable({ajax:{url:"json/empty.json",dataSrc:""},columns:[{data:"rank",orderable:!1},{data:"attackername",orderable:!0},{data:"attackerfastmove",searchable:!1,orderable:!1},{data:"attackerspecialmove",searchable:!1,orderable:!1},{data:"hpleft",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"hpleftpercent",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"timetozerohp",searchable:!1,orderable:!0,orderSequence:["asc"]},{data:"attackeravailable",visible:!1,orderable:!1},{data:"attackerevolutions",visible:!1,orderable:!1}],paging:!1,ordering:!0,processing:!0,order:[[5,"desc"]],language:{emptyTable:"Click on a row in the Defenders table to load their counters"}});$("div.dataTables_filter input").addClass("input-xs"),b.on("select",function(d,e,f,g){$("#table_attackers_filter").css("visibility","visible"),console.log(d);var h=b.row(g[0]).data();a&&b.columns([0,1,2]).data().search(h.defendername+" "+h.defenderfastmove+" "+h.defenderspecialmove).draw(),c.clear().draw(),console.log(b.search());var i="json/"+h.defendername+"_"+h.defenderfastmove+"_"+h.defenderspecialmove+".json";console.log(i),c.ajax.url(i).load(function(){c.columns.adjust().draw()})});var d=$("#table_attackers2").DataTable({ajax:{url:"json/attackers/attackers.json",dataSrc:""},columns:[{data:"attackername"},{data:"attackerfastmove",searchable:!0,orderable:!1},{data:"attackerspecialmove",searchable:!0,orderable:!1},{data:"won",searchable:!1,orderSequence:["desc"]},{data:"attackeravailable",visible:!1},{data:"attackerevolutions",visible:!1}],paging:!1,ordering:!0,order:[[3,"desc"]],select:"single",processing:!0}),e=$("#table_defenders2").DataTable({ajax:{url:"json/empty.json",dataSrc:""},columns:[{data:"rank",orderable:!1},{data:"defendername",orderable:!0},{data:"defenderfastmove",searchable:!1,orderable:!1},{data:"defenderspecialmove",searchable:!1,orderable:!1},{data:"hpleft",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"hpleftpercent",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"timetozerohp",searchable:!1,orderable:!0,orderSequence:["asc"]},{data:"defenderavailable",visible:!1,orderable:!1},{data:"defenderevolutions",visible:!1,orderable:!1}],paging:!1,ordering:!0,processing:!0,order:[[5,"desc"]],language:{emptyTable:"Click on a row in the Attackers table to load data"}});d.on("select",function(b,c,f,g){$("#table_attackers2_filter").css("visibility","visible"),console.log(b);var h=d.row(g[0]).data();a&&d.columns([0,1,2]).data().search(h.attackername+" "+h.attackerfastmove+" "+h.attackerspecialmove).draw(),e.clear().draw(),console.log(d.search());var i="json/attackers/"+h.attackername+"_"+h.attackerfastmove+"_"+h.attackerspecialmove+".json";console.log(i),e.ajax.url(i).load(function(){e.columns.adjust().draw()})}),$("#tabletype :input").change(function(){$("#defendersdiv").toggle(),$("#attackersdiv").toggle(),c.columns.adjust().draw(),b.columns.adjust().draw(),d.columns.adjust().draw(),e.columns.adjust().draw()})}),angular.module("pkgocountersApp").run(["$templateCache",function(a){a.put("views/about.html","<p>This is the about view.</p>"),a.put("views/main.html",'<div class="jumbotron"> <h1>\'Allo, \'Allo!</h1> <p class="lead"> <img src="images/yeoman.42092f92.png" alt="I\'m Yeoman"><br> Always a pleasure scaffolding your apps. </p> <p><a class="btn btn-lg btn-success" ng-href="#/">Splendid!<span class="glyphicon glyphicon-ok"></span></a></p> </div> <div class="row marketing"> <h4>HTML5 Boilerplate</h4> <p> HTML5 Boilerplate is a professional front-end template for building fast, robust, and adaptable web apps or sites. </p> <h4>Angular</h4> <p> AngularJS is a toolset for building the framework most suited to your application development. </p> <h4>Karma</h4> <p>Spectacular Test Runner for JavaScript.</p> </div>')}]);