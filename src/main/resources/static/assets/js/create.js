let select_subject = document.querySelector(".select_subject_create");
 const link= "http://localhost:8081/api";
 let weekday;
 let start;
 let dstart;
 let dend;
 let subject;
 let slotday=[1,2,3,4,5,6,7,8,9,10,11,12]
 let credit;
 let creditAction;
 let result=[]
 let capacity;
 let startDay=document.getElementById("semester_start_create").value;
 let dendTheory;
 let dendAction;
 
 
 if($(".select_subject_create").val()!= 0){
	  let selectedOption = select_subject.options[select_subject.selectedIndex];
	   let checkType = selectedOption.getAttribute("data-check");
	    if(checkType=="FullSemester"){
	   $("#form_create_both").hide()
	   $("#form_create_one").hide()
	   
	   $("#form_create_both").show();
   }else{   
	  
	   $("#form_create_one").hide()
	    $("#form_create_one").show();
   }
 }
 
 
 let smId=$("#semester_start_create").attr("data-id"); 
 $("#semester_value_create").val(smId);
 // handle after select subject
select_subject.addEventListener('change', function(e) {
  let selectedOption = select_subject.options[select_subject.selectedIndex];
  let selectedId = selectedOption.getAttribute("data-id");
  let checkType = selectedOption.getAttribute("data-check");
 credit=selectedOption.getAttribute("data-credit");
 creditAction= selectedOption.getAttribute("data-creditac");
 
 
 
 $("#semester_value_create").val(e.target.value)
  $("#subject_value_create").val(e.target.value)
 $("#subject_hidden_creaet_both").val(e.target.value)
 
   if(checkType=="FullSemester"){
	   $("#form_create_both").hide()
	   $("#form_create_one").hide()
	   
	   $("#form_create_both").show();
   }else{   
	     $("#form_create_both").hide()
	   $("#form_create_one").hide()
	    $("#form_create_one").show();
   }
  $("#subject_hidden_creaet").val(selectedId);
  subject=selectedId
   $("#select_classtype_create_theory").parent().show();
  $("#input_capacity_create_both").parent().show();
  
  
  $("#select_classtype_create").parent().show();
  $("#input_capacity_create").parent().show();
  
   $("#select_startslot_create").attr("data-id",data)
  
});

//handle onchange capacity
$("#input_capacity_create").on('change',(e)=>{
	capacity=e.target.value 


})

$("#input_capacity_create_both").on('change',(e)=>{
	capacity=e.target.value 


})

//handle after select class type 
$("#select_classtype_create").on("change",(e)=>{
	
	let date =new Date(startDay);
    $("#select_startdate_create").parent().show();
    
   
 
    if($("#select_classtype_create").val()=="SecondHalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	
    let next6day = new Date(date);
    next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create").attr("min",min)
    $("#select_startdate_create").attr("max",max)
  
});





//handle after select start date
 $("#select_startdate_create").on("change",(e)=>{	
		$("#select_startslot_create").parent().show();
		dstart=e.target.value;
		console.log(dstart)
		let date = new Date(dstart);
		weekday=date.getDay()+1;
		$("#hidden_weekday_create").val(weekday);
		$("#select_enddate_create").parent().show();
		
		     let futureDate= setDay(date,$("#select_classtype_create").val())
     dend = futureDate.toISOString().split('T')[0];
     
		
		$("#select_enddate_create").val(dend);
		
// loai bo nhung tiet khong duoc lua chon
		slotday.forEach(x=>{
			if(x<6 && x+Number(credit)<=6 || x>6 && x<12&& x+Number(credit)<=12){
				result.push(x)
			}
		})
	
		let data= result.map(e=>`<option value="${e}">${e}</option>`).join()
		$("#select_startslot_create").html(`<option value="0"> select slot</option> ${data} `)
			if(weekday!=null){
			getAvailableRoom(capacity,weekday,start,dstart,dend)
		}
	})
       
//handle after select start slot
$("#select_startslot_create").on("change", () => {
    let selectedStartSlot = $("#select_startslot_create").val();
    start=selectedStartSlot;
    let credit = $("#select_startslot_create").attr("data-id"); 
	 $("#select_endslot_create").val(Number(selectedStartSlot)+Number(credit) );
	 $("#select_endslot_create").parent().show();
	 $("#select_room_create").parent().show();
	 getAvailableRoom(capacity,weekday,start,dstart,dend) 
})

//handle after slect room
$("#select_room_create").on("change", () => {
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	$("#select_teacher_create").parent().show();
	
	
})


//set semesterid for all 

$("#semester_value_create_both").val(smId)

//handle after select type for theory
$("#select_classtype_create_theory").on("change",e=>{
	$("#select_startdate_create_theory").parent().show();
	let date =new Date(startDay)
	 if(e.target.value=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	 let next6day = new Date(date);
	 next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create_theory").attr("min",min)
    $("#select_startdate_create_theory").attr("max",max)
})

//handle after select type for action
$("#select_classtype_create_action").on("change",e=>{
	$("#select_startdate_create_action").parent().show();
	let date =new Date(startDay)
	 if(e.target.value=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	 let next6day = new Date(date);
	 next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create_action").attr("min",min)
    $("#select_startdate_create_action").attr("max",max)
})



//dandle after select start day for theory
$("#select_startdate_create_theory").on("change",e=>{
	let date=new Date(e.target.value)
	$("#hidden_weekday_create_theory").val(date.getDay()+1);
	  let futureDate= setDay(date,$("#select_classtype_create_theory").val())
   let  endTheo = futureDate.toISOString().split('T')[0];
		$("#select_enddate_create_theory").val(endTheo);
		$("#select_enddate_create_theory").parent().show()
		
		let slotArray=[];
			slotday.forEach(x=>{
			if(x<6 && x+Number(credit-creditAction)<=6 || x>6 && x<12&& x+Number(credit-creditAction)<=12){
				slotArray.push(x)
			}
		})
				
let data= slotArray.map(e=>`<option value="${e}">${e}</option>`).join()
		$("#select_startslot_create_theory").html(`<option value="0"> select slot for theory</option> ${data}`)
		$("#select_startslot_create_theory").show()
})

//dandle after slect start day for action
$("#select_startdate_create_action").on("change",e=>{
	let date=new Date(e.target.value)
	$("#hidden_weekday_create_action").val(date.getDay()+1);
	
	  let futureDate= setDay(date,$("#select_classtype_create_action").val())
   let  endTheo = futureDate.toISOString().split('T')[0];
		$("#select_enddate_create_action").val(endTheo);
		$("#select_enddate_create_action").parent().show()
		
				let slotArray=[];
			slotday.forEach(x=>{
			if(x<6 && x+Number(creditAction)<=6 || x>6 && x<12&& x+Number(creditAction)<=12){
				slotArray.push(x)
			}
		})
				
let data= slotArray.map(e=>`<option value="${e}">${e}</option>`).join()
		$("#select_startslot_create_action").html(`<option value="0"> select slot for action</option> ${data}`)
		$("#select_startslot_create_action").parent().show()
})


//handle after select startslot for theory
$("#select_startslot_create_theory").on("change",e=>{
	$("#select_endslot_create_theory").val(Number(e.target.value)+Number(credit-creditAction) );
	 $("#select_endslot_create_theory").parent().show();
	 $("#select_room_create_theory").parent().show();
	 getAvailableRoomTheory(capacity,$("#hidden_weekday_create_theory").val(),e.target.value,$("#select_startdate_create_theory").val(),$("#select_enddate_create_theory").val(),"theory")
	
})

//handle after select startslot for action
$("#select_startslot_create_action").on("change",e=>{
	$("#select_endslot_create_action").val(Number(e.target.value)+Number(creditAction) );
	 $("#select_endslot_create_action").parent().show();
	 $("#select_room_create_action").parent().show();
	 getAvailableRoomTheory(capacity,$("#hidden_weekday_create_action").val(),e.target.value,$("#select_startdate_create_action").val(),$("#select_enddate_create_action").val(),"action")
	
})

//handle after select room theory
$("#select_room_create_theory").on("change",e=>{
	
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	 getAvailableTeacherTheory(subject,$("#hidden_weekday_create_theory").val(),$("#select_startslot_create_theory").val(),$("#select_startdate_create_theory").val(),$("#select_enddate_create_theory").val(),"theory")
	$("#select_teacher_create_theory").parent().show();
})

//handle after select room action
$("#select_room_create_theory").on("change",e=>{
	
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	 getAvailableTeacherTheory(subject,$("#hidden_weekday_create_action").val(),$("#select_startslot_create_action").val(),$("#select_startdate_create_action").val(),$("#select_enddate_create_action").val(),"action")
	$("#select_teacher_create_theory").parent().show();
})

//suport function
let getAvailableTeacher= async (subject,weekday,start,dstart,dend )=>{
	await $.get(`${link}/teacher/available?id=${subject}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		$("#select_teacher_create").html(`<option value="0"> select teacher</option> ${str}`);
  });
}
		
let getAvailableRoom= async (capacity,weekday,start,dstart,dend )=>{
	await $.get(`${link}/room/available?capacity=${capacity}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		$("#select_room_create").html(`<option value="0"> Select room</option> ${str}`);
  });
}

let getAvailableRoomTheory= async (capacity,weekday,start,dstart,dend,type )=>{
	await $.get(`${link}/room/available?capacity=${capacity}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		if(type="theory"){
			$("#select_room_create_theory").html(`<option value="0"> Select room for theory</option> ${str}`);
		}
		
		if(type="action"){
			$("#select_room_create_action").html(`<option value="0"> Select room for action</option> ${str}`);
		}
		
  });
}

let getAvailableTeacherTheory= async (subject,weekday,start,dstart,dend,type )=>{
	await $.get(`${link}/teacher/available?id=${subject}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		if(type="theory"){
			$("#select_teacher_create_theory").html(`<option value="0"> select teacher for theory</option> ${str}`);
		}
		
		if(type="action"){
			$("#select_teacher_create_action").html(`<option value="0"> select teacher for theory</option> ${str}`);
		}
		
  });
}


let setDay= (data,check)=>{
	if(check=="all"){
		return futureDate = new Date(data.getTime() + (15 * 7 * 24 * 60 * 60 * 1000));  
	}
	 if(check=="lhalf"){
		
		newData = new Date(data.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
		return futureDate = new Date(newData.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));  
	}
	 if(check=="fhalf"){
		return futureDate = new Date(data.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));  
	}
}