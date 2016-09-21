$(document).ready(function(){ 
  
  var isMobile = window.matchMedia("handheld").matches;
  console.log(isMobile);
  
  var defenders = $('#table_defenders').DataTable({
    ajax: {
      url: 'json/defenders.json',
      dataSrc: ''
    },
    columns: [
              {data: "defendername"},
              {data: "defenderfastmove", "searchable": true, orderable: false},
              {data: "defenderspecialmove", "searchable": true, orderable: false},
              {data: "won", "searchable": false, "orderSequence": [ "desc" ]},
              {data: "defenderavailable", visible: false},
              {data: "defenderevolutions", visible: false}
              ],
              paging: false,
              ordering: true,
              order: [[3, 'desc']],
              select: 'single',
              processing: true
  });
//  defenders.column(4).search("Yes");
  //defenders.column(1).search("Zen");

  var attackers = $('#table_attackers').DataTable({
    ajax: {
      url: 'json/empty.json',
      dataSrc: ''
    },
    columns: [
              {data: "rank", orderable: false},
              {data: "attackername", orderable: true},
              {data: "attackerfastmove", "searchable": false, orderable: false},
              {data: "attackerspecialmove", "searchable": false, orderable: false},
              {data: "hpleft", "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
              {data: "hpleftpercent",  "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
              {data: "timetozerohp",  "searchable": false, orderable: true, "orderSequence": [ "asc" ]},
              {data: "attackeravailable", visible: false, orderable: false},
              {data: "attackerevolutions", visible: false, orderable: false}
              ],
              paging: false,
              "ordering": true,
              processing: true,
              order: [[5, 'desc']],
              language: {
                emptyTable: "Click on a row in the Defenders table to load their counters"
              }

  });
//  attackers.column(7).search("Yes");

  $('div.dataTables_filter input').addClass('input-xs');
  
  defenders.on('select', function (e, dt, type, indexes ) {
    $('#table_attackers_filter').css("visibility", "visible")
    console.log(e);
    var row = defenders.row(indexes[0]).data();
    if (isMobile)
      defenders.columns([0, 1, 2]).data().search(row.defendername+" "+ row.defenderfastmove +" "+ row.defenderspecialmove).draw();
    attackers.clear().draw();
    console.log(defenders.search());
    var name = "json/"+ row.defendername +"_"+ row.defenderfastmove +"_"+ row.defenderspecialmove +".json";
    console.log(name);
    attackers.ajax.url(name).load(function() {attackers.columns.adjust().draw()});
    ;
  });
});