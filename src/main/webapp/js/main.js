$(document).ready(function() {
	$('nav li:eq(0) i').trigger('onclick');
	loadPrograms();
	loadRules();
	loadEmployees();
	loadStations();
	loadSettings();
});

function goToSection(elem, sectionId) {
	jQuery('nav li').removeClass('tab-current');
	jQuery('section').removeClass('content-current');
	jQuery(elem).parent('li').addClass('tab-current');
	jQuery('#' + sectionId).addClass('content-current');
}

function deleteEmployee(employeeId) {
	$.ajax({
		url: "employee?employeeId=" + employeeId,
		method: "DELETE"
	}).always(function() {
		loadEmployees();
	});
}

function saveEmployee(saveButton) {
	var jContent = $(saveButton).parent('.card-buttons').next('.card-content');
	var markers = {
		id: jContent.find('input[name="id-input"]').val(),
		name: jContent.find('input[name="name-input"]').val(),
		note: jContent.find('textarea[name="note-input"]').val(),
		type: jContent.find('[name="type-input"]:checked').val(),
		stationIds: jContent.find('[name="station-input"]').val()
	};
	$.ajax({
		url: "employee",
		method: "POST",
		dataType: "jsonp",
        contentType: "application/json",
		data: JSON.stringify(markers)
	}).always(function() {
		loadEmployees();
	});
}

function editEmployee(editButton) {
	var jContent = $(editButton).parent('.card-buttons').next('.card-content');
	var blackChecked = jContent.find('[name="type-input"]').val() == 'black' ? 'checked="checked"' : '';
	var blueChecked = jContent.find('[name="type-input"]').val() == 'blue' ? 'checked="checked"' : '';
	var greenChecked = jContent.find('[name="type-input"]').val() == 'green' ? 'checked="checked"' : '';
	var redChecked = jContent.find('[name="type-input"]').val() == 'red' ? 'checked="checked"' : '';
	var employeeId = jContent.find('input[name="id-input"]').val();
	var cardContent = 
		'<div class="card">' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-save" onClick="saveEmployee(this)"></i>' +
		'  </div>' +
		'  <div class="card-content">' +
		'	<form id="edit-employee_' + employeeId + '">' +
		'		<input type="hidden" name="id-input" value="' + employeeId + '" />' +
		'		<input type="text" name="name-input" placeholder="Name" value="' + jContent.find('.name-display').html() + '" />' +
		'		<select name="station-input" multiple="multiple" value="' + jContent.find('[name="station-input"]').val() + '" /> ' +
		'		<textarea name="note-input" placeholder="Notes">' + jContent.find('input[name="note-input"]').val() + '</textarea>' +
		'		<div class="colors-select">' +
		'			<input type="radio" name="type-input" class="color black" value="black" ' + blackChecked + '>' +
		'			<input type="radio" name="type-input" class="color blue" value="blue" ' + blueChecked + '>' +
		'			<input type="radio" name="type-input" class="color green" value="green" ' + greenChecked + '>' +
		'			<input type="radio" name="type-input" class="color red" value="red" ' + redChecked + '>' +
		'		</div>' +
		'	</form>' +
		'  </div>' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-trash-o" onClick="deleteEmployee(' + jContent.find('input[name="id-input"]').val() + ');"></i>' +
		'  </div>' +
		'</div>';
	$(editButton).parents('.card').replaceWith(cardContent);
	initializeStationInput($('#edit-employee_' + employeeId + ' [name="station-input"]'));
}

function loadEmployees() {
	$.ajax({
		method: "GET",
		url: "employee"
	}).done(function(response) {
		$('#section-3 .cards-list-container .card').remove();
		$(response).each(function(index, element) {
			var cardContent = 
				'<div class="card ' + element.type + '">' +
				'  <div class="card-buttons">' +
				'  	<i class="fa fa-edit" onClick="editEmployee(this)"></i>' +
				'  </div>' +
				'  <div class="card-content">' +
				'	 <input type="hidden" name="id-input" value="' + element.id + '" /> ' +
				'	 <input type="hidden" name="type-input" value="' + element.type + '" /> ' +
				'	 <input type="hidden" name="note-input" value="' + element.note + '" /> ' +
				'	 <input type="hidden" name="station-input" value="' + element.stationIds + '" /> ' +
				'	 <div class="name-display">' + element.name + '</div>' +
				'	 <div class="station-display">Station(s): ' + element.stationNames + '</div>' +
				'    <div class="note-display">' + getString(element.note, '(missing note)').replace('\n', '<br/>') + '</div>' +
				'  </div>' +
				'</div>';
			$('#section-3 .cards-list-container').append(cardContent);
		});
		var cardContent = 
			'<div class="card">' +
			'  <div class="card-buttons">' +
			'	<i class="fa fa-save" onClick="saveEmployee(this)"></i>' +
			'  </div>' +
			'  <div class="card-content">' +
			'	<form id="new-employee">' +
			'		<input type="text" name="name-input" placeholder="Name" />' +
			'		<select name="station-input" multiple="multiple" />' +
			'		<textarea name="note-input" placeholder="Notes"></textarea>' +
			'		<div class="colors-select">' +
			'			<input type="radio" name="type-input" class="color black" value="black" checked="checked">' +
			'			<input type="radio" name="type-input" class="color blue" value="blue">' +
			'			<input type="radio" name="type-input" class="color green" value="green">' +
			'			<input type="radio" name="type-input" class="color red" value="red">' +
			'		</div>' +
			'	</form>' +
			' </div>' +
			'</div>';
		$('#section-3 .cards-list-container').append(cardContent);
		
		initializeStationInput($('#new-employee [name="station-input"]'));
	});
}

function initializeStationInput(jInput, filterDateWithoutProgram) {
	var url = "station?p=v";
	if (filterDateWithoutProgram != undefined && filterDateWithoutProgram != null) {
		url += "&filterDateWithoutProgram=" + filterDateWithoutProgram;
	}
	
	$.ajax({
		method: "GET",
		url: url
	}).done(function(response) {
		results = $.map(response, function(station) {
			return {
			  "id": station.id,
			  "text": station.name
			};
		});
		
		jInput.select2({
			placeholder: "Station",
			data: results
		});
		
		designSelect2Input(jInput);
	});
}

function initializeEmployeeInput(jInput, filterStationId, filterDate) {
	var url = "employee?p=v";
	if (filterStationId != undefined && filterStationId != null) {
		url += "&filterStationId=" + filterStationId;
	}
	if (filterDate != undefined && filterDate != null) {
		url += "&filterDate=" + filterDate;
	}
	
	$.ajax({
		method: "GET",
		url: url
	}).done(function(response) {
		results = $.map(response, function(employee) {
			return {
			  "id": employee.id,
			  "text": employee.name
			};
		});
		
		jInput.select2({
			placeholder: "Employee",
			data: results
		});
		
		designSelect2Input(jInput);
	});
}

function designSelect2Input(jInput) {
	jInput.next('.select2').css('width', '');
	jInput.next('.select2').find('.select2-search__field').css('width', '');
	if (jInput.attr('value') != undefined) {
		var stationIds = $.map(jInput.attr('value').split(','), function(val, i) {
			return parseInt(val);
		});
		jInput.select2('val', [stationIds]);
	}
	
}

function loadSettings() {
	$.ajax({
		method: "GET",
		url: "settings"
	}).done(function(response) {
	    $(response.exportColumns).each(function() {
	    	$('#section-5 [name="export-columns"][value="' + this + '"]').attr('checked', 'checked');
	    });
	});
}

function saveSettings() {
	var exportColumns = [];
    $('#section-5 [name="export-columns"]:checked').each(function() {
    	exportColumns.push($(this).val());
    });
    
	var markers = {
		newPassword: $('#section-5 #new-password').val(),
		deleteOlder: $('#section-5 #delete-older').val(),
		exportColumns: exportColumns
	};
	$.ajax({
		url: "settings",
		method: "POST",
		dataType: "jsonp",
        contentType: "application/json",
		data: JSON.stringify(markers)
	}).always(function() {
		location.reload();
	});
}

function loadStations() {
	$.ajax({
		method: "GET",
		url: "station"
	}).done(function(response) {
		$('#section-4 .cards-list-container .card').remove();
		$(response).each(function(index, element) {
			var cardContent = 
				'<div class="card ' + element.type + '">' +
				'  <div class="card-buttons">' +
				'  	<i class="fa fa-edit" onClick="editStation(this)"></i>' +
				'  </div>' +
				'  <div class="card-content">' +
				'	 <input type="hidden" name="id-input" value="' + element.id + '" /> ' +
				'	 <input type="hidden" name="capacity-input" value="' + element.capacity + '" /> ' +
				'	 <div class="name-display">' + element.name + '</div>' +
				'    <div class="capacity-display">Capacity: ' + element.capacity + ' eployees</div>' +
				'  </div>' +
				'</div>';
			$('#section-4 .cards-list-container').append(cardContent);
		});
		var cardContent = 
			'<div class="card">' +
			'  <div class="card-buttons">' +
			'	<i class="fa fa-save" onClick="saveStation(this)"></i>' +
			'  </div>' +
			'  <div class="card-content">' +
			'	<form id="new-station">' +
			'		<input type="text" name="name-input" placeholder="Name" />' +
			'		<input type="number" name="capacity-input" min="1" max="10" placeholder="Capacity" /> ' +
			'	</form>' +
			' </div>' +
			'</div>';
		$('#section-4 .cards-list-container').append(cardContent);
	});
}

function saveStation(saveButton) {
	var jContent = $(saveButton).parent('.card-buttons').next('.card-content');
	var markers = {
		id: jContent.find('input[name="id-input"]').val(),
		name: jContent.find('input[name="name-input"]').val(),
		capacity: jContent.find('input[name="capacity-input"]').val()
	};
	$.ajax({
		url: "station",
		method: "POST",
		dataType: "jsonp",
        contentType: "application/json",
		data: JSON.stringify(markers)
	}).always(function() {
		loadStations();
	});
}

function editStation(editButton) {
	var jContent = $(editButton).parent('.card-buttons').next('.card-content');
	var cardContent = 
		'<div class="card">' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-save" onClick="saveStation(this)"></i>' +
		'  </div>' +
		'  <div class="card-content' +
		'	<form id="edit-station">' +
		'		<input type="hidden" name="id-input" value="' + jContent.find('input[name="id-input"]').val() + '" />' +
		'		<input type="text" name="name-input" placeholder="Name" value="' + jContent.find('.name-display').html() + '" />' +
		'		<input type="number" name="capacity-input" placeholder="Capacity" value="' + jContent.find('input[name="capacity-input"]').val() + '" />' +
		'	</form>' +
		'  </div>' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-trash-o" onClick="deleteStation(' + jContent.find('input[name="id-input"]').val() + ');"></i>' +
		'  </div>' +
		'</div>';
	$(editButton).parents('.card').replaceWith(cardContent);
}

function deleteStation(stationId) {
	$.ajax({
		url: "station?stationId=" + stationId,
		method: "DELETE"
	}).always(function() {
		loadStations();
	});
}

function loadPrograms() {
	$('#day-programs').hide();
	$('#calendar-programs').show();
	
	if ($('#calendar-programs .calendar-wrapper').length == 0) {
		$('#calendar-programs').dcalendar({format: 'yyyy-mm-dd'}).on('dateselected', function (e) {
			loadProgramsForDay(e.date);				
		});
	}
}

function loadProgramsForDay(date) {
	var dateInput = formatDate(date);
	var dateDisplay = displayDate(date);

	$('#day-programs .card').remove();
	$('#day-programs .day-program').remove();
	$('#calendar-programs').hide();
	$('#day-programs').show();
	$('#day-programs .calendar-curr-month').html(dateDisplay);
	$('#day-programs #calendar-curr-date').val(dateInput);

	$.ajax({
		url: "programDay?dayOfProgram=" + dateInput,
		method: "GET"
	}).done(function(programs) {
		$(programs).each(function(index, element) {
			var employeesList = '';
			$(element.employees).each(function(index, element) {
				employeesList += 
					' <li class="' + element.type + '">' + 
			        ' 		<input type="hidden" name="employeeId" value="' + element.id + '"/> ' +
							element.name + 
					'</li>';
			});
			
			var programContent = 
				'<div class="card day-program">' +
				' 	<div class="card-buttons">' +
				'		<i class="fa fa-edit" onclick="editProgramManually(this)"></i>' +
				'	</div>' +
				'	<div class="card-content">' +
		        '		<div class="station">' + 
		        '			<input type="hidden" name="stationId" value="' + element.stationId + '"/>' +
		        			element.stationName + 
    			'		</div>' +
				'		<ol class="employees">' +
				employeesList +
				'		</ol>' +
				'	</div>' +
				'</div>';
			$('#day-programs').append(programContent);
		});
		
		$.ajax({
			method: "GET",
			url: "stationCount"
		}).done(function(stationsCount) {
			if (stationsCount == programs.length) {
				return;
			}
			
			var programContent = 
				'<div class="card">' + 
				'	<div class="card-buttons">' +	
				'		<i class="fa fa-save" onclick="programSaveStation(this)"></i>' +  
				'	</div>' +
				'	<div class="card-content">' +	
				' 	<form id="new-program">' +
				'		<input name="stationId" onchange="programEditStationChanged(this)" /> ' +
				'	</form>' +
				'</div>';
			$('#day-programs').append(programContent);
			initializeStationInput($('#new-program [name="stationId"]'), $('#day-programs #calendar-curr-date').val());
		});
	});
}

function editProgramManually(editButton) {
	var jEditButton = $(editButton);
	var jContent = jEditButton.parents('.day-program').find('.card-content');
	var date = $('#day-programs #calendar-curr-date').val();
	var stationId = jContent.find('input[name="stationId"]').val();
	jContent.append('<form id="new-program"></form>');
	jContent.find('ol.employees li').each(function() {
		jContent.find('#new-program').append('<input name="employee" value="' + $(this).find('[name="employeeId"]').val() + '" />');
	}).remove();

	initializeEmployeeInput(jContent.find('#new-program [name="employee"]'), stationId, date);
	jEditButton.after('<br/><br/><i class="fa fa-undo" onclick="loadProgramsForDay(\'' + date + '\')"></i>');
	jEditButton.replaceWith('<i class="fa fa-save" onclick="programSaveStation(this)"></i>');
	jContent.after('<div class="card-buttons"><i class="fa fa-trash-o" onclick="programDeleteStation(this)"></i></div>');
}

function programDeleteStation(deleteButton) {
	var stationId = jQuery(deleteButton).parents('.card').find('[name="stationId"]').val();
	var date = $('#day-programs #calendar-curr-date').val();

	$.ajax({
		url: "programDay?stationId=" + stationId + '&dayOfProgram=' + date,
		method: "DELETE"
	}).always(function() {
		loadProgramsForDay(date);
	});
}

function programEditStationChanged(select) {
	var jContent = $(select).parents('.card-content');
	jContent.find('#new-program [name="employee"]').next('.select2-container').remove();
	jContent.find('#new-program [name="employee"]').remove();
	var stationId = $(select).val();
	
	$.ajax({
		async: false,
		method: "GET",
		url: "stationCapacity?stationId=" + stationId
	}).done(function(stationsCount) {
		if (stationsCount == null) {
			return;
		}
		
		for (var i=0; i<stationsCount; i++) {
			jContent.find('#new-program').append('<input name="employee" />');
		}
	});
	
	initializeEmployeeInput(jContent.find('#new-program [name="employee"]'), stationId, $('#day-programs #calendar-curr-date').val());
}

function programSaveStation(saveButton) {
	var jContent = $(saveButton).parents('.card');
	var date = $('#day-programs #calendar-curr-date').val();
	var employees = [];
	jContent.find('input[name="employee"]').each(function() {
		employees.push({id:$(this).val()});
	});
	
	var markers = {
		stationId: jContent.find('input[name="stationId"]').val(),
		date: date,
		employees: employees
	};
	
	$.ajax({
		url: "programManually",
		method: "POST",
		dataType: "jsonp",
        contentType: "application/json",
		data: JSON.stringify(markers),
	}).always(function(response) {
		if (response.status != 200) {
			alert(response.responseText);
		}else{
			loadProgramsForDay(date);
		}
	});
}

function navigateDayPrograms(direction) {
	var date = new Date($('#day-programs #calendar-curr-date').val());
	date.setDate(date.getDate() + direction);
	loadProgramsForDay(date);
}

function generatePrograms() {
	if ($('#calendar-programs').is(":visible")) {
		var date = $('#calendar-programs .calendar-curr-month').html();
		$.ajax({
			url: "programMonth?dayOfProgram=" + getDateFromDisplay(date),
			method: "PUT",
            error: generateProgramsErrorHandler,
            beforeSend: generateProgramsErrorBeforeSend,
            complete: generateProgramsErrorComplete
		});
	}
	else if ($('#day-programs').is(":visible")) {
		var date = $('#day-programs #calendar-curr-date').val();
		$.ajax({
			url: "programDay?dayOfProgram=" + date,
			method: "PUT",
            error: generateProgramsErrorHandler,
            beforeSend: generateProgramsErrorBeforeSend,
            complete: generateProgramsErrorComplete
		}).done(function() {
			navigateDayPrograms(0);
		});
	}
}

function exportPrograms() {
	var date = null;
	if ($('#calendar-programs').is(":visible")) {
		date = $('#calendar-programs .calendar-curr-month').html();
	}
	else if ($('#day-programs').is(":visible")) {
		date = $('#day-programs .calendar-curr-month').html();
		date = date.split(' ')[1] + ' ' + date.split(' ')[2];
	}
	saveFile('generateProgramMonth?dayOfProgram=' + getDateFromDisplay(date));
}

function saveFile(url) {
	var xhr = new XMLHttpRequest();
	xhr.responseType = 'blob';
	xhr.onload = function() {
	    var a = document.createElement('a');
		a.href = window.URL.createObjectURL(xhr.response);
		a.download = 'plan_' + new Date().getTime() + '.docx';
		a.style.display = 'none';
	    document.body.appendChild(a);
	    a.click();
	    delete a;
	};
	xhr.open('GET', url);
	xhr.send();
}

function generateProgramsErrorHandler(request) {
	if (request.status = 451) {
		alert('Incompatible rules! Try again!');
	}
	else {
		alert('General error! Try again!');
	}
}

function generateProgramsErrorBeforeSend() {
	$('body').append('<div class="loading"></div>');
}

function generateProgramsErrorComplete() {
	$('.loading').remove();
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

var months = ['Ianuarie','Februarie','Martie','Aprile','Mai','Iunie','Iulie','August','Septemberbie','Octoberbrie','Noiembrie','Decembrie'];
function displayDate(date) {
	date = new Date(date);
	var year = date.getFullYear();
	var month = months[parseInt(date.getMonth())];
	var day = date.getDate();
	
	if (day.length < 2) day = '0' + day;
	
	return day + ' ' + month + ' ' + year;
}

function getDateFromDisplay(date) {
	var month = months.indexOf(date.split(' ')[0]) + 1;
	var year = date.split(' ')[1];
	return year + '-' + month + '-1';
}

function getString(text) {
	return getString(text, '');
}

function getString(text, defaultText) {
	if (text == undefined || text == null || text == '') {
		return defaultText;
	}
	return text;
}

function loadRules() {
	$.ajax({
		method: "GET",
		url: "rule"
	}).done(function(response) {
		$('#section-2 .cards-list-container').remove();
		$('#section-2').prepend('<div class="cards-list-container"></div>');
		
		$(response).each(function(index, element) {
			$('#section-2 .cards-list-container').append(
				'<div class="card vacation">' +  
					'<div class="card-content">' + 
						'<input type="hidden" id="ruleId" value="' + element.ruleId + '" />' +
						'<div class="name-display">' + element.employeeName + '</div>' +
						'<div class="interval-display">' + formatDate(new Date(element.startDate)) + ' - ' + formatDate(new Date(element.endDate)) + '</div>' +
						'<div>Replacers: ' + element.replacersName + '</div>' +
					'</div>' +
					'<div class="card-buttons">' +
						'<i class="fa fa-trash-o" onclick="deleteRule(this);"></i>' + 
					'</div>' +
				'</div>');
		});
	});
	ruleTypeChanged();
	wizardNavigate(0);
}

function deleteRule(button) {
	var ruleId = $(button).parents(".card.vacation").find("#ruleId").val();
	$.ajax({
		url: "rule?ruleId=" + ruleId,
		method: "DELETE"
	}).always(function() {
		loadRules();
	});
}

function wizardNavigate(position) {
	$('.wizard-prev').show();
	$('.wizard-next').show();
	$('.wizard-save').show();

	var activeIdx = $('.wizard section:visible').index();
	if (position == 0) {
		activeIdx = 0;
	}
	$('.wizard section').hide();
	$('.wizard section').eq(activeIdx + position).show();
	
	activeIdx = $('.wizard section:visible').index();
	if (position == 0) {
		activeIdx = 0;
	}
	if (activeIdx == 0) {
		$('.wizard-prev').hide();
	}
	if ($('.wizard section').length == (activeIdx + 1)) {
		$('.wizard-next').hide();
	}else{
		$('.wizard-save').hide();
	}
}

function ruleTypeChanged() {
	$('.wizard section').not(':eq(0)').remove();
	var selected = $('#ruleType option:selected').val();
	if (selected == 'vacation') {
		$('.wizard').append(
			'<section style="display: none;">' +
			'<form id="ruleForm">' +
			'<input name="ruleType" type="hidden" value="1" />' +
		    '<h3>Vacation</h3>' +
		    'Start date:' +
		    '<input name="startDate" type="date">' +
		    'End date:' +
		    '<input name="endDate" type="date">' +
		    'Employee:' +
		    '<input name="employee" />' +
		    'Replacers:' +
		    '<select name="replacers" multiple="multiple" />' +
		    '</form>' +
		    '</section>');

		initializeEmployeeInput($('.wizard [name="employee"]'));
		initializeEmployeeInput($('.wizard [name="replacers"]'));
	}
	else if (selected == 'together') {
		$('.wizard').append(
			'<section style="display: none;">' +
		    '<h3>Work together</h3>' +
		    '</section>');
	}
}

function saveRule() {
	var data = {};
	$.merge($('#ruleForm').find('input'), $('#ruleForm').find('select')).each(function(idx, input) {
		var jInput = $(input);
		data[jInput.attr('name')] = jInput.val();
	});

	$.ajax({
		url: "rule",
		method: "PUT",
        contentType: "application/json",
		data: JSON.stringify(data),
        error: saveRuleErrorHandler,
        success: loadRules
	});
}

function saveRuleErrorHandler(request) {
	if (request.status == 417) {
		alert('Invalid data! Correct values!');
	}
	else {
		alert('General error! Try again!');
	}
}