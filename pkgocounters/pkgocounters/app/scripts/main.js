$(document).ready(function(){

	var isMobile = window.matchMedia("handheld").matches;

	var defenders = $('#table_defenders').DataTable({
		ajax: {
			url: 'json/defenders.json',
			dataSrc: ''
		},
		columns: [
		          {data: "n"},
		          {data: "f", "searchable": true, orderable: false},
		          {data: "s", "searchable": true, orderable: false},
		          {data: "won", "searchable": false, "orderSequence": [ "desc" ]},
		          {data: "a", visible: false},
		          {data: "e", visible: false},
		          {data: "cp"}
		          ],
		searchCols: [
		             null,
		             null,
		             null,
		             null,
		             {"search": "false"},
		             {"search": "0"},
		             null
		             ],
		          paging: false,
		          ordering: true,
		          order: [[3, 'desc']],
		          select: 'single',
		          processing: true
	});
//	defenders.column(4).search("Yes");
	//defenders.column(1).search("Zen");

	var attackers = $('#table_attackers').DataTable({
		ajax: {
			url: 'json/empty.json',
			dataSrc: ''
		},
		columns: [
		          {data: "r", orderable: false},
		          {data: "n", orderable: true},
		          {data: "f", "searchable": false, orderable: false},
		          {data: "s", "searchable": false, orderable: false},
		          {data: "hpleft", "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
		          {data: "hpleftp",  "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
		          {data: "time",  "searchable": false, orderable: true, "orderSequence": [ "asc" ]},
		          {data: "a", visible: false, orderable: false},
		          {data: "r", visible: false, orderable: false},
		          {data: "cp", visible: true},
		          ],
		  		searchCols: [
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             {"search": "false"},
				             {"search": "0"},
				             null
				             ],
		          paging: false,
		          "ordering": true,
		          processing: true,
		          order: [[5, 'desc']],
		          language: {
		        	  emptyTable: "Click on a row in the Defenders table to load their counters"
		          }

	});
//	attackers.column(7).search("Yes");

	$('div.dataTables_filter input').addClass('input-xs');

	defenders.on('select', function (e, dt, type, indexes ) {
		$('#table_attackers_filter').css("visibility", "visible")
		var row = defenders.row(indexes[0]).data();
		if (isMobile)
			defenders.columns([0, 1, 2]).data().search(row.defendername+" "+ row.defenderfastmove +" "+ row.defenderspecialmove).draw();
		attackers.clear().draw();
		var name = "json/"+ row.defendername +"_"+ row.defenderfastmove +"_"+ row.defenderspecialmove +".json";
		attackers.ajax.url(name).load(function() {attackers.columns.adjust().draw()});
		;
	});

	// ----------------------------------------------------

	var attackers2 = $('#table_attackers2').DataTable({
		ajax: {
			url: 'json/attackers/attackers.json',
			dataSrc: ''
		},
		columns: [
		          {data: "n"},
		          {data: "f", "searchable": true, orderable: false},
		          {data: "s", "searchable": true, orderable: false},
		          {data: "won", "searchable": false, "orderSequence": [ "desc" ]},
		          {data: "a", visible: false},
		          {data: "e", visible: false},
		          {data: "cp"}
		          ],
		  		searchCols: [
				             null,
				             null,
				             null,
				             null,
				             {"search": "false"},
				             {"search": "0"},
				             null
				             ],
		          paging: false,
		          ordering: true,
		          order: [[3, 'desc']],
		          select: 'single',
		          processing: true
	});
//	defenders.column(4).search("Yes");
	//defenders.column(1).search("Zen");

	var defenders2 = $('#table_defenders2').DataTable({
		ajax: {
			url: 'json/empty.json',
			dataSrc: ''
		},
		columns: [
		          {data: "r", orderable: true},
		          {data: "n", orderable: true},
		          {data: "f", "searchable": false, orderable: false},
		          {data: "s", "searchable": false, orderable: false},
		          {data: "hpleft", "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
		          {data: "hpleftp",  "searchable": false, orderable: true, "orderSequence": [ "desc" ]},
		          {data: "time",  "searchable": false, orderable: true, "orderSequence": [ "asc" ]},
		          {data: "a", visible: false, orderable: false},
		          {data: "e", visible: false, orderable: false},
		          {data: "cp"}
		          ],
		searchCols: [
		             null,
		             null,
		             null,
		             null,
		             null,
		             null,
		             null,
		             {"search": "false"},
		             {"search": "0"},
		             null
		             ],
		          paging: false,
		          "ordering": true,
		          processing: true,
		          order: [[5, 'desc']],
		          language: {
		        	  emptyTable: "Click on a row in the Attackers table to load data"
		          }

	});


	attackers2.on('select', function (e, dt, type, indexes ) {
		$('#table_attackers2_filter').css("visibility", "visible")
		var row = attackers2.row(indexes[0]).data();
		if (isMobile)
			attackers2.columns([0, 1, 2]).data().search(row.attackername+" "+ row.attackerfastmove +" "+ row.attackerspecialmove).draw();
		defenders2.clear().draw();
		var name = "json/attackers/"+ row.attackername +"_"+ row.attackerfastmove +"_"+ row.attackerspecialmove +".json";
		defenders2.ajax.url(name).load(function() {defenders2.columns.adjust().draw()});
		;
	});



	// ---------Attackers / Defenders
	$("#tabletype :input").change(function() {
		$("#defendersdiv").toggle();
		$("#attackersdiv").toggle();
		attackers.columns.adjust().draw();
		defenders.columns.adjust().draw()
		attackers2.columns.adjust().draw()
		defenders2.columns.adjust().draw()
	});

	// ---------Available
	$("#available :input").change(function(event) {
		if (event.target.id === "availableyes") {
			defenders.column(4).search("false").draw();
			attackers.column(7).search("false").draw();
			attackers2.column(4).search("false").draw();
			defenders2.column(7).search("false").draw();
		} else {
			defenders.column(4).search("").draw();
			attackers.column(7).search("").draw();
			attackers2.column(4).search("").draw();
			defenders2.column(7).search("").draw();
		}
	});

	$("#evolved :input").change(function(event) {
		if (event.target.id === "evolvedfully") {
			defenders.column(5).search("0").draw();
			attackers.column(8).search("0").draw();
			attackers2.column(5).search("0").draw();
			defenders2.column(8).search("0").draw();
		} else {
			defenders.column(5).search("").draw();
			attackers.column(8).search("").draw();
			attackers2.column(5).search("").draw();
			defenders2.column(8).search("").draw();
		}
	});

//	});
});