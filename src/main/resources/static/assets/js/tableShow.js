$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})


$("#select_semeser").on("change",()=>{
	$("#form_change_semester").submit();
})


$("#select_subject_form").on("change",()=>{
	$("#form_change_semester").submit();
})



let checkChange= document.querySelectorAll(".convert_week_day");
checkChange.forEach(e=>{
	if(e.innerText==2){
		e.innerHTML="Monday"
	}
	
	if(e.innerText==3){
		e.innerHTML="Tuesday"
	}
	if(e.innerText==4){
		e.innerHTML="Wednesday"
	}
	
		if(e.innerText==5){
		e.innerHTML="Thursday"
	}
	
		if(e.innerText==6){
		e.innerHTML="friday"
	}
	
		if(e.innerText==7){
		e.innerHTML="Saturday"
	}
})


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
