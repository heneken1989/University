$(document).ready(function() {
	var classSelect = $('#classId');
	var markTable = $('#markTable tbody');
	var exportButton = $('#exportButton');
	var classForm = $('#classForm');
	$.get("/api/public/class/classes")
		.done(function(data) {
			classSelect.empty();
			data.forEach(function(classItem) {
				classSelect.append(new Option(classItem.name, classItem.id));
			});
			var storedClassId = localStorage.getItem('selectedClassId');
			if (storedClassId) {
				classSelect.val(storedClassId);
				fetchMarks(firstClassId);
			} else if (data.length > 0) {
				var firstClassId = data[0].id;
				classSelect.val(firstClassId);
			}

		})
		.fail(function() {
			alert("Failed to load classes.");
		});
	classSelect.change(function() {
		var classId = $(this).val();
		localStorage.setItem('selectedClassId', classId);
		$(classForm).submit();
		//$('#classId').val(classId);
		//fetchMarks(firstClassId);
	});

	function fetchMarks(classId) {
		$.get("/web/mark/getMarkSubject?classId=" + classId, function(data) {
			console.log(data);
			$('#markTable').html(data);
			//$('#classId').val(classId);
		});
	}

	exportButton.click(function(e) {
		e.preventDefault();
		var selectedClassId = classSelect.val();
		window.location.href = "/web/mark/export?classId=" + selectedClassId;
	});

	function showToast(message, title, type) {
		var toastTemplate = $('#toastTemplate').clone();
		toastTemplate.attr('id', ''); // Clear the ID to avoid duplicates
		toastTemplate.find('.toast-title').text(title);
		toastTemplate.find('.toast-body').text(message);

		switch (type) {
			case 'success':
				toastTemplate.find('.toast-header').addClass('bg-success text-white');
				break;
			case 'error':
				toastTemplate.find('.toast-header').addClass('bg-danger text-white');
				break;
			case 'warning':
				toastTemplate.find('.toast-header').addClass('bg-warning text-dark');
				break;
			case 'info':
			default:
				toastTemplate.find('.toast-header').addClass('bg-info text-white');
				break;
		}

		$('#toastContainer').append(toastTemplate);
		toastTemplate.toast('show');

		toastTemplate.on('hidden.bs.toast', function() {
			$(this).remove();
		});
	}

	//set all style

	$(document).ready(function() {
		$('#markForm').submit(function(event) {
			event.preventDefault(); // Prevent the default form submission

			let allValid = true; // Biến để theo dõi tính hợp lệ của tất cả các trường điểm

			// Lặp qua tất cả các ô nhập điểm
			$('input[name="marks[]"]').each(function() {
				const mark = $(this).val().trim(); // Lấy giá trị và loại bỏ khoảng trắng
				const markValue = parseFloat(mark);

				if (mark === '' || isNaN(markValue) || markValue < 0 || markValue > 100) {
					// Nếu không có giá trị hoặc giá trị không hợp lệ, hiển thị thông báo lỗi
					$(this).siblings('.feedback').show();
					allValid = false;
				} else {
					// Nếu có giá trị hợp lệ, ẩn thông báo lỗi
					$(this).siblings('.feedback').hide();
				}
			});

			// Nếu tất cả các trường đều hợp lệ, tiến hành gửi form
			if (allValid) {
				const marks = $('input[name="marks[]"]').map(function() { return parseFloat(this.value); }).get();
				const style = $('.mark-style').val();
				const studentIds = $('input[name="studentIds"]').val().split(',').map(function(id) { return parseInt(id.trim(), 10); }); // Trim and parse each ID
				const classId = parseInt($('input[name="classId"]').val(), 10);
				const subjectId = parseInt($('input[name="subjectId"]').val(), 10);

				const markSubjectCreateDtoList = marks.map((mark, index) => {
					return {
						studentId: studentIds[index],
						classId: classId,
						subjectId: subjectId,
						mark: mark,
						style: style
					};
				});

				console.log("marks", marks, "styles", style, "studentIds", studentIds, "classId", classId, "subjectId", subjectId);

				$.ajax({
					url: $(this).attr('action'),
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(markSubjectCreateDtoList),
					success: function(response) {
						console.log('Success:', response);
						window.location.href = "/admin/mark/list";
					},
					error: function(xhr, status, error) {
						console.error('Error:', error);
					}
				});
			}
		});
	});
	//mark admin
	$('#select_semeser').change(function() {
		var selectedSemester = $(this).val();
		window.location.href = '/admin/mark/list?semesterId=' + selectedSemester;
	});
});