"use strict";function WithAjaxCtrl(a,b){}angular.module("pkgocountersApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl",controllerAs:"about"}).otherwise({redirectTo:"/"})}]),angular.module("pkgocountersApp",["datatables"]).controller("MainCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}).controller("WithAjaxCtrl",WithAjaxCtrl),WithAjaxCtrl.$inject=["DTOptionsBuilder","DTColumnBuilder"],angular.module("pkgocountersApp").controller("AboutCtrl",function(){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}),$(document).ready(function(){var a=window.matchMedia("handheld").matches,b=$("#table_defenders").DataTable({ajax:{url:"json/defenders.json",dataSrc:""},columns:[{data:"defendername"},{data:"defenderfastmove",searchable:!0,orderable:!1},{data:"defenderspecialmove",searchable:!0,orderable:!1},{data:"won",searchable:!1,orderSequence:["desc"]},{data:"defenderavailable",visible:!1},{data:"defenderevolutions",visible:!1}],searchCols:[null,null,null,null,{search:"false"},{search:"0"}],paging:!1,ordering:!0,order:[[3,"desc"]],select:"single",processing:!0}),c=$("#table_attackers").DataTable({ajax:{url:"json/empty.json",dataSrc:""},columns:[{data:"rank",orderable:!1},{data:"attackername",orderable:!0},{data:"attackerfastmove",searchable:!1,orderable:!1},{data:"attackerspecialmove",searchable:!1,orderable:!1},{data:"hpleft",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"hpleftpercent",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"timetozerohp",searchable:!1,orderable:!0,orderSequence:["asc"]},{data:"attackeravailable",visible:!1,orderable:!1},{data:"attackerevolutions",visible:!1,orderable:!1}],searchCols:[null,null,null,null,null,null,null,{search:"false"},{search:"0"}],paging:!1,ordering:!0,processing:!0,order:[[5,"desc"]],language:{emptyTable:"Click on a row in the Defenders table to load their counters"}});$("div.dataTables_filter input").addClass("input-xs"),b.on("select",function(d,e,f,g){$("#table_attackers_filter").css("visibility","visible");var h=b.row(g[0]).data();a&&b.columns([0,1,2]).data().search(h.defendername+" "+h.defenderfastmove+" "+h.defenderspecialmove).draw(),c.clear().draw();var i="json/"+h.defendername+"_"+h.defenderfastmove+"_"+h.defenderspecialmove+".json";c.ajax.url(i).load(function(){c.columns.adjust().draw()})});var d=$("#table_attackers2").DataTable({ajax:{url:"json/attackers/attackers.json",dataSrc:""},columns:[{data:"attackername"},{data:"attackerfastmove",searchable:!0,orderable:!1},{data:"attackerspecialmove",searchable:!0,orderable:!1},{data:"won",searchable:!1,orderSequence:["desc"]},{data:"attackeravailable",visible:!1},{data:"attackerevolutions",visible:!1}],searchCols:[null,null,null,null,{search:"false"},{search:"0"}],paging:!1,ordering:!0,order:[[3,"desc"]],select:"single",processing:!0});defenders2=$("#table_defenders2").DataTable({ajax:{url:"json/empty.json",dataSrc:""},columns:[{data:"rank",orderable:!0},{data:"defendername",orderable:!0},{data:"defenderfastmove",searchable:!1,orderable:!1},{data:"defenderspecialmove",searchable:!1,orderable:!1},{data:"hpleft",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"hpleftpercent",searchable:!1,orderable:!0,orderSequence:["desc"]},{data:"timetozerohp",searchable:!1,orderable:!0,orderSequence:["asc"]},{data:"defenderavailable",visible:!1,orderable:!1},{data:"defenderevolutions",visible:!1,orderable:!1}],searchCols:[null,null,null,null,null,null,null,{search:"false"},{search:"0"}],paging:!1,ordering:!0,processing:!0,order:[[5,"desc"]],language:{emptyTable:"Click on a row in the Attackers table to load data"}}),d.on("select",function(b,c,e,f){$("#table_attackers2_filter").css("visibility","visible");var g=d.row(f[0]).data();a&&d.columns([0,1,2]).data().search(g.attackername+" "+g.attackerfastmove+" "+g.attackerspecialmove).draw(),defenders2.clear().draw();var h="json/attackers/"+g.attackername+"_"+g.attackerfastmove+"_"+g.attackerspecialmove+".json";defenders2.ajax.url(h).load(function(){defenders2.columns.adjust().draw()})}),$("#tabletype :input").change(function(){$("#defendersdiv").toggle(),$("#attackersdiv").toggle(),c.columns.adjust().draw(),b.columns.adjust().draw(),d.columns.adjust().draw(),defenders2.columns.adjust().draw()}),$("#available :input").change(function(a){"availableyes"===a.target.id?(b.column(4).search("false").draw(),c.column(7).search("false").draw(),d.column(4).search("false").draw(),defenders2.column(7).search("false").draw()):(b.column(4).search("").draw(),c.column(7).search("").draw(),d.column(4).search("").draw(),defenders2.column(7).search("").draw())}),$("#evolved :input").change(function(a){"evolvedfully"===a.target.id?(b.column(5).search("0").draw(),c.column(8).search("0").draw(),d.column(5).search("0").draw(),defenders2.column(8).search("0").draw()):(b.column(5).search("").draw(),c.column(8).search("").draw(),d.column(5).search("").draw(),defenders2.column(8).search("").draw())})}),angular.module("pkgocountersApp").run(["$templateCache",function(a){a.put("views/about.html","<p>This is the about view.</p>"),a.put("views/main.html",'<div class="jumbotron"> <h1>\'Allo, \'Allo!</h1> <p class="lead"> <img src="images/yeoman.42092f92.png" alt="I\'m Yeoman"><br> Always a pleasure scaffolding your apps. </p> <p><a class="btn btn-lg btn-success" ng-href="#/">Splendid!<span class="glyphicon glyphicon-ok"></span></a></p> </div> <div class="row marketing"> <h4>HTML5 Boilerplate</h4> <p> HTML5 Boilerplate is a professional front-end template for building fast, robust, and adaptable web apps or sites. </p> <h4>Angular</h4> <p> AngularJS is a toolset for building the framework most suited to your application development. </p> <h4>Karma</h4> <p>Spectacular Test Runner for JavaScript.</p> </div>')}]);