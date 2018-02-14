$(document).ready(function() {
	$('nav li:eq(0) i').trigger('onclick');
	loadEmployees();
	loadStations();
	loadPrograms();
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
		type: jContent.find('[name="type-input"]:checked').val()
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
	var cardContent = 
		'<div class="card">' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-save" onClick="saveEmployee(this)"></i>' +
		'  </div>' +
		'  <div class="card-content' +
		'	<form id="edit-employee">' +
		'		<input type="hidden" name="id-input" value="' + jContent.find('input[name="id-input"]').val() + '" />' +
		'		<input type="text" name="name-input" placeholder="Name" value="' + jContent.find('.name-display').html() + '" />' +
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
				'	 <div class="name-display">' + element.name + '</div>' +
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
	});
}

function saveSettings() {
	var markers = {
		newPassword: $('#section-5 #new-password').val()
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
	$('#calendar-programs').dcalendar({format: 'dd MMMM yyyy'}).on('dateselected', function (e) {
		loadProgramsForDay(e.date);				
	});
}

function loadProgramsForDay(date) {
	$('#calendar-programs').hide();
	$('#day-programs').show();
	$('#day-programs .calendar-curr-month').html(date);
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