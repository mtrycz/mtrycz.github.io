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
		          {data: "cp"},
		          {data: "a", visible: false},
		          {data: "e", visible: false}
		          ],
		searchCols: [
		             null,
		             null,
		             null,
		             null,
		             null,
		             {"search": "false"},
		             {"search": "0"}
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
		          {data: "cp"},
		          {data: "pr"},
		          {data: "a", visible: false, orderable: false},
		          {data: "e", visible: false, orderable: false}
		          ],
		  		searchCols: [
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             {"search": "false"},
				             {"search": "0"}
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
			defenders.columns([0, 1, 2]).data().search(row.n+" "+ row.f +" "+ row.s).draw();
		attackers.clear().draw();
		var name = "json/"+ row.n +"_"+ row.f +"_"+ row.s +".json";
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
		          {data: "cp"},
		          {data: "pr"},
		          {data: "a", visible: false},
		          {data: "e", visible: false}
		          ],
		  		searchCols: [
				             null,
				             null,
				             null,
				             null,
				             null,
				             null,
				             {"search": "false"},
				             {"search": "0"}
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
		          {data: "cp", orderable: true},
		          {data: "a", visible: false, orderable: false},
		          {data: "e", visible: false, orderable: false}
		          ],
		searchCols: [
		             null,
		             null,
		             null,
		             null,
		             null,
		             null,
		             null,
		             null,
		             {"search": "false"},
		             {"search": "0"}
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
			attackers2.columns([0, 1, 2]).data().search(row.n+" "+ row.f +" "+ row.s).draw();
		defenders2.clear().draw();
		var name = "json/attackers/"+ row.n +"_"+ row.f +"_"+ row.s +".json";
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