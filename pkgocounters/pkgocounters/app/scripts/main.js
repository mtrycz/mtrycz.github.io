$(document).ready(function(){ 
  var defenders = $('#table_defenders').DataTable({
    ajax: {
      url: 'json/defenders.json',
      dataSrc: ''
    },
    columns: [
              {data: "defendername"},
              {data: "defenderfastmove", "searchable": true},
              {data: "defenderspecialmove", "searchable": true},
              {data: "won", "searchable": false},
              {data: "defenderavailable", visible: false},
              {data: "defenderevolutions", visible: false}
              ],
              paging: false,
              ordering: false,
              select: 'single',
              processing: true
  });

  defenders.column(4).search("Yes");
  //defenders.column(1).search("Zen");

  var attackers = $('#table_attackers').DataTable({
    ajax: {
      url: 'json/empty.json',
      dataSrc: ''
    },
    columns: [
              {data: "attackername"},
              {data: "attackerfastmove", "searchable": false},
              {data: "attackerspecialmove", "searchable": false},
              {data: "hpleft", "searchable": false},
              {data: "hpleftpercent",  "searchable": false},
              {data: "timetozerohp",  "searchable": false},
              {data: "attackeravailable", visible: false},
              {data: "attackerevolutions", visible: false}
              ],
              paging: false,
              "ordering": false,
              processing: true

  }).column(6).search("Yes");

  defenders.on('select', function (e, dt, type, indexes ) {
    console.log(e);
    var row = defenders.row(indexes[0]).data();
    defenders.columns([0, 1, 2]).data().search(row.defendername+" "+ row.defenderfastmove +" "+ row.defenderspecialmove).draw();
    attackers.clear().draw();
    console.log(defenders.search());
    var name = "json/"+ row.defendername +"_"+ row.defenderfastmove +"_"+ row.defenderspecialmove +".json";
    console.log(name);
    attackers.ajax.url(name).load();
  });
});