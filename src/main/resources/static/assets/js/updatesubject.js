 const currentUrlupdate = window.location.href;
 const urlupdate = new URL(currentUrlupdate);

const baseUrlupdate = urlupdate.origin;


$("#select_type_create_subject").on("change",()=>{
	let value= $("#select_type_create_subject").val();
	
	if(value=="BOTH"){
		$("#select_credit_proper").show();
	}else if(value==""){
		$("#select_credit_proper").hide();
		$("#credit_action_proper").hide();
	}else if(value="THEORY") {
	$("#select_credit_proper").show();
			$("#credit_action_proper").hide();
	
	}else{
		$("#select_credit_proper").show();
	}
		
})



$("#credit_create_subject").on("change", () => {
	let value = $("#credit_create_subject").val();
	//$("#credit_action_proper").show();
	
	let actionValue = $("#creditAction_create_subject_new").val();
	let str = "";

	if ($("#select_type_create_subject").val() == "BOTH" && value == 1) {
		alert("cannot choose 1");
		$("#credit_action_proper").show();
		str += `<option value="1">Select credit for action</option>`;
	} else if($("#select_type_create_subject").val() == "BOTH" && actionValue == 0){
		
		alert("please choose creditAction");
		$("#credit_action_proper").show();
		str += `<option value="1">Select credit for action</option>`;
	}else{
		$("#credit_action_proper").hide();
		str = `<option value="0">0</option>`;
	}

	for (let i = 0; i < value - 1; i++) {
		str += `<option value="${i + 1}">${i + 1}</option>`;
	}

	$("#creditAction_create_subject_new").html(str);
});



$("#select_level_subject").on("change",()=>{
	$("#form_sort_level").submit()
})