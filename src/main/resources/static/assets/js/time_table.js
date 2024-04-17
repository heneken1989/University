let form= $("#form_search_time_table");

const currentUrlTime = window.location.href;
const url = new URL(currentUrlTime);
const baseUrl = url.origin;

$("#select_week_search_time_table").on("change",()=>{
	form.submit();
});

$("#slect_semester_time_table").on("change",()=>{
		form.submit();
});



//js for attendance

let submitFormAttendance=(e)=>{
	let studentId=e.parentElement.parentElement.getAttribute("data_id");
	let status=e.value;
	let classId= e.parentElement.parentElement.parentElement.getAttribute("data_id");
	
let data={studentId,status};
$.post({
    url: `${baseUrl}/api/attendance/create/${classId}`,
    data: JSON.stringify(data),
    contentType: "application/json",
    success: () => {
        console.log("success");
    }
});

}


//show notify error attenadance
let errorHidden=document.getElementById("hidden_error_attend");
	if(errorHidden){
		console.log("thanh nien moi 1 ")
			Toastify({
  text: errorHidden.value,
  duration:2000,
   style: {
    background: "orange",
  },
  offset: {
    x: 200, 
    y: 100 
  },
}).showToast();
	}
	
//seacrh for attendance
let searchForm= $("#form_search_attendance");
$("#select_attendace_show_form").on("change",()=>{
searchForm.submit();
})

// check all  stuend attend 	

let list=document.querySelectorAll(".to_check_attend_all");
$("#check_all_attend_class").on("click",()=>{
	let status="attend"
	
list.forEach(e=>{
	e.checked=true
	let classId= e.parentElement.parentElement.parentElement.getAttribute("data_id");
	let studentId=e.parentElement.parentElement.getAttribute("data_id");
	let data={studentId,status};
	
$.post({
    url: `${baseUrl}/api/attendance/create/${classId}`,
    data: JSON.stringify(data),
    contentType: "application/json",
    success: () => {
        console.log("success");
    }
});

});
});
	