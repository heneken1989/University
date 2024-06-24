$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})


$("#select_semeser").on("change",()=>{
	$("#form_change_semester").submit();
})
$("#select_user_type").on("change",()=>{
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
const status_subject=urlParams.get('status');
const level=urlParams.get('level');

aTag.forEach(a => {
    let text = a.innerHTML;
    let href = "/admin/class/list"; 
   a.setAttribute("href", href);
    if (!myParam) {
        href += `?page=${text}`;
    } else {
        href += `?semester=${myParam}&subject=${subject_list}&page=${text}&status=${status_subject}`;
    }

    a.setAttribute("href", href);
});


let currentPage= $("#next_page_btn").attr("data-s")
if(!myParam){
$("#next_page_btn").attr("href",`/web/class/list?page=${currentPage+1}`)
$("#back_page_btn").attr("href",`/web/class/list?page=${currentPage-1}`)
}else{
	$("#next_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+1}`)
	$("#back_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+-1}`)
}



const aTagAdmin =  document.querySelectorAll(".change_page_href_admin_user");
const myParamAdmin = urlParams.get('type');
const user_list_type = $("#select_user_type").val();
console.log("UUUUUU"+user_list_type);
/*change page admin user*/
aTagAdmin.forEach(a => {
    let text = a.innerHTML;
    let href = "/admin/user/list"; 
   a.setAttribute("href", href);
    if (!myParamAdmin) {
        href += `?page=${text}`;
    } else {
        href += `?type=${user_list_type}&page=${text}`;
    }

    a.setAttribute("href", href);
});


if(!myParam){
$("#next_page_btn").attr("href",`/web/class/list?page=${Number(currentPage)+1}`)
$("#back_page_btn").attr("href",`/web/class/list?page=${Number(currentPage)-1}`)
}else{
	$("#next_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+1}`)
	$("#back_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+-1}`)
}


/*next corent page admin_user*/
let currentPageAdminUser= $("#next_page_btn").attr("data-s")
if(!myParamAdmin){
$("#next_page_btn").attr("href",`/admin/user/list?page=${Number(currentPageAdminUser)+1}`)
$("#back_page_btn").attr("href",`/admin/user/list?page=${Number(currentPageAdminUser)-1}`)
}else{
	$("#next_page_btn").attr("href",`/admin/user/list?semester=${myParamAdmin}?type=${user_list_type}&page=${Number(currentPageAdminUser)+1}`)
	$("#back_page_btn").attr("href",`/admin/user/list?semester=${myParamAdmin}?type=${user_list_type}&page=${Number(currentPageAdminUser)+-1}`)
}



/*next corent page admin_user*/
let currentPageSubject= $("#next_page_btn_subject").attr("data-s")
if(!level){
$("#next_page_btn_subject").attr("href",`/admin/subject/list?page=${Number(currentPageSubject)+1}`)
$("#back_page_btn_subject").attr("href",`/admin/subject/list?page=${Number(currentPageSubject)-1}`)
}else{
	$("#next_page_btn_subject").attr("href",`/admin/subject/list?level=${level}&page=${Number(currentPageSubject)+1}`)
	$("#back_page_btn_subject").attr("href",`/admin/subject/list?level=${level}&page=${Number(currentPageSubject)+-1}`)
}




//set time eror
  setTimeout(function() {
        var errorDiv = document.getElementById('error');
        if (errorDiv) {
            errorDiv.innerHTML = '';
        }
    }, 5000);
    
    
    //js for create holiday add to table show
let showSelectStatus= false;
$("#toggle_select_class_create_holiday").on("change",()=>{
	showSelectStatus=!showSelectStatus
	
	if(showSelectStatus){
		$("#wraper_class_selece_create_hiliday").show();
	}else{
		$("#wraper_class_selece_create_hiliday").hide();
	}
})



//js for page update status
$("#btn_show_form_update").on("click",()=>{
	let select= document.querySelectorAll(".select_class_update");
	let str="";
	select.forEach(e=>{
		if(e.checked){
				str+=`<input name="class[]" readonly type="hidden"   value="${e.getAttribute("value")}">`
		}
	
	});
	$("#div_holver_list_class").empty();
	$("#div_holver_list_class").html(str);
})

//vuong

const aTagPayment = document.querySelectorAll(".change_page_href_admin_payment");
aTagPayment.forEach(a => {
    let text = a.innerHTML;
    let href = "/admin/adminpayment"; 
   a.setAttribute("href", href);
    if (!myParamAdmin) {
        href += `?page=${text}`;
    } else {
        href += `?type=${user_list_type}&page=${text}`;
    }

    a.setAttribute("href", href);
});


let currentPagepayment= $("#next_page_btn_admin_payment").attr("data-s")
if(!level){
$("#next_page_btn_admin_payment").attr("href",`/admin/adminpayment?page=${Number(currentPagepayment)+1}`)
$("#back_page_btn_admin_payment").attr("href",`/admin/adminpayment?page=${Number(currentPagepayment)-1}`)
}else{
	$("#next_page_btn_admin_payment").attr("href",`/admin/adminpayment?level=${level}&page=${Number(currentPagepayment)+1}`)
	$("#back_page_btn_admin_payment").attr("href",`/admin/adminpayment?level=${level}&page=${Number(currentPagepayment)+-1}`)
}

const aTagDetails = document.querySelectorAll(".change_page_href_web_details");
aTagDetails.forEach(a => {
    let text = a.innerHTML;
    let href = "/web/payment/details"; 
   a.setAttribute("href", href);
    if (!myParamAdmin) {
        href += `?page=${text}`;
    } else {
        href += `?type=${user_list_type}&page=${text}`;
    }

    a.setAttribute("href", href);
});

let currentPagePaymentDetails= $("#next_page_btn_web_details").attr("data-s")
if(!level){
$("#next_page_btn_web_details").attr("href",`/web/payment/details?page=${Number(currentPagePaymentDetails)+1}`)
$("#back_page_btn_web_details").attr("href",`/web/payment/details?page=${Number(currentPagePaymentDetails)-1}`)
}else{
	$("#next_page_btn_web_details").attr("href",`/web/payment/details?level=${level}&page=${Number(currentPagePaymentDetails)+1}`)
	$("#back_page_btn_admin_payment").attr("href",`/web/payment/details?level=${level}&page=${Number(currentPagePaymentDetails)+-1}`)
}

const aTagNews = document.querySelectorAll(".change_page_href_admin_news");
aTagNews.forEach(a => {
    let text = a.innerHTML;
    let href = "/admin/news/index"; 
   a.setAttribute("href", href);
    if (!myParamAdmin) {
        href += `?page=${text}`;
    } else {
        href += `?type=${user_list_type}&page=${text}`;
    }

    a.setAttribute("href", href);
});

let currentPageNews= $("#next_page_btn_admin_news").attr("data-s")
if(!level){
$("#next_page_btn_admin_news").attr("href",`/admin/news/index?page=${Number(currentPageNews)+1}`)
$("#back_page_btn_admin_news").attr("href",`/admin/news/index?page=${Number(currentPageNews)-1}`)
}else{
	$("#next_page_btn_admin_news").attr("href",`/admin/news/index?level=${level}&page=${Number(currentPageNews)+1}`)
	$("#back_page_btn_admin_news").attr("href",`/admin/news/index?level=${level}&page=${Number(currentPageNews)+-1}`)
}




//chuyeenr trang student teacher mark
const aTagMark =  document.querySelectorAll(".change_page_href_mark_student");
const myParamMark = urlParams.get('type');
console.log("UUUUUU"+user_list_type);
/*change page admin user*/
aTagMark.forEach(a => {
    let text = a.innerHTML;
    let href = "/web/mark/getMarkSubject"; 
   a.setAttribute("href", href);
    if (!myParamMark) {
        href += `?page=${text}`;
    } else {
        href += `?type=${user_list_type}&page=${text}`;
    }

    a.setAttribute("href", href);
});


if(!myParam){
$("#next_page_btn").attr("href",`/web/class/list?page=${Number(currentPage)+1}`)
$("#back_page_btn").attr("href",`/web/class/list?page=${Number(currentPage)-1}`)
}else{
	$("#next_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+1}`)
	$("#back_page_btn").attr("href",`/web/class/list?semester=${myParam}&subject=${subject_list}&page=${Number(currentPage)+-1}`)
}


/*next corent page admin_user*/
let currentPageAdminMark= $("#next_page_btn").attr("data-s")
if(!myParamMark){
$("#next_page_btn").attr("href",`/web/mark/getMarkSubject?page=${Number(currentPageAdminUser)+1}`)
$("#back_page_btn").attr("href",`/web/mark/getMarkSubject?page=${Number(currentPageAdminUser)-1}`)
}else{
	$("#next_page_btn").attr("href",`/web/mark/getMarkSubject?semester=${myParamMark}?type=${user_list_type}&page=${Number(currentPageAdminUser)+1}`)
	$("#back_page_btn").attr("href",`/web/mark/getMarkSubject?semester=${myParamMark}?type=${user_list_type}&page=${Number(currentPageAdminUser)+-1}`)
}



/*next corent page admin_user*/
let currentPageMark= $("#next_page_btn_subject").attr("data-s")
if(!level){
$("#next_page_btn_subject").attr("href",`/web/mark/getMarkSubject?page=${Number(currentPageMark)+1}`)
$("#back_page_btn_subject").attr("href",`/web/mark/getMarkSubject?page=${Number(currentPageMark)-1}`)
}else{
	$("#next_page_btn_subject").attr("href",`/web/mark/getMarkSubject?level=${level}&page=${Number(currentPageMark)+1}`)
	$("#back_page_btn_subject").attr("href",`/web/mark/getMarkSubject?level=${level}&page=${Number(currentPageMark)+-1}`)
}


