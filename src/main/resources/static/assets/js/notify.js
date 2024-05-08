// notify in holiday inndex


let errorHiddenHoliday=document.getElementById("error_index_holiday");
	if(errorHiddenHoliday){
			Toastify({
  text: errorHiddenHoliday.value,
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
	
let successHidden=document.getElementById("success_index_holiday");
	if(successHidden){
			Toastify({
  text: successHidden.value,
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