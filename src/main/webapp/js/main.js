$(document).ready(function() {
	$('nav li:eq(2) i').trigger('onclick');
	loadEmployees();
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
		id: jContent.find('#id-input').val(),
		name: jContent.find('#name-input').val(),
		note: jContent.find('#note-input').val(),
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
	var blackChecked = jContent.find('#type-input').val() == 'black' ? 'checked="checked"' : '';
	var blueChecked = jContent.find('#type-input').val() == 'blue' ? 'checked="checked"' : '';
	var greenChecked = jContent.find('#type-input').val() == 'green' ? 'checked="checked"' : '';
	var redChecked = jContent.find('#type-input').val() == 'red' ? 'checked="checked"' : '';
	var cardContent = 
		'<div class="card">' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-save" onClick="saveEmployee(this)"></i>' +
		'  </div>' +
		'  <div class="card-content' +
		'	<form id="edit">' +
		'		<input type="hidden" id="id-input" value="' + jContent.find('#id-input').val() + '" />' +
		'		<input type="text" id="name-input" placeholder="Name" value="' + jContent.find('.name-display').html() + '" />' +
		'		<textarea id="note-input" placeholder="Notes">' + jContent.find('.note-display').html().replace('<br>', '\n') + '</textarea>' +
		'		<div class="colors-select">' +
		'			<input type="radio" name="type-input" class="color black" value="black" ' + blackChecked + '>' +
		'			<input type="radio" name="type-input" class="color blue" value="blue" ' + blueChecked + '>' +
		'			<input type="radio" name="type-input" class="color green" value="green" ' + greenChecked + '>' +
		'			<input type="radio" name="type-input" class="color red" value="red" ' + redChecked + '>' +
		'		</div>' +
		'	</form>' +
		'  </div>' +
		'  <div class="card-buttons">' +
		'	<i class="fa fa-trash-o" onClick="deleteEmployee(' + jContent.find('#id-input').val() + ');"></i>' +
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
				'	 <input type="hidden" id="id-input" value="' + element.id + '" /> ' +
				'	 <input type="hidden" id="type-input" value="' + element.type + '" /> ' +
				'	 <div class="name-display">' + element.name + '</div>' +
				'    <div class="note-display">' + element.note.replace('\n', '<br/>') + '</div>' +
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
			'	<form id="new">' +
			'		<input type="text" id="name-input" placeholder="Name" />' +
			'		<textarea id="note-input" placeholder="Notes"></textarea>' +
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
		$('#section5 #new-password').val('');
	});
}