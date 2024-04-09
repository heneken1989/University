let form= $("#form_search_time_table");

$("#select_week_search_time_table").on("change",()=>{
	form.submit();
});

$("#slect_semester_time_table").on("change",()=>{
		form.submit();
});