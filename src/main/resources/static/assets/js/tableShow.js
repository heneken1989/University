$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})


$("#select_semeser").on("change",()=>{
	$("#form_change_semester").submit();
})


$("#select_subject_form").on("change",()=>{
	$("#form_change_semester").submit();
})
let currentlanguage=$(".btn_change_language").text()


let checkChange= document.querySelectorAll(".convert_week_day");
if(checkChange){
	check= currentlanguage == "English" ?1 :0
	checkChange.forEach(e=>{
	if(e.innerText==1){
		if("Việt nam")
		e.innerHTML= check==1 ?"Monday":"Thứ hai"
	}
	
	if(e.innerText==2){
		e.innerHTML=check==1 ?"Tuesday" :"Thứ ba"
	}
	if(e.innerText==3){
		e.innerHTML=check==1 ?"Wednesday":"Thứ tư"
	}
	
		if(e.innerText==4){
		e.innerHTML=check==1 ?"Thursday":"Thứ năm"
	}
	
		if(e.innerText==5){
		e.innerHTML=check==1 ?"friday":"Thứ sáu"
	}
	
		if(e.innerText==6){
		e.innerHTML=check==1 ?"Saturday":"Thứ bảy"
	}
})
}



const aTag = document.querySelectorAll(".change_page_href");
const urlParams = new URLSearchParams(window.location.search);
const myParam = urlParams.get('semester');
const subject_list = urlParams.get('subject');

aTag.forEach(a => {
    let text = a.innerHTML;
    let href = "/class/list"; 
   a.setAttribute("href", href);
    if (!myParam) {
        href += `?page=${text}`;
    } else {
        href += `?semester=${myParam}&subject=${subject_list}&page=${text}`;
    }

    a.setAttribute("href", href);
});


let currentPage= $("#next_page_btn").attr("data-s")
if(!myParam){
$("#next_page_btn").attr("href",`/class/list?page=${currentPage+1}`)
$("#back_page_btn").attr("href",`/class/list?page=${currentPage-1}`)
}else{
	$("#next_page_btn").attr("href",`/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+1}`)
	$("#back_page_btn").attr("href",`/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+-1}`)
}
